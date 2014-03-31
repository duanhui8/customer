package com.jinbo.customer.service.wei;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import com.jinbo.customer.controller.wei.CoreServlet;
import com.jinbo.customer.dao.wei.exception.ManyCustomerException;
import com.jinbo.customer.entity.customerservice.BaseMessage;
import com.jinbo.customer.entity.customerservice.Tab_Order;
import com.jinbo.customer.entity.customerservice.Tab_User;
import com.jinbo.customer.entity.customerservice.TextMessage;
import com.jinbo.customer.wei.utils.MessageWUtil;



@Service
public class EventService implements BaseService {
	@Resource(name = "loginServiceImpl")
	public LoginService loginService;
	@Resource
	public OrderService orderService;

	private Logger logger = Logger.getLogger(EventService.class);

	public String doRequest(Map<String, String> map) {
		String fromUserName = map.get("FromUserName");
		// 公众帐号
		String toUserName = map.get("ToUserName");
		String key = map.get("EventKey");
		String openid = map.get("openid");
		String[] value = CoreServlet.task.get(fromUserName);
		if (value.length <= 0) {
			value[0] = key;
		} else {
			value[0] = key;
		}
		if (key.equalsIgnoreCase("order_query")) {
			TextMessage tm = new TextMessage();
			tm.setContent("请输入你要查询的订单号!");
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			String xmlContent = MessageWUtil.textMessageToXml(tm);
			CoreServlet.task.put(fromUserName, value);
			return xmlContent;
		} else if (key.equalsIgnoreCase("rengong")) {
			TextMessage tm = new TextMessage();
			tm.setContent("人工服务请拨打:888888!\r\n投诉建议请拨打:1100");
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			String xmlContent = MessageWUtil.textMessageToXml(tm);
			return xmlContent;
		} else if (key.equalsIgnoreCase("advice")) {
			TextMessage tm = new TextMessage();
			tm.setContent("请输入你要投诉的信息！");
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			String xmlContent = MessageWUtil.textMessageToXml(tm);
			CoreServlet.task.put(fromUserName, value);
			return xmlContent;

		} else if (key.equalsIgnoreCase("address")) {
			TextMessage tm = new TextMessage();
			tm.setContent("此功能尚未开发");
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			String xmlContent = MessageWUtil.textMessageToXml(tm);
			return xmlContent;

		} else if (key.equalsIgnoreCase("customerinfo")) {
			String xmlContent = null;
			TextMessage tm = new TextMessage();
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			try {
				Tab_User user = loginService.getUserInfo(fromUserName);
				if (user == null) {
					tm.setContent("该微信号尚未绑定账号!");
					xmlContent = MessageWUtil.textMessageToXml(tm);
				} else {
					tm.setContent("查询结果：\n已绑定账号:" + user.getYhm());
					xmlContent = MessageWUtil.textMessageToXml(tm);
				}
			} catch (ManyCustomerException e) {
				logger.error("查询账号信息出现多个账号:账号:" + e.getMessage());
				tm.setContent("查询您的账号出现错误，请联系我们!");
				xmlContent = MessageWUtil.textMessageToXml(tm);
			}
			return xmlContent;

		} else if (key.equalsIgnoreCase("new_bundle")) {
			Tab_User user = null;
			try {
				user = loginService.getUserInfo(fromUserName);
			} catch (ManyCustomerException e) {
				e.printStackTrace();
			}
			TextMessage tm = new TextMessage();
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			if (user != null && user.getYhm() != null
					&& user.getYhm().trim().length() > 0) {
				tm.setContent("该微信已绑定账号：\r\n账号:" + user.getYhm()
						+ "!\r\n\r\n点击<a href=\"" + ResourceUtil.getConfigByName("site")
						+ "/login.jsp?openid=" + fromUserName
						+ "&goal=update\">重新绑定</a>。");
			} else {
				tm.setContent("请点击链接<a href=\"" + ResourceUtil.getConfigByName("site")
						+ "/login.jsp?openid=" + fromUserName + "\">绑定</a>账号。");
			}
			return MessageWUtil.textMessageToXml(tm);
		} else if (key.equalsIgnoreCase("today_order")) {
			String xmlContent = null;
			TextMessage tm = new TextMessage();
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			Tab_User user = null;
			try {
				user = loginService.getUserInfo(fromUserName);
				if (user == null) {
					tm.setContent("您还未绑定微信账号!请<a href=\""
							+ ResourceUtil.getConfigByName("site") + "/login.jsp?openid="
							+ fromUserName + "\">绑定</a>。");
					xmlContent = MessageWUtil.textMessageToXml(tm);
				} else {
					List<Tab_Order> list = orderService.findToday_Order(user);
					StringBuffer sb = new StringBuffer();
					sb.append("今日订单").append("\n");
					sb.append("-----------").append("\n");
					if (list.size() <= 0) {
						sb.append("今日无订单");
					}
					for (Tab_Order to : list) {
						sb.append(
								"订单:<a href=\"" + ResourceUtil.getConfigByName("site")
										+ "/OrderServlet?ddh=" + to.getId()
										+ "\">" + to.getDdh() + "</a>").append(
								"\n");
						sb.append("-----------").append("\n");
					}
					tm.setContent(sb.toString());
				}
			} catch (ManyCustomerException e) {
				logger.error("查询出多个用户" + e);
			} catch (SQLException e) {
				logger.error("查询今日订单sql异常" + e);
			}
			return MessageWUtil.textMessageToXml(tm);
		} else if (key.equalsIgnoreCase("")) {
			
			TextMessage tm = new TextMessage();
			tm.setContent("尊敬的用户您好:\n    感谢您关注广州金博微信自助服务平台，我们将竭诚为您提供更优质的服务，更快捷的资讯信息！/:rose");
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);			
			return MessageWUtil.textMessageToXml(tm);

		} else {
			TextMessage tm = new TextMessage();
			tm.setContent("你输入有误。按【0】返回主导航！！");
			tm = (TextMessage) fillMessageBaseInfo(tm, fromUserName, toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			String xmlContent = MessageWUtil.textMessageToXml(tm);
			return xmlContent;

		}

	}

	public static BaseMessage fillMessageBaseInfo(BaseMessage tm,
			String fromUserName, String toUserName) {
		tm.setCreateTime(System.currentTimeMillis());
		tm.setFromUserName(toUserName);
		tm.setToUserName(fromUserName);
		return tm;
	}

}
