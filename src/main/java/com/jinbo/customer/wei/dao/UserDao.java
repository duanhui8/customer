package com.jinbo.customer.wei.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jinbo.customer.dao.wei.exception.ManyCustomerException;
import com.jinbo.customer.entity.customerservice.Tab_User;

@Service
public class UserDao extends BaseDao<Tab_User> {
	private Logger logger = Logger.getLogger(UserDao.class);

	public Tab_User findUserByName(String name, String pwd) throws Exception {
		Tab_User t = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,yhm,openid from Tab_User where yhm = '" + name
				+ "' and mm='" + pwd + "'";
		t = runner.query(sql, new BeanHandler(Tab_User.class));
		return t;
	}

	public boolean saveOpenIdById(int id, String openid) throws SQLException {
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "update  tab_user set openid='" + openid + "' where id="
				+ id;
		int i = runner.update(sql);
		if (i >= 1) {
			return true;
		} else {
			return false;
		}
	}
	public boolean saveOpenIdById(int id, String openid,Connection conn) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "update  tab_user set openid='" + openid + "' where id="
				+ id;
		int i = runner.update(conn,sql);
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}
	public List<Tab_User> findUserByOpenId(String openid) throws SQLException {
		List<Tab_User> users = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,yhm,openid from tab_user where openid='" + openid
				+ "'";
		users = runner
				.query(sql, new BeanListHandler<Tab_User>(Tab_User.class));
		return users;
	}

	public Tab_User findCustomerInfo(String openid) throws SQLException,
			ManyCustomerException {
		List<Tab_User> list = null;
		QueryRunner runner = new QueryRunner(dataSource);
		String sql = "select id,yhm,openid from tab_user where openid='" + openid
				+ "'";
		list = runner.query(sql, new BeanListHandler<Tab_User>(Tab_User.class));
		if (list.size() > 1) {
			throw new ManyCustomerException("查询账号绑定出现多个账号,问题账号:"
					+ list.get(0).getYhm());
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public boolean deleteOpenIdBy(Tab_User user,Connection conn) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "update  tab_user set openid='' where id="+ user.getId()+" and yhm='"+user.getYhm()+"' and openid='"+user.getOpenid()+"'";
		int i = runner.update(conn,sql);
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

}
