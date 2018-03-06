/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Department;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JOHN
 */
@Component
public class DepartmentValidator implements Validator {

    @Override
    public boolean supports(Class dept) {
        return Department.class.isAssignableFrom(dept);
    }

    @Override
    public void validate(Object dep, Errors errors) {
        Department dept = (Department) dep;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departmentCode", "department.code");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departmentName", "department.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "functionalLocation", "department.location");

        if (OmerspiUtils.isNumber(dept.getDepartmentName()) && !dept.getDepartmentName().isEmpty()) {
            errors.rejectValue("departmentName", "department.name.hasCharacter");
        }
        if (OmerspiUtils.isNumber(dept.getFunctionalLocation()) && !dept.getFunctionalLocation().isEmpty()) {
            errors.rejectValue("functionalLocation", "department.location.hasCharacter");
        }

    }
}
