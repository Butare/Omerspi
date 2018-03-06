/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Requisitionresponses;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class RequisitionResponseToStringConverter implements Converter<Requisitionresponses, String> {

    @Override
    public String convert(Requisitionresponses obj) {
        Integer id = obj.getRequisitionResponseId();
        System.out.println("Converted Response id ID : " + id);
        if (obj.getCarrequisition() == null || obj.getStationaryrequisition() == null || obj.getOfficeassetrequisition() == null) {
            return id != null ? id.toString() : "";
        }
        return id != null ? id.toString() : "";
    }
}
