/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Itenerary;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JOHN
 */
@Component
public class IteneraryValidator implements Validator {

    @Override
    public boolean supports(Class iten) {
        return Itenerary.class.isAssignableFrom(iten);
    }

    @Override
    public void validate(Object iten, Errors errors) {
        Itenerary itenerary = (Itenerary) iten;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "iteneraryDetail", "itenerary.detail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cost", "itenerary.cost");

        if (OmerspiUtils.isNumber(itenerary.getIteneraryDetail()) && !itenerary.getIteneraryDetail().isEmpty()) {
            errors.rejectValue("iteneraryDetail", "itenerary.detail.isDigit");
        }
//        if(!OmerspiUtils.isNumber(itenerary.getCost()+"")&& itenerary.getCost().intValue()!=0){
//            errors.rejectValue("cost", "itenerary.cost.notDigit");
//            
//        }

    }
}
