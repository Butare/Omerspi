/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Employee;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface EmployeeDao {
    public void saveOrUpdateEmployee(Employee employee);
    public void deleteEmployee(Employee employee);
    public Employee getEmployeeById(Integer employeeId);
    public Employee getEmployeeByCode(String code);
    public List getAllEmployee();
    public List<Employee> getAllEmployeeByDriver();
    public List<Employee> getAllNotDriverEmployee();
    public List<Employee> resetPassword(String identificationWord,String workEmail);
    
}
