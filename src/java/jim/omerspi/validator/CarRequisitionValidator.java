/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import java.util.Date;
import jim.omerspi.model.Carrequisition;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JIMMY
 */
@Component
public class CarRequisitionValidator implements Validator {

    @Override
    public boolean supports(Class c) {
        return Carrequisition.class.isAssignableFrom(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Carrequisition carRequisition = (Carrequisition) target;

        Date departureDate = carRequisition.getDepartureTime();
        Date returnDate = carRequisition.getExpectedTimeReturn();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reason", "carRequisition.reason");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destination", "carRequisition.destination");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "destinationType", "carRequisition.destinationType");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "costingBasis", "carRequisition.costingBasis");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cartype", "carRequisition.cartype");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureTime", "carRequisition.departureTime.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expectedTimeReturn", "carRequisition.returnTime.empty");
       
        if (carRequisition.getCostingBasis().equals("itenerary") && carRequisition.getIteneraries()==null) {
            errors.rejectValue("iteneraries", "carRequisition.itenerary");
        }

    }
}
