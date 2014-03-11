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
import com.jinbo.customer.page.customerservice.CustomerSerPage;
import com.jinbo.customer.service.customerservice.CustomerSerServiceI;
import com.jinbo.customer.entity.repyservice.ServiceReplyEntity;
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
	 * 更新客服
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CustomerSerEntity customerSer,CustomerSerPage customerSerPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  customerSerPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try{
			customerSerService.updateMain(customerSer, adviceReplyList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新客服失败";
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
	 * 加载明细列表[客户查询处理结果]
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList")
	public ModelAndView adviceReplyList(CustomerSerEntity customerSer, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object aORDER0 = customerSer.getAorder();
		//===================================================================================
		//查询-客户查询处理结果
	    String hql0 = "from AdviceReplyEntity where 1 = 1 AND aORDER = ? ";
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
			    if(customerSer.getAorder()==null||customerSer.getAorder().equalsIgnoreCase("")||customerSer.getAorder().length()<=0){
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
}
