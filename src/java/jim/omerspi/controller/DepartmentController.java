/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Department;
import jim.omerspi.validator.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private ServiceContext context;
    @Autowired
    DepartmentValidator departmentValidator;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String newDepartment(Map<String, Object> model, @ModelAttribute("department") Department department) {
        model.put("department", department);
        return "department/departmentForm";
    }

    @RequestMapping("/edit")
    public String editDepartment(Map<String, Object> model, @RequestParam("departmentId") Department dept) {
        model.put("department", dept);
        return "department/departmentForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdateDepartment(HttpSession session, Map<String, Object> model, @ModelAttribute("department") Department department, BindingResult result) {
        try {
            departmentValidator.validate(department, result);
            if (result.hasErrors()) {
                return newDepartment(model, department);
            } else {
                context.getDepartmentService().saveOrUpdateDepartment(department);
                return "redirect:list";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occured, Try again");
            return "redirect:list";
        }
    }

    @RequestMapping("/list")
    public String departmentList(Map<String, Object> model) {

        model.put("departmentList", context.getDepartmentService().getAllDepartment());
        return "department/departmentList";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteDepartment(HttpSession session, @RequestParam("departmentId") Department dept) {
        try {
            context.getDepartmentService().deleteDepartment(dept);
            return "redirect:list";
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Can't be deleted, is used by other object(s)");
            return "redirect:list";
        }
    }
}
