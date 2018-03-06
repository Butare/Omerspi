/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToEmployeeConverter implements Converter<String,Employee>{

     @Autowired
    ServiceContext context;
    
    @Override
    public Employee convert(String s) {
        
         if (s == null || s.length() == 0) {
            return null;
        }
        
        Employee emp= context.getEmployeeService().getEmployeeById(Integer.valueOf(s));
        System.out.println("Converted Employee '" + s + "' to " +emp);
        
        return emp;
    }

   
}
