package com.jinbo.customer.wei.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;








public class BaseDao<T> {
	private String tableName;
	@Resource
	public DataSource dataSource;
	private Class clazz;
	public BaseDao(){
		//BaseDao<Student>叫参数化类型，ParameterizedType对象
		//获取该类的字节码对象
		Class baseDaoClass = this.getClass();
		//获取Type接口
		Type type = baseDaoClass.getGenericSuperclass();
		//将Type接口强转成ParameterizedType
		ParameterizedType pt = (ParameterizedType) type;
		//获取<Student>类型
		Type[] types = pt.getActualTypeArguments();
		//获取<Student>类型
		this.clazz = (Class) types[0];
		//根据字节码转成表名
		int index = this.clazz.getName().lastIndexOf(".");
		this.tableName = this.clazz.getName().substring(index+1).toLowerCase();
	}
	/*
	public BaseDao(String tableName, Class clazz) {
		this.tableName = tableName;
		this.clazz = clazz;
	}
	*/
	//根据OPENID查询对象
	public T findByOpenId(String id) throws Exception{
		T t = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,yhm,userRole from " + tableName + " where openid = '"+id+"'";
		t = (T) runner.query(sql,new BeanHandler(clazz));
		return t;
	}
	//根据ID查询对象
	public T findByDDH(String id,int customerID) throws Exception{
		T t = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,ddh,state,khqc from " + tableName + " where ddh = '"+id+"' and yh=?" ;
		Object[] obj = new Object[]{customerID};
		t = (T) runner.query(sql,new BeanHandler(clazz),obj);
		return t;
	}
	public T findByDDHRole(String id) throws Exception{
		T t = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,ddh,state,khqc from " + tableName + " where ddh = '"+id+"'" ;
		t = (T) runner.query(sql,new BeanHandler(clazz));
		return t;
	}
	//查询所有
	public List<T> findAll(Integer id) throws Exception{
		List<T> t = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select * from " + tableName;
		t = (List<T>) runner.query(sql,new BeanListHandler<T>(clazz));
		return t;
	}
	
	public static void main(String[] args) {
		 ApplicationContext ap = new ClassPathXmlApplicationContext("beans.xml");
		 int count = ap.getBeanDefinitionCount();
		 String[] strs = ap.getBeanDefinitionNames();
		 System.out.println(Arrays.toString(strs));;
		 System.out.println(count);
		 DataSource ds = (DataSource) ap.getBean("dataSource");
		 System.out.println(ds);		 
	}
}
