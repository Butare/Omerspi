/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Categorytype;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author JIMMY
 */
@Component
public class StringToCategoryTypeConverter implements Converter<String, Categorytype> {

    @Autowired
    ServiceContext context;

    @Override
    public Categorytype convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Categorytype categoryType = context.getCategoryTypeService().getCategoryTypeById(Integer.valueOf(s));
        return categoryType;
    }
}
