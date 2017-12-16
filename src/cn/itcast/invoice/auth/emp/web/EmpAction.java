package cn.itcast.invoice.auth.emp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.invoice.auth.dep.business.ebi.DepEbi;
import cn.itcast.invoice.auth.dep.vo.DepModel;
import cn.itcast.invoice.auth.emp.business.ebi.EmpEbi;
import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.auth.emp.vo.EmpQueryModel;
import cn.itcast.invoice.auth.role.business.ebi.RoleEbi;
import cn.itcast.invoice.auth.role.vo.RoleModel;
import cn.itcast.invoice.util.base.BaseAction;

public class EmpAction extends BaseAction{
	//1.getUm() 用
	//2.尝试性的使用public权限访问 用
	//3.setUm() 有
	//3.1 在每次调用对象时，执行set方法，创建一个新对象
	/*
	localhost:8080/ERP/emp_fn.action?um.uuid=1&um.name=2&um.age=3
	um.uuid
	um.get?  um.public? new UserModel() setUm(new...)  setUuid(1);
	um.get?	 um.public? new UserModel() setUm(new...)  setName(2);
	um.get?	 um.public? new UserModel() setUm(new...)  setAge(3);
	age name uuid 
	*/
	/*
	private UserModel um = new UserModel();
	
	public void setUm(UserModel um) {
		System.out.println("set.........................");
		this.um = um;
	}

	public String fn(){
		System.out.println(um.getUuid());
		System.out.println(um.getName());
		System.out.println(um.getAge());
		return "fn";
	}
	*/
	
	public EmpModel em = new EmpModel();
	public EmpQueryModel eqm = new EmpQueryModel();

	private EmpEbi empEbi;
	private DepEbi depEbi;
	private RoleEbi roleEbi;
	
	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}

	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}

	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}

	//跳转到列表页面
	public String list(){
		//加载部门全信息
		List<DepModel> depList = depEbi.getAll();
		put("depList",depList);
		setDataTotal(empEbi.getCount(eqm));
		List<EmpModel> empList = empEbi.getAll(eqm,pageNum,pageCount);
		put("empList",empList);
		return LIST;
	}

	public Long[] roleUuids;
	//保存/修改
	public String save(){
		if(em.getUuid()== null){
			empEbi.save(em,roleUuids);
		}else{
			empEbi.update(em,roleUuids);
		}
		return TO_LIST;
	}

	//跳转到添加/修改页面
	public String input(){
		//将部门列表数据
		List<DepModel> depList = depEbi.getAll();
		put("depList",depList);
		//加载角色数据
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList",roleList);
		if(em.getUuid()!=null){
			em = empEbi.get(em.getUuid());
			//集合->数组
			roleUuids = new Long[em.getRoles().size()];
			int i = 0;
			for(RoleModel rm:em.getRoles()){
				roleUuids[i++] = rm.getUuid();
			}
		}
		return INPUT;
	}

	//删除
	public String delete(){
		empEbi.delete(em);
		return TO_LIST;
	}

	//登陆：使用用户传递的用户名密码进行登陆
	public String login(){
		HttpServletRequest request = getRequest();
		String loginIp = request.getHeader("x-forwarded-for"); 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getRemoteAddr(); 
		}
		//使用用户名密码到数据库进行校验查询
		EmpModel loginEm = empEbi.login(em.getUserName(),em.getPwd(),loginIp);
		//判断是否登陆成功
		if(loginEm == null){
			//如果匹配失败
			//跳转到登陆页
			return "loginFail";
		}else{
			//如果匹配成功
			//将登陆的信息放入Session
			putSession("loginEm", loginEm);
			//跳转到主页
			return "loginSuccess";
		}
	}
	
	//登出/注销
	public String logout(){
		//将session中的数据清除
		putSession("loginEm", null);
		//跳转到登陆页面
		return "loginFail";
	}
	
	//修改密码
	public String changePwd(){
		//old: em.pwd
		//new: request.getParameter();
		String oldPwd = em.getPwd();
		String newPwd = getRequest().getParameter("newPwd");
		boolean flag = empEbi.changePwd(getLogin().getUserName(),oldPwd,newPwd);
		if(flag){
			//修改成功
			return logout();
		}else{
			//修改失败
			return "hehe";
		}
	}
}







