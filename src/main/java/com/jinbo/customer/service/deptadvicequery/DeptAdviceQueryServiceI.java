package com.jinbo.customer.service.deptadvicequery;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;

import java.io.Serializable;


public interface DeptAdviceQueryServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(CustomerSerEntity deptAdviceQuery,
	        List<ServiceReplyEntity> adviceReplyList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(CustomerSerEntity deptAdviceQuery,
	        List<ServiceReplyEntity> adviceReplyList);
	public void delMain (CustomerSerEntity deptAdviceQuery);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CustomerSerEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CustomerSerEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CustomerSerEntity t);
}
