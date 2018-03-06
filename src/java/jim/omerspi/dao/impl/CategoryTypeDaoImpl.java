/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import jim.omerspi.dao.CategoryTypeDao;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jimmy
 */
public class CategoryTypeDaoImpl implements CategoryTypeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateCategoryType(Categorytype categoryType) {
        sessionFactory.getCurrentSession().saveOrUpdate(categoryType);
    }

    @Override
    public void deleteCategoryType(Categorytype categoryType) {
        sessionFactory.getCurrentSession().delete(categoryType);
    }

    @Override
    public Categorytype getCategoryTypeById(Integer categoryTypeId) {
        Categorytype result = (Categorytype) sessionFactory.getCurrentSession().get(Categorytype.class, categoryTypeId);
        return result;
    }

    @Override
    public List getAllCategoryType() {
        List result = sessionFactory.getCurrentSession().createCriteria(Categorytype.class).list();
        return result;
    }

    @Override
    public List<Categorytype> getCategoryTypeByCategory(Category category) {
        List<Categorytype> byCategory = sessionFactory.getCurrentSession().createCriteria(Categorytype.class)
                .add(Restrictions.eq("category", category)).list();
        return byCategory;
    }

    @Override
    public List<Categorytype> getCategoryTypeByOfficeAssetCategory(Category category) {
        List<Categorytype> byCategory = sessionFactory.getCurrentSession().createCriteria(Categorytype.class).add(Restrictions.eq("category", category)).list();
        List<Categorytype> cat = new ArrayList<Categorytype>();
        for (int i = 0; i < byCategory.size(); i++) {
            if (byCategory.get(i).getCategory().getCategoryName().equals("FURNITURE")) {
                cat.add(byCategory.get(i));
            } else if (!byCategory.get(i).getCategory().getCategoryName().equals("FURNITURE") && (byCategory.get(i).getCategoryTypeName().equals("EQUIPMENT"))) {
                cat.add(byCategory.get(i));
            }
        }
        return cat;

    }

    @Override
    public List<Categorytype> getCategoryTypeByNotOfficeAssetCategory(Category category) {
        List<Categorytype> byCategory = sessionFactory.getCurrentSession().createCriteria(Categorytype.class)
                .add(Restrictions.eq("category", category))
                .add(Restrictions.not(Restrictions.eq("categoryTypeName", "EQUIPMENT"))).list();
        return byCategory;
    }
}
