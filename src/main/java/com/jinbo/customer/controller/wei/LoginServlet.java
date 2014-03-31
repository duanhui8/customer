package com.jinbo.customer.controller.wei;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;

import com.jinbo.customer.entity.customerservice.Tab_User;
import com.jinbo.customer.service.wei.LoginServiceImpl;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2207385540841378415L;
	private Logger logger = Logger.getLogger(LoginServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String goal = request.getParameter("goal");
		checkOpenId(request, response);
        if(goal!=null&&goal.trim().length()>0&&goal.equalsIgnoreCase("update")){
        	updateBundle(request,response);
        }else{
        	bundle(request,response);
        }
	}

	private void updateBundle(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String openid = request.getParameter("openid");
		LoginServiceImpl ls = (LoginServiceImpl) ApplicationContextUtil.getContext().getBean("loginService");
		Tab_User user = ls.getUser(username, password);
		PrintWriter pw = response.getWriter();
		if (user != null && user.getYhm() != null) {
			if (user.getOpenid() == null
					|| user.getOpenid().trim().length() <= 0) {
				try {
			            boolean flag = ls.repeatBundle(user,openid);
					if (flag) {
						request.setAttribute("info", "恭喜你，绑定成功");
						request.setAttribute("openid", openid);
						request.setAttribute("yhm", username);
						request.getRequestDispatcher("/WEB-INF/message.jsp")
								.forward(request, response);
						return;
					} else {
						request.setAttribute("info", "绑定失败");
						request.getRequestDispatcher("/WEB-INF/message.jsp")
								.forward(request, response);
						return;
					}
				} catch (Exception e) {
					logger.error("重新绑定openid出错" + e);
				}
			} else {
				request.setAttribute("info", "该账号已绑定微信。");
				request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
						request, response);
				return;

			}
		} else {
			request.setAttribute("info", "账号或者密码错误。");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
					request, response);
		}
	}

	private void bundle( HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String openid = request.getParameter("openid");
		LoginServiceImpl ls = (LoginServiceImpl) ApplicationContextUtil.getContext().getBean("loginService");
		boolean fla = ls.findUserByOpenId(openid);
		//检测这个微信有没有绑定账号
		if (fla) {
			request.setAttribute("info", "此微信已绑定账号");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
					request, response);
			return;
		}
		Tab_User user = ls.getUser(username, password);
		PrintWriter pw = response.getWriter();
		if (user != null && user.getYhm() != null) {
			if (user.getOpenid() == null|| user.getOpenid().trim().length() <= 0) {
				try {
					boolean flag = ls.saveOpenIdById(user.getId(), openid);
					if (flag) {
						request.setAttribute("info", "恭喜你，绑定成功");
						request.setAttribute("openid", openid);
						request.setAttribute("yhm", username);
						request.getRequestDispatcher("/WEB-INF/message.jsp")
								.forward(request, response);
						return;
					} else {
						request.setAttribute("info", "绑定失败");
						request.getRequestDispatcher("/WEB-INF/message.jsp")
								.forward(request, response);
						return;
					}
				} catch (SQLException e) {
					logger.error("绑定openid出错" + e);
				}
			} else {
				request.setAttribute("info", "该账号已绑定微信。");
				request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
						request, response);
				return;

			}
		} else {
			request.setAttribute("info", "账号或者密码错误。");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
					request, response);
		}
	}

	private void checkOpenId( HttpServletRequest request,
			HttpServletResponse response) {
		String openid = request.getParameter("openid");
		if (openid == null || openid.length() <= 0) {
			request.setAttribute("info", "绑定账号出错，请重新绑定。");
			try {
				request.getRequestDispatcher("/WEB-INF/message.jsp").forward(
						request, response);
			} catch (Exception e) {
				logger.error("验证openid，转发异常" + e);
			}
		}

	}

}
