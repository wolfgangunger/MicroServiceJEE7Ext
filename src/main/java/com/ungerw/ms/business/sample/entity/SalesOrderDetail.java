/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.sample.entity;

import com.ungerw.ms.business.base.entity.BusinessEntity;
import io.swagger.annotations.ApiModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Sales Order Detail
 * @author UNGERW
 */
@Entity
@Table(name = "T_SALESORDERDETAIL")
@ApiModel(description = "The Sales Order Detail model")
@NamedQueries({
    @NamedQuery(name = SalesOrderDetail.FIND_ALL, query = "SELECT od FROM  SalesOrderDetail od"),
    @NamedQuery(name = SalesOrderDetail.FIND_BY_ORDER, query = "SELECT od FROM SalesOrderDetail od WHERE od.order.id  = :orderId")})
public class SalesOrderDetail extends BusinessEntity {

    public static final String FIND_ALL = "SalesOrderDetail.findAll";
    public static final String FIND_BY_ORDER = "SalesOrderDetail.findByOrder";

    @Column(name = "DETAIL_NUMBER")
    private int detailNumber;

    @Column(name = "ITEM")
    private String item;

    @Column(name = "DESCRIPTION")
    private String description;

    //@JsonIgnore
    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private SalesOrder order;

    public int getDetailNumber() {
        return detailNumber;
    }

    public void setDetailNumber(int detailNumber) {
        this.detailNumber = detailNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public SalesOrder getOrder() {
        return order;
    }

    public void setOrder(SalesOrder order) {
        this.order = order;
    }

}
