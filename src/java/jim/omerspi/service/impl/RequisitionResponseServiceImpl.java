/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.RequisitionResponseDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.Requisitionresponses;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.service.RequisitionResponseService;

/**
 *
 * @author JOHN
 */
public class RequisitionResponseServiceImpl implements RequisitionResponseService {

    private RequisitionResponseDao requisitionResponseDao;

    public void setRequisitionResponseDao(RequisitionResponseDao requisitionResponseDao) {
        this.requisitionResponseDao = requisitionResponseDao;
    }

    @Override
    public void saveOrUpdateRequisitionResponse(Requisitionresponses requisitionResponse) {
        requisitionResponseDao.saveOrUpdateRequisitionResponse(requisitionResponse);
    }

    @Override
    public void deleteRequisitionResponse(Requisitionresponses requisitionResponse) {
        requisitionResponseDao.deleteRequisitionResponse(requisitionResponse);
    }

    @Override
    public Requisitionresponses getRequisitionResponseById(Integer requisitionResponseId) {
        return requisitionResponseDao.getRequisitionResponseById(requisitionResponseId);
    }

    @Override
    public List<Requisitionresponses> getAllRequisitionResponse() {
        return requisitionResponseDao.getAllRequisitionResponse();
    }

    @Override
    public List<Requisitionresponses> getResponseByCarRequisitionId(Carrequisition carRequisition) {
        return requisitionResponseDao.getResponseByCarRequisitionId(carRequisition);
    }

    @Override
    public List<Requisitionresponses> getResponseByStationaryRequisitionId(Stationaryrequisition stationaryRequisition) {
        return requisitionResponseDao.getResponseByStationaryRequisitionId(stationaryRequisition);
    }

    @Override
    public List<Requisitionresponses> getResponseByOfficeAssetRequisitionId(Officeassetrequisition officeAssetRequisition) {
        return requisitionResponseDao.getResponseByOfficeAssetRequisitionId(officeAssetRequisition);
    }
}
