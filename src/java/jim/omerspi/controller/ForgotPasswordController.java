/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.List;
import jim.omerspi.BCrypt;
import jim.omerspi.Constants;
import jim.omerspi.RandomString;
import jim.omerspi.ServiceContext;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.Employee;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JIMMY
 */
@Controller
public class ForgotPasswordController {

    @Autowired
    private ServiceContext context;
    private MailMail mailMail = new MailMail();


    @RequestMapping("/forgotPassword.form")
    public String getForgotPasswordForm() {

        return "login/forgotPassword";
    }

    @RequestMapping("/password/reset.htm")
    public String resetPassword(Model model, @RequestParam("identificationWord") String identificationWord, @RequestParam("workEmail") String workEmail) {


        String form = null;
        List<Employee> emp = context.getEmployeeService().resetPassword(identificationWord, workEmail);

        if (emp.isEmpty()) {


            model.addAttribute("message", "Incorrect Identification Word And/Or Work Email.  \n Try again!!");
            form = "login/forgotPassword";
            //return 

        } else if (!emp.isEmpty()) {
            for (int i = 0; i < emp.size(); i++) {
                RandomString rs = new RandomString();
                String randomString = rs.get(10);
                String hashed = BCrypt.hashpw(randomString, BCrypt.gensalt());

                for (User u : emp.get(i).getUsers()) {
                    u.setPassword(hashed);
                    context.getUserService().saveOrUpdateUser(u);
                }
                String subject = "Hello, " + emp.get(i).getFirstName() + "!\n This is your new Password: " + randomString + "\n Click here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks";
                mailMail.sendMail(Constants.omerspiEmail, emp.get(i).getWorkEmail(), "Forgot Password Helper", subject);
            }
            model.addAttribute("messageCorrect", "Your Password has been successfully reset!. Check your Work Email Address for new Password. Thanks!");
            form = "login/forgotPassword";
        }
        return form;


    }
}
