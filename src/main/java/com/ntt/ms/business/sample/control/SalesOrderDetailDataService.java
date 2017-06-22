/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.sample.control;

import com.ntt.ms.business.base.control.AbstractDataService;
import com.ntt.ms.business.sample.entity.SalesOrderDetail;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Data Service for Sales Order Details
 * 
 * @author UNGERW
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SalesOrderDetailDataService extends AbstractDataService<SalesOrderDetail> {

    @PersistenceContext(name = "ms")
    private EntityManager em;
    
    

    @Override
    protected Class<SalesOrderDetail> getEntityClass() {
        return SalesOrderDetail.class;
    }

    @Override
    protected EntityManager getEnityManager() {
        return em;
    }

    @Override
    public SalesOrderDetail create() {
        return new SalesOrderDetail();
    }

    public List<SalesOrderDetail> getAllSalesOrderDetails() {
        TypedQuery<SalesOrderDetail> query = em.createNamedQuery(SalesOrderDetail.FIND_ALL, SalesOrderDetail.class);
        return query.getResultList();
    }

    public List<SalesOrderDetail> findSalesOrderDetailsByOrder(BigInteger orderId) {
        Query query = em.createNamedQuery(SalesOrderDetail.FIND_BY_ORDER, SalesOrderDetail.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

}
