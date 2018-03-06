/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.CarRegistrationDao;
import jim.omerspi.model.Carregistration;
import jim.omerspi.service.CarRegistrationService;

/**
 *
 * @author JOHN
 */
public class CarRegistrationServiceImpl implements CarRegistrationService {

    private CarRegistrationDao carRegistrationDao;

    public void setCarRegistrationDao(CarRegistrationDao carRegistrationDao) {
        this.carRegistrationDao = carRegistrationDao;
    }

    @Override
    public void saveOrUpdateCarRegistration(Carregistration carRegistration) {
        carRegistrationDao.saveOrUpdateCarRegistration(carRegistration);
    }

    @Override
    public void deleteCarRegistration(Carregistration carRegistration) {
        carRegistrationDao.deleteCarRegistration(carRegistration);
    }

    @Override
    public Carregistration getCarRegistrationById(Integer carRegistrationId) {
        return carRegistrationDao.getCarRegistrationById(carRegistrationId);
    }

    @Override
    public List getAllCarRegistration() {
        return carRegistrationDao.getAllCarRegistration();
    }

    @Override
    public List<Carregistration> getAllCarNotInService() {
        return carRegistrationDao.getAllCarNotInService();
    }
}
