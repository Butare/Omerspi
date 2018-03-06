/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.Date;
import java.util.List;
import jim.omerspi.dao.CarRequisitionServiceDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Carrequisitionservice;
import jim.omerspi.service.CarRequisitionServiceService;

/**
 *
 * @author JOHN
 */
public class CarRequisitionServiceServiceImpl implements CarRequisitionServiceService {

    private CarRequisitionServiceDao carRequisitionServiceDao;

    public void setCarRequisitionServiceDao(CarRequisitionServiceDao carRequisitionServiceDao) {
        this.carRequisitionServiceDao = carRequisitionServiceDao;
    }

    @Override
    public void saveOrUpdateCarRequisitionService(Carrequisitionservice carRequisitionService) {
        carRequisitionServiceDao.saveOrUpdateCarRequisitionService(carRequisitionService);
    }

    @Override
    public void deleteCarRequisitionService(Carrequisitionservice carRequisitionService) {
        carRequisitionServiceDao.deleteCarRequisitionService(carRequisitionService);
    }

    @Override
    public Carrequisitionservice getCarRequisitionServiceById(Integer carServiceId) {
        return carRequisitionServiceDao.getCarRequisitionServiceById(carServiceId);
    }

    @Override
    public List getAllCarRequisitionService() {
        return carRequisitionServiceDao.getAllCarRequisitionService();
    }

    @Override
    public List<Carrequisitionservice> getCarRequisitionServiceByCarRequisition(Carrequisition carRequisition) {
        return carRequisitionServiceDao.getCarRequisitionServiceByCarRequisition(carRequisition);
    }

    @Override
    public List<Carrequisitionservice> printTotalByDriverBetweenDates(Date d1, Date d2) {
        return carRequisitionServiceDao.printTotalByDriverBetweenDates(d1, d2);
    }

    @Override
    public List<Carrequisitionservice> printAllCarrequisitionBetweenDates(Date d1, Date d2) {
        return carRequisitionServiceDao.printAllCarrequisitionBetweenDates(d1, d2);
    }

    @Override
    public List<Carrequisitionservice> printServedByDepartmentBetweenDates(Date d1, Date d2) {
        return carRequisitionServiceDao.printServedByDepartmentBetweenDates(d1, d2);
    }

    @Override
    public List<Carrequisitionservice> printTotalByCompanyBetweenDates(Date d1, Date d2) {
        return carRequisitionServiceDao.printTotalByCompanyBetweenDates(d1, d2);
    }
}
