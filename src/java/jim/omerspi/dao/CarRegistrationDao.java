/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Carregistration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface CarRegistrationDao {

    public void saveOrUpdateCarRegistration(Carregistration carRegistration);

    public void deleteCarRegistration(Carregistration carRegistration);

    public Carregistration getCarRegistrationById(Integer carRegistrationId);

    public List getAllCarRegistration();

    public List<Carregistration> getAllCarNotInService();
}
