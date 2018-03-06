/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.PrivilegeDao;
import jim.omerspi.model.Privilege;
import jim.omerspi.service.PrivilegeService;

/**
 *
 * @author JOHN
 */
public class PrivilegeServiceImpl implements PrivilegeService {

    private PrivilegeDao privilegeDao;

    public void setPrivilegeDao(PrivilegeDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }

    @Override
    public void saveOrUpdatePrivilege(Privilege privilege) {
        privilegeDao.saveOrUpdatePrivilege(privilege);
    }

    @Override
    public void deletePrivilege(Privilege privilege) {
        privilegeDao.deletePrivilege(privilege);
    }

    @Override
    public Privilege getPrivilegeByName(String privilege) {
        return privilegeDao.getPrivilegeByName(privilege);
    }

    @Override
    public List getAllPrivilege() {
        return privilegeDao.getAllPrivilege();
    }
}
