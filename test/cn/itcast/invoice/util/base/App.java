package cn.itcast.invoice.util.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class App {
	public static void main(String[] args) {
		//根据Cat获得其父类Animal的泛型String
		Class clazz = Cat.class;
		//clazz->String
		//clazz.getSuperclass();
		Type type = clazz.getGenericSuperclass();
		ParameterizedType pType =(ParameterizedType)type;
		Type[] types = pType.getActualTypeArguments(); 
		for(Type t:types){
			System.out.println((Class)t);
		}
	}
}

/*
Cat<???>
//要一个类的泛型
getGenericSuperclass() 
返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。

getSuperclass() 
返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的超类的 Class。
*/
