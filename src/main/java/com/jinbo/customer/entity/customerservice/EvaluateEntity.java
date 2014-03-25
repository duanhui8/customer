package com.jinbo.customer.entity.customerservice;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 评价
 * @author zhangdaihao
 * @date 2014-03-05 15:51:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "evaluate", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class EvaluateEntity implements java.io.Serializable {
	public java.lang.String getDepid() {
		return depid;
	}

	public void setDepid(java.lang.String depid) {
		this.depid = depid;
	}

	public java.lang.String getKefuid() {
		return kefuid;
	}

	public void setKefuid(java.lang.String kefuid) {
		this.kefuid = kefuid;
	}

	/**主键*/
	private java.lang.String id;
	/**投诉单id*/
	private java.lang.String advice_id;
	/**用户ID*/
	@Excel(exportName="编码",orderNum="1",needMerge=true)
	private java.lang.String userid;
	/**用户名*/
	@Excel(exportName="公司名",orderNum="2",needMerge=true)
	private java.lang.String username;
	/**密码*/
	private java.lang.String deptpj;
	/**部门ID*/
	private java.lang.String depid;
	/**状态*/
	private java.lang.String kefupj;
	/**客服id*/
	private java.lang.String kefuid;
	/**传真*/
	private java.lang.String note;
	/**电话*/
	private java.lang.String note2;
	/**地址*/
	private java.lang.String note3;
	/**QQ*/

	/**创建时间*/
	private java.util.Date createDatetime;
	/**创建人*/
	private java.lang.String createName;

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  户名用
	 */
	@Column(name ="USERNAME",nullable=false,length=40,unique=true)
	public java.lang.String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  户名用
	 */
	public void setUsername(java.lang.String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  真实姓名
	 */

	public java.lang.String getAdvice_id() {
		return advice_id;
	}

	public void setAdvice_id(java.lang.String advice_id) {
		this.advice_id = advice_id;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getDeptpj() {
		return deptpj;
	}

	public void setDeptpj(java.lang.String deptpj) {
		this.deptpj = deptpj;
	}



	public java.lang.String getKefupj() {
		return kefupj;
	}

	public void setKefupj(java.lang.String kefupj) {
		this.kefupj = kefupj;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getNote2() {
		return note2;
	}

	public void setNote2(java.lang.String note2) {
		this.note2 = note2;
	}

	public java.lang.String getNote3() {
		return note3;
	}

	public void setNote3(java.lang.String note3) {
		this.note3 = note3;
	}

	public java.util.Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(java.util.Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public java.lang.String getCreateName() {
		return createName;
	}

	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}
	

}
