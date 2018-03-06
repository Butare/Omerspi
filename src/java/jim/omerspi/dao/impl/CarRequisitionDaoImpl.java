/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.Date;
import java.util.List;
import jim.omerspi.dao.CarRequisitionDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Department;
import jim.omerspi.model.Employee;
import jim.omerspi.model.Role;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class CarRequisitionDaoImpl implements CarRequisitionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateCarRequisition(Carrequisition carRequisition) {
        sessionFactory.getCurrentSession().saveOrUpdate(carRequisition);
    }

    @Override
    public void deleteCarRequisition(Carrequisition carRequisition) {
        sessionFactory.getCurrentSession().delete(carRequisition);
    }

    @Override
    public Carrequisition getCarRequisitionById(Integer carRequisitionId) {
        return (Carrequisition) sessionFactory.getCurrentSession().get(Carrequisition.class, carRequisitionId);
    }

    @Override
    public List getAllCarRequisition() {
        return sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).list();
    }

    @Override
    public List<Carrequisition> getCarRequisitionByEmployee(Employee employee) {
        List carRequisition = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("employee", employee)).list();
        return carRequisition;
    }

    @Override
    public List<Carrequisition> getAllNotServedCarRequisition() {
        List notServed = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "APPROVED BY DAF")).list();
        return notServed;
    }

    @Override
    public List<Carrequisition> getAllHodApprovedCarRequisition() {
        List pending = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "APPROVED BY HOD")).list();
        return pending;
    }

    @Override
    public List<Carrequisition> getAllCarRequisitionByDepartment(Department department) {

        List requisitions = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "SENT BY PROFESSIONAL")).createCriteria("employee").add(Restrictions.eq("department", department)).list();

        return requisitions;


    }


    @Override
    public List<Carrequisition> getAllHodSentCarRequisition() {
        List hodRquisitions = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "SENT BY HOD")).list();

        return hodRquisitions;
    }

    @Override
    public String getUserRole(Integer userRegistrationId) {

        String name = sessionFactory.getCurrentSession().createCriteria(Role.class).setProjection(Projections.property("users")).add(Restrictions.eq("userId", userRegistrationId)).toString();
        return name;

    }

    @Override
    public List<Carrequisition> getAllServedCarRequisition() {
        List served = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "SERVED")).list();
        return served;
    }

    @Override
    public List<Carrequisition> getAllCommentedCarRequisition() {
        List commented = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "LOGISTICS COMMENTED")).list();
        return commented;
    }

    @Override
    public List<Carrequisition> getAllDafAcceptedCarRequisition() {
        List dafAccepted = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class)
                .add(Restrictions.eq("carRequisitionStatus", "APPROVED BY DAF")).list();
        return dafAccepted;
    }

    @Override
    public List<Carrequisition> getAllDafRejectedCarRequisition() {
        List dafRejected = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "REJECTED BY DAF")).list();
        return dafRejected;
    }

    @Override
    public List<Carrequisition> getHodPersonalApprovedCarRequisition(Department department) {
        List hodPersonalApprovedCarRequisition = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "APPROVED BY HOD")).createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return hodPersonalApprovedCarRequisition;
    }

    @Override
    public List<Carrequisition> getHodPersonalRejectedCarRequisition(Department department) {
        List hodPersonalRejectedCarRequisition = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.eq("carRequisitionStatus", "REJECTED BY HOD")).createCriteria("employee").add(Restrictions.eq("department", department)).list();
        return hodPersonalRejectedCarRequisition;
    }

    @Override
    public List<Carrequisition> getCarRequisitionBetweenDates(Date d1, Date d2) {
        List carRequisitionBetween = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.between("requestedOn", d2, d2)).list();
        return carRequisitionBetween;
    }

    @Override
    public List<Carrequisition> printCarRequisitionForm(Employee employee, Integer carRequisitionId) {
        List printCarRequisitionForm = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "SERVED"))).add(Restrictions.eq("carRequisitionId", carRequisitionId)).list();
        return printCarRequisitionForm;

    }

    // DashBoard DaoImpl's
    @Override
    public List<Carrequisition> getHodPendingCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> hodPendingList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "SENT BY PROFESSIONAL"))).list();
        return hodPendingList;
    }

    @Override
    public List<Carrequisition> getHodRejectedCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> hodRejectedList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "REJECTED BY HOD")))
                .add(Restrictions.eq("seen", Boolean.FALSE))
                .list();
        return hodRejectedList;
    }

    @Override
    public List<Carrequisition> getProfDafPendingCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> dafApprovedList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "APPROVED BY HOD"))).list();
        return dafApprovedList;
    }

    @Override
    public List<Carrequisition> getProfDafRejectedCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> dafRejectedList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "REJECTED BY DAF")))
                .add(Restrictions.eq("seen", Boolean.FALSE))
                .list();
        return dafRejectedList;
    }

    @Override
    public List<Carrequisition> getHodDafPendingCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> hodDafPendingList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "SENT BY HOD"))).list();
        return hodDafPendingList;
    }

    @Override
    public List<Carrequisition> getAllDafPendingCarRequisition() {
        List<Carrequisition> allDafPendingList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class)
                .add(
                Restrictions.or(Restrictions.eq("carRequisitionStatus", "SENT BY HOD"), 
                Restrictions.eq("carRequisitionStatus", "APPROVED BY HOD")))
                .list();
        return allDafPendingList;
    }

    @Override
    public List<Carrequisition> getDafApprovedCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> dafApprovedList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class).add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "APPROVED BY DAF"))).list();
        return dafApprovedList;
    }

    @Override
    public List<Carrequisition> getServedCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> servedList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "SERVED")))
                .add(Restrictions.eq("seen", Boolean.FALSE))
                .list();
        return servedList;
    }

    @Override
    public List<Carrequisition> getCommentedCarRequisitionByEmployee(Employee employee) {
        List<Carrequisition> commentedList = sessionFactory.getCurrentSession().createCriteria(Carrequisition.class)
                .add(Restrictions.and(Restrictions.eq("employee", employee), Restrictions.eq("carRequisitionStatus", "LOGISTICS COMMENTED")))
                .add(Restrictions.eq("seen", Boolean.FALSE))
                .list();
        return commentedList;
    }
}
