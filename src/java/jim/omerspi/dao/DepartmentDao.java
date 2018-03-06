/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Department;

/**
 *
 * @author Jimmy
 */
public interface DepartmentDao {
    
   public void saveOrUpdateDepartment(Department department);
    public void deleteDepartment(Department department);
    public Department getDepartmentById(Integer departmentId);
    public List getAllDepartment();

}
