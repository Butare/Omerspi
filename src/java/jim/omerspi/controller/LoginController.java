/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.BCrypt;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.User;
import jim.omerspi.Constants;
import jim.omerspi.OmerspiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JIMMY
 */
@Controller
public class LoginController {

    @Autowired
    private ServiceContext context;

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String loginForm(Map<String, Object> model, @ModelAttribute("user") User user, HttpServletRequest request) {
        //  System.out.println("request :" + request.getRequestURL());
        return "login/loginForm";
    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public String userAuthentication(Map<String, Object> model, HttpServletRequest request, @RequestParam("userName") String username, @RequestParam("password") String password) {

        int notEmpty = 0;
        int nameFound = 0;
        int passFound = 0;

        if (!username.isEmpty() && !password.isEmpty()) {
            notEmpty = 1;
            User u = context.getUserService().getUserByUsername(username);
            if (u != null) {
                nameFound = 1;
                if (BCrypt.checkpw(password, u.getPassword())) {
                    User user = context.getUserService().authenticate(username, u.getPassword());
                    passFound = 1;
                    if (user != null) {

                        HttpSession session = request.getSession(true);

                        session.setAttribute(Constants.USER_SESSION_ATTR, user.getUserName());
                        if (user.isSuperUser()) {
                            System.out.println("Welcome ");

                        } else {
                            System.out.println("Welcome Not Administrator");
                           
                        }
                        return "redirect:Menu";
                    } else {
                        OmerspiUtils.setErrorMessage(request.getSession(), "Error occured. Try again");
                        return loginForm(model, user, request);
                    }
                }
//                if (passFound == 0) {
//                    model.addAttribute("errorPassword", "Invalid Password.");
//                }
            }
//            if (nameFound == 0) {
//                model.addAttribute("errorUsername", "Invalid username.");
//            }

        }

//        if (notEmpty == 0) {
//            model.addAttribute("usernameField", "Username field is required!");
//            model.addAttribute("passwordField", "Password field is required!");
//        }

        request.getSession().setAttribute(Constants.ERROR_SESSION_ATTR, "Invalid username and/or password");

        return "login/loginForm";
    }
}
