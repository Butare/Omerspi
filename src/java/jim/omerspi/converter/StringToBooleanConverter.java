/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToBooleanConverter implements Converter<String,Boolean> {

    @Override
    public Boolean convert(String s) {
       if(s.equals("true"))
           return true;
       else
           return false;
    }
    
}
