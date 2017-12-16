package cn.itcast.invoice.invoice.goods.business.ebo;

import java.io.Serializable;
import java.util.List;

import cn.itcast.invoice.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.invoice.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.exception.AppException;

public class GoodsEbo implements GoodsEbi{
	private GoodsDao goodsDao;
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void save(GoodsModel gm) {
		gm.setUseNum(0);
		goodsDao.save(gm);
	}

	public void delete(GoodsModel gm) {
		goodsDao.delete(gm);
	}

	public void update(GoodsModel gm) {
		//快照更新
		goodsDao.update(gm);
	}

	public GoodsModel get(Serializable uuid) {
		return goodsDao.get(uuid);
	}

	public List<GoodsModel> getAll() {
		return goodsDao.getAll();
	}

	public List<GoodsModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return goodsDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return goodsDao.getCount(qm);
	}

	public List<GoodsModel> getAllByGtmUuid(Long gtmUuid) {
		return goodsDao.getAllByGtmUuid(gtmUuid);
	}

}
