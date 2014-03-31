package com.jinbo.customer.controller.wei;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;

import com.jinbo.customer.entity.customerservice.Tab_OrderDetail;
import com.jinbo.customer.service.wei.OrderService;

public class OrderServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(OrderServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
             String id = request.getParameter("ddh");
             if(id!=null&&id.trim().length()>0){
            	 OrderService orService = (OrderService) ApplicationContextUtil.getContext().getBean("orderService");
            	 try {
					List<Tab_OrderDetail> list = orService.findDetailsByZbid(Integer.parseInt(id));
					request.setAttribute("list",list );
					request.getRequestDispatcher("/WEB-INF/order_details.jsp").forward(request, response);
					return;
				} catch (NumberFormatException e) {
                  logger.error("id格式不合法");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
	}

}
