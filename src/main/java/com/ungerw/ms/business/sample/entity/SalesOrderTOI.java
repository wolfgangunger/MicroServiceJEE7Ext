/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.sample.entity;

import com.ungerw.ms.business.base.entity.AbstractEntityTOI;
import io.swagger.annotations.ApiModel;

/**
 * transfer object interface - order without details (for example usage in lists)
 * @author UNGERW
 */
@ApiModel(description = "The Sales Order TOI model")
public interface SalesOrderTOI extends AbstractEntityTOI{

    String getDescription();

    String getOrderNumber();

    void setDescription(String description);

    void setOrderNumber(String orderNumber);
    
}
