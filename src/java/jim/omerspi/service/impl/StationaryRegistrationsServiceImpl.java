/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import jim.omerspi.dao.StationaryRegistrationsDao;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import jim.omerspi.model.Stationaryregistration;
import jim.omerspi.service.StationaryRegistrationsService;

/**
 *
 * @author JOHN
 */
public class StationaryRegistrationsServiceImpl implements StationaryRegistrationsService {

    private StationaryRegistrationsDao stationaryRegistrationsDao;

    public void setStationaryRegistrationsDao(StationaryRegistrationsDao stationaryRegistrationsDao) {
        this.stationaryRegistrationsDao = stationaryRegistrationsDao;
    }

    @Override
    public void saveOrUpdateStationaryRegistrations(Stationaryregistration stationaryRegistrations) {
        stationaryRegistrationsDao.saveOrUpdateStationaryRegistration(stationaryRegistrations);
    }

    @Override
    public void deleteStationaryRegistrations(Stationaryregistration stationaryRegistrations) {
        stationaryRegistrationsDao.deleteStationaryRegistrations(stationaryRegistrations);
    }

    @Override
    public Stationaryregistration getStationaryRegistrationsById(Integer stationaryRegistrationId) {
        return stationaryRegistrationsDao.getStationaryRegistrationsById(stationaryRegistrationId);
    }

    @Override
    public List getAllStationaryRegistrations() {
        return stationaryRegistrationsDao.getAllStationaryRegistrations();
    }

    @Override
    public List<Stationaryregistration> getStationaryregistrationsByItem(Items item) {
        return stationaryRegistrationsDao.getStationaryregistrationsByItem(item);
    }

    @Override
    public List<Stationaryregistration> getOutOfStockStationary() {
        return stationaryRegistrationsDao.getOutOfStockStationary();
    }

    @Override
    public List<Stationaryregistration> getStationaryBetweenDates(Date d1, Date d2) {
        return stationaryRegistrationsDao.getStationaryBetweenDates(d1, d2);
    }

    @Override
    public Map<Category, List<Stationaryregistration>> groupedStationaryByCategoryBetweenDates(Date d1, Date d2) {
        return stationaryRegistrationsDao.groupedStationaryByCategoryBetweenDates(d1, d2);
    }

    @Override
    public Map<Items, List<Stationaryregistration>> groupedStationaryByItemBetweenDates(Date d1, Date d2) {
        return stationaryRegistrationsDao.groupedStationaryByItemBetweenDates(d1, d2);
    }

    @Override
    public int getStationaryTotalByItem(Items item) {
        return stationaryRegistrationsDao.getStationaryTotalByItem(item);
    }

    @Override
    public List<Stationaryregistration> getAllAvailableStationaryByCategoryType(Categorytype categoryType) {
        return stationaryRegistrationsDao.getAllAvailableStationaryByCategoryType(categoryType);
    }
}
