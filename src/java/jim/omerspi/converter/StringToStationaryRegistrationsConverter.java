/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Stationaryregistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToStationaryRegistrationsConverter implements Converter<String, Stationaryregistration> {

    @Autowired
    ServiceContext context;

    @Override
    public Stationaryregistration convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        Stationaryregistration stationaryRegistrations = context.getStationaryRegistrationsService().getStationaryRegistrationsById(Integer.valueOf(s));
        return stationaryRegistrations;


    }
}
