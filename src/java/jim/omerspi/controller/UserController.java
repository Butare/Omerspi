/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.BCrypt;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Employee;
import jim.omerspi.model.User;
import jim.omerspi.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jim.omerspi.Context;
import jim.omerspi.PrivilegeConstants;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String createAccountForm(Map<String, Object> model, @ModelAttribute("user") User user) {

        List<Employee> empl = new ArrayList<Employee>();

        List<Employee> emp = context.getEmployeeService().getAllNotDriverEmployee();
        List<User> us = context.getUserService().getAllUser();

        //find employee has no account
        for (int i = 0; i < emp.size(); i++) {
            int found = 0;
            for (int j = 0; j < us.size(); j++) {
                if (emp.get(i).getEmployeeRegistrationId().equals(us.get(j).getEmployee().getEmployeeRegistrationId())) {
                    found++;
                }
            }
            if (found == 0) {
                empl.add(emp.get(i));
            }
        }
        model.put("employeeList", empl);

        return "user/userAccountForm";

    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String userRegistration(Model message, @ModelAttribute("user") User user, BindingResult errors, @RequestParam("identificationWord") String identity, @RequestParam("confirmPassword") String confirmPassword, Map<String, Object> model) {


        userValidator.validate(user, errors);

        String form = null;
        if (errors.hasErrors()) {
            if (identity.isEmpty()) {
                model.put("identificationWord", "Identification word is required");
            }
            if (confirmPassword.isEmpty()) {
                model.put("confirmPassword", "Confirm password is required.");
            }
            return createAccountForm(model, user);
        }
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        Employee employee = user.getEmployee();


        // if (user.getUserName() != null && !(user.getUserName().equals(context.getUserService().getUserByUsername(user.getUserName())))) {
        if (BCrypt.checkpw(confirmPassword, hashed)) {

            user.setPassword(hashed);

            if (identity != null && identity.equals(employee.getIdentificationWord())) {
                context.getUserService().saveOrUpdateUser(user);
                message.addAttribute("accountCreated", "Your account has successfully Created. You can Login.");
                form = "user/userAccountForm";
            } else {
                model.put("identificationWord", "Identification word is incorrect");
                return createAccountForm(model, user);
            }

        } else {
            model.put("identificationWord", "Identification word is incorrect");
            return createAccountForm(model, user);

        }
        return form;

    }

    @RequestMapping("/edit")
    public String editUserAccount(Map<String, Object> model, @RequestParam("userId") User user) {

        List<Employee> emp = context.getEmployeeService().getAllEmployee();
        model.put("employeeList", emp);
        model.put("user", user);
        return "user/userAccountForm";

    }

    @RequestMapping("/list")
    public String userAccountList(HttpServletRequest request, Map<String, Object> model) {

        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            // session.setAttribute(Constants.USER_SESSION_ATTR, user.getUserName());
            List<User> userList = context.getUserService().getAllUser();
            model.put("userList", userList);
            return "user/userAccountList";

        }
        return "redirect:/login.htm";
    }
}
