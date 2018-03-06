/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import javax.servlet.http.HttpSession;
import jim.omerspi.model.User;
import jim.omerspi.service.ItemService;
import jim.omerspi.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.util.Properties;
import org.springframework.mail.MailSender;

/**
 *
 * @author Jimmy
 */
public class Context implements ApplicationContextAware {

    private static Properties runtimeProperties;

    private static ServiceContext serviceContext = null;
    
    private static MailSender mailSender = null;

    public static MailSender getMailSender() {
        return mailSender;
    }
    

    public static void setMailSender(MailSender mailSender) {
        Context.mailSender = mailSender;
    }
    
    

    public static Properties getRuntimeProperties() {
        return runtimeProperties;
    }

    public static void setRuntimeProperties(Properties runtimeProperties) {
        Context.runtimeProperties = runtimeProperties;
    }
    
    public static ServiceContext getServiceContext() {
        return serviceContext;
    }

    public static boolean hasPrivilege(HttpSession session, String privilege) {
        // Get logged in user from session
        User user = getCurrentUser(session);

        // Check if user has the privilege
        if (user == null) {
            return false;
        } else {
            return user.hasPrivilege(privilege);
        }
    }

    public static User getCurrentUser(HttpSession session) {
        if (session != null) {
            String username = (String) session.getAttribute(Constants.USER_SESSION_ATTR);
            if (username != null) {
                return getUserService().getUserByUsername(username);
            }
        }
        return null;
    }

    public static UserService getUserService() {
        return serviceContext.getUserService();
    }

    public static ItemService getItemService() {
        return serviceContext.getItemService();
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        serviceContext = ac.getBean(ServiceContext.class);
    }
}