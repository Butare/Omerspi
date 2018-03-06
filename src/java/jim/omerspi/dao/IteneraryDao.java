/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Itenerary;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface IteneraryDao {

    public void saveOrUpdateItenerary(Itenerary itenerary);

    public void deleteItenerary(Itenerary itenerary);

    public Itenerary  getIteneraryById(Integer itemId);

    public List getAllItenerary();
    
}
