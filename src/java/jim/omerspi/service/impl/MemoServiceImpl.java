/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.service.impl;

import java.util.List;
import jim.omerspi.dao.MemoDao;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Memo;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.service.MemoService;

/**
 *
 * @author JOHN
 */
public class MemoServiceImpl implements MemoService {

    private MemoDao memoDao;

    public void setMemoDao(MemoDao memoDao) {
        this.memoDao = memoDao;
    }

    @Override
    public void saveOrUpdateMemo(Memo memo) {
        memoDao.saveOrUpdateMemo(memo);
    }

    @Override
    public void deleteMemo(Memo memo) {
        memoDao.deleteMemo(memo);
    }

    @Override
    public Memo getMemoById(Integer memoId) {
        return memoDao.getMemoById(memoId);
    }

    @Override
    public List<Memo> getAllMemo() {
        return memoDao.getAllMemo();
    }

    @Override
    public List getMemoByOfficeAssetRequisitionId(Officeassetrequisition officeAssetRequisition) {
        return memoDao.getMemoByOfficeAssetRequisitionId(officeAssetRequisition);
    }

    @Override
    public List getMemoByCarRequisitionId(Carrequisition carRequisition) {
        return memoDao.getMemoByCarRequisitionId(carRequisition);
    }
}
