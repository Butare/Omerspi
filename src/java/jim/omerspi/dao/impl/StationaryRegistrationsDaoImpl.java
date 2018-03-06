/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import jim.omerspi.dao.StationaryRegistrationsDao;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import jim.omerspi.model.Stationaryregistration;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Jimmy
 */
public class StationaryRegistrationsDaoImpl implements StationaryRegistrationsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateStationaryRegistration(Stationaryregistration stationaryRegistrations) {
        sessionFactory.getCurrentSession().saveOrUpdate(stationaryRegistrations);
    }

    @Override
    public void deleteStationaryRegistrations(Stationaryregistration stationaryRegistrations) {
        sessionFactory.getCurrentSession().delete(stationaryRegistrations);
    }

    @Override
    public Stationaryregistration getStationaryRegistrationsById(Integer stationaryRegistrationId) {
        Stationaryregistration result = (Stationaryregistration) sessionFactory.getCurrentSession().get(Stationaryregistration.class, stationaryRegistrationId);
        return result;
    }

    @Override
    public List<Stationaryregistration> getAllStationaryRegistrations() {
        List result = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class).list();
        return result;
    }

    @Override
    public List<Stationaryregistration> getStationaryregistrationsByItem(Items item) {
        List stationaryRegistrationsByItem = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class)
                .add(Restrictions.eq("items", item)).list();
        return stationaryRegistrationsByItem;
    }

    @Override
    public List<Stationaryregistration> getOutOfStockStationary() {

        List<Stationaryregistration> all = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class)
                .list();

//        Map<Item, Integer> stationeries = new LinkedHashMap<Item, Integer>();
//        for (Stationaryregistrations stationary : all) {
//            Item ite = stationary.getItem();
//            if (!stationeries.containsKey(ite)) {
//                stationeries.put(ite, 0);
//            }
//            
//        }

        List outOfStock = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class)
                .createAlias("items", "item")
                .setProjection(Projections.projectionList()
                .add(Projections.property("items"), "items")
                .add(Projections.property("minimumQty"), "minimumQty")
                .add(Projections.groupProperty("items")))
                .add(Restrictions.le("item.itemQty", 10))
                .setResultTransformer(Transformers.aliasToBean(Stationaryregistration.class))
                .list();
        return outOfStock;

    }

    @Override
    public List<Stationaryregistration> getStationaryBetweenDates(Date d1, Date d2) {
        List purchasedItems = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class)
                .add(Restrictions.between("acquisitionDate", d1, d2))
                .list();
        return purchasedItems;
    }

    @Override
    public Map<Category, List<Stationaryregistration>> groupedStationaryByCategoryBetweenDates(Date d1, Date d2) {
        List<Stationaryregistration> registrations = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class).add(Restrictions.between("acquisitionDate", d1, d2))
                .list();

        Map<Category, List<Stationaryregistration>> groupedStationary = new LinkedHashMap<Category, List<Stationaryregistration>>();

        for (Stationaryregistration registration : registrations) {
            Category cat = registration.getItems().getCategorytype().getCategory();

            if (!groupedStationary.containsKey(cat)) {
                groupedStationary.put(cat, new ArrayList<Stationaryregistration>());
            }

            groupedStationary.get(cat).add(registration);
        }

        return groupedStationary;
    }

    @Override
    public Map<Items, List<Stationaryregistration>> groupedStationaryByItemBetweenDates(Date d1, Date d2) {
        List<Stationaryregistration> registrations = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class)
                .add(Restrictions.between("acquisitionDate", d1, d2)).list(); //.createAlias("item", "item")

        Map<Items, List<Stationaryregistration>> stationaryByItem = new LinkedHashMap<Items, List<Stationaryregistration>>();
        for (Stationaryregistration registration : registrations) {
            Items ite = registration.getItems();
            if (!stationaryByItem.containsKey(ite)) {
                stationaryByItem.put(ite, new ArrayList<Stationaryregistration>());
            }
            stationaryByItem.get(ite).add(registration);


        }


        System.out.println("Grouped byItem size : " + stationaryByItem.size());
//        for (int i = 0; i < stationaryByItem.size(); i++) {
//            System.out.println("Hey List Total: " + stationaryByItem.get(i).getCurrentTotalQty() + " Quantity : " + stationaryByItem.get(i).getQuantity());
//        }
        return stationaryByItem;

    }

    @Override
    public int getStationaryTotalByItem(Items item) {
        List<Stationaryregistration> all = sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class).list();
        int totalQty = 0;
        if (!all.isEmpty()) {
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).getItems().getItemId() == item.getItemId()) {
                    totalQty += all.get(i).getPurchasedQty();
                }
            }
            return totalQty;
        }
        return totalQty;
    }

    @Override
    public List<Stationaryregistration> getAllAvailableStationaryByCategoryType(Categorytype categoryType) {
         List itemsByCategory=sessionFactory.getCurrentSession().createCriteria(Stationaryregistration.class)
                .createAlias("items", "item")
                .add(Restrictions.and(Restrictions.eq("item.isAllowed", Boolean.TRUE),Restrictions.eq("item.categorytype", categoryType)))        
                .setProjection(Projections.projectionList()
                 .add(Projections.property("items"),"items")
                 .add(Projections.groupProperty("items")))
                 .setResultTransformer(Transformers.aliasToBean(Stationaryregistration.class))
                 .list();
        return itemsByCategory;
    }
}
