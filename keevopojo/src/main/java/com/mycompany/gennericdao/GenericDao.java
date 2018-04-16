package com.mycompany.gennericdao;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract  interface GenericDao<T ,ID extends Serializable> {


    public abstract void setEm(EntityManager paramEntityManager);

    public abstract Session getSession();
    public abstract EntityManager getEm();

    public abstract Class<T> getEntityClass();

    public abstract T findById(ID paramID);

    public abstract List<T> findByNamedQuery(String paramString, Object... paramVarArgs);

    public abstract List<T> findByNamedQuery(String paramString, Map<String, ? extends Object> paramMap);

    public abstract T save(T paramT)
            throws Exception;

    public abstract void delete(T paramT)
            throws Exception;

    public abstract void deleteById(ID paramID)
            throws Exception;

    public abstract void delete(ID[] paramArrayOfID)
            throws Exception;

    public abstract List<T> findAll();

    public abstract int countAll();

    public abstract int countByExample(T paramT);

    public abstract List<T> findByExample(T paramT);
    public abstract List<T> findByCriteria(int paramInt1, int paramInt2, ProjectionList paramProjectionList, List<Criterion> paramList, List<String> paramList1);

    public abstract List<T> findByCriteria(int paramInt1, int paramInt2, ProjectionList paramProjectionList, List<Criterion> paramList, Map<String, String> paramMap);

    public abstract List<T> findByCriteria(Criterion... paramVarArgs);

    public abstract List<T> findByCriteria(int paramInt1, int paramInt2, Criterion... paramVarArgs);

//    public abstract List<T> findByCriteria(int paramInt1, int paramInt2, Map<String, String> paramMap, Criterion... paramVarArgs);

    public abstract int countByCriteria(Criterion... paramVarArgs);

    public abstract int countByCriteria(Map<String, String> paramMap, List<Criterion> paramList);

    public abstract List<T> findByCriteria(int paramInt1, int paramInt2, ProjectionList paramProjectionList, Map<String, String> paramMap, List<Criterion> paramList, List<String> paramList1);


}

