/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Cartype;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JIMMY
 */
@Component
public class CarTypeValidator implements Validator {

    @Override
    public boolean supports(Class carT) {
        return Cartype.class.isAssignableFrom(carT);
    }

    @Override
    public void validate(Object carT, Errors errors) {
        Cartype carType = (Cartype) carT;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeName", "carType.name");

        if (OmerspiUtils.isNumber(carType.getTypeName()) && !carType.getTypeName().isEmpty()) {
            errors.rejectValue("typeName", "carType.name.isDigit");
        }
    }
}
