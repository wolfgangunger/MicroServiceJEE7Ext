/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.sample.control;

import com.ntt.ms.business.base.control.AbstractDataService;
import com.ntt.ms.business.base.control.AbstractService;
import com.ntt.ms.business.base.performance.PerformanceInterceptor;
import com.ntt.ms.business.base.performance.ProfiledMethodInterceptor;
import com.ntt.ms.business.base.security.AccountSecurityInterceptor;
import com.ntt.ms.business.monitoring.boundary.Monitored;
import com.ntt.ms.business.sample.entity.SalesOrder;
import com.ntt.ms.business.system.entity.Account;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * there might be a lot of business logic for sales orders - therefore a addtional service class is required
 * the data service should not contain any business logic
 * @author UNGERW,
 */
@Interceptors({ PerformanceInterceptor.class, ProfiledMethodInterceptor.class })
@Monitored
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SalesOrderService extends AbstractService<SalesOrder> {


    @Inject
    private SalesOrderDataService orderDataService;

    @Override
    protected AbstractDataService<SalesOrder> getAbstractDataService() {
        return orderDataService;
    }

    public List<SalesOrder> getAllSalesOrders() {
        return orderDataService.getAllSalesOrders();
    }

    public SalesOrder findBySalesOrderByNumber(String orderNumber) {
        return orderDataService.findBySalesOrderByNumber(orderNumber);
    }

    /**
     * delete method with programmatic security
     * @param account
     * @param id
     */
    @Interceptors({ AccountSecurityInterceptor.class })
    public void delete(Account account, BigInteger id) {
        super.delete(id); 
    }
    
    

}
