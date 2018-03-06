/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carrequisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToCarRequisitionConverter implements Converter<String, Carrequisition> {

    @Autowired
    ServiceContext context;

    @Override
    public Carrequisition convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Carrequisition carReq = context.getCarRequisitionService().getCarRequisitionById(Integer.valueOf(s));
        System.out.println("Converted '" + s + "' to " + carReq);
        return carReq;
    }
}
