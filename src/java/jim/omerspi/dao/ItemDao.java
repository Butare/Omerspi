/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface ItemDao {

    public void saveOrUpdateItem(Items item);

    public void deleteItem(Items item);

    public Items getItemById(Integer itemId);

    public List<Items> getAllItem();

    public List<Items> getItemByCategory(Category category);

//    public List<Item> getAvailableItemByCategoryType(Categorytype categoryType);

//    public List<Item> getAllStationaryItemNames();

    public List<Items> getAllOfficeAssetItemNames();

    public List<Items> getAllItemGroupedByCategory();
}
