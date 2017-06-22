package com.ntt.ms.business.base;

import com.ntt.ms.business.monitoring.boundary.MonitoringBF;
import com.ntt.ms.business.sample.boundary.OrderBF;
import io.swagger.jaxrs.config.BeanConfig;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//set the path to REST web services
@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        System.out.println("ApplicationConfig App");
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setTitle("JEE7 Microservices");
        beanConfig.setBasePath("/api/v1");
        beanConfig.setResourcePackage("com.ntt.ms");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        System.out.println("ApplicationConfig App get Classes");
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        set.add(OrderBF.class);
        set.add(MonitoringBF.class);
        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return set;
    }

}
