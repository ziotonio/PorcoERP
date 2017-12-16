package cn.itcast.invoice.invoice.storeoper.business.ebo;

import java.io.Serializable;
import java.util.List;

import cn.itcast.invoice.invoice.storeoper.business.ebi.StoreOperEbi;
import cn.itcast.invoice.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.invoice.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.exception.AppException;

public class StoreOperEbo implements StoreOperEbi{
	private StoreOperDao storeOperDao;
	public void setStoreOperDao(StoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}

	public void save(StoreOperModel sm) {
		storeOperDao.save(sm);
	}

	public void delete(StoreOperModel sm) {
		storeOperDao.delete(sm);
	}

	public void update(StoreOperModel sm) {
		storeOperDao.update(sm);
	}

	public StoreOperModel get(Serializable uuid) {
		return storeOperDao.get(uuid);
	}

	public List<StoreOperModel> getAll() {
		return storeOperDao.getAll();
	}

	public List<StoreOperModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return storeOperDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return storeOperDao.getCount(qm);
	}

}
