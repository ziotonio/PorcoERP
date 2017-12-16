package cn.itcast.invoice.auth.emp.business.ebo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.invoice.auth.dep.vo.DepModel;
import cn.itcast.invoice.auth.emp.business.ebi.EmpEbi;
import cn.itcast.invoice.auth.emp.dao.dao.EmpDao;
import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.auth.res.dao.dao.ResDao;
import cn.itcast.invoice.auth.role.vo.RoleModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.exception.AppException;
import cn.itcast.invoice.util.format.MD5Utils;

public class EmpEbo implements EmpEbi{
	private EmpDao empDao;
	private ResDao resDao;
	
	public void setResDao(ResDao resDao) {
		this.resDao = resDao;
	}

	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

	public void save(EmpModel em) {
		//为某些数据进行初始化
		em.setPwd(MD5Utils.md5(em.getPwd()));
		em.setLastLoginIp("--");
		em.setLastLoginTime(System.currentTimeMillis());
		//设置新注册用户登录次数为0
		em.setLoginTimes(0);
		empDao.save(em);
	}

	public void delete(EmpModel em) {
		empDao.delete(em);
	}

	public void update(EmpModel em) {
		//缺少一些数据，这些数据不能从页面收集
		//快照更新
		EmpModel temp = empDao.get(em.getUuid());
		//将缺少的值全部赋值上去
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setTele(em.getTele());
		temp.setAddress(em.getAddress());
		temp.setBirthday(em.getBirthday());
		temp.setGender(em.getGender());
		temp.setDm(em.getDm());
		
		//hibernate一级缓存中的对象OID不可修改，因此抛出异常
		//temp.getDm().setUuid(em.getDm().getUuid());
		
		/*
		//由于使用get方法加载的对象时PO对象
		//此时修改的对象将从DO->PO
		//两个对象将具有相同的OID，位于同一一级缓存空间内，引发ID重复冲突
		//将缺少的值全部赋值上去
		em.setUserName(temp.getUserName());
		em.setPwd(temp.getPwd());
		em.setLastLoginIp(temp.getLastLoginIp());
		em.setLastLoginTime(temp.getLastLoginTime());
		em.setLoginTimes(temp.getLoginTimes());
		empDao.update(em);
		*/
	}

	public EmpModel get(Serializable uuid) {
		return empDao.get(uuid);
	}

	public List<EmpModel> getAll() {
		return empDao.getAll();
	}

	public List<EmpModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return empDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return empDao.getCount(qm);
	}

	public EmpModel login(String userName, String pwd ,String lastLoginIp) {
		//对密码进行md5加密
		pwd = MD5Utils.md5(pwd);
		EmpModel loginEm = empDao.getByNameAndPwd(userName,pwd);
		//任意时刻取出的数据，第一件事必须做null判定
		if(loginEm != null){
			//记录登陆的时间,ip,次数
			loginEm.setLastLoginIp(lastLoginIp);
			loginEm.setLastLoginTime(System.currentTimeMillis());
			//修改登陆次数
			loginEm.setLoginTimes(loginEm.getLoginTimes()+1);
			
			//登陆时，将当前用户的所有可操作资源转换为一个长字符串，并设置到登陆对象中
			List<String> resList = resDao.getAllResByEmp(loginEm.getUuid());
			StringBuilder sbf = new StringBuilder();
			for(String url:resList){
				sbf.append(url);
				sbf.append(" ");
			}
			loginEm.setResValue(sbf.toString());
		}
		/*
		else{
			throw new AppException("INFO_EMP_USERNAME_OR_PWD_ERROR");
		}
		*/
		return loginEm;
	}

	public boolean changePwd(String userName, String oldPwd, String newPwd) {
		oldPwd = MD5Utils.md5(oldPwd);
		newPwd = MD5Utils.md5(newPwd);
		return empDao.updatePwdByUserNameAndPwd(userName,oldPwd,newPwd);
	}

	public void save(EmpModel em, Long[] roleUuids) {
		//为某些数据进行初始化
		em.setPwd(MD5Utils.md5(em.getPwd()));
		em.setLastLoginIp("--");
		em.setLastLoginTime(System.currentTimeMillis());
		//设置新注册用户登录次数为0
		em.setLoginTimes(0);
		
		//建立与角色的关系
		//数组->集合
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long uuid:roleUuids){
			RoleModel rm = new RoleModel();
			rm.setUuid(uuid);
			roles.add(rm);
		}
		em.setRoles(roles);
		empDao.save(em);
	}

	public void update(EmpModel em, Long[] roleUuids) {
		//缺少一些数据，这些数据不能从页面收集
		//快照更新
		EmpModel temp = empDao.get(em.getUuid());
		//将缺少的值全部赋值上去
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setTele(em.getTele());
		temp.setAddress(em.getAddress());
		temp.setBirthday(em.getBirthday());
		temp.setGender(em.getGender());
		temp.setDm(em.getDm());
		
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long uuid:roleUuids){
			RoleModel rm = new RoleModel();
			rm.setUuid(uuid);
			roles.add(rm);
		}
		temp.setRoles(roles);
	}

	public List<EmpModel> getAllByDep(Long depUuid) {
		return empDao.getAllByDepUuid(depUuid);
	}
	
}
