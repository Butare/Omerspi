/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.validator;

import jim.omerspi.OmerspiUtils;
import jim.omerspi.model.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author JIMMY
 */
@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class cat) {
        return Category.class.isAssignableFrom(cat);
    }

    @Override
    public void validate(Object cat, Errors errors) {
        Category category = (Category) cat;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "category.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryDescription", "category.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryCode", "category.categoryCode");

        if (OmerspiUtils.isNumber(category.getCategoryName()) && !category.getCategoryName().isEmpty()) {
            errors.rejectValue("categoryName", "category.name.isDigit");
        }
        if(OmerspiUtils.isNumber(category.getCategoryDescription())&& !category.getCategoryDescription().isEmpty()){
            errors.rejectValue("categoryDescription", "category.description.isDigit");
        }
        if(OmerspiUtils.isCharacter(category.getCategoryCode())&& !category.getCategoryCode().isEmpty()){
            errors.rejectValue("categoryCode", "category.code.isCharacter");
        }
    }
}
