/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface UserDao {

    public void saveOrUpdateUser(User user);

    public void deleteUser(User user);

    public User getUserById(Integer userRegistrationId);

    public List getAllUser();
    
    public User getUserByUsername(String username);
    
   public User authenticate(String username, String password);
   
  
}
