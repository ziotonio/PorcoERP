package cn.itcast.invoice.auth.menu.business.ebo;

import java.io.Serializable;
import java.util.List;

import cn.itcast.invoice.auth.menu.business.ebi.MenuEbi;
import cn.itcast.invoice.auth.menu.dao.dao.MenuDao;
import cn.itcast.invoice.auth.menu.vo.MenuModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.exception.AppException;

public class MenuEbo implements MenuEbi{
	private MenuDao menuDao;
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void save(MenuModel mm) {
		menuDao.save(mm);
	}

	public void delete(MenuModel mm) {
		//页面收集的数据中仅包含uuid,此处删除业务需要进行级联，必须先将关联关系数据加载上
		//因此在删除之前进行一次查询，加载关联数据
		mm = menuDao.get(mm.getUuid());
		menuDao.delete(mm);
	}

	public void update(MenuModel mm) {
		menuDao.update(mm);
	}

	public MenuModel get(Serializable uuid) {
		return menuDao.get(uuid);
	}

	public List<MenuModel> getAll() {
		return menuDao.getAll();
	}

	public List<MenuModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return menuDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return menuDao.getCount(qm);
	}

	public List<MenuModel> getParentMenu() {
		return menuDao.getByUuidAndPuuidIsOne();
	}

	public List<MenuModel> getParentMenu2() {
		return menuDao.getByPuuidIsOne();
	}

	public List<MenuModel> getMenusByPuuid(Long puuid) {
		return menuDao.getByPuuid(puuid);
	}

	public List<MenuModel> getParentMenuByEmp(Long uuid) {
		return menuDao.getParentByEmpUuid(uuid);
	}

	public List<MenuModel> getMenusByPuuidAndEmp(Long puuid, Long empUuid) {
		return menuDao.getMenusByPuuidAndEmp(puuid,empUuid);
	}

}
