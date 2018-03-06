/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.UserDao;
import jim.omerspi.model.Employee;
import jim.omerspi.model.User;
import jim.omerspi.service.UserService;

/**
 *
 * @author JOHN
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveOrUpdateUser(User user) {
        userDao.saveOrUpdateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public User getUserById(Integer userRegistrationId) {
        return userDao.getUserById(userRegistrationId);
    }

    @Override
    public List getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User authenticate(String username, String password) {
        return userDao.authenticate(username, password);
    }
}
