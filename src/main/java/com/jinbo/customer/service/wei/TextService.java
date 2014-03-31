package com.jinbo.customer.service.wei;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import com.jinbo.customer.controller.wei.CoreServlet;
import com.jinbo.customer.dao.wei.exception.DDHException;
import com.jinbo.customer.entity.customerservice.Article;
import com.jinbo.customer.entity.customerservice.NewsMessage;
import com.jinbo.customer.entity.customerservice.Tab_Order;
import com.jinbo.customer.entity.customerservice.Tab_OrderDetail;
import com.jinbo.customer.entity.customerservice.Tab_User;
import com.jinbo.customer.entity.customerservice.TextMessage;
import com.jinbo.customer.wei.dao.UserDao;
import com.jinbo.customer.wei.utils.MessageWUtil;



/**
 * 文本信息处理类 当客户发送文本信息，会进入此类方法
 * 
 * @author Administrator
 * 
 */
@Service
public class TextService implements BaseService {
	@Resource
	OrderService orderService;
	@Resource
	UserDao userDao;
	private static Logger logger = Logger.getLogger(TextService.class);

	public String doRequest(Map<String, String> map) {
		System.out.println("进入text服务");
		// 发送方帐号（open_id）
		String fromUserName = map.get("FromUserName");
		String[] tasks = CoreServlet.task.get(fromUserName);
		if (tasks[0] == null) {
			tasks[0] = "0";
			CoreServlet.task.put(fromUserName, tasks);
			return getDefault(map);
		}
		String last = tasks[0];
		// 公众帐号
		String toUserName = map.get("ToUserName");
		// 消息类型
		String msgType = map.get("MsgType");

		String content = map.get("Content");
		// 当上一步操作是订单查询进入此方法
		if (content.trim().equalsIgnoreCase("0")) {
			tasks[0] = "0";
			CoreServlet.task.put(fromUserName, tasks);
			return getDefault(map);
		} else if (last.equals("order_query")) {
			// -----------------判断是否绑定-------------
			Tab_User tab_User = null;
			try {
				tab_User = userDao.findByOpenId(fromUserName);
				if (tab_User == null || tab_User.getYhm() == null) {
					TextMessage tm = new TextMessage();
					tm.setContent("您还未绑定微信账号!请<a href=\""+ResourceUtil.getConfigByName("site")+"/login.jsp?openid="+fromUserName+"\">绑定</a>。");
					tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
					tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
					return MessageWUtil.textMessageToXml(tm);
				}
			} catch (Exception e1) {
				logger.error("查询账号出错" + e1);
			}
			// ----------------业务操作----------------------

			if (content.length() < 8) {
				TextMessage tm = new TextMessage();
				tm.setContent("你输入的订单号有误");
				tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
				tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
				return MessageWUtil.textMessageToXml(tm);
			} else {
				Tab_Order order = null;
				try {
					//根据用户的订单号和用户ID去查询订单和明细
					order = orderService.findOrdersByOpenId(tab_User,
							content);
				} catch (DDHException e) {
					TextMessage tm = new TextMessage();
					tm.setContent("未查询到此订单信息!");
					tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
					tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
					return MessageWUtil.textMessageToXml(tm);
				} catch (Exception e) {
					logger.error("查询订单出错" + e);
					TextMessage tm = new TextMessage();
					tm.setContent("此订单相关信息不存在!");
					tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
					tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
					return MessageWUtil.textMessageToXml(tm);
				}
				StringBuffer sb = new StringBuffer();
				sb.append(
						"尊敬的客户,订单查询结果,共" + order.getOrderDetails().size() + "条")
						.append("\n\n");
				sb.append("货主:" + order.getKhqc()).append("\n");
				sb.append("--------------------").append("\n");
				for (Tab_OrderDetail to : order.getOrderDetails()) {
					sb.append("品名：" + to.getPm()).append("\n");
					sb.append("材质：" + to.getCz()).append("\n");
					sb.append("规格：" + to.getGg()).append("\n");
					sb.append("重量：" + to.getZl()).append("\n");
					sb.append("品牌：" + to.getPp()).append("\n");
					sb.append("库位：" + to.getCk()).append("\n");
					sb.append("--------------------").append("\n");

				}
				TextMessage tm = new TextMessage();
				tm.setContent(sb.toString());
				tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
				tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
				// 将图文消息对象转换成xml字符串
				return MessageWUtil.textMessageToXml(tm);
			}
			//如果用户点击投诉建议进入
		} else if (last.equals("advice")) {
			TextMessage tm = new TextMessage();
			tm.setContent("您的投诉建议我们已采纳，感谢您的宝贵建议,我们会认真考虑,斟酌采用\r\n按【0】返回主导航!。");
			tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			tasks[0] = "0";
			CoreServlet.task.put(fromUserName, tasks);
			return MessageWUtil.textMessageToXml(tm);
		} else if (content.equalsIgnoreCase("1")) {
			List<Article> articleList = new ArrayList<Article>();
			NewsMessage newsMessage = new NewsMessage();
			newsMessage = (NewsMessage) EventService.fillMessageBaseInfo(newsMessage,fromUserName,toUserName);
			newsMessage.setMsgType(MessageWUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			Article article1 = new Article();
			article1.setTitle("最新");
			article1.setDescription("最新信息");
			article1.setPicUrl("");
			article1.setUrl("");

			Article article2 = new Article();
			article2.setTitle("【1】");
			article2.setDescription("dfsdfsdf");


			Article article3 = new Article();
			article3.setTitle("【2】信息");
			article3.setDescription("grgrgc");
			article3.setPicUrl("ycl.jpg");
			article3.setUrl("www.baidu.com");

			articleList.add(article1);
			articleList.add(article2);
			articleList.add(article3);
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);	
			return  MessageWUtil.newsMessageToXml(newsMessage);
		} else if (content.equalsIgnoreCase("2")) {
			List<Article> articleList = new ArrayList<Article>();
			NewsMessage newsMessage = new NewsMessage();
			newsMessage = (NewsMessage) EventService.fillMessageBaseInfo(newsMessage,fromUserName,toUserName);
			newsMessage.setMsgType(MessageWUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			Article article = new Article();
			article.setTitle("香港金钧介绍");
			article.setDescription("简介");
			article.setPicUrl("http://jbwl.steelhome.cn/city/guangzhou.jpg");
			article.setUrl("www.baidu.com");
			articleList.add(article);
			// 设置图文消息个数
			newsMessage.setArticleCount(articleList.size());
			// 设置图文消息包含的图文集合
			newsMessage.setArticles(articleList);
			// 将图文消息对象转换成xml字符串
			return MessageWUtil.newsMessageToXml(newsMessage);

		} else if (content.equalsIgnoreCase("3")) {
			TextMessage tm = new TextMessage();
			tm.setContent("欢迎关注广州金博，详细请咨询\r\n电话：XXXXXXXX!");
			tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			return MessageWUtil.textMessageToXml(tm);

		} else if (content.equalsIgnoreCase("0")) {
			return getDefault(map);

		} else if(content.equalsIgnoreCase("?")){
			   TextMessage tm = new TextMessage();
			   tm.setContent("此功能尚未开发");
			   tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
			   tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			   return MessageWUtil.textMessageToXml(tm);
		}{
			TextMessage tm = new TextMessage();
			tm.setContent("你输入有误！按【0】返回主菜单");
			tm = (TextMessage) EventService.fillMessageBaseInfo(tm,fromUserName,toUserName);
			tm.setMsgType(MessageWUtil.REQ_MESSAGE_TYPE_TEXT);
			return MessageWUtil.textMessageToXml(tm);

		}

	}

	public String getDefault(Map<String, String> maps) {
		// 发送方帐号（open_id）
		String fromUserName = maps.get("FromUserName");
		// 公众帐号
		String toUserName = maps.get("ToUserName");
		// 消息类型
		String msgType = maps.get("MsgType");

		// 默认回复此文本消息
		TextMessage textMessage = new TextMessage();
		textMessage = (TextMessage) EventService.fillMessageBaseInfo(textMessage,fromUserName,toUserName);
		textMessage.setMsgType(MessageWUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
		textMessage.setContent(getMainMenu());
		// 将文本消息对象转换成xml字符串
		return MessageWUtil.textMessageToXml(textMessage);

	}

	public static String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，欢迎关注广州金博集团，请回复数字选择服务：").append("\n\n");
		buffer.append("【1】  博汇置业").append("\n");
		buffer.append("【2】  金钧 ").append("\n");
		buffer.append("【3】  招商咨询").append("\n");
		buffer.append("回复“?”显示此帮助菜单");
		return buffer.toString();
	}

}
