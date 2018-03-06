/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Officeassetrequisition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class OfficeAssetRequisitionToStringConverter implements Converter<Officeassetrequisition, String> {
    
    @Override
    public String convert(Officeassetrequisition obj) {       
        Integer id = obj.getOfficeAssetRequisitionId();
        System.out.println("Converted ID : "+id);
        return id != null ? id.toString() : "";
    }
}
