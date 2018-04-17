package com.mycompany.services.userProfile;

import com.mycompany.model.Personel;
import com.mycompany.userProfile.dao.UserprofileDao;
import com.mycompany.utill.filter.JWTTokenNeeded;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("profile")
public class UserProfile {

    @Inject
    private Logger logger;

    @Inject
    private UserprofileDao userprofileDao;

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

    @GET
    @Path("/{id}")
    @JWTTokenNeeded
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadById(@PathParam("id") int Id) throws Exception {
        try {
            logger.log(Level.INFO, "By id ");
            Personel personel;
            personel = this.userprofileDao.findProfileById(Id);
            return Response.status(200).entity(personel).build();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new WebApplicationException(exc.getMessage());
        }

    }


    @GET
    @Path("/qury")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadByNamedQuery() throws Exception {
        try {
            logger.log(Level.INFO, "By id ");
            String findAll = "Personel.findAll";
            Object[] k = new Object[0];
            List<Personel> personelList = this.userprofileDao.findProfileByNamedQuery(findAll, k);
            return Response.status(200).entity(personelList).build();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new WebApplicationException(exc.getMessage());
        }

    }



}
