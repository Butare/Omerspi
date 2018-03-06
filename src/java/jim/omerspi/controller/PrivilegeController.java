/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Privilege;
import jim.omerspi.validator.PrivilegeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

/**
 *
 * @author JIMMY
 */
@Controller
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private PrivilegeValidator privilegeValidator;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getPrivilegeForm(Map<String, Object> model, @ModelAttribute("privilege") Privilege privilege) {

        model.put("privilege", privilege);
        return "privilege/privilegeForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdatePrivilege(@ModelAttribute("privilege") Privilege privilege, BindingResult result, HttpServletRequest request, Map<String, Object> model) {

        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_PRIVILEGE)) {
            try {
                privilegeValidator.validate(privilege, result);
                if (result.hasErrors()) {
                    return getPrivilegeForm(model, privilege);
                }
                context.getPrivilegeService().saveOrUpdatePrivilege(privilege);
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Error occurred, Try again.");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/edit")
    public String editPrivilege(HttpServletRequest request, Map<String, Object> model, @RequestParam("privilege") Privilege privilege) {

        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_PRIVILEGE)) {

            model.put("privilege", privilege);
            return "privilege/privilegeForm";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/delete")
    public String deletePrivilege(HttpServletRequest request, @RequestParam("privilege") Privilege privilege) {

        HttpSession session = request.getSession(true);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_RECORDS)) {
            context.getPrivilegeService().deletePrivilege(privilege);
            return "redirect:list";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/list")
    public String privilegeList(HttpServletRequest request, Map<String, Object> model) {

        HttpSession session = request.getSession(true);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_PRIVILEGE)) {
            model.put("privilegeList", context.getPrivilegeService().getAllPrivilege());
            return "privilege/privilegeList";
        } else {
            return "redirect:/login.htm";
        }

    }
}
