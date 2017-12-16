package cn.itcast.invoice.util.interceptor;

import cn.itcast.invoice.util.exception.AppException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (AppException e) {
			//使用下列对象获取actionsupport常用操作
			ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError(as.getText(e.getMessage()));
			//给开发人员发一份email
			//记录日志e.printStackTrace();
			//获取Action的调用环境
			return "error";
		} catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
}
