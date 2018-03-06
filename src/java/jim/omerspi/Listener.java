/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

import java.io.*;

import org.springframework.web.context.ContextLoaderListener;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author JOHN
 */
public class Listener extends ContextLoaderListener {

    public void loadProperties() {
        try {
            Properties configProp = new Properties();

            String propFilePath = System.getenv("OMERSPI_PROPERTIES_FILE");
            Reader in = new FileReader(propFilePath);
            configProp.load(in);
            System.out.println("Mysql CONNECTOR: " + configProp.getProperty("jdbc.databaseurl"));
            
            Context.setRuntimeProperties(configProp);

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setProtocol("smtp");
            mailSender.setUsername(configProp.getProperty("omerspi.email.username"));
            mailSender.setPassword(configProp.getProperty("omerspi.email.password"));


            //Mail Properties
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.quitwait", "false");
            prop.put("mail.smtps.debug", "true");
            mailSender.setJavaMailProperties(prop);

            Context.setMailSender(mailSender);

//            Properties prop = new Properties();
//
//            prop.setProperty("jdbc.driverClassName", configProp.getProperty("jdbc.driverClassName"));
//            prop.setProperty("jdbc.dialect", configProp.getProperty("jdbc.dialect"));
//            prop.setProperty("jdbc.username", configProp.getProperty("jdbc.username"));
//            prop.setProperty("jdbc.password", configProp.getProperty("jdbc.password"));
//            prop.setProperty("jdbc.databaseurl", configProp.getProperty("jdbc.databaseurl"));
//            
//            
//            
//            prop.store(new FileWriter("C:\\Users\\JOHN\\Desktop\\NEW\\OMERSPI\\web\\WEB-INF\\jdbc.properties"), null);
//
//            Properties p = new Properties();
//            p.load(new FileReader("C:\\Users\\JOHN\\Desktop\\NEW\\OMERSPI\\web\\WEB-INF\\jdbc.properties"));
//            System.out.println("dialect : " + p.getProperty("jdbc.dialect"));
//            System.out.println("username : " + p.getProperty("jdbc.username"));
//            System.out.println("password : " + p.getProperty("jdbc.dialect"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loadProperties();

        super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroyed");

        super.contextDestroyed(sce);
    }
}
