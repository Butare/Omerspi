/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import java.util.List;
import jim.omerspi.model.Department;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface DepartmentService {
    
    public void saveOrUpdateDepartment(Department department );
    public void deleteDepartment(Department department);
    public Department getDepartmentById(Integer departmentId);
    public List getAllDepartment();
    
}
