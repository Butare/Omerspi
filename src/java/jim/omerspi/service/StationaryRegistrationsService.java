package jim.omerspi.service;

import java.util.List;
import java.util.Map;
import java.util.Date;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import jim.omerspi.model.Stationaryregistration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface StationaryRegistrationsService {

    public void saveOrUpdateStationaryRegistrations(Stationaryregistration stationaryRegistrations);

    public void deleteStationaryRegistrations(Stationaryregistration stationaryRegistrations);

    public Stationaryregistration getStationaryRegistrationsById(Integer stationaryRegistrationId);

    public List getAllStationaryRegistrations();

    public List<Stationaryregistration> getStationaryregistrationsByItem(Items item);

    public List<Stationaryregistration> getOutOfStockStationary();

    public List<Stationaryregistration> getStationaryBetweenDates(Date d1, Date d2);

    public Map<Category, List<Stationaryregistration>> groupedStationaryByCategoryBetweenDates(Date d1, Date d2);

    public Map<Items, List<Stationaryregistration>> groupedStationaryByItemBetweenDates(Date d1, Date d2);

    public int getStationaryTotalByItem(Items item);

    public List<Stationaryregistration> getAllAvailableStationaryByCategoryType(Categorytype categoryType);
}
