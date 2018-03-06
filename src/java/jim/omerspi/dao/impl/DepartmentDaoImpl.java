/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.DepartmentDao;
import jim.omerspi.model.Department;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateDepartment(Department department) {

        sessionFactory.getCurrentSession().saveOrUpdate(department);

    }

    @Override
    public void deleteDepartment(Department department) {

        sessionFactory.getCurrentSession().delete(department);

    }

    @Override
    public Department getDepartmentById(Integer departmentId) {

        Department result = (Department) sessionFactory.getCurrentSession().get(Department.class, departmentId);

        return result;
    }

    @Override
    public List getAllDepartment() {

        List result = sessionFactory.getCurrentSession().createCriteria(Department.class).list();
        return result;
    }
}
