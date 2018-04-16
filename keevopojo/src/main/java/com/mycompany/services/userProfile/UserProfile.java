package com.mycompany.services.userProfile;

import com.mycompany.model.Personel;
import com.mycompany.userProfile.dao.UserprofileDao;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
   private void init()
     {
      this.userprofileDao.setEm(this.em);
      }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
     public List<Personel> loadAll() throws Exception
     {
         logger.log(Level.INFO,"kevol");
         List<Personel>personelList = new ArrayList<>();
         personelList =this.userprofileDao.findAllprofile();
         return personelList;
      }


}
