package com.mycompany.userProfile.dao.daoImpl;

import com.mycompany.gennericdao.genericdaoImpl.GeericDaoImpl;
import com.mycompany.model.Personel;
import com.mycompany.userProfile.dao.UserprofileDao;

import java.util.List;

public class UserProfileImpl extends GeericDaoImpl<Personel, Long> implements UserprofileDao {


    @Override
    public List<Personel> findAllprofile() {
        return findAll();
    }
}

