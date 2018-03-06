/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.ServletContext;

/**
 * This class is not used for now.
 * @author JIMMY
 */
public class SessionCounterListener implements HttpSessionBindingListener {
    
    
//Commented lines was for getting session id for individual loged in user.
    
//     SessionCounterListener scl = new SessionCounterListener(request.getSession().getServletContext());
//        request.getSession().setAttribute("sclObject", scl);
//        System.out.println("Object bound to session Car requisition : " + request.getSession().getId());
//        // Carrequisition carRequisition = new Carrequisition();
    
    
    ServletContext context;

    public SessionCounterListener(ServletContext context) {
        this.context = context;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        context.log("" + (new java.util.Date()) + " Binding " + event.getName() + " to session "
                + event.getSession().getId());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        context.log("" + (new java.util.Date()) + " Unbinding " + event.getName() + " from session "
                + event.getSession().getId());
    }
}
