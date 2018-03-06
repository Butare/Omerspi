/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import java.util.Date;
import java.text.SimpleDateFormat;

import jim.omerspi.Constants;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

    @Override
    public Date convert(String source) {
        try {
            if (source == null) {
                return new Date();
            }
            System.out.println("Converted Date : "+source);
            return sdf.parse(source);
        } catch (Exception ex) {
            return null;
        }
    }
}
