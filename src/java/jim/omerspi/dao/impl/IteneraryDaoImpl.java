/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.IteneraryDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Itenerary;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class IteneraryDaoImpl implements IteneraryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateItenerary(Itenerary itenerary) {
        sessionFactory.getCurrentSession().saveOrUpdate(itenerary);
    }

    @Override
    public void deleteItenerary(Itenerary itenerary) {
        sessionFactory.getCurrentSession().delete(itenerary);
    }

    @Override
    public Itenerary  getIteneraryById(Integer itemId) {
        Itenerary result = (Itenerary) sessionFactory.getCurrentSession().get(Itenerary.class, itemId);
        return result;
    }

    @Override
    public List getAllItenerary() {
        List result = sessionFactory.getCurrentSession().createCriteria(Itenerary.class).list();
        return result;
    }

}
