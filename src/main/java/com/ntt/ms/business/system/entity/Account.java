/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.system.entity;

import com.ntt.ms.business.base.entity.BusinessEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author UNGERW
 */
@Entity
@Table(name = "T_ACCOUNT")
public class Account extends BusinessEntity{
    
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    
    
}
