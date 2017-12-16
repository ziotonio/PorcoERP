package cn.itcast.invoice.util.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.itcast.invoice.auth.res.business.ebi.ResEbi;

public class LoadResourceListener implements ServletContextListener{
	
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent event) {
		//服务器启动时，加载所有全资源数据:JDBC
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/erpdb1","root","root");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select url from tbl_res");
			List<String> temp = new ArrayList<String>();
			while(rs.next()){
				temp.add(rs.getString("url"));
			}
			//将集合放入ServletContext范围
			event.getServletContext().setAttribute("resAllUrl", temp);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
