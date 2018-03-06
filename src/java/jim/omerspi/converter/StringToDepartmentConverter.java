/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.converter;

import jim.omerspi.ServiceContext;
import jim.omerspi.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOHN
 */
@Component
public class StringToDepartmentConverter implements Converter<String, Department> {

    @Autowired
    ServiceContext context;

    @Override
    public Department convert(String s) {

        if (s == null || s.length() == 0) {
            return null;
        }

        Department dept = context.getDepartmentService().getDepartmentById(Integer.valueOf(s));
        System.out.println("Converted '" + s + "' to " + dept);
        return dept;
    }
}
