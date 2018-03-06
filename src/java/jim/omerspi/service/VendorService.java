/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import java.util.List;
import jim.omerspi.model.Vendor;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface VendorService {

    public void saveOrUpdateVendor(Vendor vendor);

    public void deleteVendor(Vendor vendor);

    public Vendor getVendorById(Integer vendorId);

    public List getAllVendor();

    public List<Vendor> getAllValidEquipmentVendors();

    public List<Vendor> getAllValidTransportVendors();
}
