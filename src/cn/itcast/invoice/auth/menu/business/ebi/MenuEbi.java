package cn.itcast.invoice.auth.menu.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.auth.menu.vo.MenuModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface MenuEbi extends BaseEbi<MenuModel> {
	/**
	 * 获取所有父菜单
	 * @return
	 */
	public List<MenuModel> getParentMenu();
	/**
	 * 获取所有父亲菜单，不包含系统菜单
	 * @return
	 */
	public List<MenuModel> getParentMenu2();
	/**
	 * 获取指定id下的所有子菜单项
	 * @param puuid 指定菜单uuid
	 * @return
	 */
	public List<MenuModel> getMenusByPuuid(Long puuid);
	/**
	 * 根据登陆人获取课操作菜单
	 * @param uuid 登陆人uuid
	 * @return
	 */
	public List<MenuModel> getParentMenuByEmp(Long uuid);
	
	public List<MenuModel> getMenusByPuuidAndEmp(Long puuid, Long uuid);
}
