package com.jinbo.customer.entity.customerservice;

/**
 * 文本消息
 * 
 * @date 2013-05-19
 */
public class LinkMessage extends BaseMessage {
	// 回复的消息内容
	private String Description;
	private String Url;
	private String Title;
	private String MsgId;
	
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}


}