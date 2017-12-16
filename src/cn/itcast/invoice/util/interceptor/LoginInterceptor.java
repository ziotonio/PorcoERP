package cn.itcast.invoice.util.interceptor;

import cn.itcast.invoice.auth.emp.vo.EmpModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor extends AbstractInterceptor {
	public String intercept(ActionInvocation invocation) throws Exception {
		
		//如果是跳转到登陆页的操作，放行
		String an = invocation.getProxy().getActionName(); //pages_login
		if(an.equals("pages_login")){
			return invocation.invoke();
		}
		//如果是登陆功能，放行
		//获取当前执行的操作，如果是登陆cn.itcast.invoice.auth.emp.web.EmpAction.login放行
		String actionName = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String totalName = actionName+"."+methodName;

		if(totalName.equals("cn.itcast.invoice.auth.emp.web.EmpAction.login")){
			return invocation.invoke();
		}
		
		//判断用户是否登陆，如果登陆，向下放行
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get("loginEm");
		if(loginEm == null){
			//如果未登陆，跳转到登陆失败页
			return "loginFail"; 
		}else{
			//如果登陆成功，放行该操作
			return invocation.invoke();
		}
	}
}
