package cn.itcast.invoice.util.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.itcast.invoice.auth.res.business.ebi.ResEbi;

public class LoadResourceListener implements ServletContextListener{
	Properties props = new Properties();
	FileInputStream in = null;
	
	/**
	 * Creation of the contextDestroyed method for ServletContextListener Class
	 */
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * Creation of the contextInitialized method for ServletContextListener Class
	 */
	public void contextInitialized(ServletContextEvent event) {
		//æœ�åŠ¡å™¨å�¯åŠ¨æ—¶ï¼ŒåŠ è½½æ‰€æœ‰å…¨èµ„æº�æ•°æ�®:JDBC
		Connection conn = null;
		try {
			ArrayList<String> Info = getDBInfo();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/**
		 * Creation of connection to the ERP database 
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(getDBInfo().get(0), getDBInfo().get(1), getDBInfo().get(2));
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select url from tbl_res");
			List<String> temp = new ArrayList<String>();
			while(rs.next()){
				temp.add(rs.getString("url"));
			}
			//å°†é›†å�ˆæ”¾å…¥ServletContextèŒƒå›´
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
	
	
	/**
	 * This method connects to the file where db info are saved
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> getDBInfo() throws IOException {
		ArrayList<String> DBInfo = new ArrayList();
		try {
			in = new FileInputStream("src/cn/itcast/invoice/util/listener/db.properties");
			props.load(in);
			DBInfo.add(props.getProperty("db.url"));
			DBInfo.add(props.getProperty("db.user"));
			DBInfo.add(props.getProperty("db.password"));
		}catch(FileNotFoundException e) {
			Logger lgr = Logger.getLogger(LoadResourceListener.class.getName());
	        lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		finally {
			in.close();
		}
		return DBInfo;
	}

}
