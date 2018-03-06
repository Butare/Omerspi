/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;

import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface CategoryTypeDao {

    public void saveOrUpdateCategoryType(Categorytype categoryType);

    public void deleteCategoryType(Categorytype categoryType);

    public Categorytype getCategoryTypeById(Integer categoryTypeId);

    public List getAllCategoryType();

    public List<Categorytype> getCategoryTypeByCategory(Category category);

    public List<Categorytype> getCategoryTypeByOfficeAssetCategory(Category category);

    public List<Categorytype> getCategoryTypeByNotOfficeAssetCategory(Category category);
}
