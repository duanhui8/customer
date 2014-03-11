package com.jinbo.customer.entity.repyservice;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 客户查询处理结果
 * @author onlineGenerator
 * @date 2014-03-07 10:50:48
 * @version V1.0   
 *
 */
@Entity
@Table(name = "advice_reply", schema = "")
@SuppressWarnings("serial")
public class ServiceReplyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**受理单号*/
	private java.lang.String aorder;
	/**处理结果*/
	private java.lang.String acontent;
	/**处理时间*/
	private java.lang.String createDatetime;
	/**处理人*/
	private java.lang.String createName;
	/**处理部门*/
	private java.lang.String createDept;
	
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
	@Column(name ="AORDER",nullable=true,length=120)
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
	 *@return: java.lang.String  处理结果
	 */
	@Column(name ="ACONTENT",nullable=true,length=500)
	public java.lang.String getAcontent(){
		return this.acontent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理结果
	 */
	public void setAcontent(java.lang.String acontent){
		this.acontent = acontent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理时间
	 */
	@Column(name ="CREATE_DATETIME",nullable=true,length=150)
	public java.lang.String getCreateDatetime(){
		return this.createDatetime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理时间
	 */
	public void setCreateDatetime(java.lang.String createDatetime){
		this.createDatetime = createDatetime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理人
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=140)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理人
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理部门
	 */
	@Column(name ="CREATE_DEPT",nullable=true,length=150)
	public java.lang.String getCreateDept(){
		return this.createDept;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理部门
	 */
	public void setCreateDept(java.lang.String createDept){
		this.createDept = createDept;
	}
}
