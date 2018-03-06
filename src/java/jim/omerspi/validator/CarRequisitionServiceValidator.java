/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Carrequisitionservice;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JOHN
 */
@Component
public class CarRequisitionServiceValidator implements Validator {

    @Override
    public boolean supports(Class carService) {
        return Carrequisitionservice.class.isAssignableFrom(carService);
    }

    @Override
    public void validate(Object car, Errors errors) {
        Carrequisitionservice carService = (Carrequisitionservice) car;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendor", "carService.vendorName");

        if (carService.getCarregistration() == null && carService.getNumberPlate().isEmpty()) {
            errors.rejectValue("numberPlate", "carService.numberPlate");

        }
        if (OmerspiUtils.isNumber(carService.getNumberPlate()) && !carService.getNumberPlate().isEmpty()) {
            errors.rejectValue("numberPlate", "carRegistration.plateNo.numbers");
        }
        if (OmerspiUtils.isCharacter(carService.getNumberPlate()) && !carService.getNumberPlate().isEmpty()) {
            errors.rejectValue("numberPlate", "carRegistration.plateNo.string");
        }

        if (carService.getEmployee() == null && carService.getDriverNames().isEmpty()) {
            errors.rejectValue("driverNames", "carService.driverNames");
        }
        if (OmerspiUtils.isNumber(carService.getDriverNames()) && !carService.getDriverNames().isEmpty()) {
            errors.rejectValue("driverNames", "carService.driver.isDigit");
        }

    }
}
