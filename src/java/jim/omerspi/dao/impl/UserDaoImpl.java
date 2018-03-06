/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.UserDao;
import jim.omerspi.model.Employee;
import jim.omerspi.model.Privilege;
import jim.omerspi.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public User getUserById(Integer userRegistrationId) {
        User result = (User) sessionFactory.getCurrentSession().get(User.class, userRegistrationId);
        return result;
    }

    @Override
    public List getAllUser() {
        return sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", username)).list();
        
        return (users.size() > 0) ? users.get(0) : null;
    }

    @Override
    public User authenticate(String username, String password) {
       List<User> user=sessionFactory.getCurrentSession().createCriteria(User.class)
               .add(Restrictions.eq("userName", username))
               .add(Restrictions.eq("password", password)).list();
       return (user.size()>0)?user.get(0):null;
    }


    
}
