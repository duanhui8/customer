package org.jeecgframework.web.system.pojo.base;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@GenericGenerator(name = "systemUUID", strategy = "uuid")
public class Message {

	private String mid;

	private String userName;
	private String phone;
	private String userCode;
	private String content; 
	private List<MsgItem> msgItem;
	//用来接收短信
	//接收短信phone
	private String ErrorNum;
	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="rootId",cascade={CascadeType.MERGE})
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<MsgItem> getMsgItem() {
		return msgItem;
	}

	public void setMsgItem(List<MsgItem> msgItem) {
		this.msgItem = msgItem;
	}

	private String deptName;







	
	public String getErrorNum() {
		return ErrorNum;
	}

	public void setErrorNum(String errorNum) {
		ErrorNum = errorNum;
	}





	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// @JsonIgnore
	// @ManyToOne(optional=true,fetch=FetchType.LAZY,cascade={CascadeType.REFRESH})
	// @JoinColumn(name="userId")
	// public Users getUsers() {
	// return users;
	// }
	//
	// public void setUsers(Users users) {
	// this.users = users;
	// }

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@Column(length = 50)
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Column(length = 5000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
