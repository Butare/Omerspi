/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Memo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToMemoConverter implements Converter<String, Memo> {

    @Autowired
    ServiceContext context;

    @Override
    public Memo convert(String s) {

        if (s == null || s.length() == 0) {
            return null;
        }
        Memo m = context.getMemoService().getMemoById(Integer.valueOf(s));
        return m;
    }
}
