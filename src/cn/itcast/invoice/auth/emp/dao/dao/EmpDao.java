package cn.itcast.invoice.auth.emp.dao.dao;

import java.util.List;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface EmpDao extends BaseDao<EmpModel> {
	/**
	 * 根据用户名密码获取用户信息
	 * @param userName 用户名
	 * @param pwd 密码
	 * @return 登陆用户信息模型
	 */
	public EmpModel getByNameAndPwd(String userName, String pwd);

	public boolean updatePwdByUserNameAndPwd(String userName, String oldPwd,
			String newPwd);

	public List<EmpModel> getAllByDepUuid(Long depUuid);
}
