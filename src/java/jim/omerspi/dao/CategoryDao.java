/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Category;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface CategoryDao {

    public void saveOrUpdateCategory(Category category);

    public void deleteCategory(Category category);

    public Category getCategoryById(Integer categoryId);

    public List<Category> getAllCategory();

    public List<Category> getAllNotOfficeAssetCategory();

    public List<Category> getAllOfficeAssetCategory();
}