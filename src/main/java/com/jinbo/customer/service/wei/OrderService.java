package com.jinbo.customer.service.wei;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jinbo.customer.dao.wei.exception.DDHException;
import com.jinbo.customer.entity.customerservice.Tab_Order;
import com.jinbo.customer.entity.customerservice.Tab_OrderDetail;
import com.jinbo.customer.entity.customerservice.Tab_User;
import com.jinbo.customer.wei.dao.OrderDao;
import com.jinbo.customer.wei.dao.OrderDetailDao;
import com.jinbo.customer.wei.dao.UserDao;


@Service
public class OrderService {
	@Resource
	UserDao userDao;
	@Resource
	OrderDao orderDao;
	@Resource
	OrderDetailDao orderDetailDao;

	public Tab_Order findOrdersByOpenId(Tab_User user, String ddh) throws Exception{
		// 根据微信号查用户是否存在
	
			// 根据订单号去查订单
		String role = user.getUserRole();
		Tab_Order tab_Order = null;
		    if(role.trim().equalsIgnoreCase("1")){
		    	tab_Order = orderDao.findByDDHRole(ddh);
		    }else{
			tab_Order = orderDao.findByDDH(ddh,user.getId());
		    }
			if (tab_Order!= null && tab_Order.getId()> 0) {
				// 根据单去查明细
				List<Tab_OrderDetail> details = orderDetailDao.findByZbId(tab_Order.getId());
				if (details != null) {
					//将明细放入订单
					tab_Order.setOrderDetails(details);
					//返回
					return tab_Order;
				} else {
					throw new DDHException("未查询到该订单号明细");
				}

			} else {
				throw new DDHException("未查询到该订单号");
			}
		} 

    public List<Tab_Order> findToday_Order(Tab_User user) throws SQLException{   	
    	return orderDao.findToday_Order(user);
    }	
    public List<Tab_OrderDetail> findDetailsByZbid(int zbid) throws Exception{
    	return orderDetailDao.findByZbId(zbid);
    }
}

