/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.RequestedItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class StringToRequestedItemsConverter implements Converter<String, RequestedItems> {

    @Autowired
    ServiceContext context;

    @Override
    public RequestedItems convert(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        RequestedItems requestedItem = context.getRequestedItemService().getRequestedItemById(Integer.valueOf(s));
        System.out.println("Converted to RequestedItem : '" + s + "' to " + requestedItem);
        return requestedItem;
    }
}
