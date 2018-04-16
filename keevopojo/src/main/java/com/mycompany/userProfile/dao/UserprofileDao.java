package com.mycompany.userProfile.dao;

import com.mycompany.gennericdao.GenericDao;
import com.mycompany.model.Personel;


import javax.ejb.Local;
import java.util.List;

@Local
public abstract interface UserprofileDao  extends GenericDao<Personel, Integer>
{
   public abstract  List<Personel> findAllprofile();
   public  abstract  Personel findProfileById(int Id);
   public  abstract  List<Personel> findProfileByNamedQuery(String paramString, Object... paramVarArgs);

}
