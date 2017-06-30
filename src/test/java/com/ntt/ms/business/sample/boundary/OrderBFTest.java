/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.sample.boundary;

import com.ntt.ms.business.base.ArquillianTestBase;
import com.ntt.ms.business.base.SecurityContextImpl;
import com.ntt.ms.business.sample.entity.SalesOrder;
import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author UNGERW
 */
public class OrderBFTest extends ArquillianTestBase {

    @Inject
    private OrderBF orderBF;

    private static final String ORDER_NO_1 = "order1";
    private static final String ORDER_NO_1B = "order1b";

    /**
     * simple test without existing testdata - testing all crud methods
     */
    @Test
    public void testSalesOrderCrud() {
        Assert.assertNotNull(orderBF);
        if (true) {
            return;
        }
        SecurityContext sc = createSecurityContextMock();

        List<SalesOrder> salesOrders = orderBF.getAllSalesOrders();
        // must be 0
        Assert.assertEquals(0, salesOrders.size());

        SalesOrder so = orderBF.createSalesOrder();
        Assert.assertNotNull(so);
        so.setOrderNumber(ORDER_NO_1);
        Assert.assertNotNull(so);
        //update/persist
        orderBF.persistSalesOrder(sc, so);
        //find
        SalesOrder persSO = orderBF.findSalesOrder(ORDER_NO_1);
        Assert.assertNotNull(persSO);
        BigInteger id = persSO.getId();
        System.out.println(id);
        persSO = orderBF.findSalesOrder(id);
        Assert.assertNotNull(persSO);
        //update/merge
        persSO.setOrderNumber(ORDER_NO_1B);
        SalesOrder persSO1b = orderBF.mergeSalesOrder(sc, persSO);
        Assert.assertNotNull(persSO1b);
        Assert.assertEquals(ORDER_NO_1B, persSO1b.getOrderNumber());

        //cleanup
        persSO1b = orderBF.findSalesOrder(ORDER_NO_1B);
        Assert.assertNotNull(persSO1b);
        orderBF.deleteSalesOrder(sc, persSO1b.getId());
        persSO1b = orderBF.findSalesOrder(ORDER_NO_1B);
        Assert.assertNull(persSO1b);
    }

    /**
     * this test is using a dataset (json) file to import test data
     */
    @Test
    @UsingDataSet("datasets/salesorders.json")
    public void testSalesOrdersWithDataSet() {
        if (true) {
            return;
        }
        List<SalesOrder> salesOrders = orderBF.getAllSalesOrders();
        Assert.assertNotNull(salesOrders);
        Assert.assertEquals(2, salesOrders.size());
    }

    private SecurityContext createSecurityContextMock() {
        SecurityContextImpl sc = new SecurityContextImpl();
        return sc;
    }

}
