package cn.itcast.invoice.auth.dep.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.auth.dep.vo.DepModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface DepEbi extends BaseEbi<DepModel> {
}


/*
public void save(DepModel dm);

public void update(DepModel dm);

public void delete(DepModel dm);

public DepModel get(Long uuid);

public List<DepModel> getAll();

/**
 * 分页获取数据
 * @param dqm 查询条件
 * @param pageNum 页码值
 * @param pageCount 每页显示数
 * @return
 */
/*	public List<DepModel> getAll(DepQueryModel dqm, Integer pageNum,Integer pageCount);

public Integer getCount(DepQueryModel dqm);
*/