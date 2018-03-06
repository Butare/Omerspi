/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Stationaryrequisition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StationaryRequisitionToStringConverter implements Converter<Stationaryrequisition, String> {

    @Override
    public String convert(Stationaryrequisition obj) {
       Integer id = obj.getStationaryRequisitionId();
        System.out.println("Converted ID : "+id);
        return id != null ? id.toString() : "";
    }
    
}
