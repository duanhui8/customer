package com.jinbo.customer.service.wei;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jinbo.customer.entity.customerservice.Article;
import com.jinbo.customer.entity.customerservice.NewsMessage;
import com.jinbo.customer.entity.customerservice.TextMessage;
import com.jinbo.customer.wei.utils.MessageWUtil;









/**
 * 核心服务类
 * 
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	
	public static String str="";
	public static StringBuffer sb;
	
	
	public  static String processRequest(Map<String,String> maps) {
		String respMessage = null;
		try {
			// 发送方帐号（open_id）
			String fromUserName = maps.get("FromUserName");
			// 公众帐号
			String toUserName = maps.get("ToUserName");
			// 消息类型
			String msgType = maps.get("MsgType");

			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageWUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			textMessage.setContent(getMainMenu());
			// 将文本消息对象转换成xml字符串
			respMessage = MessageWUtil.textMessageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageWUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = maps.get("Content");
				if ("6".equals(content)) {	
					textMessage.setContent("此功能还在开发中");
					
					respMessage = MessageWUtil.textMessageToXml(textMessage);
				
				}
				
				
				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageWUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("3".equals(content)) {
					Article article = new Article();
					article.setTitle("推荐和分析");
					article.setDescription("分析");
					article.setPicUrl("http://3.yclandroid.duapp.com/image/3.jpg");
					article.setUrl("http://3g.500.com/info/info_more?flag=&colid=59&rnd=206LDKQPJM04KG431C");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageWUtil.newsMessageToXml(newsMessage);
				}
				// 单图文消息---不含图片
				else if ("4".equals(content)) {
					Article article = new Article();
					article.setTitle("号码");
					// 图文消息中可以使用QQ表情、符号表情
					article.setDescription("近20期");
					// 将图片置为空
					article.setPicUrl("http://3.yclandroid.duapp.com/image/5.jpg");
					article.setUrl("http://wap.24cai.com/open/getTermsListByType.do?lotteryType=3&pageSize=10");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageWUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("1".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("最新");
					article1.setDescription("最新信息");
					article1.setPicUrl("");
					article1.setUrl("http://wap.24cai.com");

					Article article2 = new Article();
					article2.setTitle("【1】");
					article2.setDescription("");
					article2.setPicUrl("http://3.yclandroid.duapp.com/image/ycl.jpg");
					article2.setUrl("http://3g.500.com/info/prize/open/index;jsessionid=5CE6DE6E2518F963EE6700554F0AB4B2?hzid=2353&rnd=49R8TLVTXFH5HIAO4C");

					Article article3 = new Article();
					article3.setTitle("【2】信息");
					article3.setDescription("");
					article3.setPicUrl("ycl.jpg");
					article3.setUrl("http://wap.24cai.com/buy/index.do?lotteryType=23");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageWUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if ("2".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("专家名人");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("http://3.yclandroid.duapp.com/image/2.jpg");
					article1.setUrl("http://wap.24cai.com");

					Article article2 = new Article();
					article2.setTitle("大乐");
					article2.setDescription("");
					article2.setPicUrl("http://3.yclandroid.duapp.com/image/ycl.jpg");
					article2.setUrl("http://3g.500.com/buy/hm/hm_index?flag=dlt&from=wx");

					Article article3 = new Article();
					article3.setTitle("买单推荐");
					article3.setDescription("");
					article3.setPicUrl("http://3.yclandroid.duapp.com/image/ycl.jpg");
					article3.setUrl("http://3g.500.com/buy/hm/jczq/parti.action?from=wx");

					Article article4 = new Article();
					article4.setTitle("推荐");
					article4.setDescription("");
					article4.setPicUrl("http://3.yclandroid.duapp.com/image/ycl.jpg");
					article4.setUrl("http://3g.500.com/info/info_hero?lotid=10000&type=1&?from=wx");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageWUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if ("5".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("图表分析");
					article1.setDescription("");
					article1.setPicUrl("http://3.yclandroid.duapp.com/image/4.jpg");
					article1.setUrl("http://wap.24cai.com");

					Article article2 = new Article();
					article2.setTitle("3D图表走势图\n组选图表分析");
					article2.setDescription("");
					article2.setPicUrl("http://3.yclandroid.duapp.com/image/ycl.jpg");
					article2.setUrl("http://wap.24cai.com/chart/toAnalysis.do?lotteryType=fc3d&flag=4");

					Article article3 = new Article();
					article3.setTitle("图表走势图\n红球前区图表分析");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("http://3.yclandroid.duapp.com/image/ycl.jpg");
					article3.setUrl("http://wap.24cai.com/chart/toAnalysis.do?lotteryType=ssq&flag=1");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageWUtil.newsMessageToXml(newsMessage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	/**
	 * xiaoqrobot的主菜单
	 * 
	 * @return
	 */
	public static String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，欢迎关注xx，请回复数字选择服务：").append("\n\n");
		buffer.append("【1】  最新").append("\n");
		buffer.append("【2】  推荐 ").append("\n");
		buffer.append("【3】  分析").append("\n");
		buffer.append("【4】  历史").append("\n");
		buffer.append("【5】  图表").append("\n\n");
		buffer.append("回复“?”显示此帮助菜单");
		return buffer.toString();
	}
	
}