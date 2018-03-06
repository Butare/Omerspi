/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.Requisitionresponses;
import jim.omerspi.model.Stationaryrequisition;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface RequisitionResponseDao {

    public void saveOrUpdateRequisitionResponse(Requisitionresponses requisitionResponse);

    public void deleteRequisitionResponse(Requisitionresponses requisitionResponse);

    public Requisitionresponses getRequisitionResponseById(Integer requisitionResponseId);

    public List<Requisitionresponses> getAllRequisitionResponse();

    public List<Requisitionresponses> getResponseByCarRequisitionId(Carrequisition carRequisition);

    public List<Requisitionresponses> getResponseByStationaryRequisitionId(Stationaryrequisition stationaryRequisition);

    public List<Requisitionresponses> getResponseByOfficeAssetRequisitionId(Officeassetrequisition officeAssetRequisition);
}
