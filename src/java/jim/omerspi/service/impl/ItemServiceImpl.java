/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.ItemDao;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import jim.omerspi.service.ItemService;

/**
 *
 * @author JOHN
 */
public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao;

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public void saveOrUpdateItem(Items item) {
        itemDao.saveOrUpdateItem(item);
    }

    @Override
    public void deleteItem(Items item) {
        itemDao.deleteItem(item);
    }

    @Override
    public Items getItemById(Integer itemId) {
        return itemDao.getItemById(itemId);
    }

    @Override
    public List getAllItem() {
        return itemDao.getAllItem();
    }

    @Override
    public List<Items> getItemByCategory(Category category) {
        return itemDao.getItemByCategory(category);
    }

//    @Override
//    public List<Item> getAvailableItemByCategoryType(Categorytype categoryType) {
//        return itemDao.getAvailableItemByCategoryType(categoryType);
//    }

//    @Override
//    public List<Item> getAllStationaryItemNames() {
//        return itemDao.getAllStationaryItemNames();
//    }
    @Override
    public List<Items> getAllOfficeAssetItemNames() {
        return itemDao.getAllOfficeAssetItemNames();
    }

    @Override
    public List<Items> getAllItemGroupedByCategory() {
        return itemDao.getAllItemGroupedByCategory();
    }
}
