/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToPrivilegeConverter implements Converter<String, Privilege> {

    @Autowired
    ServiceContext context;

    @Override
    public Privilege convert(String s) {

        if (s == null || s.length() == 0) {
            return null;
        }

        Privilege pr = context.getPrivilegeService().getPrivilegeByName(s);
        System.out.println("Converted to Privilege : '" + s + "' to " + pr);
        return pr;
    }
}
