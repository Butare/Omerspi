/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.model.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jimmy
 */
@Component
public class EmployeeToStringConverter implements Converter<Employee, String> {

    @Override
    public String convert(Employee obj) {
        Integer id = obj.getEmployeeRegistrationId();
        System.out.println("Converted EMPLOYEE ID : " + id);
        return id != null ? id.toString() : "";
    }
}
