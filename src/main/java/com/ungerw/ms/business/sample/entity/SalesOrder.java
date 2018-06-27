/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.sample.entity;

import com.ungerw.ms.business.base.entity.BusinessEntity;
import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sales Order 
 * @author UNGERW
 */
@Entity
@Table(name = "T_SALESORDER")
@ApiModel(description = "The Sales Order model")
@NamedQueries({
    @NamedQuery(name = SalesOrder.FIND_ALL, query = "SELECT o FROM  SalesOrder o"),
    @NamedQuery(name = SalesOrder.FIND_BY_ORDER_NUMBBER, query = "SELECT o FROM SalesOrder o WHERE o.orderNumber  = :orderNumber")})
public class SalesOrder extends BusinessEntity implements SalesOrderTOI {

    public static final String FIND_ALL = "SalesOrder.findAll";    
    public static final String FIND_BY_ORDER_NUMBBER = "SalesOrder.findOrderNumber";

    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private List<SalesOrderDetail> details = new ArrayList<>();

    @Override
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public List<SalesOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SalesOrderDetail> details) {
        this.details = details;
    }
    
    

}
