package com.jinbo.customer.service.impl.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinbo.customer.service.user.CustomerUserServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("customerUserService")
@Transactional
public class CustomerUserServiceImpl extends CommonServiceImpl implements CustomerUserServiceI {
	
}