package cn.itcast.invoice.auth.menu.dao.dao;

import java.util.List;

import cn.itcast.invoice.auth.menu.vo.MenuModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface MenuDao extends BaseDao<MenuModel> {

	public List<MenuModel> getByUuidAndPuuidIsOne();

	public List<MenuModel> getByPuuidIsOne();

	public List<MenuModel> getByPuuid(Long puuid);

	public List<MenuModel> getParentByEmpUuid(Long uuid);

	public List<MenuModel> getMenusByPuuidAndEmp(Long puuid, Long empUuid);
}
