/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.DepartmentDao;
import jim.omerspi.model.Department;
import jim.omerspi.service.DepartmentService;

/**
 *
 * @author JOHN
 */

public class DepartmentServiceImpl implements DepartmentService{

    private DepartmentDao departmentDao;

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }      
    
    @Override
    public void saveOrUpdateDepartment(Department department) {
      departmentDao.saveOrUpdateDepartment(department);  
    }
     
    @Override
    public void deleteDepartment(Department department) {
        departmentDao.deleteDepartment(department);
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return departmentDao.getDepartmentById(departmentId);
    }
    
    @Override
    public List getAllDepartment() {
        return departmentDao.getAllDepartment();
    }
    
}
