/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Cartype;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface CartypeDao {

    public void saveOrUpdateCartype(Cartype carType);

    public void deleteCartype(Cartype carType);

    public Cartype getCartypeById(Integer carTypeId);

    public List getAllCartype();
}
