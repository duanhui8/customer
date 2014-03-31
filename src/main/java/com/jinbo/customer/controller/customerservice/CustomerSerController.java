package com.jinbo.customer.controller.customerservice;
import java.util.List;
import java.util.Properties;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ADVICESTATUS;
import org.jeecgframework.core.util.AINFOSTATUS;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.MessageUtil;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.RandomUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UpdateUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.demo.entity.test.JeecgDemoCkfinderEntity;
import org.jeecgframework.web.system.pojo.base.Message;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.CustomerUserEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.page.advice.CustomerAdvicePage;
import com.jinbo.customer.page.customerservice.CustomerSerPage;
import com.jinbo.customer.service.customerservice.CustomerSerServiceI;
import com.jinbo.customer.wei.utils.HTMLSpirit;
/**   
 * @Title: Controller
 * @Description: 客服
 * @author onlineGenerator
 * @date 2014-03-07 10:50:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/customerSerController")
public class CustomerSerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerSerController.class);

	@Autowired
	private CustomerSerServiceI customerSerService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 客服列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "customerSer")
	public ModelAndView customerSer(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/customerservice/customerSerList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(CustomerSerEntity customerSer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerSerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerSer);
		try{
		//自定义追加查询条件
		String query_createDatetime_begin = request.getParameter("createDatetime_begin");
		String query_createDatetime_end = request.getParameter("createDatetime_end");
		if(StringUtil.isNotEmpty(query_createDatetime_begin)){
			cq.ge("createDatetime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDatetime_begin));
		}
		if(StringUtil.isNotEmpty(query_createDatetime_end)){
			cq.le("createDatetime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDatetime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.le("astatus", ADVICESTATUS.等待反馈结果);
		cq.add();
		this.customerSerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客服
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerSerEntity customerSer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if(!customerSer.getAinfo().equalsIgnoreCase("3")){
			message = "该投诉单是用户创建,不能删除！";
			j.setMsg(message);
			return j;
		}
		customerSer = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
		message = "删除成功";
		try{
			customerSerService.delete(customerSer);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}



	/**
	 * 下发投诉单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CustomerSerEntity customerSer,CustomerSerPage customerSerPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  customerSerPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "投诉单下发成功";
		try{
            String note = (String) request.getParameter("anotes");
            String dept = (String) request.getParameter("aadept");
            CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
            if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待客服确认)){
            	message = "请先确认!";
            }else if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待客服下发)){             	
            	UpdateUtil.update(cus, customerSer);
            	//cus.setAnotes(note);
            	cus.setAstatus("2");
            }else if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待部门处理)){
            	message = "下发失败，部门正在处理!";
            	
            }else if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待反馈结果)){
            	
            	message = "下发失败，请反馈结果!";
            }
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "投诉单下发失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 取消下发
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doCancel")
	@ResponseBody
	public AjaxJson doCancel(CustomerSerEntity customerSer,CustomerSerPage customerSerPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  customerSerPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "投诉单下发成功";
		try{
            String dept = (String) request.getParameter("ids");
            CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, dept);
            if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待客服确认)){
            	message = "受理单还未确认，取消下发失败!";
            }else if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待客服下发)){ 
            	message = "受理单还未下发，取消失败!";
            }else if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待部门处理)){
            	cus.setAstatus(ADVICESTATUS.等待客服下发);
            	message = "取消下发成功!";
            	
            }else if(cus.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待反馈结果)){
            	
            	message = "部门已处理完，取消下发失败!";
            }
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "取消下发失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "doReturnResult")
	@ResponseBody
	public AjaxJson doReturnResult(CustomerSerEntity customerSer,CustomerSerPage customerSerPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  customerSerPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "反馈结果成功";
		try{
			String returnS = request.getParameter("returnS");
			CustomerSerEntity cu = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
			if(cu.getAstatus().equalsIgnoreCase("3")){
				if(returnS.equalsIgnoreCase("0")){
					message = "网页反馈结果成功";
					CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
					cus.setAstatus("4");
					cus.setComDatetime(DataUtils.getDate());
					cus.setComName(ResourceUtil.getSessionUserName().getUserName());
				}else if(returnS.equalsIgnoreCase("2")){
					message = "短信发送成功";
					CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
					String hql = "FROM ServiceReplyEntity e WHERE e.aorder=?";
					List<ServiceReplyEntity> reply = systemService.findHql(hql, cus.getAorder());
				    //判断是否只有一条回复
                    if(reply!=null&&reply.size()==1){
                    	ServiceReplyEntity sr = reply.get(0);
    					Message msg = new Message();
    					StringBuffer sb = new StringBuffer();
    					sb.append(ResourceUtil.getConfigByName("title"));
    					sb.append(HTMLSpirit.delHTMLTag(sr.getAcontent()));
    					sb.append(ResourceUtil.getConfigByName("last"));
    				    msg.setContent(sb.toString());
    				    msg.setPhone(cus.getAtel());
    				    String s = MessageUtil.send(msg);
    				    if(s!=null&&s.equalsIgnoreCase("0")){
    				    	cus.setAstatus(ADVICESTATUS.已完成);
    				    	cus.setComDatetime(DataUtils.getDate());
    				    	cus.setComName(ResourceUtil.getSessionUserName().getUserName());
    				    }else{
    						message = "短信发送失败";
    				    }
                    }
					 
			
					
				}
				
			}else if(cu.getAstatus().equalsIgnoreCase("0")||cu.getAstatus().equalsIgnoreCase("1")){
				message = "请先确认下发";				
			}else if(cu.getAstatus().equalsIgnoreCase("2")){
				message = "等待部门处理";				
			}else{
				message = "该投诉已完成";	
				
			}
			
 /*           String note = (String) request.getParameter("note");
            String dept = (String) request.getParameter("dept1");
            CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
            cus.setAnotes(note);
            cus.setAstatus("1");
            cus.setAadept(dept);*/
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "反馈结果失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 客服新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CustomerSerEntity customerSer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerSer.getId())) {
			customerSer = customerSerService.getEntity(CustomerSerEntity.class, customerSer.getId());
			req.setAttribute("customerSerPage", customerSer);
		}
		return new ModelAndView("com/jinbo/customer/customerservice/customerSer-add");
	}
	
	/**
	 * 客服编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerSerEntity customerSer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerSer.getId())) {
		
			customerSer = customerSerService.getEntity(CustomerSerEntity.class, customerSer.getId());
			req.setAttribute("customerSerPage", customerSer);
		}
		return new ModelAndView("com/jinbo/customer/customerservice/customerSer-update");
	}
	
	/**
	 * 客服查看回复页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "goQuery")
	public ModelAndView goQuery(CustomerSerEntity customerSer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerSer.getId())) {
			customerSer = customerSerService.getEntity(CustomerSerEntity.class, customerSer.getId());
			req.setAttribute("customerSerPage", customerSer);
		}
		return new ModelAndView("com/jinbo/customer/customerservice/customerSer-query");
	}
	
	
	/**
	 * 加载明细列表[客户查询处理结果]
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList")
	public ModelAndView adviceReplyList(CustomerSerEntity customerSer, HttpServletRequest req) {
	     System.out.println("进入查询回复");
		//===================================================================================
		//获取参数
		Object aORDER0 = customerSer.getAorder();
		//===================================================================================
		//查询-客户查询处理结果
	    String hql0 = "from ServiceReplyEntity e where 1 = 1 AND e.aorder = ? ";
	    try{
	    	List<ServiceReplyEntity> adviceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("adviceReplyList", adviceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/repyservice/kefuxiafa");
	}
	
	/**
	 *回馈客户
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList2")
	public ModelAndView adviceReplyList2(CustomerSerEntity customerSer, HttpServletRequest req) {
	     System.out.println("进入查询回复");
		//===================================================================================
		//获取参数
		Object aORDER0 = customerSer.getAorder();
		//===================================================================================
		//查询-客户查询处理结果
	    String hql0 = "from ServiceReplyEntity e where 1 = 1 AND e.aorder = ? ";
	    try{
	    	List<ServiceReplyEntity> adviceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("adviceReplyList", adviceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/repyservice/adviceReplyList");
	}
	
	
	/**
	 * 批量确认受理投诉单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchConfirm")
	@ResponseBody
	public AjaxJson doBatchConfirm(String ids,HttpServletRequest request){
		long count =1;
		AjaxJson j = new AjaxJson();
		message = "受理单确认成功!";
		try{
			for(String id:ids.split(",")){
				CustomerSerEntity customerSer = systemService.getEntity(CustomerSerEntity.class,
				id
				);
			    if(customerSer.getAstatus().equalsIgnoreCase("0")){
			    	String num = RandomUtils.getUsername(7);
					count = systemService.getCountForJdbc("select count(*) from customer_advice where aorder ='"+num+"'");
				    while(count!=0){
						num = RandomUtils.getUsername(7);
						count = systemService.getCountForJdbc("select count(*) from customer_advice where aorder ='"+num+"'");
				    }
				    customerSer.setSlDatetime(DataUtils.getDate());
				    customerSer.setSlName(ResourceUtil.getSessionUserName().getUserName());
				    customerSer.setAorder(num);
				    customerSer.setAktatus("1");
				    customerSer.setAstatus("1");
				    customerSer.setKefuid(ResourceUtil.getSessionUserName().getId());
					customerSerService.updateEntitie(customerSer);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			    }else{
			    	message = "该投诉已确认，请下发!";
			   	
			    }
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "受理单确认失败!";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
	 /**
		 * 批量取消确认
		 * 
		 * @return
		 */
		 @RequestMapping(params = "doBatchCancel")
		@ResponseBody
		public AjaxJson doBatchCancel(String ids,HttpServletRequest request){
			long count =1;
			AjaxJson j = new AjaxJson();
			message = "受理单取消成功!";
			try{
				for(String id:ids.split(",")){
					CustomerSerEntity customerSer = systemService.getEntity(CustomerSerEntity.class,
					id
					);
				    if(customerSer.getAstatus().equalsIgnoreCase("1")){    	
					    customerSer.setAktatus("0");
					    customerSer.setAstatus("0");
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				    }else if(customerSer.getAstatus().equalsIgnoreCase("0")){
				    	message = "该投诉单还未确认，不能取消";				   	
				    }else{
				    	
				    	message = "该投诉单正在处理，不能取消";		
				    }
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "受理单取消失败!";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
	 
	 
		@RequestMapping(params = "goReturnResult")
		public ModelAndView goReturnResult(CustomerSerEntity customerSer, HttpServletRequest req) {
			if (StringUtil.isNotEmpty(customerSer.getId())) {
				customerSer = customerSerService.getEntity(CustomerSerEntity.class, customerSer.getId());
				req.setAttribute("customerSerPage", customerSer);
			}
			return new ModelAndView("com/jinbo/customer/customerservice/customerSer-return");
		}
		@RequestMapping(params = "goReturnResult2")
		public ModelAndView goReturnResult2(CustomerSerEntity customerSer, HttpServletRequest req) {
			if (StringUtil.isNotEmpty(customerSer.getId())) {
				customerSer = customerSerService.getEntity(CustomerSerEntity.class, customerSer.getId());
				req.setAttribute("customerSerPage", customerSer);
				System.out.println("id是"+customerSer.getId());
			}
			return new ModelAndView("com/jinbo/customer/customerservice/customerSer-return2");
		}

	 /**
		 * 下发投诉单
		 * 
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "returnResult")
		@ResponseBody
		public AjaxJson returnResult(CustomerSerEntity customerSer,CustomerSerPage customerSerPage, HttpServletRequest request) {
			List<ServiceReplyEntity> adviceReplyList =  customerSerPage.getAdviceReplyList();
			AjaxJson j = new AjaxJson();
			message = "投诉单下发成功";
			try{
	            String note = (String) request.getParameter("note");
	            String dept = (String) request.getParameter("dept1");
	            CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
	            cus.setAnotes(note);
	            cus.setAstatus("1");
	            cus.setAadept(dept);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "投诉单下发失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
     
		
		
		/**
		 * 客服添加客户电话投诉
		 * 
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "doAdd")
		@ResponseBody
		public AjaxJson doAdd(CustomerSerEntity customerAdvice,CustomerAdvicePage customerAdvicePage, HttpServletRequest request) {
			//List<ServiceReplyEntity> adviceReplyList =  customerAdvicePage.getAdviceReplyList();
			AjaxJson j = new AjaxJson();
			message = "添加成功";
			try{
				customerAdvice.setCreateDatetime(DataUtils.getDate());
				customerAdvice.setCreateName(ResourceUtil.getSessionUserName().getUserName());
				//需要电话投诉的公司id
				String deptid = customerAdvice.getDeptid();
				TSDepart ts = systemService.getEntity(TSDepart.class, deptid);
				if(ts!=null&&ts.getSource()!=null){					
					CustomerUserEntity cus = systemService.getEntity(CustomerUserEntity.class, ts.getSource());
					if(cus!=null){				
						customerAdvice.setAtel(cus.getTel());
						customerAdvice.setArealname(cus.getRealname());
						
					}
					customerAdvice.setAstatus("0");
					customerAdvice.setAinfo("3");
					customerAdvice.setAktatus("0");
					systemService.save(customerAdvice);	            
					//	customerAdviceService.addMain(customerAdvice, adviceReplyList);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}else{
					
					message = "该需要投诉的公司，未找不到！";
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "客户投诉添加失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
		
		
		/**
		 * 客服修改界面跳转
		 * 
		 * @return
		 */
		@RequestMapping(params = "goUpdateK")
		public ModelAndView goUpdateK(CustomerSerEntity customerAdvice, HttpServletRequest req) {
			if (StringUtil.isNotEmpty(customerAdvice.getId())) {
				customerAdvice = customerSerService.getEntity(CustomerSerEntity.class, customerAdvice.getId());
				if(customerAdvice.getAinfo().equalsIgnoreCase(AINFOSTATUS.电话_客服录入)){
					
					req.setAttribute("customerSerPage", customerAdvice);
					return new ModelAndView("com/jinbo/customer/customerservice/customerSer-updateKe");
				}else{
					return new ModelAndView("com/jinbo/customer/customerservice/jizhiupdate");
				}
			}
			return new ModelAndView("com/jinbo/customer/customerservice/customerSer-updateKe");

		}
		/**
		 * 客服修改自己的投诉单
		 * 
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "doUpdatekefu")
		@ResponseBody
		public AjaxJson doUpdatekefu(CustomerSerEntity customerAdvice,CustomerAdvicePage customerAdvicePage, HttpServletRequest request) {
			List<ServiceReplyEntity> adviceReplyList =  customerAdvicePage.getAdviceReplyList();
			AjaxJson j = new AjaxJson();
			message = "更新成功";
			try{
				CustomerSerEntity old = systemService.getEntity(CustomerSerEntity.class, customerAdvice.getId());
				if(old.getAstatus().equalsIgnoreCase("0")){
					UpdateUtil.update(old, customerAdvice);
				    systemService.updateEntitie(old);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					
				}else{
					message = "客户已受理不能修改";
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "更新客户投诉失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			
			return j;
		}
		
		/**
		 * 预览
		 * 
		 * @return
		 */
		@RequestMapping(params = "preview")
		public ModelAndView preview(CustomerSerEntity customerAdvice,
				HttpServletRequest req) {
		        String nam = customerAdvice.getAfile();
                nam = nam.replace("|", "%");            
				req.setAttribute("customerAdvice", URLDecoder.decode(nam));
			
			return new ModelAndView("com/jinbo/customer/customerservice/Preview");
		}
		
		@RequestMapping(params = "downFile")
		public ModelAndView downFile(String path,
				HttpServletRequest req) {
		  //      String nam = customerAdvice.getAfile();
             //   nam = nam.replace("|", "%");            
			//	req.setAttribute("customerAdvice", URLDecoder.decode(nam));
			
			return new ModelAndView("com/jinbo/customer/customerservice/Preview");
		}
}
