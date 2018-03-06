/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import java.util.List;
import jim.omerspi.model.Employee;
import jim.omerspi.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface UserService {

    public void saveOrUpdateUser(User user);

    public void deleteUser(User user);

    public User getUserById(Integer userRegistrationId);

    public List getAllUser();
    
    public User getUserByUsername(String username);
    
    public User authenticate(String username, String password);
}
