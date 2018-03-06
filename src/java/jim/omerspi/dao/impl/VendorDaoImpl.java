/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.dao.VendorDao;
import jim.omerspi.model.Vendor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author JOHN
 */
public class VendorDaoImpl implements VendorDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdateVendor(Vendor vendor) {

        sessionFactory.getCurrentSession().saveOrUpdate(vendor);

    }

    @Override
    public void deleteVendor(Vendor vendor) {

        sessionFactory.getCurrentSession().delete(vendor);
    }

    @Override
    public Vendor getVendorById(Integer vendorId) {

        Vendor result = (Vendor) sessionFactory.getCurrentSession().get(Vendor.class, vendorId);

        return result;
    }

    @Override
    public List getAllVendor() {

        List result = sessionFactory.getCurrentSession().createCriteria(Vendor.class).list();

        return result;
    }

    @Override
    public List<Vendor> getAllValidEquipmentVendors() {
  
        Date d1 = OmerspiUtils.getFirstMomentOfDay(new Date());
   
        List<Vendor> validVendors = sessionFactory.getCurrentSession().createCriteria(Vendor.class)
                .add(Restrictions.eq("tenderPurpose", "Equipment"))
                .add(Restrictions.gt("endingPeriod", d1))
                .list();
        return validVendors;
       
    }

    @Override
    public List<Vendor> getAllValidTransportVendors() {
        
        Date d1 = OmerspiUtils.getFirstMomentOfDay(new Date());
         List<Vendor> validVendors = sessionFactory.getCurrentSession().createCriteria(Vendor.class)
                .add(Restrictions.eq("tenderPurpose", "Transport"))
                .add(Restrictions.gt("endingPeriod", d1))
                .list();
        return validVendors;
    }
}
