/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.EmployeeDao;
import jim.omerspi.model.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateEmployee(Employee employee) {

        sessionFactory.getCurrentSession().saveOrUpdate(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {

        sessionFactory.getCurrentSession().delete(employee);

    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {

        return (Employee) sessionFactory.getCurrentSession().get(Employee.class, employeeId);
    }

    @Override
    public List getAllEmployee() {
        return sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
    }

    @Override
    public Employee getEmployeeByCode(String code) {
        List<Employee> employees = sessionFactory.getCurrentSession().createCriteria(Employee.class)
                .add(Restrictions.eq("identificationWord", code)).list();
        
        return (employees.size() > 0) ? employees.get(0) : null;
    }

    @Override
    public List<Employee> getAllEmployeeByDriver() {
        List<Employee> driver=sessionFactory.getCurrentSession().createCriteria(Employee.class)
                .add(Restrictions.eq("driver", true)).list();
        return driver;
                
    }

    @Override
    public List<Employee> getAllNotDriverEmployee() {
         List<Employee> notDriver=sessionFactory.getCurrentSession().createCriteria(Employee.class)
                .add(Restrictions.eq("driver", false)).list();
        return notDriver;
    }

    @Override
    public List< Employee> resetPassword(String identificationWord, String workEmail) {
        List<Employee> emp= sessionFactory.getCurrentSession().createCriteria(Employee.class)
                .add(Restrictions.and(Restrictions.eq("identificationWord", identificationWord), Restrictions.eq("workEmail", workEmail)))
                .list();
           
       return emp;
    }
}
