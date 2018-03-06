/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import jim.omerspi.model.Officeassetrequisitionservice;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import jim.omerspi.model.RequestedItems;

@Transactional
public interface OfficeAssetRequisitionServiceDao {

    public void saveOrUpdateOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService);

    public void deleteOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService);

    public Officeassetrequisitionservice getOfficeAssetRequisitionServiceById(Integer serviceId);

    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionService();

    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionServiceByRequestedItem(RequestedItems requested);
}
