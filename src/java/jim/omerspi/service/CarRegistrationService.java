/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import java.util.List;
import jim.omerspi.model.Carregistration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface CarRegistrationService {

    public void saveOrUpdateCarRegistration(Carregistration carRegistration);

    public void deleteCarRegistration(Carregistration carRegistration);

    public Carregistration getCarRegistrationById(Integer carRegistrationId);

    public List getAllCarRegistration();

    public List<Carregistration> getAllCarNotInService();
}
