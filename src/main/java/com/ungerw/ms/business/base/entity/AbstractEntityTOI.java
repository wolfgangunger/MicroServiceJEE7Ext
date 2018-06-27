/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base.entity;

import java.math.BigInteger;

/**
 * TOP Interface tor TranserObject Interfaces
 * @copyright Wolfgang Unger
 * 
 * @author UNGERW
 */
public interface AbstractEntityTOI {

    /**
     * 
     * @return ID
     */
    BigInteger getId();

    /**
     * 
     * @param id 
     */
    void setId(BigInteger id);

}
