/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToVendorConverter implements Converter<String, Vendor> {

    @Autowired
    ServiceContext context;

    @Override
    public Vendor convert(String s) {

        if (s == null || s.length() == 0) {
            return null;
        }

        Vendor vendor = context.getVendorService().getVendorById(Integer.valueOf(s));
         System.out.println("Converted to Vendor : '" + s + "' to " + vendor);
        return vendor;
    }
}
