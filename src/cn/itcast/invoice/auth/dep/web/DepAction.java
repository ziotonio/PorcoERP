package cn.itcast.invoice.auth.dep.web;

import java.util.List;

import cn.itcast.invoice.auth.dep.business.ebi.DepEbi;
import cn.itcast.invoice.auth.dep.vo.DepModel;
import cn.itcast.invoice.auth.dep.vo.DepQueryModel;
import cn.itcast.invoice.util.base.BaseAction;

public class DepAction extends BaseAction{

	public DepModel dm = new DepModel();
	public DepQueryModel dqm = new DepQueryModel();
	
	private DepEbi depEbi;
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	
	//跳转到列表页面
	public String list(){
		//计算最大页码值 : 记录总条目数与每页显示数计算而来
		//dataTotal = depEbi.getCount(dqm);
		//最大页码值
		/*
		37  10 	4
		(37+10 -1) /10 	4
		39	10	4
		(39+10-1) /10  4
		40	10	4
		(40+10-1) /10  4
		41  10	5
		(41+10-1) /10  5
		*/
		//maxPageNum = (dataTotal + pageCount -1) / pageCount; 
		setDataTotal(depEbi.getCount(dqm));
		//获取所有的部门数据
		List<DepModel> depList = depEbi.getAll(dqm,pageNum,pageCount);
		//将该数据放入指定范围
		//ActionContext.getContext().put("depList", depList);
		put("depList",depList);
		//跳转页面
		//页面中从指定范围内获取数据展示
		return LIST;
	}
	
	/*
	//按条件查询
	public String queryList(){
		//根据查询条件进行查询获得最终显示的数据
		List<DepModel> depList = depEbi.getAll(dqm);
		//放入指定范围
		ActionContext.getContext().put("depList",depList);
		//跳转页面
		return "list";
	}
	*/
	
	//新建部门
	public String save(){
		//通过页面是否传递有具体的uuid值来区分究竟是添加还是修改
		if(dm.getUuid()== null){	//没有传递uuid值，添加
			depEbi.save(dm);
		}else{						//传递了uuid值，修改
			depEbi.update(dm);
		}
		return TO_LIST;
	}
	
	//跳转到修改页面
	public String input(){
		//如果传递有uuid，执行查询
		if(dm.getUuid()!=null){
			//根据传递的uuid获取对应的数据
			dm = depEbi.get(dm.getUuid());
		}
		return INPUT;
	}
	
	//删除部门信息
	public String delete(){
		depEbi.delete(dm);
		return TO_LIST;
	}
	
}


/*
public class DepAction extends ActionSupport{
	public DepModel dm = new DepModel();
	public DepQueryModel dqm = new DepQueryModel();
	
	private DepEbi depEbi;
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	
	public Integer pageNum = 1;
	public Integer pageCount = 2;
	public Integer maxPageNum ;
	public Integer dataTotal;
	//跳转到列表页面
	public String list(){
		//计算最大页码值 : 记录总条目数与每页显示数计算而来
		dataTotal = depEbi.getCount(dqm);
		//最大页码值
		37  10 	4
		(37+10 -1) /10 	4
		39	10	4
		(39+10-1) /10  4
		40	10	4
		(40+10-1) /10  4
		41  10	5
		(41+10-1) /10  5
		maxPageNum = (dataTotal + pageCount -1) / pageCount; 
		//获取所有的部门数据
		List<DepModel> depList = depEbi.getAll(dqm,pageNum,pageCount);
		//将该数据放入指定范围
		ActionContext.getContext().put("depList", depList);
		//跳转页面
		//页面中从指定范围内获取数据展示
		return "list";
	}
	
	//按条件查询
	public String queryList(){
		//根据查询条件进行查询获得最终显示的数据
		List<DepModel> depList = depEbi.getAll(dqm);
		//放入指定范围
		ActionContext.getContext().put("depList",depList);
		//跳转页面
		return "list";
	}
	
	//新建部门
	public String save(){
		//通过页面是否传递有具体的uuid值来区分究竟是添加还是修改
		if(dm.getUuid()== null){	//没有传递uuid值，添加
			depEbi.save(dm);
		}else{						//传递了uuid值，修改
			depEbi.update(dm);
		}
		return "toList";
	}
	
	//跳转到修改页面
	public String input(){
		//如果传递有uuid，执行查询
		if(dm.getUuid()!=null){
			//根据传递的uuid获取对应的数据
			dm = depEbi.get(dm.getUuid());
		}
		return "input";
	}
	
	//删除部门信息
	public String delete(){
		depEbi.delete(dm);
		return "toList";
	}
	
}
*/