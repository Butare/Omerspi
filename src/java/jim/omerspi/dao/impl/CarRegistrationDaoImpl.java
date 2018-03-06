/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.CarRegistrationDao;
import jim.omerspi.model.Carregistration;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class CarRegistrationDaoImpl implements CarRegistrationDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateCarRegistration(Carregistration carRegistration) {

        sessionFactory.getCurrentSession().saveOrUpdate(carRegistration);

    }

    @Override
    public void deleteCarRegistration(Carregistration carRegistration) {

        sessionFactory.getCurrentSession().delete(carRegistration);

    }

    @Override
    public Carregistration getCarRegistrationById(Integer carRegistrationId) {
       
       return (Carregistration) sessionFactory.getCurrentSession().get(Carregistration.class, carRegistrationId);

    }

    @Override
    public List getAllCarRegistration() {

        List result = sessionFactory.getCurrentSession().createCriteria(Carregistration.class).list();

        return result;
    }

    @Override
    public List<Carregistration> getAllCarNotInService() {
       List notInServiceCars=sessionFactory.getCurrentSession().createCriteria(Carregistration.class)
               .add(Restrictions.eq("carServiceStatus", "Not in Service")).list();
       return notInServiceCars;
    }
}
