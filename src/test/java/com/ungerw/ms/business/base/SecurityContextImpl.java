/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author UNGERW
 */
public class SecurityContextImpl implements SecurityContext {

    @Override
    public Principal getUserPrincipal() {
        return new PrincipalImpl();
    }

    @Override
    public boolean isUserInRole(String role) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return "";
    }

    public class PrincipalImpl implements Principal {

        @Override
        public String getName() {
            return "qxl5748xx";
        }

    }
}
