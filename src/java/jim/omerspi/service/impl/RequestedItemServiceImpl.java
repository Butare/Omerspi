/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.Date;
import java.util.List;
import jim.omerspi.dao.RequestedItemDao;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.service.RequestedItemService;

/**
 *
 * @author JIMMY
 */
public class RequestedItemServiceImpl implements RequestedItemService {
 
    private RequestedItemDao requestedItemDao;

    public void setRequestedItemDao(RequestedItemDao requestedItemDao) {
        this.requestedItemDao = requestedItemDao;
    }

    @Override
    public void saveOrUpdateRequestedItem(RequestedItems requestedItem) {
        requestedItemDao.saveOrUpdateRequestedItem(requestedItem);
    }

    @Override
    public void deleteRequestedItem(RequestedItems requestedItem) {
        requestedItemDao.deleteRequestedItem(requestedItem);
    }

    @Override
    public RequestedItems getRequestedItemById(Integer requestedId) {
        return requestedItemDao.getRequestedItemById(requestedId);
    }

    @Override
    public List getAllRequestedItem() {
       return requestedItemDao.getAllRequestedItem();
    }

    @Override
    public List<RequestedItems> getRequestedItemByStationaryRequisition(Stationaryrequisition stationaryRequisition) {
       return requestedItemDao.getRequestedItemByStationaryRequisition(stationaryRequisition);
    }

    @Override
    public List<RequestedItems> getServedItemBetweenDates(Date d1, Date d2) {
        return requestedItemDao.getServedItemBetweenDates(d1, d2);
    }

    @Override
    public List<RequestedItems> getServedItemGroupedByCategory(Date d1, Date d2) {
       return requestedItemDao.getServedItemGroupedByCategory(d1, d2);
    }

    @Override
    public List<RequestedItems> getServedItemGroupedByItem(Date d1, Date d2) {
        return requestedItemDao.getServedItemGroupedByItem(d1, d2);
    }

    @Override
    public List<RequestedItems> getRequestedItemByOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition) {
        return requestedItemDao.getRequestedItemByOfficeAssetRequisition(officeAssetRequisition);
    }
    
}
