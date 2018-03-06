/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Carregistration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class CarRegistrationToStringConverter implements Converter<Carregistration, String> {

    @Override
    public String convert(Carregistration obj) {
        Integer id=obj.getCarRegistrationId();
        return id!=null?id.toString():"";
    }
    
}
