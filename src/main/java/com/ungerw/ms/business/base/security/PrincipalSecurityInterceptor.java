package com.ungerw.ms.business.base.security;

import com.ungerw.ms.business.system.control.AuthService;
import com.ungerw.ms.business.system.entity.Account;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author UNGERW
 */
public class PrincipalSecurityInterceptor {

    @Inject
    private AuthService authService;

    private static final Logger LOGGER = Logger.getLogger(PrincipalSecurityInterceptor.class.getName());

    @AroundInvoke
    Object handle(InvocationContext ic) throws Exception {
        LOGGER.info("PrincipalSecurityInterceptor" + ic.toString());
        Object sc = (ic.getParameters()!=null&&ic.getParameters().length>0) ?  ic.getParameters()[0] : null;
        if (sc != null && sc instanceof SecurityContext) {
            authorize((SecurityContext) sc);
        }
        return ic.proceed();
    }

    private void authorize(SecurityContext sc) {
        String principal = sc.getUserPrincipal() != null ? sc.getUserPrincipal().getName() : "--";
        LOGGER.info(principal);
        // continue recieving the user/account/role object by the princical and do authorization
        Account account = authService.authenticateAccount(principal);
        //check for roles ...rights..
    }

    private void verifyUserPermission(final InvocationContext ic) {
        // TODO add here any checks for security
        System.out.println("User Permission Check: " + ic.getMethod().getDeclaringClass() + "#" + ic.getMethod().getName());
    }

    private <T extends Annotation> T getAnnotation(final Class<T> annotationClass, final Method method) {
        return method.getAnnotation(annotationClass);
    }

    private <T extends Annotation> T getAnnotation(final Class<T> annotationClass, final Class<?> clazz) {
        return clazz.getAnnotation(annotationClass);
    }

    private <T extends Annotation> boolean hasAnnotation(final InvocationContext ic, final Class<T> annotationClass) {
        return getAnnotation(annotationClass, ic.getMethod().getDeclaringClass()) != null
                || getAnnotation(annotationClass, ic.getMethod()) != null;
    }
}
