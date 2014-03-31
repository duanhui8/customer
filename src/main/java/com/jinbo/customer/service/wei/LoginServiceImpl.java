package com.jinbo.customer.service.wei;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jinbo.customer.dao.wei.exception.ManyCustomerException;
import com.jinbo.customer.entity.customerservice.Tab_User;
import com.jinbo.customer.wei.dao.UserDao;

@Service
public class LoginServiceImpl implements BaseService,LoginService {
	@Resource
	public UserDao userDao;
	@Resource
	public DataSource dataSource;
	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);

	public String doRequest(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	// 根据用户名和密码查询用户，并返回
	public Tab_User getUser(String name, String pwd) {
		try {
			return userDao.findUserByName(name, pwd);
		} catch (Exception e) {
			logger.error("查询用户出错" + e);
		}
		return null;
	}

	// 绑定微信账号
	public boolean saveOpenIdById(int id, String openid) throws SQLException {
		return userDao.saveOpenIdById(id, openid);
	}

	// 根据微信号查询用户是否存在
	public boolean findUserByOpenId(String openid) {
		try {
			List<Tab_User> list = userDao.findUserByOpenId(openid);
			if (list.size() >= 1) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("根据openid查用户出错" + e);
		}
		return false;
	}

	public boolean repeatBundle(Tab_User new_user, String openid) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			Tab_User old_user = userDao.findCustomerInfo(openid);
			conn.setAutoCommit(false);
			boolean flag = userDao.deleteOpenIdBy(old_user, conn);
			if (flag) {
				flag = userDao.saveOpenIdById(new_user.getId(),
						openid, conn);
				if (flag) {
					conn.commit();
					return true;
				} else {
					conn.rollback();
					conn.commit();
					return false;
				}
			}
		}  catch (Exception e) {
	         logger.error("重新绑定用户出错误"+e);
	 		try {
				conn.rollback();
				conn.commit();
			} catch (SQLException e1) {
				logger.error("重新绑定,回滚出错误"+e1);
			}
		}
		return false;
	}

	// 查询用户绑定账号

	public Tab_User getUserInfo(String openid) throws ManyCustomerException {
		try {
			return userDao.findCustomerInfo(openid);
		} catch (SQLException e) {
			logger.error("查询用户信息sql异常" + e);
		}
		return null;
	}
}
