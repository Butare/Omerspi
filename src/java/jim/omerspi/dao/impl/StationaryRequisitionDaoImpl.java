/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

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

/**
 *
 * @author JOHN
 */
public class StationaryRequisitionDaoImpl implements StationaryRequisitionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateStationaryRequisition(Stationaryrequisition stationaryRequisition) {

        sessionFactory.getCurrentSession().saveOrUpdate(stationaryRequisition);
    }

    @Override
    public void deleteStationaryRequisition(Stationaryrequisition stationaryRequisition) {

        for (RequestedItems request : stationaryRequisition.getRequestedItemses()) {
            sessionFactory.getCurrentSession().delete(request);
        }

        sessionFactory.getCurrentSession().delete(stationaryRequisition);
    }

    @Override
    public Stationaryrequisition getStationaryRequisitionById(Integer stationaryRequisitionId) {
        return (Stationaryrequisition) sessionFactory.getCurrentSession().get(Stationaryrequisition.class, stationaryRequisitionId);
    }

    @Override
    public List getAllStationaryRequisition() {
        return sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).list();
    }

    @Override
    public List<Stationaryrequisition> getStationaryRequisitionByEmployee(Employee employee) {
        List stationaryRequisition = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.eq("employee", employee))
                .list();
        return stationaryRequisition;
    }

    @Override
    public List<Stationaryrequisition> getStationaryRequisitionByDepartment(Department department) {

        List requisitions = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.eq("status", "SENT BY PROFESSIONAL"))
                .createCriteria("employee").add(Restrictions.eq("department", department)).list();

        return requisitions;
    }

    @Override
    public List<Stationaryrequisition> getAllHodApprovedStationaryRequisition() {
        List stationaryHodApproved = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).add(Restrictions.eq("status", "APPROVED BY HOD")).list();
        return stationaryHodApproved;
    }

    @Override
    public List<Stationaryrequisition> getAllHodSentStationaryRequisition() {
        List stationaryHodSent = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).add(Restrictions.eq("status", "SENT BY HOD")).list();

        return stationaryHodSent;
    }

    @Override
    public List<Stationaryrequisition> getAllDafApprovedStationaryRequisition() {
        List stationaryRequisitionApprovedByDaf = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).add(Restrictions.eq("status", "APPROVED BY DAF")).list();
        return stationaryRequisitionApprovedByDaf;
    }
    
       @Override
    public List<Stationaryrequisition> getAllDafRejectedStationaryRequisition() {
        List stationaryRequisitionApprovedByDaf = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).add(Restrictions.eq("status", "REJECTED BY DAF")).list();
        return stationaryRequisitionApprovedByDaf;
    }

    @Override
    public List<Stationaryrequisition> getAllServedStationaryRequisition() {
        List served = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).add(Restrictions.eq("status", "SERVED")).list();
        return served;
    }

    @Override
    public List<Stationaryrequisition> getAllCommentedStationaryRequisition() {
        List served = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class).add(Restrictions.eq("status", "LOGISTICS COMMENTED")).list();
        return served;
    }

    @Override
    public List<Stationaryrequisition> getHodPersonalApprovedStationaryRequisition(Department department) {
        List hodPersonalApprovedstationaryRequisition = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                  .add(Restrictions.eq("status", "APPROVED BY HOD"))
                .createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return hodPersonalApprovedstationaryRequisition;
    }

    @Override
    public List<Stationaryrequisition> getHodPersonalRejectedStationaryRequisition(Department department) {
        List hodPersonalRejectedstationaryRequisition = sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                  .add(Restrictions.eq("status", "REJECTED BY HOD"))
                .createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return hodPersonalRejectedstationaryRequisition;
    }

    @Override
    public List<Stationaryrequisition> printStationaryRequisitionForm(Employee employee, Integer stationaryRequisitionId) {
        List printCarRequisitionForm=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status","SERVED")))
                .add(Restrictions.eq("stationaryRequisitionId",stationaryRequisitionId)).list();
         return printCarRequisitionForm;
    }
    
      @Override
    public List<User> getHoD(Department department) {
        List hod=sessionFactory.getCurrentSession().createCriteria(User.class,"user")
               
                .createAlias("user.employee", "emp")
                .createAlias("emp.department", "dept")
                .add(Restrictions.eq("emp.department",department))
                .list();
       
        return hod;
    }
    
     
    

    //DashBoard DaoImpl's
    
    @Override
    public List<Stationaryrequisition> getHodPendingStationaryRequisitionByEmployee(Employee employee) {
        List<Stationaryrequisition> hodPendingList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "SENT BY PROFESSIONAL")))
                .list();
        return hodPendingList;
    }

    @Override
    public List<Stationaryrequisition> getHodRejectedStationaryRequisitionByEmployee(Employee employee) {
        List<Stationaryrequisition> hodRejectedList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "REJECTED BY HOD")))
                .add(Restrictions.eq("stationarySeen", Boolean.FALSE))
                .list();
        return hodRejectedList;
    }

    @Override
    public List<Stationaryrequisition> getProfDafPendingStationaryRequisitionByEmployee(Employee employee) {
        List<Stationaryrequisition> profDafPendingList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "APPROVED BY HOD")))
                .list();
        return profDafPendingList;
    }

    @Override
    public List<Stationaryrequisition> getDafRejectedStationaryRequisitionByEmployee(Employee employee) {
       List<Stationaryrequisition> dafRejectedList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "REJECTED BY DAF")))
                .add(Restrictions.eq("stationarySeen", Boolean.FALSE))
                .list();
        return dafRejectedList; 
    }

    @Override
    public List<Stationaryrequisition> getHodDafPendingStationaryRequisitionByEmployee(Employee employee) {
      List<Stationaryrequisition> hodDafPendingList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("status", "SENT BY HOD")))
                .list();
        return hodDafPendingList;   
    }

    @Override
    public List<Stationaryrequisition> getAllDafPendingStationaryRequisition() {
        List<Stationaryrequisition> allDafPendingList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.or(Restrictions.eq("status", "SENT BY HOD"), Restrictions.eq("status", "APPROVED BY HOD")))
                .list();
        return allDafPendingList;
    }

    @Override
    public List<Stationaryrequisition> getDafApprovedStationaryRequisitionByEmployee(Employee employee) {
        List<Stationaryrequisition> dafApprovedList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status", "APPROVED BY DAF")))
                .list();
        return dafApprovedList;
    }

    @Override
    public List<Stationaryrequisition> getServedStationaryRequisitionByEmployee(Employee employee) {
        List<Stationaryrequisition> logisticServedList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status", "SERVED")))
                .add(Restrictions.eq("stationarySeen", Boolean.FALSE))
                .list();
        return logisticServedList;
    }

    @Override
    public List<Stationaryrequisition> getCommentedStationaryRequisitionByEmployee(Employee employee) {
       List<Stationaryrequisition> logisticCommentedList=sessionFactory.getCurrentSession().createCriteria(Stationaryrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee),Restrictions.eq("status", "LOGISTICS COMMENTED")))
                .add(Restrictions.eq("stationarySeen", Boolean.FALSE))
                .list();
        return logisticCommentedList;
    }

 
}
