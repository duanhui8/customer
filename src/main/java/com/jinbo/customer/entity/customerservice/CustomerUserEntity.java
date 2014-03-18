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
 * @Description: 客户
 * @author zhangdaihao
 * @date 2014-03-05 15:51:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "customer_user", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CustomerUserEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**微信账号*/
	private java.lang.String weiname;
	/**编码*/
	@Excel(exportName="编码",orderNum="1",needMerge=true)
	private java.lang.String username;
	/**公司名*/
	@Excel(exportName="公司名",orderNum="2",needMerge=true)
	private java.lang.String realname;



	/**密码*/
	private java.lang.String passwd;
	/**状态*/
	private java.lang.Integer status;
	/**传真*/
	private java.lang.String phone;
	/**电话*/
	@Excel(exportName="电话",orderNum="3",needMerge=true)
	private java.lang.String tel;
	/**地址*/
	@Excel(exportName="地址",orderNum="4",needMerge=true)
	private java.lang.String address;
	/**QQ*/
	private java.lang.String qq;
	/**邮箱*/
	private java.lang.String email;
	/**等级*/
	private java.lang.Integer grade;
	/**创建时间*/
	private java.util.Date createDatetime;
	/**创建人*/
	private java.lang.String createName;
	/**修改时间*/
	private java.util.Date updateDatetime;
	/**修改人*/
	private java.lang.String updateName;
	
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
	@Column(name ="REALNAME",nullable=false,length=129)
	public java.lang.String getRealname(){
		return this.realname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  真实姓名
	 */
	public void setRealname(java.lang.String realname){
		this.realname = realname;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  密码
	 */
	@Column(name ="PASSWD",nullable=false,length=150)
	public java.lang.String getPasswd(){
		return this.passwd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密码
	 */
	public void setPasswd(java.lang.String passwd){
		this.passwd = passwd;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATUS",nullable=false,precision=10,scale=0)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  手机号
	 */
	@Column(name ="PHONE",nullable=true,precision=10,scale=0)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  手机号
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  电话
	 */
	@Column(name ="TEL",nullable=true,precision=10,scale=0)
	public java.lang.String getTel(){
		return this.tel;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  电话
	 */
	public void setTel(java.lang.String tel){
		this.tel = tel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=200)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  QQ
	 */
	@Column(name ="QQ",nullable=true,precision=10,scale=0)
	public java.lang.String getQq(){
		return this.qq;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  QQ
	 */
	public void setQq(java.lang.String qq){
		this.qq = qq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱
	 */
	@Column(name ="EMAIL",nullable=true,length=200)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮箱
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  等级
	 */
	@Column(name ="GRADE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getGrade(){
		return this.grade;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  等级
	 */
	public void setGrade(java.lang.Integer grade){
		this.grade = grade;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATETIME",nullable=false)
	public java.util.Date getCreateDatetime(){
		return this.createDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDatetime(java.util.Date createDatetime){
		this.createDatetime = createDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_NAME",nullable=false,length=140)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATE_DATETIME",nullable=true)
	public java.util.Date getUpdateDatetime(){
		return this.updateDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDatetime(java.util.Date updateDatetime){
		this.updateDatetime = updateDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=140)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	
	public java.lang.String getWeiname() {
		return weiname;
	}

	public void setWeiname(java.lang.String weiname) {
		this.weiname = weiname;
	}

}
