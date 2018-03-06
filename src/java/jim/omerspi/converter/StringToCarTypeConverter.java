/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Cartype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToCarTypeConverter implements Converter<String, Cartype> {

    @Autowired
    ServiceContext context;

    @Override
    public Cartype convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Cartype car = context.getCartypeService().getCartypeById(Integer.valueOf(s));
        return car;
    }
}
