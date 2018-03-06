/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import java.util.List;
import jim.omerspi.model.Cartype;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface CartypeService {

    public void saveOrUpdateCartype(Cartype carType);

    public void deleteCartype(Cartype carType);

    public Cartype getCartypeById(Integer carTypeId);

    public List getAllCartype();
}
