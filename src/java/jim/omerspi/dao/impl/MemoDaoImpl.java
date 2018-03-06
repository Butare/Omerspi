/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.util.List;
import jim.omerspi.dao.MemoDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Memo;
import jim.omerspi.model.Officeassetrequisition;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JOHN
 */
public class MemoDaoImpl implements MemoDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateMemo(Memo memo) {
        sessionFactory.getCurrentSession().saveOrUpdate(memo);
    }

    @Override
    public void deleteMemo(Memo memo) {
        sessionFactory.getCurrentSession().delete(memo);
    }

    @Override
    public Memo getMemoById(Integer memoId) {
        Memo result = (Memo) sessionFactory.getCurrentSession().get(Memo.class, memoId);
        return result;
    }

    @Override
    public List<Memo> getAllMemo() {
        List result = sessionFactory.getCurrentSession().createCriteria(Memo.class).list();
        return result;
    }

    @Override
    public List<Memo> getMemoByOfficeAssetRequisitionId(Officeassetrequisition officeAssetRequisition) {

        List memo = sessionFactory.getCurrentSession().createCriteria(Memo.class).add(Restrictions.eq("officeassetrequisition", officeAssetRequisition)).list();
        return memo;
    }

    @Override
    public List getMemoByCarRequisitionId(Carrequisition carRequisition) {
        List memoByCar = sessionFactory.getCurrentSession().createCriteria(Memo.class).add(Restrictions.eq("carrequisition", carRequisition)).list();
        return memoByCar;
    }
}
