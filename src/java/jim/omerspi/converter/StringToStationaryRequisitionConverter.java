/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Stationaryrequisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToStationaryRequisitionConverter implements Converter<String,Stationaryrequisition> {

     @Autowired
    ServiceContext context;
    
    @Override
    public Stationaryrequisition convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Stationaryrequisition stationaryRequisition=context.getStationaryRequisitionService().getStationaryRequisitionById(Integer.valueOf(s));
        return stationaryRequisition;
    }
    
}
