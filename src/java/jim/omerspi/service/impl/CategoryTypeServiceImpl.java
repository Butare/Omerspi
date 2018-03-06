/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.CategoryTypeDao;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.service.CategoryTypeService;

/**
 *
 * @author JOHN
 */
public class CategoryTypeServiceImpl implements CategoryTypeService {

    private CategoryTypeDao categoryTypeDao;

    public void setCategoryTypeDao(CategoryTypeDao categoryTypeDao) {
        this.categoryTypeDao = categoryTypeDao;
    }

    @Override
    public void saveOrUpdateCategoryType(Categorytype categoryType) {
        categoryTypeDao.saveOrUpdateCategoryType(categoryType);
    }

    @Override
    public void deleteCategoryType(Categorytype categoryType) {
        categoryTypeDao.deleteCategoryType(categoryType);
    }

    @Override
    public Categorytype getCategoryTypeById(Integer categoryTypeId) {
        return categoryTypeDao.getCategoryTypeById(categoryTypeId);
    }

    @Override
    public List<Categorytype> getAllCategoryType() {
        return categoryTypeDao.getAllCategoryType();
    }

    @Override
    public List<Categorytype> getCategoryTypeByCategory(Category category) {
        return categoryTypeDao.getCategoryTypeByCategory(category);
    }

    @Override
    public List<Categorytype> getCategoryTypeByOfficeAssetCategory(Category category) {
        return categoryTypeDao.getCategoryTypeByOfficeAssetCategory(category);
    }

    @Override
    public List<Categorytype> getCategoryTypeByNotOfficeAssetCategory(Category category) {
        return categoryTypeDao.getCategoryTypeByNotOfficeAssetCategory(category);
    }
}
