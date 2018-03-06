/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Vendor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class VendorToStringConverter implements Converter<Vendor,String> {

    @Override
    public String convert(Vendor obj) {
        Integer id=obj.getVendorRegistrationId();
        System.out.println("Converted Vendor ID : " + id);
        return id!=null?id.toString():"";
    }
    
}
