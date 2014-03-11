package com.jinbo.customer.service.advice;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import com.jinbo.customer.entity.advice.CustomerAdviceEntity;
import java.io.Serializable;
import com.jinbo.customer.entity.replyadvice.AdviceReplyEntity;

public interface CustomerAdviceServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(CustomerAdviceEntity customerAdvice,
	        List<AdviceReplyEntity> adviceReplyList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(CustomerAdviceEntity customerAdvice,
	        List<AdviceReplyEntity> adviceReplyList);
	public void delMain (CustomerAdviceEntity customerAdvice);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CustomerAdviceEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CustomerAdviceEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CustomerAdviceEntity t);
}
