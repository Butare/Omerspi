/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Carregistration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JIMMY
 */
@Component
public class CarRegistrationValidator implements Validator {

    @Override
    public boolean supports(Class a) {
        return Carregistration.class.isAssignableFrom(a);
    }

    @Override
    public void validate(Object cars, Errors errors) {
        Carregistration carRegistration = (Carregistration) cars;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plateNo", "carRegistration.plate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "carRegistration.model");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "carRegistration.status");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "carRegistration.country");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendorName", "carRegistration.vendor");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "carCondition", "carRegistration.condition");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "carServiceStatus", "carRegistration.service");

        if (OmerspiUtils.isNumber(carRegistration.getPlateNo()) && !carRegistration.getPlateNo().isEmpty()) {
            errors.rejectValue("plateNo", "carRegistration.plateNo.numbers");
        }
        if (OmerspiUtils.isCharacter(carRegistration.getPlateNo()) && !carRegistration.getPlateNo().isEmpty()) {
            errors.rejectValue("plateNo", "carRegistration.plateNo.string");
        }
    }
}
