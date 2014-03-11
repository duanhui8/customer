package com.jinbo.customer.service.impl.deptservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import com.jinbo.customer.service.deptservice.DeptSerServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;

import com.jinbo.customer.entity.deptreply.DeptReplyEntity;
import com.jinbo.customer.entity.deptservice.DeptSerEntity;
@Service("deptSerService")
@Transactional
public class DeptSerServiceImpl extends CommonServiceImpl implements DeptSerServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((DeptSerEntity)entity);
 	}
	
	public void addMain(DeptSerEntity deptSer,List<DeptReplyEntity> deptReplyList){
			//保存主信息
			this.save(deptSer);
		
			/**保存-部门回复*/
			for(DeptReplyEntity deptReply:deptReplyList){
				//外键设置
				deptReply.setAorder(deptSer.getAorder());
				this.save(deptReply);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(deptSer);
	}

	
	public void updateMain(DeptSerEntity deptSer,List<DeptReplyEntity> deptReplyList) {
		//保存主表信息
		this.saveOrUpdate(deptSer);
		//===================================================================================
		//获取参数
		Object aORDER0 = deptSer.getAorder();
		//===================================================================================
		//1.查询出数据库的明细数据-部门回复
	    String hql0 = "from deptReplyEntity where 1 = 1 AND aORDER = ? ";
	    List<DeptReplyEntity> deptReplyOldList = this.findHql(hql0,aORDER0);
		//2.筛选更新明细数据-部门回复
		for(DeptReplyEntity oldE:deptReplyOldList){
			boolean isUpdate = false;
				for(DeptReplyEntity sendE:deptReplyList){
					//需要更新的明细数据-部门回复
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-部门回复
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-部门回复
			for(DeptReplyEntity deptReply:deptReplyList){
				if(deptReply.getId()!=null){
					//外键设置
					deptReply.setAorder(deptSer.getAorder());
					this.save(deptReply);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(deptSer);
	}

	
	public void delMain(DeptSerEntity deptSer) {
		//删除主表信息
		this.delete(deptSer);
		//===================================================================================
		//获取参数
		Object aORDER0 = deptSer.getAorder();
		//===================================================================================
		//删除-部门回复
	    String hql0 = "from deptReplyEntity where 1 = 1 AND aORDER = ? ";
	    List<DeptReplyEntity> deptReplyOldList = this.findHql(hql0,aORDER0);
		this.deleteAllEntitie(deptReplyOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(DeptSerEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(DeptSerEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(DeptSerEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,DeptSerEntity t){
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