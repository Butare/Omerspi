/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Privilege;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface PrivilegeDao {

    public void saveOrUpdatePrivilege(Privilege privilege);

    public void deletePrivilege(Privilege privilege);

    public Privilege getPrivilegeByName(String privilege);

    public List getAllPrivilege();
}
