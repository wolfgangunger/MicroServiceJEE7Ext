/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * super class for all entities - extend all entities from this class with
 * additional information createdBy, created ...
 *
 * @author UNGERW
 */
@MappedSuperclass
public class BusinessEntity extends AbstractEntity implements BusinessEntityTOI {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED")
    private Date updated;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "CREATED_BY")
    private BigInteger createdBy;

    @Column(name = "UPDATED_BY")
    private BigInteger updatedBy;

    @Override
    public Date getCreated() {
        return created;
    }

    /**
     * 
     * @param created 
     */
    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Date getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated 
     */
    @Override
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Long getVersion() {
        return version;
    }

    /**
     * 
     * @param version 
     */
    @Override
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 
     * @return 
     */
    @Override
    public BigInteger getCreatedBy() {
        return createdBy;
    }

    /**
     * 
     * @param createdBy 
     */
    @Override
    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 
     * @return 
     */
    @Override
    public BigInteger getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 
     * @param updatedBy 
     */
    @Override
    public void setUpdatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    

}
