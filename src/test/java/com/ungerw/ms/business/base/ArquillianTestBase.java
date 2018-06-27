package com.ungerw.ms.business.base;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public abstract class ArquillianTestBase {

  /**
   * @return a java archive with the classes to deploy
   */
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap
        .create(JavaArchive.class)
        .addPackages(true, "com.ungerw.ms")
        .addAsResource("test-persistence.xml", 
                "META-INF/persistence.xml");
  }
}
