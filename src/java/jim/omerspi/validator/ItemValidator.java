/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Items;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JOHN
 */
@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class ite) {
        return Items.class.isAssignableFrom(ite);
    }

    @Override
    public void validate(Object ite, Errors errors) {
        Items item = (Items) ite;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "item.name");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categorytype", "item.categorytype");
        
        if(OmerspiUtils.isNumber(item.getItemName())&& !item.getItemName().isEmpty()){
            errors.rejectValue("itemName", "item.name.isDigit");
        }
    }
}
