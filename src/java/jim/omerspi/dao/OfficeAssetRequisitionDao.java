/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;

import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OfficeAssetRequisitionDao {

    public void saveOrUpdateOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition);

    public void deleteOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition);

    public Officeassetrequisition getOfficeAssetRequisitionById(Integer officeAssetRequisitionId);

    public List<Officeassetrequisition> getAllOfficeAssetRequisition();

    public List<Officeassetrequisition> getOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getOfficeAssetRequisitionByDepartment(Department department);

    public List<Officeassetrequisition> getAllHodApprovedOfficeAssetRequisition();

    public List<Officeassetrequisition> getAllDafRejectedOfficeAssetRequisition();

    public List<Officeassetrequisition> getAllDafApprovedOfficeAssetRequisition();

    public List<Officeassetrequisition> getAllServedOfficeAssetRequisition();

    public List<Officeassetrequisition> getAllCommentedOfficeAssetRequisition();

    public List<Officeassetrequisition> getAllHodSentOfficeAssetRequisition();

    public List<Officeassetrequisition> getHodPersonalApprovedOfficeAssetRequisition(Department department);

    public List<Officeassetrequisition> getHodPersonalRejectedOfficeAssetRequisition(Department department);

    
    //DashBoard DAO's
    public List<Officeassetrequisition> getHodPendingOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getHodRejectedOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getProfDafPendingOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getDafRejectedOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getHodDafPendingOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getAllDafPendingOfficeAssetRequisition();

    public List<Officeassetrequisition> getDafApprovedOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getServedOfficeAssetRequisitionByEmployee(Employee employee);

    public List<Officeassetrequisition> getCommentedOfficeAssetRequisitionByEmployee(Employee employee);
}
