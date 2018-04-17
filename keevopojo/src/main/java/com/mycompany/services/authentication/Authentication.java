package com.mycompany.services.authentication;

import com.mycompany.model.coredb.Personel;
import com.mycompany.model.userprofile.Login;
import com.mycompany.userProfile.dao.UserprofileDao;
import com.mycompany.utill.security.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Stateless
@Path("authenticate")
@Produces(APPLICATION_JSON)
public class Authentication {

    @Inject
    private Logger logger;
    @Inject
    private UserprofileDao userprofileDao;
    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;
    @PersistenceContext
    private EntityManager em;
    @PostConstruct
    private void init() {
        this.userprofileDao.setEm(this.em);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadAll() throws Exception {
        try {
            logger.log(Level.INFO, "kevol");
            List<Personel> personelList;
            personelList = this.userprofileDao.findAllprofile();
            return Response.status(200).entity(personelList).build();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new WebApplicationException(exc.getMessage());
        }
    }

    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String login,
                                     @FormParam("password") String password) {


        try {

            logger.info("#### login/password : " + login+ "/" + password);

            // Authenticate the user using the credentials provided
            authenticate(login,password);

            // Issue a token for the user
            String token = issueToken(login);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String login, String password) throws Exception {

        logger.log(Level.INFO, "By id ");
        String findAll = "Personel.findByPassword";
        Object[] k =  new Object[] { login, password};

        List<Personel> personelList = this.userprofileDao.findProfileByNamedQuery(findAll, k);
        Personel personel;
        personel= personelList.get(0);
//        query.setParameter("password", PasswordUtils.digestPassword(password));
        if (personel == null)
            throw new SecurityException("Invalid user/password");
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }
    @SuppressWarnings("Since15")
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
