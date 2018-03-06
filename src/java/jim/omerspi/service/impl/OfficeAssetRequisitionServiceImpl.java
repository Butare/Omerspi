/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.OfficeAssetRequisitionDao;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.service.OfficeAssetRequisitionService;

/**
 *
 * @author JOHN
 */
public class OfficeAssetRequisitionServiceImpl implements OfficeAssetRequisitionService {

    private OfficeAssetRequisitionDao officeAssetRequisitionDao;

    public void setOfficeAssetRequisitionDao(OfficeAssetRequisitionDao officeAssetRequisitionDao) {
        this.officeAssetRequisitionDao = officeAssetRequisitionDao;
    }

    @Override
    public void saveOrUpdateOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition) {
        officeAssetRequisitionDao.saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
    }

    @Override
    public void deleteOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition) {
        officeAssetRequisitionDao.deleteOfficeAssetRequisition(officeAssetRequisition);
    }

    @Override
    public Officeassetrequisition getOfficeAssetRequisitionById(Integer officeAssetRequisitionId) {
        return officeAssetRequisitionDao.getOfficeAssetRequisitionById(officeAssetRequisitionId);
    }

    @Override
    public List<Officeassetrequisition> getAllOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getOfficeAssetRequisitionByDepartment(Department department) {
        return officeAssetRequisitionDao.getOfficeAssetRequisitionByDepartment(department);
    }

    @Override
    public List<Officeassetrequisition> getAllHodApprovedOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllHodApprovedOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getAllDafRejectedOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllDafRejectedOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getAllDafApprovedOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllDafApprovedOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getAllServedOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllServedOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getAllCommentedOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllCommentedOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getAllHodSentOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllHodSentOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getHodPersonalApprovedOfficeAssetRequisition(Department department) {
        return officeAssetRequisitionDao.getHodPersonalApprovedOfficeAssetRequisition(department);
    }

    @Override
    public List<Officeassetrequisition> getHodPersonalRejectedOfficeAssetRequisition(Department department) {
        return officeAssetRequisitionDao.getHodPersonalRejectedOfficeAssetRequisition(department);
    }

    
    //Dashboard serviceimpl's
    
    @Override
    public List<Officeassetrequisition> getHodPendingOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getHodPendingOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getHodRejectedOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getHodRejectedOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getProfDafPendingOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getProfDafPendingOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getDafRejectedOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getDafRejectedOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getHodDafPendingOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getHodDafPendingOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getAllDafPendingOfficeAssetRequisition() {
        return officeAssetRequisitionDao.getAllDafPendingOfficeAssetRequisition();
    }

    @Override
    public List<Officeassetrequisition> getDafApprovedOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getDafApprovedOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getServedOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getServedOfficeAssetRequisitionByEmployee(employee);
    }

    @Override
    public List<Officeassetrequisition> getCommentedOfficeAssetRequisitionByEmployee(Employee employee) {
        return officeAssetRequisitionDao.getCommentedOfficeAssetRequisitionByEmployee(employee);
    }
}
