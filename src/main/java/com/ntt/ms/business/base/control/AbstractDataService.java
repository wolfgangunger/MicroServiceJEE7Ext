/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.base.control;

import com.ntt.ms.business.base.entity.AbstractEntity;
import com.ntt.ms.business.base.entity.BusinessEntity;
import java.math.BigInteger;

import javax.persistence.EntityManager;


import java.util.Date;

/**
 * superclass for all data services each entity object will have its own
 * dataservice for CRUD and more implement a EntityNameDataService and extend
 * this class. use TransactionAttributeType.MANDATORY for all DataServices thus
 * you will get CRUD functionality for free. further methods (find ...) must be
 * implemented
 *
 * @author UNGERW
 * @param <T> entity class
 */
public abstract class AbstractDataService<T extends AbstractEntity> {

    protected abstract Class<T> getEntityClass();

    protected abstract EntityManager getEnityManager();

    /**
     *
     * @param id
     * @return
     */
    public T find(BigInteger id) {
        return getEnityManager().find(this.getEntityClass(), id);
    }

    /**
     * delete oject by passed oject
     *
     * @param entity
     */
    public void delete(T entity) {
        T pers = find(entity.getId());
        getEnityManager().remove(pers);
    }

    /**
     * delete object by passed ID
     *
     * @param id
     */
    public void delete(BigInteger id) {
        T pers = find(id);
        getEnityManager().remove(pers);
    }

    /**
     *
     * @param entity
     */
    public void persist(T entity) {
        if (entity instanceof BusinessEntity) {
            ((BusinessEntity) entity).setCreated(new Date());
        }
        getEnityManager().persist(entity);
    }

    /**
     *
     * @param entity
     * @return
     */
    public T merge(T entity) {
        if (entity instanceof BusinessEntity) {
            ((BusinessEntity) entity).setUpdated(new Date());
        }
        return getEnityManager().merge(entity);
    }

    /**
     *
     * @return
     */
    public abstract T create();

}
