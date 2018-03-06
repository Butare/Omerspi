/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import jim.omerspi.OmerspiUtils;

/**
 *
 * @author JOHN
 */
@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class arg0) {
        return User.class.equals(arg0);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        User user = (User) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "createAccount.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "createAccount.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee", "createAccount.employee");

        if (OmerspiUtils.hasSpecial(user.getUserName())) {
            errors.rejectValue("userName", "createAccount.user.hasSpecial");
        }


    }

   
}
