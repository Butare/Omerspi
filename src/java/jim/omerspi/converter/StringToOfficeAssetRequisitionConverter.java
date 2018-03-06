/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Officeassetrequisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToOfficeAssetRequisitionConverter implements Converter<String, Officeassetrequisition> {

    @Autowired
    ServiceContext context;

    @Override
    public Officeassetrequisition convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Officeassetrequisition notfound = context.getOfficeAssetRequisitionService().getOfficeAssetRequisitionById(Integer.valueOf(s));
       
         System.out.println("Converted '" + s + "' to " + notfound);
        
        return notfound;
    }
}
