/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    @Autowired
    ServiceContext context;

    @Override
    public Category convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        Category cat = context.getCategoryService().getCategoryById(Integer.valueOf(s));
        System.out.println("Converted '" + s + "' to " + cat);
        return cat;

    }
}
