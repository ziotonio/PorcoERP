package cn.itcast.invoice.util.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailModel;

public class GeneratorUtil {
	
	public static void main(String[] args) throws Exception {
		//依赖Model生成若干个文件
		//执行某个方法，就生成对应的所有代码，方法要携带Class
		//EmpModel,RoleModel,ResModel,MenuModel
		//SupplierModel,GoodsTypeModel,GoodsModel
		//OrderModel,OrderDetailModel
		//StoreModel,StoreOperModel,StoreDetailModel
		new GeneratorUtil(StoreDetailModel.class);
		System.out.println("end...");
	}
	
	private Class clazz;
	private String pkg;
	private String rootDir;
	private String big;
	private String small;
	private String little;
	/*
	pkg		cn.itcast.invoice.auth.emp
	rootDir	src/cn/itcast/invoice/auth/emp
	big		Emp		EmpModel	EmpQueryModel
	small	emp		empEbi	empDao
	little	e		em		eqm
	*/
	public GeneratorUtil(Class clazz)throws Exception{
		this.clazz = clazz;
		//-1.数据初始化
		dataInit();
		//0.生成保存文件的目录
		genderatorDirectories();
		//1.生成QueryModel
		generatorQueryModel();
		//2.生成Model.hbm.xml
		generatorHbmXml();
		//3.生成Dao
		generatorDao();
		//4.生成DaoImpl
		generatorDaoImpl();
		//5.生成Ebi
		generatorEbi();
		//6.生成Ebo
		generatorEbo();
		//7.生成Action
		generatorAction();
		//8.生成applicationContext.xml
		generatorApplicationContextXml();
		//9.生成struts.xml
	}
	
	//8.生成applicationContext.xml
	private void generatorApplicationContextXml() throws Exception{
		//1.创建文件
		File f = new File("resources/applicationContext-"+small+".xml");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();

		bw.write("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
		bw.newLine();

		bw.write("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		bw.newLine();
		
		bw.write("	xsi:schemaLocation=\"");
		bw.newLine();
		
		bw.write("		http://www.springframework.org/schema/beans");
		bw.newLine();
		
		bw.write("		http://www.springframework.org/schema/beans/spring-beans.xsd");
		bw.newLine();
		
		bw.write("		\">  ");
		bw.newLine();
		
		bw.write("	<!-- Action -->");
		bw.newLine();
		
		bw.write("	<bean id=\""+small+"Action\" class=\""+pkg+".web."+big+"Action\" scope=\"prototype\">");
		bw.newLine();
		
		bw.write("		<property name=\""+small+"Ebi\" ref=\""+small+"Ebi\"/>");
		bw.newLine();
		
		bw.write("	</bean>");
		bw.newLine();
		
		bw.write("	<!-- Ebi -->");
		bw.newLine();
		
		bw.write("	<bean id=\""+small+"Ebi\" class=\""+pkg+".business.ebo."+big+"Ebo\">");
		bw.newLine();
		
		bw.write("		<property name=\""+small+"Dao\" ref=\""+small+"Dao\"/>");
		bw.newLine();
		
		bw.write("	</bean>");
		bw.newLine();
		
		bw.write("	<!-- Dao -->");
		bw.newLine();
		
		bw.write("	<bean id=\""+small+"Dao\" class=\""+pkg+".dao.impl."+big+"DaoImpl\">");
		bw.newLine();
		
		bw.write("		<property name=\"sessionFactory\" ref=\"sessionFactory\"/>");
		bw.newLine();
		
		bw.write("	</bean>");
		bw.newLine();
		
		bw.write("</beans>");
		bw.newLine();
		
		bw.flush();
		bw.close();
	}
	//7.生成Action
	private void generatorAction() throws Exception{
		//1.创建文件
		File f = new File(rootDir+"/web/"+big+"Action.java");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".web;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import java.util.List;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import "+pkg+".business.ebi."+big+"Ebi;");
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"Model;");
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"QueryModel;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseAction;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public class "+big+"Action extends BaseAction{");
		bw.newLine();
		
		bw.write("	public "+big+"Model "+little+"m = new "+big+"Model();");
		bw.newLine();
		
		bw.write("	public "+big+"QueryModel "+little+"qm = new "+big+"QueryModel();");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	private "+big+"Ebi "+small+"Ebi;");
		bw.newLine();
		
		bw.write("	public void set"+big+"Ebi("+big+"Ebi "+small+"Ebi) {");
		bw.newLine();
		
		bw.write("		this."+small+"Ebi = "+small+"Ebi;");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	//跳转到列表页面");
		bw.newLine();
		
		bw.write("	public String list(){");
		bw.newLine();
		
		bw.write("		setDataTotal("+small+"Ebi.getCount("+little+"qm));");
		bw.newLine();
		
		bw.write("		List<"+big+"Model> "+small+"List = "+small+"Ebi.getAll("+little+"qm,pageNum,pageCount);");
		bw.newLine();
		
		bw.write("		put(\""+small+"List\","+small+"List);");
		bw.newLine();
		
		bw.write("		return LIST;");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	//保存/修改");
		bw.newLine();
		
		bw.write("	public String save(){");
		bw.newLine();
		
		bw.write("		if("+little+"m.getUuid()== null){");
		bw.newLine();
		
		bw.write("			"+small+"Ebi.save("+little+"m);");
		bw.newLine();
		
		bw.write("		}else{");
		bw.newLine();
		
		bw.write("			"+small+"Ebi.update("+little+"m);");
		bw.newLine();
		
		bw.write("		}");
		bw.newLine();
		
		bw.write("		return TO_LIST;");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	//跳转到添加/修改页面");
		bw.newLine();
		
		bw.write("	public String input(){");
		bw.newLine();
		
		bw.write("		if("+little+"m.getUuid()!=null){");
		bw.newLine();
		
		bw.write("			"+little+"m = "+small+"Ebi.get("+little+"m.getUuid());");
		bw.newLine();
		
		bw.write("		}");
		bw.newLine();
		
		bw.write("		return INPUT;");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	//删除");
		bw.newLine();
		
		bw.write("	public String delete(){");
		bw.newLine();
		
		bw.write("		"+small+"Ebi.delete("+little+"m);");
		bw.newLine();
		
		bw.write("		return TO_LIST;");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("}");
		bw.newLine();

		bw.flush();
		bw.close();
	}
	//6.生成Ebo
	private void generatorEbo() throws Exception{
		File f = new File(rootDir+"/business/ebo/"+big+"Ebo.java");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".business.ebo;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import java.io.Serializable;");
		bw.newLine();
		
		bw.write("import java.util.List;");
		bw.newLine();

		bw.newLine();
		
		bw.write("import "+pkg+".business.ebi."+big+"Ebi;");
		bw.newLine();
		
		bw.write("import "+pkg+".dao.dao."+big+"Dao;");
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"Model;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseQueryModel;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.exception.AppException;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public class "+big+"Ebo implements "+big+"Ebi{");
		bw.newLine();
		
		bw.write("	private "+big+"Dao "+small+"Dao;");
		bw.newLine();
		
		bw.write("	public void set"+big+"Dao("+big+"Dao "+small+"Dao) {");
		bw.newLine();
		
		bw.write("		this."+small+"Dao = "+small+"Dao;");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	public void save("+big+"Model "+little+"m) {");
		bw.newLine();
		
		bw.write("		"+small+"Dao.save("+little+"m);");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	public void delete("+big+"Model "+little+"m) {");
		bw.newLine();
		
		bw.write("		"+small+"Dao.delete("+little+"m);");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	public void update("+big+"Model "+little+"m) {");
		bw.newLine();
		
		bw.write("		"+small+"Dao.update("+little+"m);");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	public "+big+"Model get(Serializable uuid) {");
		bw.newLine();
		
		bw.write("		return "+small+"Dao.get(uuid);");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();

		bw.write("	public List<"+big+"Model> getAll() {");
		bw.newLine();
		
		bw.write("		return "+small+"Dao.getAll();");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	public List<"+big+"Model> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {");
		bw.newLine();
		
		bw.write("		return "+small+"Dao.getAll(qm,pageNum,pageCount);");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("	public Integer getCount(BaseQueryModel qm) {");
		bw.newLine();
		
		bw.write("		return "+small+"Dao.getCount(qm);");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		bw.flush();
		bw.close();

	}
	//5.生成Ebi
	private void generatorEbi() throws Exception{
		//1.创建文件
		File f = new File(rootDir+"/business/ebi/"+big+"Ebi.java");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".business.ebi;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import org.springframework.transaction.annotation.Transactional;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"Model;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseEbi;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("@Transactional");
		bw.newLine();
		
		bw.write("public interface "+big+"Ebi extends BaseEbi<"+big+"Model> {");
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		bw.flush();
		bw.close();
	}
	//4.生成DaoImpl
	private void generatorDaoImpl() throws Exception{
		//1.创建文件
		File f = new File(rootDir+"/dao/impl/"+big+"DaoImpl.java");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".dao.impl;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import org.hibernate.criterion.DetachedCriteria;");
		bw.newLine();
		
		bw.write("import org.hibernate.criterion.Restrictions;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import "+pkg+".dao.dao."+big+"Dao;");
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"Model;");
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"QueryModel;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseDaoImpl;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseQueryModel;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public class "+big+"DaoImpl extends BaseDaoImpl<"+big+"Model> implements "+big+"Dao{");
		bw.newLine();
		
		bw.write("	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){");
		bw.newLine();
		
		bw.write("		"+big+"QueryModel "+little+"qm = ("+big+"QueryModel) qm;");
		bw.newLine();
		
		bw.write("		//TODO 添加自定义查询规则");
		bw.newLine();
		
		bw.write("	}");
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		bw.flush();
		bw.close();
		
	}
	//3.生成Dao
	private void generatorDao() throws Exception{
		//1.创建文件
		File f = new File(rootDir+"/dao/dao/"+big+"Dao.java");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".dao.dao;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import "+pkg+".vo."+big+"Model;");
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseDao;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public interface "+big+"Dao extends BaseDao<"+big+"Model> {");
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		bw.flush();
		bw.close();
		
	}
	//2.生成Model.hbm.xml
	private void generatorHbmXml() throws Exception{
		File f = new File(rootDir+"/vo/"+big+"Model.hbm.xml");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		
		bw.write("<!DOCTYPE hibernate-mapping PUBLIC");
		bw.newLine();
		
		bw.write("        '-//Hibernate/Hibernate Mapping DTD 3.0//EN'");
		bw.newLine();
		
		bw.write("        'http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd'>");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("<hibernate-mapping>");
		bw.newLine();
		
		bw.write("    <class name=\""+pkg+".vo."+big+"Model\" table=\"tbl_"+small+"\">");
		bw.newLine();
		
		bw.write("        <id name=\"uuid\">");
		bw.newLine();
		
		bw.write("            <generator class=\"native\" />");
		bw.newLine();
		
		bw.write("        </id>");
		bw.newLine();
		//循环生成
		//需要获取所有的字段名
		Field[] fields = clazz.getDeclaredFields();
		for(Field fd:fields){
			//如果是私有的生成
			if(fd.getModifiers() == Modifier.PRIVATE){
				if(!fd.getName().endsWith("View") && !fd.getName().equals("uuid")){
					//满足Long,Double,Integer,String的生成
					if(fd.getType().equals(Long.class)
							||fd.getType().equals(Double.class)
							||fd.getType().equals(Integer.class)
							||fd.getType().equals(String.class)
							)
					bw.write("        <property name=\""+fd.getName()+"\"/>");
					bw.newLine();
				}
			}
		}
		
		bw.write("    </class>");
		bw.newLine();
		
		bw.write("</hibernate-mapping>");
		
		bw.flush();
		bw.close();
		
	}
/*
"+big+"
"+small+"
"+pkg+"
*/
	//1.生成QueryModel
	private void generatorQueryModel() throws Exception {
		//1.创建文件
		File f = new File(rootDir+"/vo/"+big+"QueryModel.java");
		if(f.exists()){
			return;
		}
		f.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".vo;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import cn.itcast.invoice.util.base.BaseQueryModel;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public class "+big+"QueryModel extends "+big+"Model implements BaseQueryModel{");
		bw.newLine();
		
		bw.write("	//TODO 请添加自定义查询条件字段");
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		bw.flush();
		bw.close();
		
	}

	//0.生成保存文件的目录
	private void genderatorDirectories() {
		//创建所有文件的保存目录
		//web
		File f = new File(rootDir+"/web");
		f.mkdirs();
		//business.ebi
		f = new File(rootDir+"/business/ebi");
		f.mkdirs();
		//business.ebo
		f = new File(rootDir+"/business/ebo");
		f.mkdirs();
		//dao.dao
		f = new File(rootDir+"/dao/dao");
		f.mkdirs();
		//dao.impl
		f = new File(rootDir+"/dao/impl");
		f.mkdirs();
	}
	
	//-1.数据初始化
	private void dataInit() {
		//pkg;
		String allPkg = clazz.getPackage().getName();
		pkg = allPkg.substring(0,allPkg.length()-3);
		//rootDir;			src/cn/itcast/invoice/auth/emp
		rootDir = "src/"+pkg.replace(".", "/");
		//big;		Emp
		String allClazzName = clazz.getSimpleName();
		big = allClazzName.substring(0, allClazzName.length()-5);
		//little;
		little = big.substring(0, 1).toLowerCase();
		//small;
		small = little+big.substring(1);
	}
	
}
