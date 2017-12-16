package cn.itcast.invoice.auth.res.web;

import java.util.List;

import cn.itcast.invoice.auth.res.business.ebi.ResEbi;
import cn.itcast.invoice.auth.res.vo.ResModel;
import cn.itcast.invoice.auth.res.vo.ResQueryModel;
import cn.itcast.invoice.auth.role.business.ebi.RoleEbi;
import cn.itcast.invoice.auth.role.vo.RoleModel;
import cn.itcast.invoice.util.base.BaseAction;

public class ResAction extends BaseAction{
	public ResModel rm = new ResModel();
	public ResQueryModel rqm = new ResQueryModel();

	private ResEbi resEbi;
	private RoleEbi roleEbi;
	
	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}

	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}

	//跳转到列表页面
	public String list(){
		setDataTotal(resEbi.getCount(rqm));
		List<ResModel> resList = resEbi.getAll(rqm,pageNum,pageCount);
		put("resList",resList);
		return LIST;
	}
	
	public Long[] roleUuids;
	//保存/修改
	public String save(){
		if(rm.getUuid()== null){
			resEbi.save(rm,roleUuids);
		}else{
			resEbi.update(rm,roleUuids);
		}
		return TO_LIST;
	}

	//跳转到添加/修改页面
	public String input(){
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList",roleList);
		if(rm.getUuid()!=null){
			rm = resEbi.get(rm.getUuid());
			//将集合转换为数组
			roleUuids = new Long[rm.getRoles().size()];
			int i = 0;
			for(RoleModel temp : rm.getRoles()){
				roleUuids[i++] = temp.getUuid();
			}
		}
		return INPUT;
	}

	//删除
	public String delete(){
		resEbi.delete(rm);
		return TO_LIST;
	}

}
