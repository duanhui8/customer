package com.jinbo.customer.wei.dao;

import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Service;

import com.jinbo.customer.entity.customerservice.Tab_OrderDetail;



@Service
public class OrderDetailDao extends BaseDao<Tab_OrderDetail> {
	public List<Tab_OrderDetail> findByZbId(int id) throws Exception {
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,pm,cz,gg,pp,ck,zl from Tab_OrderDetail where zbid = ?";
		Object[] obj = new Object[] { id };
		List<Tab_OrderDetail> list = runner.query(sql, new BeanListHandler<Tab_OrderDetail>(Tab_OrderDetail.class), obj);
		return list;
	}
	
}
