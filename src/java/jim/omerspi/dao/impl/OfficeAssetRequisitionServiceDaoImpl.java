/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.OfficeAssetRequisitionServiceDao;
import jim.omerspi.model.Officeassetrequisitionservice;
import jim.omerspi.model.RequestedItems;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author JOHN
 */
public class OfficeAssetRequisitionServiceDaoImpl implements OfficeAssetRequisitionServiceDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService) {
        sessionFactory.getCurrentSession().saveOrUpdate(officeAssetRequisitionService);
    }

    @Override
    public void deleteOfficeAssetRequisitionService(Officeassetrequisitionservice officeAssetRequisitionService) {
        sessionFactory.getCurrentSession().delete(officeAssetRequisitionService);
    }

    @Override
    public Officeassetrequisitionservice getOfficeAssetRequisitionServiceById(Integer serviceId) {
        return (Officeassetrequisitionservice) sessionFactory.getCurrentSession().get(Officeassetrequisitionservice.class, serviceId);
    }

    @Override
    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionService() {
        List<Officeassetrequisitionservice> result = sessionFactory.getCurrentSession().createCriteria(Officeassetrequisitionservice.class).list();
        return result;
    }

    @Override
    public List<Officeassetrequisitionservice> getAllOfficeAssetRequisitionServiceByRequestedItem(RequestedItems requested) {
       List<Officeassetrequisitionservice> allByRequested=sessionFactory.getCurrentSession().createCriteria(Officeassetrequisitionservice.class)
               .add(Restrictions.eq("requestedItems", requested))
               .list();
       return allByRequested;
    }
}
