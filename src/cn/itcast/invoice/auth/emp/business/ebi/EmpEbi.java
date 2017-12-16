package cn.itcast.invoice.auth.emp.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface EmpEbi extends BaseEbi<EmpModel> {
	/**
	 * 根据用户名，密码进行登陆
	 * @param userName 用户名
	 * @param pwd 密码
	 * @param lastLoginIp 登陆IP地址
	 * @return
	 */
	public EmpModel login(String userName, String pwd,String lastLoginIp);
	/**
	 * 修改密码
	 * @param userName 用户名
	 * @param oldPwd 原始密码
	 * @param newPwd 新密码
	 * @return
	 */
	public boolean changePwd(String userName, String oldPwd, String newPwd);
	public void save(EmpModel em, Long[] roleUuids);
	public void update(EmpModel em, Long[] roleUuids);
	/**
	 * 根据部门uuid获取部门员工信息
	 * @param depUuid 部门uuid
	 * @return
	 */
	public List<EmpModel> getAllByDep(Long depUuid);
}
