/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import java.util.List;
import jim.omerspi.model.RequestedItems;

/**
 *
 * @author JOHN
 */
public class RequestedItemListForm {

    private List<RequestedItems> requestedItemsByRequisition;

    public List<RequestedItems> getRequestedItemsByRequisition() {
        return requestedItemsByRequisition;
    }

    public void setRequestedItemsByRequisition(List<RequestedItems> requestedItemsByRequisition) {
        this.requestedItemsByRequisition = requestedItemsByRequisition;
    }
}
