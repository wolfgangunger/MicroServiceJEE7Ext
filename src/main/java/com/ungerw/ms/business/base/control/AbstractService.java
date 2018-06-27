/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base.control;


import com.ungerw.ms.business.base.entity.AbstractEntity;
import java.math.BigInteger;

/**
 * super class for all entity orientated services create EntityNameService and
 * extend this class, class knows its DataService, thus CRUD methods use
 * TransactionAttributeType.REQUIRED for all Services
 *
 * @author UNGERW
 * @param <T> entity class
 */
public abstract class AbstractService<T extends AbstractEntity> {
    
    protected abstract AbstractDataService<T> getAbstractDataService();

    /**
     * find entity by id
     *
     * @param id
     * @return
     */
    public T find(BigInteger id) {
        return getAbstractDataService().find(id);
    }

    /**
     * delete entity
     *
     * @param entity
     */
    public void delete(T entity) {
        getAbstractDataService().delete(entity);
    }
    
    /**
     * delete entity by id
     * @param id 
     */
    public void delete(BigInteger id) {
        getAbstractDataService().delete(id);
    }

    /**
     * persist new entity
     *
     * @param entity
     */
    public void persist(T entity) {
        getAbstractDataService().persist(entity);
    }

    /**
     * merge existing entity
     *
     * @param entity
     * @return
     */
    public T merge(T entity) {
        return getAbstractDataService().merge(entity);
    }

    /**
     * use this method to create a new (default ) initialized entity
     *
     * @return
     */
    public T create() {
        return getAbstractDataService().create();
    }    
}
