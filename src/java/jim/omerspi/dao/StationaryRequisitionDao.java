/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface StationaryRequisitionDao {

    public void saveOrUpdateStationaryRequisition(Stationaryrequisition stationaryRequisition);

    public void deleteStationaryRequisition(Stationaryrequisition stationaryRequisition);

    public Stationaryrequisition getStationaryRequisitionById(Integer stationaryRequisitionId);

    public List getAllStationaryRequisition();

    public List<Stationaryrequisition> getStationaryRequisitionByEmployee(Employee employee);

    public List<Stationaryrequisition> getStationaryRequisitionByDepartment(Department department);

    public List<Stationaryrequisition> getAllHodApprovedStationaryRequisition();

    public List<Stationaryrequisition> getAllHodSentStationaryRequisition();

    public List<Stationaryrequisition> getAllDafApprovedStationaryRequisition();
    
    public List<Stationaryrequisition> getAllDafRejectedStationaryRequisition();

    public List<Stationaryrequisition> getAllServedStationaryRequisition();

    public List<Stationaryrequisition> getAllCommentedStationaryRequisition();

    public List<Stationaryrequisition> getHodPersonalApprovedStationaryRequisition(Department department);
    
    public List<Stationaryrequisition> getHodPersonalRejectedStationaryRequisition(Department department);
    
    public List<Stationaryrequisition> printStationaryRequisitionForm(Employee employee,Integer stationaryRequisitionId);
    
    public List<User> getHoD(Department department);
    
    //DashBoard DAO's
    public List<Stationaryrequisition>  getHodPendingStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition>  getHodRejectedStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition>  getProfDafPendingStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition>  getDafRejectedStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition>  getHodDafPendingStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition>  getAllDafPendingStationaryRequisition();
    
    public List<Stationaryrequisition> getDafApprovedStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition> getServedStationaryRequisitionByEmployee(Employee employee);
    public List<Stationaryrequisition> getCommentedStationaryRequisitionByEmployee(Employee employee);
}
