/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.IteneraryDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Itenerary;
import jim.omerspi.service.IteneraryService;

/**
 *
 * @author JOHN
 */
public class IteneraryServiceImpl implements IteneraryService {

    private IteneraryDao iteneraryDao;

    public void setIteneraryDao(IteneraryDao iteneraryDao) {
        this.iteneraryDao = iteneraryDao;
    }

    @Override
    public void saveOrUpdateItenerary(Itenerary itenerary) {
        iteneraryDao.saveOrUpdateItenerary(itenerary);
    }

    @Override
    public void deleteItenerary(Itenerary itenerary) {
        iteneraryDao.deleteItenerary(itenerary);
    }

    @Override
    public Itenerary getIteneraryById(Integer itemId) {
        return iteneraryDao.getIteneraryById(itemId);
    }

    @Override
    public List getAllItenerary() {
        return iteneraryDao.getAllItenerary();
    }
}
