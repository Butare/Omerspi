/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.EmployeeDao;
import jim.omerspi.model.Employee;
import jim.omerspi.service.EmployeeService;

/**
 *
 * @author JOHN
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void saveOrUpdateEmployee(Employee employee) {

        employeeDao.saveOrUpdateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeDao.deleteEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public List getAllEmployee() {
        return employeeDao.getAllEmployee();
    }

    @Override
    public Employee getEmployeeByCode(String code) {
        return employeeDao.getEmployeeByCode(code);
    }

    @Override
    public List<Employee> getAllEmployeeByDriver() {
       return employeeDao.getAllEmployeeByDriver();
    }

    @Override
    public List<Employee> getAllNotDriverEmployee() {
        return employeeDao.getAllNotDriverEmployee();
    }

    @Override
    public List<Employee> resetPassword(String identificationWord, String workEmail) {
        return employeeDao.resetPassword(identificationWord, workEmail);
    }
}
