/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carrequisitionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToCarRequisitionServiceConverter implements Converter<String, Carrequisitionservice> {

    @Autowired
    ServiceContext context;

    @Override
    public Carrequisitionservice convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Carrequisitionservice carRequisitionService = context.getCarRequisitionServiceService().getCarRequisitionServiceById(Integer.valueOf(s));
        return carRequisitionService;
    }
}
