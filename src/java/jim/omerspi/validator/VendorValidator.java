/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;


import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Vendor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JIMMY
 */
@Component
public class VendorValidator implements Validator {

    @Override
    public boolean supports(Class ven) {
        return Vendor.class.isAssignableFrom(ven);
    }

    @Override
    public void validate(Object vend, Errors errors) {
        Vendor vendor = (Vendor) vend;

       

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendor", "vendor.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "vendor.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "vendor.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "vendor.phone");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderNumber", "vendor.tender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderPurpose", "vendor.purpose");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startingPeriod", "vendor.startPeriod");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endingPeriod", "vendor.endPeriod");

        if (!OmerspiUtils.isEmailValid(vendor.getEmail()) && !vendor.getEmail().isEmpty()) {
            errors.rejectValue("email", "vendor.email.invalid");
        }
        if (OmerspiUtils.isNumber(vendor.getAddress()) && !vendor.getAddress().isEmpty()) {
            errors.rejectValue("address", "vendor.address.isDigit");
        }
        if (OmerspiUtils.isCharacter(vendor.getTenderNumber()) && !vendor.getTenderNumber().isEmpty()) {
            errors.rejectValue("tenderNumber", "vendor.tenderNo.isCharacter");
        }
        
    }
}
