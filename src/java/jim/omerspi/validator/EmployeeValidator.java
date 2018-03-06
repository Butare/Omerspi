/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author JOHN
 */
@Component
public class EmployeeValidator implements Validator {

    @Override
    public boolean supports(Class c) {
        return Employee.class.isAssignableFrom(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee emp = (Employee) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeCode", "employee.employeeCode");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "employee.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "employee.firstname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "employee.lastname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "department", "employee.department");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobPosition", "employee.jobPosition");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nationalId", "employee.nationalId");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "employee.phoneNumber");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workEmail", "employee.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "employee.state");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "driver", "employee.driver");
        if (emp.getDriver() == true) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "licenceNumber", "employee.licence");
        }
        if (emp.getDriver() == false) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identificationWord", "employee.identificationWord");
        }

        if (emp.getNationalId().trim().length() < 10) {
            errors.rejectValue("nationalId", "employee.nationalId.size");
        }
        if(!OmerspiUtils.isNumber(emp.getTelephone())){
            errors.rejectValue("telephone", "employee.phoneNumber.number");
        }
    }

  
}
