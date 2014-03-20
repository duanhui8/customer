package com.jinbo.customer.controller.customerservice;
import java.util.List;
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
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.RandomUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.page.customerservice.CustomerSerPage;
import com.jinbo.customer.service.customerservice.CustomerSerServiceI;
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
		cq.le("astatus", "2");
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
		customerSer = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
		message = "客服删除成功";
		try{
			customerSerService.delete(customerSer);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客服删除失败";
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
            String note = (String) request.getParameter("note");
            String dept = (String) request.getParameter("dept1");
            CustomerSerEntity cus = systemService.getEntity(CustomerSerEntity.class, customerSer.getId());
            if(cus.getAktatus().equalsIgnoreCase("0")){
            	message = "请先确认!";
            }else if(cus.getAktatus().equalsIgnoreCase("1")){            	
            	cus.setAnotes(note);
            	cus.setAstatus("2");
            	cus.setAadept(dept);          	
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
					cus.setAstatus("3");
					cus.setComDatetime(DataUtils.getDate());
					cus.setComName(ResourceUtil.getSessionUserName().getUserName());
				}else if(returnS.equalsIgnoreCase("2")){
					message = "短信反馈结果成功";
					
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
		return new ModelAndView("com/jinbo/customer/repyservice/adviceReplyList2");
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

	 /**
		 * 更新客服
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
     
}
