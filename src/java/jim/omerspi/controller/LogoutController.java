/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Jimmy
 */
@Controller
public class LogoutController {

    @Autowired
    private ServiceContext context;

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        if (a != null) {
            a.setLastLoginTime(new Date());
            context.getUserService().saveOrUpdateUser(a);
            request.getSession().removeAttribute(Constants.USER_SESSION_ATTR);
            request.getSession().invalidate();

            return "redirect:login.htm";
        }
        return "redirect:/login.htm";
    }
}
