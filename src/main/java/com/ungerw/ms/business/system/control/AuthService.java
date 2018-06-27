/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.system.control;

import com.ungerw.ms.business.system.entity.Account;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author UNGERW
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AuthService {
    
        public Account authenticateAccount(String qNumber) {
            // implementation : check if account/ user exists and is authenticated
            Account result =  new Account();
            result.setAccountName("wunger");
            return result;
        }
    
}
