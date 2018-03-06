/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToUserConverter implements Converter<String,User>{

      @Autowired
    ServiceContext context;
    
    @Override
    public User convert(String s) {
         if (s == null || s.length() == 0) {
            return null;
        }
         User user=context.getUserService().getUserById(Integer.valueOf(s));
         return user;
    }
    
}
