package com.jinbo.customer.wei.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.jinbo.customer.entity.customerservice.Tab_Order;
import com.jinbo.customer.entity.customerservice.Tab_User;


@Service
public class OrderDao extends BaseDao<Tab_Order>{
    public List<Tab_Order> findToday_Order(Tab_User user) throws SQLException{
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select * from tab_order where  convert(varchar(10),getdate(),120) = convert(varchar(10),zdrq,120) and yh="+user.getId();		 
		List<Tab_Order> list = runner.query(sql, new BeanListHandler<Tab_Order>(Tab_Order.class));
		return list;
    }	
    public static void main(String[] args){
   	 ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
   	OrderDao os =  (OrderDao) app.getBean("orderDao");
   	Tab_User user = new Tab_User();
   	user.setId(2330);
   	try {
		List<Tab_Order> list =	os.findToday_Order(user);
		for(Tab_Order to:list){
			System.out.println(to.getKhqc());
			System.out.println(to.getZdrq().toLocaleString());
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	
	}
}
