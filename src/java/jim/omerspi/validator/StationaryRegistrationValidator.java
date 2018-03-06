/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Stationaryregistration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JOHN
 */
@Component
public class StationaryRegistrationValidator implements Validator {

    @Override
    public boolean supports(Class s) {
        return Stationaryregistration.class.isAssignableFrom(s);
    }

    @Override
    public void validate(Object stationary, Errors errors) {
        try {
            Stationaryregistration stationaryRegistrations = (Stationaryregistration) stationary;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryNoteNumber", "stationaryRegistration.deliveryNote");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specification", "stationaryRegistration.specification");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "items", "stationaryRegistration.item");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractNumber", "stationaryRegistration.contract");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendor", "stationaryRegistration.supplier");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purchasedQty", "stationaryRegistration.purchasedQty.null");

            if (stationaryRegistrations.getUnitPrice() == 0) {
                errors.rejectValue("unitPrice", "stationaryRegistration.price");
            }
            if (OmerspiUtils.isCharacter(stationaryRegistrations.getContractNumber()) && !stationaryRegistrations.getContractNumber().isEmpty()) {
                errors.rejectValue("contractNumber", "stationaryRegistration.contractNumber.isCharacter");
            }
            if (OmerspiUtils.isCharacter(stationaryRegistrations.getDeliveryNoteNumber()) && !stationaryRegistrations.getDeliveryNoteNumber().isEmpty()) {
                errors.rejectValue("deliveryNoteNumber", "stationaryRegistration.deliveryNote.isCharacter");
            }
             if (OmerspiUtils.isCharacter(stationaryRegistrations.getPurchaseOrder()) && !stationaryRegistrations.getPurchaseOrder().isEmpty()) {
                errors.rejectValue("purchaseOrder", "stationaryRegistration.purchaseOrder.isCharacter");
            }

            if (stationaryRegistrations.getPurchasedQty() == 0) {
                errors.rejectValue("purchasedQty", "stationaryRegistration.qty");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
