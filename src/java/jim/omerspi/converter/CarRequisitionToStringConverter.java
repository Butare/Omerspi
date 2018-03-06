/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Carrequisition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class CarRequisitionToStringConverter implements Converter<Carrequisition,String>{

    @Override
    public String convert(Carrequisition obj) {
        Integer id=obj.getCarRequisitionId();
        System.out.println("Converted ID : "+id);
         return id != null ? id.toString() : "";
    }
    
}
