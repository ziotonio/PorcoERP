package cn.itcast.invoice.auth.res.dao.dao;

import java.util.List;

import cn.itcast.invoice.auth.res.vo.ResModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface ResDao extends BaseDao<ResModel> {

	public List<String> getAllResByEmp(Long empUuid);

	public List<String> getUrls();
}
