/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Privilege;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JOHN
 */
@Component
public class PrivilegeValidator implements Validator {

    @Override
    public boolean supports(Class priv) {
        return Privilege.class.isAssignableFrom(priv);
    }

    @Override
    public void validate(Object priv, Errors errors) {
        Privilege privilege = (Privilege) priv;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "privilege", "privilege.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "previlageDescription", "privilege.description");
        
        if(OmerspiUtils.isNumber(privilege.getPrivilege())&&!privilege.getPrivilege().isEmpty()){
         errors.rejectValue("privilege", "privilege.privilege.isDigit");
        }
        if(OmerspiUtils.isNumber(privilege.getPrevilageDescription())&& !privilege.getPrevilageDescription().isEmpty()){
        errors.rejectValue("previlageDescription", "privilege.description.isDigit");
        }
        
    }
}
