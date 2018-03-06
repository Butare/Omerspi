/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.CartypeDao;
import jim.omerspi.model.Cartype;
import jim.omerspi.service.CartypeService;

/**
 *
 * @author JOHN
 */
public class CartypeServiceImpl implements CartypeService {

    private CartypeDao cartypeDao;

    public void setCartypeDao(CartypeDao cartypeDao) {
        this.cartypeDao = cartypeDao;
    }

    @Override
    public void saveOrUpdateCartype(Cartype carType) {
        cartypeDao.saveOrUpdateCartype(carType);
    }

    @Override
    public void deleteCartype(Cartype carType) {
        cartypeDao.deleteCartype(carType);
    }

    @Override
    public Cartype getCartypeById(Integer carTypeId) {
        return cartypeDao.getCartypeById(carTypeId);
    }

    @Override
    public List getAllCartype() {
        return cartypeDao.getAllCartype();
    }
}
