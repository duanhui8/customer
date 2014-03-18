package com.jinbo.customer.service.customerservice;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;

import java.io.Serializable;

public interface CustomerSerServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(CustomerSerEntity customerSer,
	        List<ServiceReplyEntity> adviceReplyList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(CustomerSerEntity customerSer,
	        List<ServiceReplyEntity> adviceReplyList);
	public void delMain (CustomerSerEntity customerSer);
	
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
