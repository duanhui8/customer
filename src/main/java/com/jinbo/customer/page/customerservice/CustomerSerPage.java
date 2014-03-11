package com.jinbo.customer.page.customerservice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

import com.jinbo.customer.entity.repyservice.ServiceReplyEntity;

/**   
 * @Title: Entity
 * @Description: 客服
 * @author onlineGenerator
 * @date 2014-03-07 10:50:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "customer_advice", schema = "")
@SuppressWarnings("serial")
public class CustomerSerPage implements java.io.Serializable {
	/**保存-客户查询处理结果*/
	private List<ServiceReplyEntity> adviceReplyList = new ArrayList<ServiceReplyEntity>();
	public List<ServiceReplyEntity> getAdviceReplyList() {
		return adviceReplyList;
	}
	public void setAdviceReplyList(List<ServiceReplyEntity> adviceReplyList) {
		this.adviceReplyList = adviceReplyList;
	}


	/**主键*/
	private java.lang.String id;
	/**受理单号*/
	private java.lang.String aorder;
	/**投诉标题*/
	private java.lang.String atitle;
	/**投诉类型*/
	private java.lang.Integer atype;
	/**投诉内容*/
	private java.lang.String acontent;
	/**投诉来源*/
	private java.lang.String ainfo;
	/**客服状态*/
	private java.lang.String aktatus;
	/**客服备注*/
	private java.lang.String anotes;
	/**进度*/
	private java.lang.String astatus;
	/**投诉时间*/
	private java.util.Date createDatetime;
	/**投诉人*/
	private java.lang.String createName;
	/**客户账号*/
	private java.lang.String azhtype;
	/**公司名*/
	private java.lang.String arealname;
	/**受理时间*/
	private java.util.Date slDatetime;
	/**受理人*/
	private java.lang.String slName;
	/**部门处理时间*/
	private java.util.Date deDatetime;
	/**处理人*/
	private java.lang.String deName;
	/**反馈时间*/
	private java.util.Date comDatetime;
	/**反馈人*/
	private java.lang.String comName;
	/**附件*/
	private java.lang.String afile;
	/**电话*/
	private java.lang.String atel;
	/**投诉部门*/
	private java.lang.String aadept;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  受理单号
	 */
	@Column(name ="AORDER",nullable=true,length=300)
	public java.lang.String getAorder(){
		return this.aorder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受理单号
	 */
	public void setAorder(java.lang.String aorder){
		this.aorder = aorder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投诉标题
	 */
	@Column(name ="ATITLE",nullable=true,length=300)
	public java.lang.String getAtitle(){
		return this.atitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投诉标题
	 */
	public void setAtitle(java.lang.String atitle){
		this.atitle = atitle;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  投诉类型
	 */
	@Column(name ="ATYPE",nullable=false,length=200)
	public java.lang.Integer getAtype(){
		return this.atype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  投诉类型
	 */
	public void setAtype(java.lang.Integer atype){
		this.atype = atype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投诉内容
	 */
	@Column(name ="ACONTENT",nullable=true,length=700)
	public java.lang.String getAcontent(){
		return this.acontent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投诉内容
	 */
	public void setAcontent(java.lang.String acontent){
		this.acontent = acontent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投诉来源
	 */
	@Column(name ="AINFO",nullable=true,length=300)
	public java.lang.String getAinfo(){
		return this.ainfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投诉来源
	 */
	public void setAinfo(java.lang.String ainfo){
		this.ainfo = ainfo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客服状态
	 */
	@Column(name ="AKTATUS",nullable=true,length=200)
	public java.lang.String getAktatus(){
		return this.aktatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客服状态
	 */
	public void setAktatus(java.lang.String aktatus){
		this.aktatus = aktatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客服备注
	 */
	@Column(name ="ANOTES",nullable=true,length=400)
	public java.lang.String getAnotes(){
		return this.anotes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客服备注
	 */
	public void setAnotes(java.lang.String anotes){
		this.anotes = anotes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  进度
	 */
	@Column(name ="ASTATUS",nullable=true,length=300)
	public java.lang.String getAstatus(){
		return this.astatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  进度
	 */
	public void setAstatus(java.lang.String astatus){
		this.astatus = astatus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  投诉时间
	 */
	@Column(name ="CREATE_DATETIME",nullable=true,length=200)
	public java.util.Date getCreateDatetime(){
		return this.createDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  投诉时间
	 */
	public void setCreateDatetime(java.util.Date createDatetime){
		this.createDatetime = createDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投诉人
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=300)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投诉人
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户账号
	 */
	@Column(name ="AZHTYPE",nullable=true,length=250)
	public java.lang.String getAzhtype(){
		return this.azhtype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户账号
	 */
	public void setAzhtype(java.lang.String azhtype){
		this.azhtype = azhtype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司名
	 */
	@Column(name ="AREALNAME",nullable=true,length=300)
	public java.lang.String getArealname(){
		return this.arealname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司名
	 */
	public void setArealname(java.lang.String arealname){
		this.arealname = arealname;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  受理时间
	 */
	@Column(name ="SL_DATETIME",nullable=true,length=300)
	public java.util.Date getSlDatetime(){
		return this.slDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  受理时间
	 */
	public void setSlDatetime(java.util.Date slDatetime){
		this.slDatetime = slDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  受理人
	 */
	@Column(name ="SL_NAME",nullable=true,length=200)
	public java.lang.String getSlName(){
		return this.slName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  受理人
	 */
	public void setSlName(java.lang.String slName){
		this.slName = slName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  部门处理时间
	 */
	@Column(name ="DE_DATETIME",nullable=true,length=250)
	public java.util.Date getDeDatetime(){
		return this.deDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  部门处理时间
	 */
	public void setDeDatetime(java.util.Date deDatetime){
		this.deDatetime = deDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理人
	 */
	@Column(name ="DE_NAME",nullable=true,length=230)
	public java.lang.String getDeName(){
		return this.deName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理人
	 */
	public void setDeName(java.lang.String deName){
		this.deName = deName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  反馈时间
	 */
	@Column(name ="COM_DATETIME",nullable=true,length=250)
	public java.util.Date getComDatetime(){
		return this.comDatetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  反馈时间
	 */
	public void setComDatetime(java.util.Date comDatetime){
		this.comDatetime = comDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  反馈人
	 */
	@Column(name ="COM_NAME",nullable=true,length=250)
	public java.lang.String getComName(){
		return this.comName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  反馈人
	 */
	public void setComName(java.lang.String comName){
		this.comName = comName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件
	 */
	@Column(name ="AFILE",nullable=true,length=340)
	public java.lang.String getAfile(){
		return this.afile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件
	 */
	public void setAfile(java.lang.String afile){
		this.afile = afile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="ATEL",nullable=true,length=100)
	public java.lang.String getAtel(){
		return this.atel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setAtel(java.lang.String atel){
		this.atel = atel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  投诉部门
	 */
	@Column(name ="AADEPT",nullable=true,length=250)
	public java.lang.String getAadept(){
		return this.aadept;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  投诉部门
	 */
	public void setAadept(java.lang.String aadept){
		this.aadept = aadept;
	}
}
