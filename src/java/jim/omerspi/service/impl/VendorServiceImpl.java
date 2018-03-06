/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.VendorDao;
import jim.omerspi.model.Vendor;
import jim.omerspi.service.VendorService;

/**
 *
 * @author JOHN
 */
public class VendorServiceImpl implements VendorService {

    private VendorDao vendorDao;

    public void setVendorDao(VendorDao vendorDao) {
        this.vendorDao = vendorDao;
    }

    @Override
    public void saveOrUpdateVendor(Vendor vendor) {

        vendorDao.saveOrUpdateVendor(vendor);
    }

    @Override
    public void deleteVendor(Vendor vendor) {
        vendorDao.deleteVendor(vendor);
    }

    @Override
    public Vendor getVendorById(Integer vendorId) {
        return vendorDao.getVendorById(vendorId);
    }

    @Override
    public List getAllVendor() {
        return vendorDao.getAllVendor();
    }

    @Override
    public List<Vendor> getAllValidEquipmentVendors() {
        return vendorDao.getAllValidEquipmentVendors();
    }

    @Override
    public List<Vendor> getAllValidTransportVendors() {
        return vendorDao.getAllValidTransportVendors();
    }
}
