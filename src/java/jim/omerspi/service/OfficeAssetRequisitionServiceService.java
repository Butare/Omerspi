/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import jim.omerspi.model.Officeassetrequisitionservice;
import java.util.List;
import jim.omerspi.model.RequestedItems;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JIMMY
 */
@Transactional
public interface OfficeAssetRequisitionServiceService {

    public void saveOrUpdateOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService);

    public void deleteOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService);

    public Officeassetrequisitionservice getOfficeAssetRequisitionServiceById(Integer serviceId);

    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionService();

    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionServiceByRequestedItem(RequestedItems requested);
}
