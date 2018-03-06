/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Requisitionresponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToRequisitionResponseConverter implements Converter<String, Requisitionresponses> {

    @Autowired
    ServiceContext context;

    @Override
    public Requisitionresponses convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        Requisitionresponses requisitionResponse = context.getRequisitionResponseService().getRequisitionResponseById(Integer.valueOf(s));
        return requisitionResponse;

    }
}
