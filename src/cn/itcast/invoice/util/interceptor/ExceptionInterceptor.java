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
			//ä½¿ç”¨ä¸‹åˆ—å¯¹è±¡èŽ·å�–actionsupportå¸¸ç”¨æ“�ä½œ
			ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError(as.getText(e.getMessage()));
			//ç»™å¼€å�‘äººå‘˜å�‘ä¸€ä»½email
			//è®°å½•æ—¥å¿—e.printStackTrace();
			//èŽ·å�–Actionçš„è°ƒç”¨çŽ¯å¢ƒ
			return "error";
		} catch(Exception e){
			 System.out.println("Something was wrong");
			return "error";
		}
	}
}
