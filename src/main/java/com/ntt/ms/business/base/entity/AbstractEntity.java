/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.base.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

/**
 * super class for all entities with ID - extend all entities from this class
 * 
 * @author UNGERW
 */
@MappedSuperclass
public class AbstractEntity implements AbstractEntityTOI, Serializable{

     /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1868447758611081029L;
    
    /**
     * primary technical key
     */
    @Id
    @SequenceGenerator(name = "EntitySeq", sequenceName = "ENTITY_SEQ", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTITY_SEQ")
    @Column(name = "ID")
    private BigInteger id;

    
    /**
     * ID field
     * @return BigInteger
     */
    @Override
    public BigInteger getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }
    
    
    

}
