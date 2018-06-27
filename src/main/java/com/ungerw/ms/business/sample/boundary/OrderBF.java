/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.sample.boundary;

import com.ungerw.ms.business.base.boundary.AbstractBF;
import com.ungerw.ms.business.base.security.AccountSecurityInterceptor;
import com.ungerw.ms.business.base.util.TOIMapperUtil;

import com.ungerw.ms.business.sample.control.SalesOrderDetailDataService;
import com.ungerw.ms.business.sample.control.SalesOrderService;

import com.ungerw.ms.business.sample.entity.SalesOrder;
import com.ungerw.ms.business.sample.entity.SalesOrderDetail;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.ungerw.ms.business.sample.entity.SalesOrderTOI;
import com.ungerw.ms.business.system.control.AuthService;
import com.ungerw.ms.business.system.entity.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Business Facade example - Boundary
 * this BF contains methods for sales orders and sales order details <br>
 * some of the methods (delete, update) use a Security Context and Principal
 * 
 * @author UNGERW
 */
//@Interceptors({ PrincipalSecurityInterceptor.class,PerformanceInterceptor.class })
//@Interceptors({ PrincipalSecurityInterceptor.class})
@Interceptors({ AccountSecurityInterceptor.class})
//@DeclareRoles({"Admin", "Manager"})
@Path("/sample")
@Api(value = "/sample", description = "Example Business Facade")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderBF extends AbstractBF{

    // sales order is more complex - needs a service class
    @Inject
    private SalesOrderService orderService;

    // details are simple - use data service directly - no service class required
    @Inject
    private SalesOrderDetailDataService orderDetailDataService;
    
    //authentication service
    @Inject
    private AuthService authService;

    ///////////////// template BF methods //////////////
    //CRUD for Order entity ////////
    // C Create
    /**
     * C : create (initialize) a new Order Entity
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/initSalesOrder/")
    @ApiOperation(value = "SalesOrder - create a new not yet persisted SalesOrder", response = SalesOrder.class)
    public SalesOrder createSalesOrder() {
        return orderService.create();
    }

    // R Read one order by ID
    /**
     * 
     * @param id Sales Order ID
     * @return 
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrders/{id}")
        @ApiOperation(value = "SalesOrder - find SalesOrder", response = SalesOrder.class)
    public SalesOrder findSalesOrder(@PathParam("id") BigInteger id) {
        return orderService.find(id);
    }

    /**
     * read one order by orderNumber
     *
     * @param orderNumber
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrderByOrderNumber/{orderNumber}")
    public SalesOrder findSalesOrder( @PathParam("orderNumber") String orderNumber) {
        return orderService.findBySalesOrderByNumber(orderNumber);
    }

    /**
     * read all orders
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrders")
   // @RolesAllowed({"Admin", "Manager"})
    public List<SalesOrder> getAllSalesOrders() {
        return orderService.getAllSalesOrders();
    }

    /**
     * read all orders return small OrderTOIs (TransferObjectInterfaces) for
     * e.g. grids
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrderTOIs")
    public List<SalesOrderTOI> getAllSalesOrderTOIs() {
        return TOIMapperUtil.mapList(orderService.getAllSalesOrders());
    }

    // U Update
    /**
     * persist/update a new Order entity ( JPA = persist ; REST = create)
     *
     * @param sc SecurityContext including Principal
     * @param order
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrders/")
    public SalesOrder persistSalesOrder(@Context SecurityContext sc, SalesOrder order) {
        orderService.persist(order);
        return order;
    }

    /**
     * update existing Order
     *
     * @param sc SecurityContext including Principal
     * @param order
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrders/{id}")
    public SalesOrder mergeSalesOrder(@Context SecurityContext sc, SalesOrder order) {
        return orderService.merge(order);
    }

    /**
     * delete a Order
     *
     * @param sc SecurityContext including Principal
     * @param id
     */
    @DELETE
    @Path("/salesOrders/{id}")
    public void deleteSalesOrder(@Context SecurityContext sc, @PathParam("id") BigInteger id) {
        // get account object from service, you might use this for interceptors on servive tier
        Account account = authService.authenticateAccount(sc.getUserPrincipal().getName());
        orderService.delete(account,id);
    }

    //CRUD for Order Detail entity - use dataService directly ////////
    
        // R Read one orderDetail by ID
    /**
     * read one orderDetail
     * @param id
     * @return 
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrderDetails/{id}")
    public SalesOrderDetail findSalesOrderDetail(@PathParam("id") BigInteger id) {
        return orderDetailDataService.find(id);
    }
    
    /**
     * read all order details
     * @param sc
     * @return 
     */
      @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrderDetails")
    public List<SalesOrderDetail> getAllSalesOrderDetails() {
        return orderDetailDataService.getAllSalesOrderDetails();
    }
    /**
     * read all orderdetail for order
     * @param orderId
     * @return 
     */
      @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrderDetailsByOrder/{orderId}")
    public List<SalesOrderDetail> getSalesOrderDetailsByOrder( @PathParam("orderId") BigInteger orderId) {
        return orderDetailDataService.findSalesOrderDetailsByOrder(orderId);
    }

    /**
     * get one sales order detail by id
     * @param sc
     * @param id
     * @return 
     */
      @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salesOrderDetails/{id}")
    public SalesOrderDetail getSalesOrderDetail(@PathParam("id") BigInteger id) {
        return orderDetailDataService.find(id);
    }    
}
