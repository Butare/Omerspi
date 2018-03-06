/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import java.util.List;
import javax.servlet.http.HttpSession;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class OmerspiNotification {

    @Autowired
    private ServiceContext context;
    @Autowired
    private MailMail mailMail;

    
    //HOD EMAIL STATIONARY
    public void sentStationaryHoDEmail(HttpSession session) {

        User user = Context.getCurrentUser(session);

        List<User> hod = context.getStationaryRequisitionService().getHoD(user.getEmployee().getDepartment());
        for (int i = 0; i < hod.size(); i++) {
            if (hod.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                String subject = "Hello, " + hod.get(i).getEmployee().getFirstName() + "!\nYou have New Stationary Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                mailMail.sendMail(Constants.omerspiEmail, hod.get(i).getEmployee().getWorkEmail(), "Stationary Requisition", subject);
            }
        }
    }
    
    //DAF EMAILS
    public void sendStationaryDafEmail() {

        //SEND STATIONARY EMAIL TO DAF
        List<User> daf = context.getUserService().getAllUser();
        for (int i = 0; i < daf.size(); i++) {
            if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Stationary Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Stationary Requisition", subject);
            }
        }
    }

    public void sendCarDafEmail() {

        //SEND CAR EMAIL TO DAF
        List<User> daf = context.getUserService().getAllUser();
        for (int i = 0; i < daf.size(); i++) {
            if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
            }
        }
    }

    //LOGISTIC EMAILS
    public void sendStationaryLogisticEmail() {
        //SEND STATIONARY EMAIL TO LOGISTICS OFFICER
        List<User> logistics = context.getUserService().getAllUser();
        for (int i = 0; i < logistics.size(); i++) {
            if (logistics.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
                String subject = "Hello, " + logistics.get(i).getEmployee().getFirstName() + "!\nYou have New Stationary Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                mailMail.sendMail(Constants.omerspiEmail, logistics.get(i).getEmployee().getWorkEmail(), "Stationary Requisition", subject);
            }
        }

    }

    public void sendCarLogisticEmail() {
        //SEND CAR EMAIL TO LOGISTICS OFFICER
        List<User> logistics = context.getUserService().getAllUser();
        for (int i = 0; i < logistics.size(); i++) {
            if (logistics.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
                String subject = "Hello, " + logistics.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                mailMail.sendMail(Constants.omerspiEmail, logistics.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
            }
        }

    }
}
