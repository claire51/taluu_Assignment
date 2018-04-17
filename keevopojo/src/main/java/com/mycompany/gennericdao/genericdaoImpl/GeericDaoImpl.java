package com.mycompany.gennericdao.genericdaoImpl;

import com.mycompany.gennericdao.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GeericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    private final Class<T> persistentClass;

    @PersistenceContext()
    private EntityManager em;

    private Session session;


    public void setEm(EntityManager em) {
        this.em = em;
        this.session = ((Session) this.em.getDelegate());
    }

    public EntityManager getEm() {
        return this.em;
    }

    public Session getSession() {
        return this.session;
    }

    public GeericDaoImpl() {
        this.persistentClass = ((Class) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public GeericDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }


    public Class<T> getEntityClass() {
        return this.persistentClass;
    }


    public T save(T entity)
            throws Exception {
        entity = this.em.merge(entity);
        return entity;
    }


    public void delete(T entity)
            throws Exception {
        this.em.remove(entity);
    }


    public void deleteById(ID id)
            throws Exception {
        T entity = this.em.find(this.persistentClass, id);
        if (entity != null) {
            this.em.remove(entity);
        }
    }


    public void delete(ID[] ids)
            throws Exception {
        int size = ids.length;

        for (int idx = 0; idx < size; idx++) {
            if (ids[idx] != null) {
                T entity = this.em.find(this.persistentClass, ids[idx]);
                if (entity != null) {
                    this.em.remove(entity);
                }
            }
        }
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(this.persistentClass));
        return em.createQuery(cq).getResultList();
    }


    public T findById(ID id) {
        T result = this.em.find(this.persistentClass, id);
        return result;
    }


    public List<T> findByNamedQuery(String name, Object... params) {
        Query query = this.em.createNamedQuery(name);
try {
    for (int i = 0; i < params.length; i++) {
        query.setParameter(i + 1, params[i]);
    }
}catch(Exception h){h.printStackTrace();}
        List<T> result = query.getResultList();
        return result;
    }


    public List<T> findByNamedQuery(String name, Map<String, ? extends Object> params) {
        Query query = this.em.createNamedQuery(name);

        for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
            query.setParameter((String) param.getKey(), param.getValue());
        }

        Object result = query.getResultList();
        return (List<T>) result;
    }


    public int countAll() {
        return countByCriteria(new Criterion[0]);
    }


    public int countByExample(T exampleInstance) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());
        crit.setProjection(Projections.rowCount());
        crit.add(Example.create(exampleInstance));

        return ((Integer) crit.list().get(0)).intValue();
    }


    public List<T> findByExample(T exampleInstance) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());
        List<T> result = crit.list();
        return result;
    }


    public List<T> findByCriteria(int firstResult, int maxResults, ProjectionList projectionList, List<Criterion> criterion, List<String> orderBy) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());

        if (projectionList != null) {
            crit.setProjection(projectionList);
        }
        for (Criterion c : criterion) {
            crit.add(c);
        }

        for (String order : orderBy) {
            crit.addOrder(Order.asc(order));
        }

        if (firstResult > 0) {
            crit.setFirstResult(firstResult);
        }

        if (maxResults > 0) {
            crit.setMaxResults(maxResults);
        }


        Object result = crit.list();

        return (List<T>) result;
    }


    public List<T> findByCriteria(int firstResult, int maxResults, ProjectionList projectionList, Map<String, String> aliases, List<Criterion> criterion, List<String> orderBy) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());

        if (aliases != null) {
            for (Map.Entry<String, String> alias : aliases.entrySet()) {
                crit.createAlias((String) alias.getKey(), (String) alias.getValue());
            }
        }
        if (projectionList != null) {
            crit.setProjection(projectionList);
        }
        for (Criterion c : criterion) {
            crit.add(c);
        }

        if (orderBy != null) {
            for (String order : orderBy) {
                crit.addOrder(Order.asc(order));
            }
        }
        if (firstResult > 0) {
            crit.setFirstResult(firstResult);
        }

        if (maxResults > 0) {
            crit.setMaxResults(maxResults);
        }


        Object result = crit.list();

        return (List<T>) result;
    }


    public List<T> findByCriteria(int firstResult, int maxResults, ProjectionList projectionList, List<Criterion> criterion, Map<String, String> orderBy) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());

        if (projectionList != null) {
            crit.setProjection(projectionList);
        }
        for (Criterion c : criterion) {
            crit.add(c);
        }

        for (Map.Entry<String, String> order : orderBy.entrySet()) {
            if (((String) order.getValue()).equals("desc")) {
                crit.addOrder(Order.desc((String) order.getKey()));
            } else {
                crit.addOrder(Order.asc((String) order.getKey()));
            }
        }
        if (firstResult > 0) {
            crit.setFirstResult(firstResult);
        }

        if (maxResults > 0) {
            crit.setMaxResults(maxResults);
        }


        Object result = crit.list();

        return (List<T>) result;
    }


    public List<T> findByCriteria(Criterion... criterion) {
        return findByCriteria(-1, -1, criterion);
    }

    public List<T> findByCriteria(int firstResult, int maxResults, Criterion... criterion) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());

        for (Criterion c : criterion) {
            crit.add(c);
        }

        if (firstResult > 0) {
            crit.setFirstResult(firstResult);
        }

        if (maxResults > 0) {
            crit.setMaxResults(maxResults);
        }

        Object result = crit.list();
        return (List<T>) result;
    }


//public List<T> findByCriteria(int firstResult, int maxResults, Map<String, String> aliases, Criterion... criterion)
//           {
//           Session session = (Session)this.em.getDelegate();
//        Criteria crit = session.createCriteria(getEntityClass());
//
//          for (Object localObject1 = aliases.entrySet().iterator(); ((Iterator)localObject1).hasNext();) {
//            alias = (Map.Entry)((Iterator)localObject1).next();
//               crit.createAlias((String)alias.getKey(), (String)alias.getValue());
//                 }
//
//         Object[] localObject1 = criterion;Map.Entry<String, String> alias = localObject1.length;
//         for (Map.Entry<String, String> localEntry1 = 0; localEntry1 < alias; localEntry1++) {
//             Criterion c = localObject1[localEntry1];
//                 crit.add(c);
//                 }
//
//           if (firstResult > 0) {
//             crit.setFirstResult(firstResult);
//                 }
//
//          if (maxResults > 0) {
//              crit.setMaxResults(maxResults);
//                 }
//
//           Object result = crit.list();
//        return (List<T>)result;
//           }


    public int countByCriteria(Criterion... criterion) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());
        crit.setProjection(Projections.rowCount());

        for (Criterion c : criterion) {
            crit.add(c);
        }

        return ((Long) crit.list().get(0)).intValue();
    }


    public int countByCriteria(Map<String, String> aliases, List<Criterion> criterion) {
        Session session = (Session) this.em.getDelegate();
        Criteria crit = session.createCriteria(getEntityClass());
        crit.setProjection(Projections.rowCount());

        for (Map.Entry<String, String> alias : aliases.entrySet()) {
            crit.createAlias((String) alias.getKey(), (String) alias.getValue());
        }

        for (Criterion c : criterion) {
            crit.add(c);
        }

        return ((Long) crit.list().get(0)).intValue();
    }


}








