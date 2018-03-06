/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.PrivilegeDao;
import jim.omerspi.model.Privilege;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class PrivilegeDaoImpl implements PrivilegeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdatePrivilege(Privilege privilege) {
        sessionFactory.getCurrentSession().saveOrUpdate(privilege);
    }

    @Override
    public void deletePrivilege(Privilege privilege) {
        sessionFactory.getCurrentSession().delete(privilege);
    }

    @Override
    public Privilege getPrivilegeByName(String privilege) {
        Privilege result = (Privilege) sessionFactory.getCurrentSession().get(Privilege.class, privilege);
        return result;
    }

    @Override
    public List getAllPrivilege() {
        List result = sessionFactory.getCurrentSession().createCriteria(Privilege.class).list();
        return result;
    }
}
