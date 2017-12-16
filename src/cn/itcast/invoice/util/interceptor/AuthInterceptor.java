package cn.itcast.invoice.util.interceptor;

import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.auth.res.business.ebi.ResEbi;
import cn.itcast.invoice.auth.res.vo.ResModel;
import cn.itcast.invoice.auth.role.vo.RoleModel;
import cn.itcast.invoice.util.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor{
	/*
	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}
	*/
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String totalName = actionName+"."+methodName;
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get("loginEm");
		if(loginEm == null){
			return invocation.invoke();
		}
		
		//由于全资源数据每次操作均要使用
		//因此需要将其提前初始化完毕，并放置在某个固定的位置，共整个应用共享
		//思考：什么时候放置该数据？
		//启动服务器时，加载该数据————监听器
		List<String> resAllUrl = (List<String>) ServletActionContext.getServletContext().getAttribute("resAllUrl");
		if(resAllUrl.contains(totalName)){
			//由于用户登陆后，每次操作所有的功能均需要进行权限校验，对于员工具有的可操作资源应该进行优化
			//否则将在每次调用时，重新查找，造成整体系统性能下降
			//ServletContext范围内只能放置全应用用户的公共信息
			//对于当前登陆员工的私有信息，只能选择更低范围的信息共享(Session)
			//List<String> resList = resEbi.getAllResByEmp(loginEm.getUuid());
			
			//从session中后去的数据已经具有了当前用户可操作的全资源
			if(loginEm.getResValue().contains(totalName)){
				return invocation.invoke();
			}else{
				throw new AppException("对不起！请不要进行非法操作！您不具有当前操作的权限！");
			}
		}else{
			return invocation.invoke();
		}
	}

	/*
	public String intercept(ActionInvocation invocation) throws Exception {
		//获取本次请求的操作内容
		//思考：数据存储方面，什么格式描述的请求？全包名.类名.方法名
		//获取本次请求的Action类的类名+方法名
		String actionName = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String totalName = actionName+"."+methodName;
		//获取登陆人信息
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get("loginEm");
		if(loginEm == null){
			return invocation.invoke();
		}
		
		//方案一:延迟加载
		//与当前登陆人所能操作的资源进行比对
		//由于登陆时使用的session(Hiberate)对象已经关闭，openSessionInView功能无法正常工作
		//所以此处无法使用emp的延迟加载功能，加载其关联数据，抛出异常
		Set<RoleModel> roleSet = emp.getRoles();
		for(RoleModel rm:roleSet){
			Set<ResModel> reses = rm.getReses();
			for(ResModel resm :reses){
				//如果比对成功
				if(totalName.equals(resm.getUrl())){
					//放行
					return invocation.invoke();
				}
			}
		}
		
		//如果比对失败
		//拦截：对不起，您没有权限！
		throw new AppException("对不起！请不要进行非法操作！您不具有当前操作的权限！");
		
		
		
		
		
		
		//方案二：重新查询(角色，员工，资源)
		//区分当前操作是否需要拦截，如果不需要拦截，放行
		//需要拦截的有哪些？在资源表中出现的才需要拦截，如果未出现，可以随便调用
		List<String> resAllUrl = resEbi.getAllUrl();
		//比对
		if(resAllUrl.contains(totalName)){
			List<String> resList = resEbi.getAllResByEmp(loginEm.getUuid());
			//检测用户调用的资源是否在可操作资源列表中
			if(resList.contains(totalName)){
				//放行
				return invocation.invoke();
			}else{
				throw new AppException("对不起！请不要进行非法操作！您不具有当前操作的权限！");
			}
		}else{
			//所有资源过滤
			return invocation.invoke();
		}
	}
	 */
}
