/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base.entity;

import java.math.BigInteger;
import java.util.Date;

/**
 * TOP Interface tor TranserObject Interfaces
 * @copyright Wolfgang Unger
 * 
 * @author UNGERW
 */
public interface BusinessEntityTOI extends AbstractEntityTOI{

    /**
     * 
     * @return Date
     */
    Date getCreated();

    /**
     * 
     * @return BigInteger
     */
    BigInteger getCreatedBy();

    /**
     * 
     * @return Date
     */
    Date getUpdated();

    /**
     * 
     * @return 
     */
    BigInteger getUpdatedBy();

    /**
     * 
     * @return Long
     */
    Long getVersion();

    /**
     * 
     * @param created 
     */
    void setCreated(Date created);

    /**
     * 
     * @param createdBy 
     */
    void setCreatedBy(BigInteger createdBy);

    /**
     * 
     * @param updated 
     */
    void setUpdated(Date updated);

    /**
     * 
     * @param updatedBy 
     */
    void setUpdatedBy(BigInteger updatedBy);

    /**
     * 
     * @param version 
     */
    void setVersion(Long version);
    
}
