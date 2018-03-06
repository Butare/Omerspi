/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.CartypeDao;
import jim.omerspi.model.Cartype;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class CartypeDaoImpl implements CartypeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateCartype(Cartype carType) {
        sessionFactory.getCurrentSession().saveOrUpdate(carType);
    }

    @Override
    public void deleteCartype(Cartype carType) {
        sessionFactory.getCurrentSession().delete(carType);
    }

    @Override
    public Cartype getCartypeById(Integer carTypeId) {
        Cartype result = (Cartype) sessionFactory.getCurrentSession().get(Cartype.class, carTypeId);
        return result;
    }

    @Override
    public List getAllCartype() {
        List result = sessionFactory.getCurrentSession().createCriteria(Cartype.class).list();
        return result;
    }
}
