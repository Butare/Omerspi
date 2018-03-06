/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.ItemDao;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateItem(Items item) {
        
        sessionFactory.getCurrentSession().saveOrUpdate(item);
      
    }

    @Override
    public void deleteItem(Items item) {
       
        sessionFactory.getCurrentSession().delete(item);
      
    }

    @Override
    public Items getItemById(Integer itemId) {
        
        Items result = (Items) sessionFactory.getCurrentSession().get(Items.class, itemId);
        
        return result;
    }

    @Override
    public List getAllItem() {
      
        List result = sessionFactory.getCurrentSession().createCriteria(Items.class).list();
        
        return result;
    }

    @Override
    public List<Items> getItemByCategory(Category category) {
       List itemsByCategory=sessionFactory.getCurrentSession().createCriteria(Items.class)
                .add(Restrictions.eq("category", category))
               .list();
        return itemsByCategory;
    }
    
//     @Override
//    public List<Item> getAvailableItemByCategoryType(Categorytype categoryType) {
//        List itemsByCategory=sessionFactory.getCurrentSession().createCriteria(Item.class)
//                .add(Restrictions.and(Restrictions.eq("isAllowed", Boolean.TRUE),Restrictions.eq("categorytype", categoryType)))        
//                .list();
//        return itemsByCategory;
//    }

//    @Override
//    public List<Item> getAllStationaryItemNames() {
//       List stationaryItemNames=sessionFactory.getCurrentSession().createCriteria(Item.class)
//               .add(Restrictions.not(Restrictions.eq("type", "Office Asset"))).list();
//
//       return stationaryItemNames;
//    }

    @Override
    public List<Items> getAllOfficeAssetItemNames() {
         List officeAssetItemNames=sessionFactory.getCurrentSession().createCriteria(Items.class)
               .add(Restrictions.eq("type", "Office Asset")).list();
       return officeAssetItemNames;
    }

    @Override
    public List<Items> getAllItemGroupedByCategory() {
        List<Items> itemGroupedByCategory=sessionFactory.getCurrentSession().createCriteria(Items.class,"item")
                .createAlias("item.category", "cat")
                .setProjection(Projections.projectionList()
                .add(Projections.property("itemName"),"itemName")
                .add(Projections.property("itemQty"),"itemQty")
                .add(Projections.property("category"),"category")
                .add(Projections.groupProperty("category"))
                )
                .setResultTransformer(Transformers.aliasToBean(Items.class))
                .list();

        return itemGroupedByCategory;
    }

   
}
