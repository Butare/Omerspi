/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.Date;
import java.util.List;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryrequisition;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface RequestedItemDao {

    public void saveOrUpdateRequestedItem(RequestedItems requestedItem);

    public void deleteRequestedItem(RequestedItems requestedItem);

    public RequestedItems getRequestedItemById(Integer requestedId);

    public List getAllRequestedItem();

    public List<RequestedItems> getRequestedItemByStationaryRequisition(Stationaryrequisition stationaryRequisition);

    public List<RequestedItems> getServedItemBetweenDates(Date d1, Date d2);

    public List<RequestedItems> getServedItemGroupedByCategory(Date d1, Date d2);

    public List<RequestedItems> getServedItemGroupedByItem(Date d1, Date d2);
    
    //for office asset requisition
    
    public List<RequestedItems> getRequestedItemByOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition);
}
