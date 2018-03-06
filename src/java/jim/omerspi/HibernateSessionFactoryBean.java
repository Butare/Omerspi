/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 *
 * @author JOHN
 */
public class HibernateSessionFactoryBean extends AnnotationSessionFactoryBean {

    @Override
    public Configuration newConfiguration() throws HibernateException {
        Configuration config = super.newConfiguration();

        Properties runtimeProperties = Context.getRuntimeProperties();
       System.out.println("Mysql CONNECTORS: "+runtimeProperties.getProperty("jdbc.databaseurl"));
        config.setProperty("hibernate.connection.url", runtimeProperties.getProperty("jdbc.databaseurl"));
        config.setProperty("hibernate.connection.username", runtimeProperties.getProperty("jdbc.username"));
        config.setProperty("hibernate.connection.password", runtimeProperties.getProperty("jdbc.password"));
        config.setProperty("hibernate.connection.driver_class", runtimeProperties.getProperty("jdbc.driverClassName"));
        config.setProperty("dialect", runtimeProperties.getProperty("jdbc.dialect"));
        config.setProperty("show_sql", "true");
        config.setProperty("hibernate.jdbc.use_get_generated_keys", "true");
        config.setProperty("hibernate.connection.release_mode", "auto");
        config.setProperty("hbm2ddl.auto", "update");

     
        
        return config;
    }
}
