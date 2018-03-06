/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.AuthenticationException;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Jimmy
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private EmployeeValidator employeeValidator;

    //FORM CONTROLLERS
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String setEmployeeForm(@ModelAttribute(value = "employee") Employee employee, Map<String, Object> model, HttpSession session) {

        //Employee employee = new Employee();
        if (!Context.hasPrivilege(session, PrivilegeConstants.VIEW_EMPLOYEE)) {
            throw new AuthenticationException(PrivilegeConstants.VIEW_EMPLOYEE);
        }
        if (employee == null) {
            employee.setDriver(Boolean.FALSE);
        }
        System.out.append("Driver is : " + employee.getDriver());

        model.put("employee", employee);

        Map<String, String> sex = new LinkedHashMap<String, String>();
        sex.put("M", "Male");
        sex.put("F", "Female");
        model.put("sexList", sex);

        List<Department> departments = context.getDepartmentService().getAllDepartment();
        model.put("departmentList", departments);

        return "employee/employeeForm";

    }

    //SAVING CONTROLLERS
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdateEmployee(Map<String, Object> model, @ModelAttribute(value = "employee") Employee empVal, BindingResult result, HttpSession session) {
        String form = "";
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_EMPLOYEE)) {
            try {

                employeeValidator.validate(empVal, result);
                if (empVal.getDriver() != null) {

                    if (empVal.getFirstDate() == null) {
                        Date date = new Date();
                        empVal.setFirstDate(date);
                    }
                    if (!empVal.getDriver()) {
                        empVal.setLicenceNumber("");

                    }
                    if (empVal.getDriver()) {
                        empVal.setIdentificationWord("");

                    }

                    if (result.hasErrors()) {
                        return setEmployeeForm(empVal, model, session);
                    } else {
                        context.getEmployeeService().saveOrUpdateEmployee(empVal);
                        form = "redirect:list";
                        return form;
                    }

                }

            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Error Ocurred, Verify employee code.");
                return setEmployeeForm(empVal, model, session);
            }
            return form;
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/edit")
    public String editEmployee(Map<String, Object> model, @RequestParam("employeeId") Employee emp) {
        model.put("employee", emp);

        Map<String, String> sex = new LinkedHashMap<String, String>();
        sex.put("M", "Male");
        sex.put("F", "Female");
        model.put("sexList", sex);

        List<Department> departments = context.getDepartmentService().getAllDepartment();
        model.put("departmentList", departments);

        return "employee/employeeForm";
    }

    //LIST CONTROLLERS
    @RequestMapping("/list")
    public String employeeList(Map<String, Object> model, HttpSession session) {

        if (!Context.hasPrivilege(session, PrivilegeConstants.VIEW_EMPLOYEE)) {
            throw new AuthenticationException(PrivilegeConstants.VIEW_EMPLOYEE);
        } else {
            model.put("employeeList", context.getEmployeeService().getAllEmployee());
            return "employee/employeeList";
        }
    }

    // DELETING CONTROLLERS
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteEmployee(HttpSession session, @RequestParam("employeeId") Employee employee) {
        try {
            context.getEmployeeService().deleteEmployee(employee);
            return "redirect:list";
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Can't be deleted, used by other object(s)");
            return "redirect:list";
        }
    }
}
