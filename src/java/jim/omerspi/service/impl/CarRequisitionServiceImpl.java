/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.Date;
import java.util.List;
import jim.omerspi.dao.CarRequisitionDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.service.CarRequisitionService;

/**
 *
 * @author JOHN
 */
public class CarRequisitionServiceImpl implements CarRequisitionService {

    private CarRequisitionDao carRequisitionDao;

    public void setCarRequisitionDao(CarRequisitionDao carRequisitionDao) {
        this.carRequisitionDao = carRequisitionDao;
    }

    @Override
    public void saveOrUpdateCarRequisition(Carrequisition carRequisition) {
        carRequisitionDao.saveOrUpdateCarRequisition(carRequisition);
    }

    @Override
    public void deleteCarRequisition(Carrequisition carRequisition) {
        carRequisitionDao.deleteCarRequisition(carRequisition);
    }

    @Override
    public Carrequisition getCarRequisitionById(Integer carRequisitionId) {
        return carRequisitionDao.getCarRequisitionById(carRequisitionId);
    }

    @Override
    public List getAllCarRequisition() {
        return carRequisitionDao.getAllCarRequisition();
    }

    @Override
    public List<Carrequisition> getCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getAllNotServedCarRequisition() {
        return carRequisitionDao.getAllNotServedCarRequisition();
    }

    @Override
    public List<Carrequisition> getAllHodApprovedCarRequisition() {
        return carRequisitionDao.getAllHodApprovedCarRequisition();
    }

    @Override
    public List<Carrequisition> getAllCarRequisitionByDepartment(Department department) {
        return carRequisitionDao.getAllCarRequisitionByDepartment(department);
    }

    @Override
    public List<Carrequisition> getAllHodSentCarRequisition() {
        return carRequisitionDao.getAllHodSentCarRequisition();
    }

    @Override
    public String getUserRole(Integer userRegistrationId) {
        return carRequisitionDao.getUserRole(userRegistrationId);
    }

    @Override
    public List<Carrequisition> getAllServedCarRequisition() {
        return carRequisitionDao.getAllServedCarRequisition();
    }

    @Override
    public List<Carrequisition> getAllCommentedCarRequisition() {
        return carRequisitionDao.getAllCommentedCarRequisition();
    }

    @Override
    public List<Carrequisition> getAllDafAcceptedCarRequisition() {
        return carRequisitionDao.getAllDafAcceptedCarRequisition();
    }

    @Override
    public List<Carrequisition> getAllDafRejectedCarRequisition() {
        return carRequisitionDao.getAllDafRejectedCarRequisition();
    }

    @Override
    public List<Carrequisition> getHodPersonalApprovedCarRequisition(Department department) {
        return carRequisitionDao.getHodPersonalApprovedCarRequisition(department);
    }

    @Override
    public List<Carrequisition> getHodPersonalRejectedCarRequisition(Department department) {
        return carRequisitionDao.getHodPersonalRejectedCarRequisition(department);
    }

    @Override
    public List<Carrequisition> getCarRequisitionBetweenDates(Date d1, Date d2) {
        return carRequisitionDao.getCarRequisitionBetweenDates(d1, d2);
    }

    @Override
    public List<Carrequisition> printCarRequisitionForm(Employee employee, Integer carRequisitionId) {
        return carRequisitionDao.printCarRequisitionForm(employee, carRequisitionId);
    }

    //DashBoard ServiceImpl's
    @Override
    public List<Carrequisition> getHodPendingCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getHodPendingCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getHodRejectedCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getHodRejectedCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getProfDafPendingCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getProfDafPendingCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getProfDafRejectedCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getProfDafRejectedCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getHodDafPendingCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getHodDafPendingCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getAllDafPendingCarRequisition() {
        return carRequisitionDao.getAllDafPendingCarRequisition();
    }

    @Override
    public List<Carrequisition> getDafApprovedCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getDafApprovedCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getServedCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getServedCarRequisitionByEmployee(employee);
    }

    @Override
    public List<Carrequisition> getCommentedCarRequisitionByEmployee(Employee employee) {
        return carRequisitionDao.getCommentedCarRequisitionByEmployee(employee);
    }
}
