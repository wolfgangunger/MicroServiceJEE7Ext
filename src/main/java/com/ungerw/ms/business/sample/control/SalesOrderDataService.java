/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.sample.control;

import com.ungerw.ms.business.base.control.AbstractDataService;
import com.ungerw.ms.business.sample.entity.SalesOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Data Service for Sales Orders 
 * implement data access methods in this class, no business logic (Service)
 * @author UNGERW
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SalesOrderDataService extends AbstractDataService<SalesOrder> {

    @PersistenceContext(name = "ms")
    private EntityManager em;

    @Override
    protected Class<SalesOrder> getEntityClass() {
        return SalesOrder.class;
    }

    @Override
    protected EntityManager getEnityManager() {
        return em;
    }

    @Override
    public SalesOrder create() {
        SalesOrder order = new SalesOrder();
        // do initialization
        order.setOrderNumber("0001"); // e.g. Nummerkreis
        return order;
    }

    public List<SalesOrder> getAllSalesOrders() {
        TypedQuery<SalesOrder> query = em.createNamedQuery(SalesOrder.FIND_ALL, SalesOrder.class);
        return query.getResultList();
    }

    public SalesOrder findBySalesOrderByNumber(String orderNumber) {
        Query query = em.createNamedQuery(SalesOrder.FIND_BY_ORDER_NUMBBER, SalesOrder.class);
        query.setParameter("orderNumber", orderNumber);
        if (!query.getResultList().isEmpty()) {
            return (SalesOrder) query.getResultList().get(0);
        } else {
            return null;
        }
    }

}
