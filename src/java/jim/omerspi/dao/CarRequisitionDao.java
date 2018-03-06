/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.Date;
import java.util.List;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface CarRequisitionDao {

    public void saveOrUpdateCarRequisition(Carrequisition carRequisition);

    public void deleteCarRequisition(Carrequisition carRequisition);

    public Carrequisition getCarRequisitionById(Integer carRequisitionId);

    public List getAllCarRequisition();

    public List<Carrequisition> getAllNotServedCarRequisition();

    public List<Carrequisition> getAllServedCarRequisition();

    public List<Carrequisition> getAllCommentedCarRequisition();

    public List<Carrequisition> getAllHodApprovedCarRequisition();

    public List<Carrequisition> getAllHodSentCarRequisition();

    public List<Carrequisition> getCarRequisitionByEmployee(Employee employee);

    public List<Carrequisition> getAllCarRequisitionByDepartment(Department department);
    
    public String getUserRole(Integer userRegistrationId);

    public List<Carrequisition> getAllDafAcceptedCarRequisition();

    public List<Carrequisition> getAllDafRejectedCarRequisition();

    public List<Carrequisition> getHodPersonalApprovedCarRequisition(Department department);

    public List<Carrequisition> getHodPersonalRejectedCarRequisition(Department department);
    
    public List<Carrequisition> getCarRequisitionBetweenDates(Date d1,Date d2);
    
    public List<Carrequisition> printCarRequisitionForm(Employee employee,Integer carRequisitionId);
    
    // DashBoard DAO's
    
    public List<Carrequisition> getHodPendingCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getHodRejectedCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getProfDafPendingCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getProfDafRejectedCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getHodDafPendingCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getAllDafPendingCarRequisition();
    
    public List<Carrequisition> getDafApprovedCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getServedCarRequisitionByEmployee(Employee employee);
    public List<Carrequisition> getCommentedCarRequisitionByEmployee(Employee employee);

}
