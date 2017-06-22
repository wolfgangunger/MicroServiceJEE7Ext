package com.ntt.ms.business.base.security;

import com.ntt.ms.business.system.control.AuthService;
import com.ntt.ms.business.system.entity.Account;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author UNGERW
 */
public class AccountSecurityInterceptor {

    @Inject
    private AuthService authService;

    private static final Logger LOGGER = Logger.getLogger(AccountSecurityInterceptor.class.getName());

    @AroundInvoke
    Object handle(InvocationContext ic) throws Exception {
        LOGGER.info("AccountSecurityInterceptor" + ic.toString());
        Object account = (ic.getParameters() != null && ic.getParameters().length > 0) ? ic.getParameters()[0] : null;
        if (account != null && account instanceof Account) {
            authorize((Account) account);
        }
        return ic.proceed();
    }

    private void authorize(Account a) {
        LOGGER.info(a.getAccountName());
        // continue recieving the user/account/role object by the princical and do authorization
        Account account = authService.authenticateAccount(a.getAccountName());
        //check for roles ...rights..
    }


}
