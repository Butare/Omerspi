/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.wrapper;

import javax.validation.Valid;
import jim.omerspi.model.Employee;
import jim.omerspi.model.User;

/**
 *
 * @author JOHN
 */
public class MyForm {

   @Valid
    private User userDetails;
 @Valid
    private Employee employeeDetails;
    
    

    public MyForm() {
    }
  
    public Employee getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(Employee employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }
}
