/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToItemConverter implements Converter<String, Items> {

    @Autowired
    ServiceContext context;

    @Override
    public Items convert(String s) {

        if (s == null || s.length() == 0) {
            return null;
        }
        Items item = context.getItemService().getItemById(Integer.valueOf(s));
        return item;
    }
}
