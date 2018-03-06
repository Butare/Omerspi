/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.CategoryDao;
import jim.omerspi.model.Category;
import jim.omerspi.service.CategoryService;

/**
 *
 * @author JOHN
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void saveOrUpdateCategory(Category category) {
        categoryDao.saveOrUpdateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryDao.deleteCategory(category);
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    @Override
    public List getAllCategory() {
        return categoryDao.getAllCategory();
    }

    @Override
    public List<Category> getAllNotOfficeAssetCategory() {
        return categoryDao.getAllNotOfficeAssetCategory();
    }

    @Override
    public List<Category> getAllOfficeAssetCategory() {
        return categoryDao.getAllOfficeAssetCategory();
    }
}
