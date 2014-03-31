package org.jeecgframework.web.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
@GenericGenerator(name = "systemUUID", strategy = "uuid")
public class MsgItem {
	private String id;
	private String Phone;
	private String RecDate;
	private String RecTime;
	private String PostFixNum;
	private String MsgItem;
	private String rootId;

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@Column(length = 50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getMsgItem() {
		return MsgItem;
	}

	public void setMsgItem(String msgItem) {
		MsgItem = msgItem;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getRecDate() {
		return RecDate;
	}

	public void setRecDate(String recDate) {
		RecDate = recDate;
	}

	public String getRecTime() {
		return RecTime;
	}

	public void setRecTime(String recTime) {
		RecTime = recTime;
	}

	public String getPostFixNum() {
		return PostFixNum;
	}

	public void setPostFixNum(String postFixNum) {
		PostFixNum = postFixNum;
	}

}
