/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.model.Items;

/**
 *
 * @author JOHN
 */
public class ItemRequest {

    private Map<Integer, Integer> items = new LinkedHashMap<Integer, Integer>();
    
    public static ItemRequest getCurrent(HttpSession session) {
        ItemRequest current = (ItemRequest) session.getAttribute(Constants.ITEMREQUEST_SESSION_ATTR);
        
        if (current == null) {
            current = new ItemRequest();
            session.setAttribute(Constants.ITEMREQUEST_SESSION_ATTR, current);
        }
        
        return current;
    }
    
    public void addItem(Items item, int quantity) {
        items.put(item.getItemId(), quantity);
    }
    
    public Map<Items, Integer> getItems() {
        Map<Items, Integer> temp = new LinkedHashMap<Items, Integer>();
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Items item = Context.getItemService().getItemById(entry.getKey());
            int quantity = entry.getValue();
            temp.put(item, quantity);
        }
        return temp;
    }
    
    public Integer getQuantity(Items item) {
        return items.get(item.getItemId());
    }
    
    public void setQuantity(Items item, int quantity) {
        if (quantity == 0) {
            items.remove(item.getItemId());
        }
        else {
            items.put(item.getItemId(), quantity);
            
        }
    }
    
    public void clear() {
        items.clear();
    }
    
    
    
}
