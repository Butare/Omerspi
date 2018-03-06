/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.CategoryDao;
import jim.omerspi.model.Category;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateCategory(Category category) {
        sessionFactory.getCurrentSession().saveOrUpdate(category);

    }

    @Override
    public void deleteCategory(Category category) {

        sessionFactory.getCurrentSession().delete(category);

    }

    @Override
    public Category getCategoryById(Integer categoryId) {

        Category result = (Category) sessionFactory.getCurrentSession().get(Category.class, categoryId);

        return result;
    }

    @Override
    public List<Category> getAllCategory() {

        List<Category> result = sessionFactory.getCurrentSession().createCriteria(Category.class).list();

        return result;
    }

    @Override
    public List<Category> getAllNotOfficeAssetCategory() {
        List<Category> result=sessionFactory.getCurrentSession().createCriteria(Category.class)
                .add(Restrictions.not(Restrictions.eq("categoryName", "FURNITURE")))
                .list();
        return result;
    }

    @Override
    public List<Category> getAllOfficeAssetCategory() {
         List<Category> result=sessionFactory.getCurrentSession().createCriteria(Category.class)
                .add(Restrictions.or(Restrictions.eq("categoryName", "FURNITURE"),Restrictions.eq("categoryName", "ELECTRONICS")))
                .list();
        return result;
    }
}
