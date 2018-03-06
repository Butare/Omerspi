/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.OfficeAssetRequisitionServiceDao;
import jim.omerspi.model.Officeassetrequisitionservice;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.service.OfficeAssetRequisitionServiceService;

/**
 *
 * @author JIMMY
 */
public class OfficeAssetRequisitionServiceServiceImpl implements OfficeAssetRequisitionServiceService {

    private OfficeAssetRequisitionServiceDao officeAssetRequisitionServiceDao;

    public void setOfficeAssetRequisitionServiceDao(OfficeAssetRequisitionServiceDao officeAssetRequisitionServiceDao) {
        this.officeAssetRequisitionServiceDao = officeAssetRequisitionServiceDao;
    }

    @Override
    public void saveOrUpdateOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService) {
        officeAssetRequisitionServiceDao.saveOrUpdateOfficeAssetRequisitionService(officeAssetRequisitionService);
    }

    @Override
    public void deleteOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService) {
        officeAssetRequisitionServiceDao.deleteOfficeAssetRequisitionService(officeAssetRequisitionService);
    }

    @Override
    public Officeassetrequisitionservice getOfficeAssetRequisitionServiceById(Integer serviceId) {
        return officeAssetRequisitionServiceDao.getOfficeAssetRequisitionServiceById(serviceId);
    }

    @Override
    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionService() {
        return officeAssetRequisitionServiceDao.getAllOfficeAssetRequisitionService();
    }

    @Override
    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionServiceByRequestedItem(RequestedItems requested) {
        return officeAssetRequisitionServiceDao.getAllOfficeAssetRequisitionServiceByRequestedItem(requested);
    }
}
