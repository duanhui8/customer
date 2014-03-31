package org.jeecgframework.core.interceptors;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.dom4j.DocumentException;
import org.jeecgframework.core.util.ADVICESTATUS;
import org.jeecgframework.core.util.AINFOSTATUS;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.MessageUtil;
import org.jeecgframework.web.system.pojo.base.Message;
import org.jeecgframework.web.system.pojo.base.MsgItem;
import org.jeecgframework.web.system.service.SystemService;
import com.jinbo.customer.entity.customerservice.CustomerSerEntity;



//1065712068888813
public class MsgInterfaceListener implements ServletContextListener {

    private String status = null;
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			
			String num = MessageUtil.logOut();
			if(num!=null&&num.equalsIgnoreCase("0")){
				System.out.println("注销成功");
			}else{
				
				System.out.println("注销失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void contextInitialized(ServletContextEvent arg0) {
		MyTread my = new MyTread();
		my.start();
		

	}

	private boolean login() {
       try {
		status =  MessageUtil.login();
	    if(status!=null&&status.equalsIgnoreCase("0")){	    	
	    	return true;
	    }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
       return false;
	}

	
	
	class MyTask extends TimerTask {
	    private SystemService systemService = null;

		@Override
		public void run() {
			try {
				System.out.println("心跳链接---");
				String num = MessageUtil.heart();
				if (num != null && num.equalsIgnoreCase("0")) {
					System.out.println("链接正常");
					System.out.println("读取短信内容");
			        if(systemService!=null){
			        	Message msg = MessageUtil.fentchMessage();
			        	//判断接收的短信不能是空的
			        	if(msg!=null){			        		
			        		for(MsgItem mi : msg.getMsgItem()){
			        			//客户发送的短信内容不能是空的
			        			if(mi!=null&&mi.getMsgItem()!=null&&mi.getMsgItem().length()>0){			        				
			        				CustomerSerEntity cus = new CustomerSerEntity();
			        				cus.setCreateDatetime(DataUtils.getDate());
			        				cus.setAstatus(ADVICESTATUS.等待客服确认);
			        				cus.setAcontent(mi.getMsgItem());
			        				cus.setAinfo(AINFOSTATUS.移动短信);
			        				cus.setAtype(3);
			        				cus.setAtel(mi.getPhone());
			        				try {
			        					systemService.save(cus);
			        					Message returnmsg = new Message();
			        					returnmsg.setContent("尊敬的客户您好！您的投诉和建议我们已采纳，我们将在一个工作日内处理完成并给您答复。如有疑问请致电客服110-222-2");
			        					returnmsg.setPhone(mi.getPhone());
			        					MessageUtil.send(returnmsg);
			        				} catch (Exception e) {
			        					Message returnmsg = new Message();
			        					returnmsg.setContent("尊敬的客户您好，系统收集短信，发生错误请致电客服咨询11-223");
			        					returnmsg.setPhone(mi.getPhone());
			        					MessageUtil.send(returnmsg);
			        				}
			        			}
			        		}
			        	}
			        }else{
						systemService =	(SystemService) ApplicationContextUtil.getContext().getBean("systemService");

			        }

				} else {
	                System.out.println("服务器失去链接,尝试登录");
	                login();
	                
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



	}

	class MyTread  extends Thread{
		
		@Override
		public void run() {
			System.out.println("系统初始化了-------");			
			boolean flag = login();
			if(!flag){
				System.out.println("登录失败");
			}
			Timer timer = new Timer();
			timer.schedule(new MyTask(), 1000*20,1000*70);
		}
		
		
	}
}


