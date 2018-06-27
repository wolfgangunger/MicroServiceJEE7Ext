/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base.control;

import com.ungerw.ms.business.base.entity.AbstractEntity;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * use this class if you don't want to extend AbstractDataService - for simple
 * entities where no DataService is required, maybe just a delete
 *
 * @author UNGERW
 *
 * TransactionAttributeType was changed to REQUIRED, so this service can be used
 * from a transactionless BF
 */
@Stateless
//@TransactionAttribute(TransactionAttributeType.MANDATORY)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GenericDataService {

    //private final Logger logger = Logger.getLogger(GenericDataService.class.getName());

    @PersistenceContext(name = "atg")
    private EntityManager em;

    /**
     * 
     * @param <T>
     * @param id
     * @param entityClass
     * @return 
     */
    public <T extends AbstractEntity> AbstractEntity find(BigInteger id, Class<T> entityClass) {
        return em.find(entityClass, id);
    }

 
    /**
     * 
     * @param <T>
     * @param entity 
     */
    public <T extends AbstractEntity> void delete(T entity) {
        T pers = (T)find(BigInteger.ONE, entity.getClass());
        em.remove(pers);

    }


    /**
     * 
     * @param <T>
     * @param id
     * @param entityClass 
     */
    public <T extends AbstractEntity> void delete(BigInteger id, Class<T> entityClass) {
        T pers = (T) find(id, entityClass);
        em.remove(pers);
    }

    /**
     * 
     * @param <T>
     * @param entity 
     */
    public <T extends AbstractEntity> void persist(T entity) {
        em.persist(entity);
    }

    
    /**
     * 
     * @param <T>
     * @param entity
     * @return 
     */
    public <T extends AbstractEntity> AbstractEntity merge(T entity) {
        return em.merge(entity);
    }

}
