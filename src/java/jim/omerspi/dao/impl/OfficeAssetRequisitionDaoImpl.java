/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.OfficeAssetRequisitionDao;
import jim.omerspi.model.Officeassetrequisition;

import java.util.List;
import jim.omerspi.dao.StationaryRequisitionDao;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class OfficeAssetRequisitionDaoImpl implements OfficeAssetRequisitionDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void saveOrUpdateOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition) {
        sessionFactory.getCurrentSession().saveOrUpdate(officeAssetRequisition);
    }
    
    @Override
    public void deleteOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition) {
        for (RequestedItems requested : officeAssetRequisition.getRequestedItemses()) {
            sessionFactory.getCurrentSession().delete(requested);
        }
        sessionFactory.getCurrentSession().delete(officeAssetRequisition);
    }
    
    @Override
    public Officeassetrequisition getOfficeAssetRequisitionById(Integer officeAssetRequisitionId) {
        return (Officeassetrequisition) sessionFactory.getCurrentSession().get(Officeassetrequisition.class, officeAssetRequisitionId);
    }
    
    @Override
    public List<Officeassetrequisition> getAllOfficeAssetRequisition() {
        List<Officeassetrequisition> officeRequisition = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .list();
        return officeRequisition;
    }
    
    @Override
    public List<Officeassetrequisition> getOfficeAssetRequisitionByEmployee(Employee employee) {
        List<Officeassetrequisition> officeRequisition = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.eq("employee", employee))
                .list();       
        return officeRequisition;
    }

    @Override
    public List<Officeassetrequisition> getOfficeAssetRequisitionByDepartment(Department department) {
         List requisitions = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.eq("status", "SENT BY PROFESSIONAL"))
                .createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return requisitions;
    }

    @Override
    public List<Officeassetrequisition> getAllHodApprovedOfficeAssetRequisition() {
         List officeHodApproved = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                 .add(Restrictions.eq("status", "APPROVED BY HOD")).list();
        return officeHodApproved;
    }

    @Override
    public List<Officeassetrequisition> getAllDafRejectedOfficeAssetRequisition() {
        List officeRequisitionApprovedByDaf = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.eq("status", "REJECTED BY DAF")).list();
        return officeRequisitionApprovedByDaf;
    }

    @Override
    public List<Officeassetrequisition> getAllDafApprovedOfficeAssetRequisition() {
         List officeRequisitionApprovedByDaf = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                 .add(Restrictions.eq("status", "APPROVED BY DAF"))
                 .list();
        return officeRequisitionApprovedByDaf;
    }

    @Override
    public List<Officeassetrequisition> getAllServedOfficeAssetRequisition() {
         List served = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                 .add(Restrictions.eq("status", "SERVED")).list();
        return served;
    }

    @Override
    public List<Officeassetrequisition> getAllCommentedOfficeAssetRequisition() {
         List commented = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                 .add(Restrictions.eq("status", "LOGISTICS COMMENTED")).list();
        return commented;
    }

    @Override
    public List<Officeassetrequisition> getAllHodSentOfficeAssetRequisition() {
         List officeAssetHodSent = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                 .add(Restrictions.eq("status", "SENT BY HOD")).list();
        return officeAssetHodSent;
    }

    @Override
    public List<Officeassetrequisition> getHodPersonalApprovedOfficeAssetRequisition(Department department) {
         List hodPersonalApprovedOfficeAssetRequisition = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                  .add(Restrictions.eq("status", "APPROVED BY HOD"))
                .createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return hodPersonalApprovedOfficeAssetRequisition;
    }

    @Override
    public List<Officeassetrequisition> getHodPersonalRejectedOfficeAssetRequisition(Department department) {
        List hodPersonalRejectedOfficeAssetRequisition = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                  .add(Restrictions.eq("status", "REJECTED BY HOD"))
                .createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return hodPersonalRejectedOfficeAssetRequisition;
    }
    
    
    //DashBoard DaoImpl's
    
    @Override
    public List<Officeassetrequisition> getHodPendingOfficeAssetRequisitionByEmployee(Employee employee) {
        List<Officeassetrequisition> hodPendingList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "SENT BY PROFESSIONAL")))
                .list();
        return hodPendingList;
    }

    @Override
    public List<Officeassetrequisition> getHodRejectedOfficeAssetRequisitionByEmployee(Employee employee) {
        List<Officeassetrequisition> hodRejectedList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "REJECTED BY HOD")))
                .add(Restrictions.eq("officeRequisitionSeen", Boolean.FALSE))
                .list();
        return hodRejectedList;
    }

    @Override
    public List<Officeassetrequisition> getProfDafPendingOfficeAssetRequisitionByEmployee(Employee employee) {
        List<Officeassetrequisition> profDafPendingList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "APPROVED BY HOD")))
                .list();
        return profDafPendingList;
    }

    @Override
    public List<Officeassetrequisition> getDafRejectedOfficeAssetRequisitionByEmployee(Employee employee) {
       List<Officeassetrequisition> dafRejectedList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "REJECTED BY DAF")))
                .add(Restrictions.eq("officeRequisitionSeen", Boolean.FALSE))
                .list();
        return dafRejectedList; 
    }

    @Override
    public List<Officeassetrequisition> getHodDafPendingOfficeAssetRequisitionByEmployee(Employee employee) {
      List<Officeassetrequisition> hodDafPendingList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "SENT BY HOD")))
                .list();
        return hodDafPendingList;   
    }

    @Override
    public List<Officeassetrequisition> getAllDafPendingOfficeAssetRequisition() {
        List<Officeassetrequisition> allDafPendingList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.or(Restrictions.eq("status", "SENT BY HOD"), Restrictions.eq("status", "APPROVED BY HOD")))
                .list();
        return allDafPendingList;
    }

    @Override
    public List<Officeassetrequisition> getDafApprovedOfficeAssetRequisitionByEmployee(Employee employee) {
        List<Officeassetrequisition> dafApprovedList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status", "APPROVED BY DAF")))
                .list();
        return dafApprovedList;
    }

    @Override
    public List<Officeassetrequisition> getServedOfficeAssetRequisitionByEmployee(Employee employee) {
        List<Officeassetrequisition> logisticServedList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status", "SERVED")))
                .add(Restrictions.eq("officeRequisitionSeen", Boolean.FALSE))
                .list();
        return logisticServedList;
    }

    @Override
    public List<Officeassetrequisition> getCommentedOfficeAssetRequisitionByEmployee(Employee employee) {
       List<Officeassetrequisition> logisticCommentedList=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status", "LOGISTICS COMMENTED")))
                .add(Restrictions.eq("officeRequisitionSeen", Boolean.FALSE))
                .list();
        return logisticCommentedList;
    }

}
