/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.RequisitionResponseDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.Requisitionresponses;
import jim.omerspi.model.Stationaryrequisition;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class RequisitionResponseDaoImpl implements RequisitionResponseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateRequisitionResponse(Requisitionresponses requisitionResponse) {
        sessionFactory.getCurrentSession().saveOrUpdate(requisitionResponse);
    }

    @Override
    public void deleteRequisitionResponse(Requisitionresponses requisitionResponse) {
        sessionFactory.getCurrentSession().delete(requisitionResponse);
    }

    @Override
    public Requisitionresponses getRequisitionResponseById(Integer requisitionResponseId) {
        return (Requisitionresponses) sessionFactory.getCurrentSession().get(Requisitionresponses.class, requisitionResponseId);
    }

    @Override
    public List<Requisitionresponses> getAllRequisitionResponse() {
        return sessionFactory.getCurrentSession().createCriteria(Requisitionresponses.class).list();
    }

    @Override
    public List getResponseByCarRequisitionId(Carrequisition carRequisition) {
        List response=sessionFactory.getCurrentSession().createCriteria(Requisitionresponses.class)
                .add(Restrictions.eq("carrequisition",carRequisition))
                .list();
         return response;
    }

    @Override
    public List getResponseByStationaryRequisitionId(Stationaryrequisition stationaryRequisition) {
         List response=sessionFactory.getCurrentSession().createCriteria(Requisitionresponses.class)
                .add(Restrictions.eq("stationaryrequisition",stationaryRequisition))
                .list();
         return response;
    }

    @Override
    public List getResponseByOfficeAssetRequisitionId(Officeassetrequisition officeAssetRequisition) {
         List response=sessionFactory.getCurrentSession().createCriteria(Requisitionresponses.class)
                .add(Restrictions.eq("officeassetrequisition",officeAssetRequisition))
                .list();
         return response;
    }
}
