/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.Date;
import java.util.List;
import jim.omerspi.dao.CarRequisitionServiceDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Carrequisitionservice;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author JOHN
 */
public class CarRequisitionServiceDaoImpl implements CarRequisitionServiceDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateCarRequisitionService(Carrequisitionservice carRequisitionService) {
        sessionFactory.getCurrentSession().saveOrUpdate(carRequisitionService);
    }

    @Override
    public void deleteCarRequisitionService(Carrequisitionservice carRequisitionService) {
        sessionFactory.getCurrentSession().delete(carRequisitionService);
    }

    @Override
    public Carrequisitionservice getCarRequisitionServiceById(Integer carServiceId) {
        return (Carrequisitionservice) sessionFactory.getCurrentSession().get(Carrequisitionservice.class, carServiceId);
    }

    @Override
    public List getAllCarRequisitionService() {
        return sessionFactory.getCurrentSession().createCriteria(Carrequisitionservice.class).list();
    }

    @Override
    public List<Carrequisitionservice> getCarRequisitionServiceByCarRequisition(Carrequisition carRequisition) {
        List carRequisitionService= sessionFactory.getCurrentSession().createCriteria(Carrequisitionservice.class)
                .add(Restrictions.eq("carrequisition", carRequisition)).list();
        return carRequisitionService;
    }

    @Override
    public List<Carrequisitionservice> printTotalByDriverBetweenDates(Date d1, Date d2) {
            //d2=DateUtils.addDays(d2, 1);
        List totalByDrivers=sessionFactory.getCurrentSession().createCriteria(Carrequisitionservice.class,"service")
                .createAlias("carrequisition", "car")
                //.createAlias("service.vendor", "vendor")
                .add(Restrictions.between("service.servedOn",d1,d2))
                .setProjection(
                 Projections.projectionList()
                .add(Projections.property("servedOn"),"servedOn")
                .add(Projections.property("driverNames"),"driverNames")
                .add(Projections.property("carrequisition"),"carrequisition")
                .add(Projections.property("vendor"),"vendor")
                .add(Projections.groupProperty("driverNames"))
                )
                .setResultTransformer(Transformers.aliasToBean(Carrequisitionservice.class))
                .list();
        return totalByDrivers;
    }

    @Override
    public List<Carrequisitionservice> printAllCarrequisitionBetweenDates(Date d1, Date d2) {
        //d2=DateUtils.addDays(d2,1);
        List allBetweenDates=sessionFactory.getCurrentSession().createCriteria(Carrequisitionservice.class)
         .add(Restrictions.between("servedOn",d1,d2)).list();
        return allBetweenDates;
    }

    @Override
    public List<Carrequisitionservice> printServedByDepartmentBetweenDates(Date d1, Date d2) {
       // d2=DateUtils.addDays(d2, 1);
        List servedByDepartment=sessionFactory.getCurrentSession().createCriteria(Carrequisitionservice.class,"service")
                .createAlias("service.carrequisition", "car")
                .createAlias("car.employee", "emp")
                .createAlias("emp.department", "dept")
                .add(Restrictions.between("servedOn", d1, d2))
                .setProjection(
                 Projections.projectionList()
                .add(Projections.property("carrequisition"),"carrequisition")
                .add(Projections.property("driverNames"),"driverNames")
                .add(Projections.groupProperty("dept.departmentName"))
                )
                .setResultTransformer(Transformers.aliasToBean(Carrequisitionservice.class))
                .list();
        return servedByDepartment;
    }

    @Override
    public List<Carrequisitionservice> printTotalByCompanyBetweenDates(Date d1, Date d2) {
        // d2=DateUtils.addDays(d2, 1);
         List servedByCompany=sessionFactory.getCurrentSession().createCriteria(Carrequisitionservice.class,"service")
                 .createAlias("service.carrequisition", "car")
                 .createAlias("service.vendor", "vendor")
                 .add(Restrictions.between("servedOn", d1, d2))
                 .setProjection(Projections.projectionList()
                 .add(Projections.property("carrequisition"),"carrequisition")
                 .add(Projections.groupProperty("vendor"),"vendor"))
                 .setResultTransformer(Transformers.aliasToBean(Carrequisitionservice.class))
                 .list();
         return servedByCompany;
    }
}
