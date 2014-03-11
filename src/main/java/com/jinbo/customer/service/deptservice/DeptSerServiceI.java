package com.jinbo.customer.service.deptservice;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;

import com.jinbo.customer.entity.deptreply.DeptReplyEntity;
import com.jinbo.customer.entity.deptservice.DeptSerEntity;
import java.io.Serializable;


public interface DeptSerServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(DeptSerEntity deptSer,
	        List<DeptReplyEntity> deptReplyList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(DeptSerEntity deptSer,
	        List<DeptReplyEntity> deptReplyList);
	public void delMain (DeptSerEntity deptSer);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DeptSerEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DeptSerEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DeptSerEntity t);
}
