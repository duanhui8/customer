package com.jinbo.customer.service.impl.advice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.service.advice.CustomerAdviceServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import java.util.UUID;

@Service("customerAdviceService")
@Transactional
public class CustomerAdviceServiceImpl extends CommonServiceImpl implements CustomerAdviceServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((CustomerSerEntity)entity);
 	}
	
	public void addMain(CustomerSerEntity customerAdvice,
	        List<ServiceReplyEntity> adviceReplyList){
			//保存主信息
			this.save(customerAdvice);
		
			/**保存-回复投诉*/
			for(ServiceReplyEntity adviceReply:adviceReplyList){
				//外键设置
				adviceReply.setAorder(customerAdvice.getAorder());
				this.save(adviceReply);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(customerAdvice);
	}

	
	public void updateMain(CustomerSerEntity customerAdvice,
	        List<ServiceReplyEntity> adviceReplyList) {
		//保存主表信息
		this.saveOrUpdate(customerAdvice);
		//===================================================================================
		//获取参数
		Object aORDER0 = customerAdvice.getAorder();
		//===================================================================================
		//1.查询出数据库的明细数据-回复投诉
	    String hql0 = "from ServiceReplyEntity where 1 = 1 AND aORDER = ? ";
	    List<ServiceReplyEntity> adviceReplyOldList = this.findHql(hql0,aORDER0);
		//2.筛选更新明细数据-回复投诉
		for(ServiceReplyEntity oldE:adviceReplyOldList){
			boolean isUpdate = false;
				for(ServiceReplyEntity sendE:adviceReplyList){
					//需要更新的明细数据-回复投诉
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-回复投诉
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-回复投诉
			for(ServiceReplyEntity adviceReply:adviceReplyList){
				if(adviceReply.getId()!=null){
					//外键设置
					adviceReply.setAorder(customerAdvice.getAorder());
					this.save(adviceReply);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(customerAdvice);
	}

	
	public void delMain(CustomerSerEntity customerAdvice) {
		//删除主表信息
		this.delete(customerAdvice);
		//===================================================================================
		//获取参数
		Object aORDER0 = customerAdvice.getAorder();
		//===================================================================================
		//删除-回复投诉
	    String hql0 = "from ServiceReplyEntity where 1 = 1 AND aORDER = ? ";
	    List<ServiceReplyEntity> adviceReplyOldList = this.findHql(hql0,aORDER0);
		this.deleteAllEntitie(adviceReplyOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(CustomerSerEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(CustomerSerEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(CustomerSerEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,CustomerSerEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{aorder}",String.valueOf(t.getAorder()));
 		sql  = sql.replace("#{atitle}",String.valueOf(t.getAtitle()));
 		sql  = sql.replace("#{atype}",String.valueOf(t.getAtype()));
 		sql  = sql.replace("#{acontent}",String.valueOf(t.getAcontent()));
 		sql  = sql.replace("#{ainfo}",String.valueOf(t.getAinfo()));
 		sql  = sql.replace("#{aktatus}",String.valueOf(t.getAktatus()));
 		sql  = sql.replace("#{anotes}",String.valueOf(t.getAnotes()));
 		sql  = sql.replace("#{astatus}",String.valueOf(t.getAstatus()));
 		sql  = sql.replace("#{create_datetime}",String.valueOf(t.getCreateDatetime()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{azhtype}",String.valueOf(t.getAzhtype()));
 		sql  = sql.replace("#{arealname}",String.valueOf(t.getArealname()));
 		sql  = sql.replace("#{sl_datetime}",String.valueOf(t.getSlDatetime()));
 		sql  = sql.replace("#{sl_name}",String.valueOf(t.getSlName()));
 		sql  = sql.replace("#{de_datetime}",String.valueOf(t.getDeDatetime()));
 		sql  = sql.replace("#{de_name}",String.valueOf(t.getDeName()));
 		sql  = sql.replace("#{com_datetime}",String.valueOf(t.getComDatetime()));
 		sql  = sql.replace("#{com_name}",String.valueOf(t.getComName()));
 		sql  = sql.replace("#{afile}",String.valueOf(t.getAfile()));
 		sql  = sql.replace("#{atel}",String.valueOf(t.getAtel()));
 		sql  = sql.replace("#{aadept}",String.valueOf(t.getAadept()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}