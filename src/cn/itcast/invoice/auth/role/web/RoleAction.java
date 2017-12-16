package cn.itcast.invoice.auth.role.web;

import java.util.List;

import cn.itcast.invoice.auth.menu.business.ebi.MenuEbi;
import cn.itcast.invoice.auth.menu.vo.MenuModel;
import cn.itcast.invoice.auth.res.business.ebi.ResEbi;
import cn.itcast.invoice.auth.res.vo.ResModel;
import cn.itcast.invoice.auth.role.business.ebi.RoleEbi;
import cn.itcast.invoice.auth.role.vo.RoleModel;
import cn.itcast.invoice.auth.role.vo.RoleQueryModel;
import cn.itcast.invoice.util.base.BaseAction;

public class RoleAction extends BaseAction{
	public RoleModel rm = new RoleModel();
	public RoleQueryModel rqm = new RoleQueryModel();

	private RoleEbi roleEbi;
	private ResEbi resEbi;
	private MenuEbi menuEbi;
	
	public void setMenuEbi(MenuEbi menuEbi) {
		this.menuEbi = menuEbi;
	}

	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}

	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}

	//跳转到列表页面
	public String list(){
		setDataTotal(roleEbi.getCount(rqm));
		List<RoleModel> roleList = roleEbi.getAll(rqm,pageNum,pageCount);
		put("roleList",roleList);
		return LIST;
	}
	//资源的uuid数组
	public Long[] resUuids;
	//菜单的uuid数组
	public Long[] menuUuids;
	//保存/修改
	public String save(){
		if(rm.getUuid()== null){
			roleEbi.save(rm,resUuids,menuUuids);
		}else{
			roleEbi.update(rm,resUuids,menuUuids);
		}
		return TO_LIST;
	}
	
	//跳转到添加/修改页面
	public String input(){
		//加载所有的资源数据
		List<ResModel> resList = resEbi.getAll();
		put("resList",resList);
		
		//加载所有的菜单数据
		List<MenuModel> menuList = menuEbi.getAll();
		put("menuList",menuList);
		
		if(rm.getUuid()!=null){
			rm = roleEbi.get(rm.getUuid());
			//将rm对象中的reses转换为页面可以接收的数据格式 resUuids数组
			//集合Set->Long[]
			resUuids = new Long[rm.getReses().size()];
			int i = 0;
			for(ResModel temp: rm.getReses()){
				resUuids[i++] = temp.getUuid();
			}
			
			menuUuids = new Long[rm.getMenus().size()];
			i = 0;
			for(MenuModel temp: rm.getMenus()){
				menuUuids[i++] = temp.getUuid();
			}
		}
		return INPUT;
	}

	//删除
	public String delete(){
		roleEbi.delete(rm);
		return TO_LIST;
	}

}
