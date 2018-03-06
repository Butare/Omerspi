/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.StationaryRequisitionDao;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import jim.omerspi.service.StationaryRequisitionService;

/**
 *
 * @author JOHN
 */
public class StationaryRequisitionServiceImpl implements StationaryRequisitionService {

    private StationaryRequisitionDao stationaryRequisitionDao;

    public void setStationaryRequisitionDao(StationaryRequisitionDao stationaryRequisitionDao) {
        this.stationaryRequisitionDao = stationaryRequisitionDao;
    }

    @Override
    public void saveOrUpdateStationaryRequisition(Stationaryrequisition stationaryRequisition) {
        stationaryRequisitionDao.saveOrUpdateStationaryRequisition(stationaryRequisition);
    }

    @Override
    public void deleteStationaryRequisition(Stationaryrequisition stationaryRequisition) {
        stationaryRequisitionDao.deleteStationaryRequisition(stationaryRequisition);
    }

    @Override
    public Stationaryrequisition getStationaryRequisitionById(Integer stationaryRequisitionId) {
        return stationaryRequisitionDao.getStationaryRequisitionById(stationaryRequisitionId);
    }

    @Override
    public List getAllStationaryRequisition() {
        return stationaryRequisitionDao.getAllStationaryRequisition();
    }

    @Override
    public List<Stationaryrequisition> getStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getStationaryRequisitionByDepartment(Department department) {
        return stationaryRequisitionDao.getStationaryRequisitionByDepartment(department);
    }

    @Override
    public List<Stationaryrequisition> getAllHodApprovedStationaryRequisition() {
        return stationaryRequisitionDao.getAllHodApprovedStationaryRequisition();
    }

    @Override
    public List<Stationaryrequisition> getAllHodSentStationaryRequisition() {
        return stationaryRequisitionDao.getAllHodSentStationaryRequisition();
    }

    @Override
    public List<Stationaryrequisition> getAllDafApprovedStationaryRequisition() {
        return stationaryRequisitionDao.getAllDafApprovedStationaryRequisition();
    }
    
      @Override
    public List<Stationaryrequisition> getAllDafRejectedStationaryRequisition() {
        return stationaryRequisitionDao.getAllDafRejectedStationaryRequisition();
    }


    @Override
    public List<Stationaryrequisition> getAllServedStationaryRequisition() {
        return stationaryRequisitionDao.getAllServedStationaryRequisition();
    }

    @Override
    public List<Stationaryrequisition> getAllCommentedStationaryRequisition() {
        return stationaryRequisitionDao.getAllCommentedStationaryRequisition();
    }

    @Override
    public List<Stationaryrequisition> getHodPersonalApprovedStationaryRequisition(Department department) {
        return stationaryRequisitionDao.getHodPersonalApprovedStationaryRequisition(department);
    }

    @Override
    public List<Stationaryrequisition> getHodPersonalRejectedStationaryRequisition(Department department) {
        return stationaryRequisitionDao.getHodPersonalRejectedStationaryRequisition(department);
    }

    @Override
    public List<Stationaryrequisition> printStationaryRequisitionForm(Employee employee, Integer stationaryRequisitionId) {
        return stationaryRequisitionDao.printStationaryRequisitionForm(employee, stationaryRequisitionId);
    }
    
     @Override
    public List<User> getHoD(Department department) {
        return stationaryRequisitionDao.getHoD(department);
    }
    
    //DashBoard ServiceImpl's

    @Override
    public List<Stationaryrequisition> getHodPendingStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getHodPendingStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getHodRejectedStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getHodRejectedStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getProfDafPendingStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getProfDafPendingStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getDafRejectedStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getDafRejectedStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getHodDafPendingStationaryRequisitionByEmployee(Employee employee) {
       return stationaryRequisitionDao.getHodDafPendingStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getAllDafPendingStationaryRequisition() {
        return stationaryRequisitionDao.getAllDafPendingStationaryRequisition();
    }

    @Override
    public List<Stationaryrequisition> getDafApprovedStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getDafApprovedStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getServedStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getServedStationaryRequisitionByEmployee(employee);
    }

    @Override
    public List<Stationaryrequisition> getCommentedStationaryRequisitionByEmployee(Employee employee) {
        return stationaryRequisitionDao.getCommentedStationaryRequisitionByEmployee(employee);
    }

   
}
