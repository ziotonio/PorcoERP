package cn.itcast.invoice.auth.menu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.itcast.invoice.auth.menu.business.ebi.MenuEbi;
import cn.itcast.invoice.auth.menu.vo.MenuModel;
import cn.itcast.invoice.auth.menu.vo.MenuQueryModel;
import cn.itcast.invoice.util.base.BaseAction;

public class MenuAction extends BaseAction{
	public MenuModel mm = new MenuModel();
	public MenuQueryModel mqm = new MenuQueryModel();

	private MenuEbi menuEbi;
	public void setMenuEbi(MenuEbi menuEbi) {
		this.menuEbi = menuEbi;
	}

	//跳转到列表页面
	public String list(){
		//加载所有的父菜单数据
		List<MenuModel> parentList = menuEbi.getParentMenu();
		put("parentList",parentList);
		setDataTotal(menuEbi.getCount(mqm));
		List<MenuModel> menuList = menuEbi.getAll(mqm,pageNum,pageCount);
		put("menuList",menuList);
		return LIST;
	}

	//保存/修改
	public String save(){
		if(mm.getUuid()== null){
			menuEbi.save(mm);
		}else{
			menuEbi.update(mm);
		}
		return TO_LIST;
	}

	//跳转到添加/修改页面
	public String input(){
		//加载所有的父菜单
		List<MenuModel> menuList = menuEbi.getParentMenu();
		put("menuList",menuList);
		if(mm.getUuid()!=null){
			mm = menuEbi.get(mm.getUuid());
		}
		return INPUT;
	}

	//删除
	public String delete(){
		//进行删除时，如果存在有一对多关系
		//删除一时，首先将多的外键设置为null
		//然后删除多方对象
		//然后删除一方对象
		
		menuEbi.delete(mm);
		return TO_LIST;
	}

	//----------------
	public String showMenu() throws IOException{
		//根据php的内容，按照原始格式返回php的数据
		//学习source.php内容，翻译成action内容
		//将php代码解析后得到结论
		//请求参数root的值如果是source返回一种json数据数组
		//父菜单
		//{"text":"aaa","expanded":false,"classes":"自定义的样式class","id":"为止编号","hasChildren":true}
		//最后一级菜单
		//{"text":"aaa","classes":"自定义的样式class","id":"未知编号","hasChildren":false}
		//否则返回另一种json数据数组
		
		/*
		//测试一:
		//数据的返回需要使用response
		PrintWriter pw = getResponse().getWriter();
		
		pw.write("[");
		pw.write("{\"text\":\"aaa\",\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"1\"},");
		pw.write("{\"text\":\"bbb\",\"hasChildren\":false,\"classes\":\"file\",\"id\":\"2\"},");
		pw.write("{\"text\":\"ccc\",\"expanded\":false,\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"3\"}");
		pw.write("]");
		
		pw.flush();
		return null;
		*/
		
		/*
		//测试二:
		//页面结构已经测试完毕，需要获取真实数据进行测试
		//获取所有的父菜单数据（不包含系统菜单）
		List<MenuModel> menuList = menuEbi.getParentMenu2();
		PrintWriter pw = getResponse().getWriter();
		
		StringBuilder jsonStr = new StringBuilder();
		
		jsonStr.append("[");
		for(MenuModel temp:menuList){
			jsonStr.append("{\"text\":\"");
			jsonStr.append(temp.getName());
			jsonStr.append("\",\"expanded\":false,\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
			jsonStr.append(temp.getUuid().toString());
			jsonStr.append("\"},");
		}
		//jsonStr.substring(0, jsonStr.length()-1);
		jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		
		pw.write(jsonStr.toString());
		
		pw.flush();
		return null;
		*/
		
		/*
		//测试三:
		//当系统第一次加载菜单时，请求中包含有root=source参数
		//当点击子菜单项时，加载的请求中包含有root=id(uuid)
		String root = getRequest().getParameter("root");
		
		getResponse().setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = getResponse().getWriter();
		
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("[");
		
		List<MenuModel> menuList = null;
		
		if(root.equals("source")){
			menuList = menuEbi.getParentMenu2();
			for(MenuModel temp:menuList){
				jsonStr.append("{\"text\":\"");
				jsonStr.append(temp.getName());
				jsonStr.append("\",\"expanded\":false,\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
				jsonStr.append(temp.getUuid().toString());
				jsonStr.append("\"},");
			}
		}else{
			//根据传递的id值，获取对应的子菜单,展示
			//根据传递的菜单项目的uuid获取其子项的菜单集合
			Long puuid = new Long(root); 
			menuList = menuEbi.getMenusByPuuid(puuid);
			//jsonStr.append("{\"text\":\"bbb\",\"hasChildren\":false,\"classes\":\"file\",\"id\":\"2\"},");
			for(MenuModel temp:menuList){
				jsonStr.append("{\"text\":\"");
				jsonStr.append("<a target='main' href='");
				jsonStr.append(temp.getUrl());
				jsonStr.append("'>");
				jsonStr.append(temp.getName());
				jsonStr.append("</a>");
				jsonStr.append("\",\"classes\":\"file\"},");
			}
		}
		
		jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		
		pw.write(jsonStr.toString());
		
		pw.flush();
		return null;
		*/
		
		//方案四：基于登陆用户进行菜单加载
		
		String root = getRequest().getParameter("root");
		
		getResponse().setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = getResponse().getWriter();
		
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("[");
		
		List<MenuModel> menuList = null;
		
		if(root.equals("source")){
			//根据登陆人获取对应的菜单项
			menuList = menuEbi.getParentMenuByEmp(getLogin().getUuid());
			for(MenuModel temp:menuList){
				jsonStr.append("{\"text\":\"");
				jsonStr.append(temp.getName());
				jsonStr.append("\",\"expanded\":false,\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
				jsonStr.append(temp.getUuid().toString());
				jsonStr.append("\"},");
			}
		}else{
			//根据传递的菜单项目的uuid和登陆人信息获取其子项的菜单集合
			Long puuid = new Long(root); 
			menuList = menuEbi.getMenusByPuuidAndEmp(puuid,getLogin().getUuid());
			//jsonStr.append("{\"text\":\"bbb\",\"hasChildren\":false,\"classes\":\"file\",\"id\":\"2\"},");
			for(MenuModel temp:menuList){
				jsonStr.append("{\"text\":\"");
				jsonStr.append("<a target='main' href='");
				jsonStr.append(temp.getUrl());
				jsonStr.append("'>");
				jsonStr.append(temp.getName());
				jsonStr.append("</a>");
				jsonStr.append("\",\"classes\":\"file\"},");
			}
		}
		
		jsonStr.deleteCharAt(jsonStr.length()-1);
		jsonStr.append("]");
		
		pw.write(jsonStr.toString());
		
		pw.flush();
		return null;
	}
	
}
/*
[
 	{"text":"bb","expanded":false,"hasChildren":true,"classes":"folder","id":"3"},
 	{"text":"cc","expanded":false,"hasChildren":true,"classes":"folder","id":"4"},
 	{"text":"dd","expanded":false,"hasChildren":true,"classes":"folder","id":"8"}
 	,
 ]
*/		
		
		
		
		
		
		
		
		
		
		