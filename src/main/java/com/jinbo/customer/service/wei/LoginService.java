package com.jinbo.customer.service.wei;

import java.sql.SQLException;

import com.jinbo.customer.dao.wei.exception.ManyCustomerException;
import com.jinbo.customer.entity.customerservice.Tab_User;

public interface LoginService {
	public Tab_User getUser(String name, String pwd);
	public boolean saveOpenIdById(int id, String openid)throws SQLException ;
	public boolean findUserByOpenId(String openid);
	public boolean repeatBundle(Tab_User new_user, String openid);
	public Tab_User getUserInfo(String openid) throws ManyCustomerException;
}
