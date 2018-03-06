/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.Date;
import java.util.List;
import jim.omerspi.dao.RequestedItemDao;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryrequisition;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class RequestedItemDaoImpl implements RequestedItemDao{

     @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
     
     
    @Override
    public void saveOrUpdateRequestedItem(RequestedItems requestedItem) {
           sessionFactory.getCurrentSession().saveOrUpdate(requestedItem);
    }

    @Override
    public void deleteRequestedItem(RequestedItems requestedItem) {
        sessionFactory.getCurrentSession().delete(requestedItem);
    }

    @Override
    public RequestedItems getRequestedItemById(Integer requestedId) {
        RequestedItems result = (RequestedItems) sessionFactory.getCurrentSession().get(RequestedItems.class, requestedId);
        return result;
    }

    @Override
    public List<RequestedItems> getAllRequestedItem() {
         List result = sessionFactory.getCurrentSession().createCriteria(RequestedItems.class).list();
        return result;
    }

    @Override
    public List<RequestedItems> getRequestedItemByStationaryRequisition(Stationaryrequisition stationaryRequisition) {
         List<RequestedItems> requestedItemList=sessionFactory.getCurrentSession().createCriteria(RequestedItems.class)
                 .add(Restrictions.eq("stationaryrequisition", stationaryRequisition)).list();
         return requestedItemList;
    }

    @Override
    public List<RequestedItems> getServedItemBetweenDates(Date d1, Date d2) {
        
       // d2=DateUtils.addDays(d2, 1);
        
        List<RequestedItems> servedListBetweenDates=sessionFactory.getCurrentSession().createCriteria(RequestedItems.class,"requested")
                .createAlias("requested.items", "items")
                .add(Restrictions.and(Restrictions.isNotNull("serviceDate"),Restrictions.between("serviceDate", d1, d2)))
                .add(Restrictions.isNotNull("servedQty"))
                .addOrder(Order.asc("items.itemName"))
                .list();
        return servedListBetweenDates;
    }

    @Override
    public List<RequestedItems> getServedItemGroupedByCategory(Date d1, Date d2) {
        // d2=DateUtils.addDays(d2, 1);
        
        List<RequestedItems> servedByCategory=sessionFactory.getCurrentSession().createCriteria(RequestedItems.class,"requested")
                .createAlias("requested.stationaryrequisition", "stationary")
                .createAlias("requested.officeassetrequisition", "officeassetrequisition")
                .createAlias("requested.items", "items")
                .createAlias("items.categorytype", "categorytype")
                .setProjection(Projections.projectionList()
                .add(Projections.property("stationaryrequisition"),"stationaryrequisition")
                .add(Projections.property("officeassetrequisition"),"officeassetrequisition")
                .add(Projections.groupProperty("categorytype.category"))
                )
                .add(Restrictions.and(Restrictions.isNotNull("serviceDate"),Restrictions.between("serviceDate", d1, d2)))
                .setResultTransformer(Transformers.aliasToBean(RequestedItems.class))
                .list();
        return servedByCategory;
    }

    @Override
    public List<RequestedItems> getServedItemGroupedByItem(Date d1, Date d2) {
        // d2=DateUtils.addDays(d2, 1);
        
        List<RequestedItems> servedByItem=sessionFactory.getCurrentSession().createCriteria(RequestedItems.class,"requested")
                .createAlias("requested.stationaryrequisition", "stationary")
                .createAlias("requested.officeassetrequisition", "officeassetrequisition")
               
                .setProjection(Projections.projectionList()
                .add(Projections.property("stationaryrequisition"),"stationaryrequisition")
                .add(Projections.property("officeassetrequisition"),"officeassetrequisition")
                .add(Projections.groupProperty("items"))
                )
                .add(Restrictions.and(Restrictions.isNotNull("serviceDate"),Restrictions.between("serviceDate", d1, d2)))
                .setResultTransformer(Transformers.aliasToBean(RequestedItems.class))
                .list();
        return servedByItem;
    }

    
    //for office asset requisition 
    
    @Override
    public List<RequestedItems> getRequestedItemByOfficeAssetRequisition(Officeassetrequisition officeAssetRequisition) {
         List<RequestedItems> requestedItemList=sessionFactory.getCurrentSession().createCriteria(RequestedItems.class)
                 .add(Restrictions.eq("officeassetrequisition", officeAssetRequisition)).list();
         return requestedItemList;
    }
    
}
