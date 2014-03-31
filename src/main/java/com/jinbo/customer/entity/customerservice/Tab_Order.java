package com.jinbo.customer.entity.customerservice;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class Tab_Order {
	private int id;
	private String ddh;
	private int state;
	private String khqc;
	private Timestamp zdrq;
	
	




	public Timestamp getZdrq() {
		return zdrq;
	}

	public void setZdrq(Timestamp zdrq) {
		this.zdrq = zdrq;
	}

	private List<Tab_OrderDetail> OrderDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDdh() {
		return ddh;
	}

	public void setDdh(String ddh) {
		this.ddh = ddh;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getKhqc() {
		return khqc;
	}

	public void setKhqc(String khqc) {
		this.khqc = khqc;
	}

	public List<Tab_OrderDetail> getOrderDetails() {
		return OrderDetails;
	}

	public void setOrderDetails(List<Tab_OrderDetail> orderDetails) {
		OrderDetails = orderDetails;
	}


}
