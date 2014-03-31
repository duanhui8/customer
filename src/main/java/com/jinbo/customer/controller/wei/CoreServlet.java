package com.jinbo.customer.controller.wei;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ResourceUtil;

import com.jinbo.customer.dao.wei.exception.ParserXmlException;
import com.jinbo.customer.service.wei.BaseService;
import com.jinbo.customer.wei.utils.MessageWUtil;
import com.jinbo.customer.wei.utils.SignUtil;


public class CoreServlet extends HttpServlet {
	

	private static final long serialVersionUID = 747692755210552861L;

	private static Logger logger=Logger.getLogger(CoreServlet.class);
	
	public static Map<String,String[]> task = new LinkedHashMap<String,String[]>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}else{
			
			System.out.println("校验失败");
		}
		out.close();
		out = null;
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	     long start = System.currentTimeMillis();
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		 
		try {
			//获取用户任务链表，获取用上一步操作。
			//微信xml数据解析为MAP集合
			Map<String, String> maps = MessageWUtil.parseXml(request);	
			String fromUserName = maps.get("FromUserName");
		    if(task.get(fromUserName)==null){
		    	task.put(fromUserName, new String[1]);
		    	System.out.println("此用户是第一次登录");		    	
		    }else{		    	
		    	System.out.println("此用户上次操作是"+task.get(fromUserName)[0]);
		    }
			//获取用户发送数据类型
			String mstype = maps.get("MsgType");
			//根据用户发送数据类型，判断调用那一个类，进行处理。			
			String clazz = ResourceUtil.getConfigByName(mstype);
			//获取服务类实例
			BaseService bs = (BaseService) ApplicationContextUtil.getContext().getBean(clazz);
			//处理完返回结果
		    String xmlContent = bs.doRequest(maps);
			// 响应消息
			PrintWriter out = response.getWriter();
			//向用户输出结果。
			out.print(xmlContent);
	       long end = System.currentTimeMillis();  
	       long time = (end-start);
	       System.out.println("本次"+mstype+"查询共花"+time+"毫秒");
		} catch (ParserXmlException e) {
			logger.error("解析XML错误"+e);
		}
	
		

	}

}
