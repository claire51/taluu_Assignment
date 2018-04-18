package com.mycompany.userProfile.dao.daoImpl;

import com.mycompany.gennericdao.genericdaoImpl.GeericDaoImpl;
import com.mycompany.model.coredb.Personel;
import com.mycompany.userProfile.dao.UserprofileDao;

import java.util.List;

public class UserProfileImpl extends GeericDaoImpl<Personel, Integer> implements UserprofileDao {


    @Override
    public List<Personel> findAllprofile() {
        return findAll();
    }

    @Override
    public Personel findProfileById(int Id) {
        return findById(Id);
    }

    @Override
    public List<Personel> findProfileByNamedQuery(String paramString, Object... paramVarArgs) {
        return findByNamedQuery(paramString, paramVarArgs);
    }
}

