/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service;

import java.util.List;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Itenerary;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface IteneraryService {

    public void saveOrUpdateItenerary(Itenerary itenerary);

    public void deleteItenerary(Itenerary itenerary);

    public Itenerary getIteneraryById(Integer itemId);

    public List getAllItenerary();

}
