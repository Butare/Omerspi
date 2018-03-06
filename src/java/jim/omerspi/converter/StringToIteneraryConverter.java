/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Itenerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToIteneraryConverter implements Converter<String, Itenerary> {

    @Autowired
    ServiceContext context;

    @Override
    public Itenerary convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        Itenerary itene = context.getIteneraryService().getIteneraryById(Integer.valueOf(s));
        System.out.println("Converted Itenerary '" + s + "' to " + itene);
        return itene;

    }
}
