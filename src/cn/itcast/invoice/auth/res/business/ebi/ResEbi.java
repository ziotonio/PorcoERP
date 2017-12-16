package cn.itcast.invoice.auth.res.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.auth.res.vo.ResModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface ResEbi extends BaseEbi<ResModel> {

	public void save(ResModel rm, Long[] roleUuids);

	public void update(ResModel rm, Long[] roleUuids);
	/**
	 * 获取指定员工的所有可操作资源信息
	 * @param uuid 员工uuid
	 * @return
	 */
	public List<String> getAllResByEmp(Long uuid);

	public List<String> getAllUrl();
}
