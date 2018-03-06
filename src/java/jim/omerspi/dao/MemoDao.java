/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.List;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Memo;
import jim.omerspi.model.Officeassetrequisition;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JOHN
 */
@Transactional
public interface MemoDao {

    public void saveOrUpdateMemo(Memo memo);

    public void deleteMemo(Memo memo);

    public Memo getMemoById(Integer memoId);

    public List<Memo> getAllMemo();

    public List getMemoByOfficeAssetRequisitionId(Officeassetrequisition officeAssetRequisition);

    public List getMemoByCarRequisitionId(Carrequisition carRequisition);
}
