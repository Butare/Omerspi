/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carregistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToCarRegistrationConverter implements Converter<String,Carregistration>{

     @Autowired
    ServiceContext context;
    
    @Override
    public Carregistration convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Carregistration carRegistration=context.getCarRegistrationService().getCarRegistrationById(Integer.valueOf(s));
          System.out.println("Converted Carregistration: '" + s + "' to " + carRegistration);
        return carRegistration;
    }
    
}
