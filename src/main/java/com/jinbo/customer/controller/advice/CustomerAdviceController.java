package com.jinbo.customer.controller.advice;
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
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.jinbo.customer.entity.advice.CustomerAdviceEntity;
import com.jinbo.customer.page.advice.CustomerAdvicePage;
import com.jinbo.customer.service.advice.CustomerAdviceServiceI;
import com.jinbo.customer.entity.replyadvice.AdviceReplyEntity;
/**   
 * @Title: Controller
 * @Description: 客户投诉
 * @author onlineGenerator
 * @date 2014-03-06 17:09:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/customerAdviceController")
public class CustomerAdviceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerAdviceController.class);

	@Autowired
	private CustomerAdviceServiceI customerAdviceService;
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
	 * 客户投诉列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "customerAdvice")
	public ModelAndView customerAdvice(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/advice/customerAdviceList");
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
	public void datagrid(CustomerAdviceEntity customerAdvice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerAdviceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerAdvice);
		try{
		//自定义追加查询条件
		cq.addOrder("createDatetime",SortDirection.desc);
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
		this.customerAdviceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客户投诉
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerAdviceEntity customerAdvice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		customerAdvice = systemService.getEntity(CustomerAdviceEntity.class, customerAdvice.getId());
		message = "客户投诉删除成功";
		try{
			if(!customerAdvice.getAstatus().equalsIgnoreCase("0")){
				message="该投诉信息客服已受理，不能删除。";
				j.setMsg(message);
				return j;
				
			}
			customerAdviceService.delete(customerAdvice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户投诉删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除客户投诉
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "客户投诉删除成功";
		try{
			for(String id:ids.split(",")){
				CustomerAdviceEntity customerAdvice = systemService.getEntity(CustomerAdviceEntity.class,
				id
				);
				if(customerAdvice.getAorder()==null||customerAdvice.getAorder().equalsIgnoreCase("")||customerAdvice.getAorder().length()<=0){					
					customerAdviceService.delete(customerAdvice);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}else{
					message = "删除失败,该投诉信息正被处理中..";
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "客户投诉删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加客户投诉
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(CustomerAdviceEntity customerAdvice,CustomerAdvicePage customerAdvicePage, HttpServletRequest request) {
		//List<AdviceReplyEntity> adviceReplyList =  customerAdvicePage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			customerAdvice.setCreateDatetime(DataUtils.getDate());
			customerAdvice.setCreateName(ResourceUtil.getSessionUserName().getUserName());
			customerAdvice.setAstatus("0");
			customerAdvice.setAinfo("0");
			customerAdvice.setAktatus("0");
			customerAdvice.setAzhtype(ResourceUtil.getSessionUserName().getUserName());
            systemService.save(customerAdvice);
            
		//	customerAdviceService.addMain(customerAdvice, adviceReplyList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户投诉添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新客户投诉
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CustomerAdviceEntity customerAdvice,CustomerAdvicePage customerAdvicePage, HttpServletRequest request) {
		List<AdviceReplyEntity> adviceReplyList =  customerAdvicePage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try{
			customerAdviceService.updateMain(customerAdvice, adviceReplyList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新客户投诉失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 客户投诉新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CustomerAdviceEntity customerAdvice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerAdvice.getId())) {
			customerAdvice = customerAdviceService.getEntity(CustomerAdviceEntity.class, customerAdvice.getId());
			req.setAttribute("customerAdvicePage", customerAdvice);
		}
		return new ModelAndView("com/jinbo/customer/advice/customerAdvice-add");
	}
	
	/**
	 * 客户投诉编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerAdviceEntity customerAdvice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerAdvice.getId())) {
			customerAdvice = customerAdviceService.getEntity(CustomerAdviceEntity.class, customerAdvice.getId());
			req.setAttribute("customerAdvicePage", customerAdvice);
		}
		return new ModelAndView("com/jinbo/customer/advice/customerAdvice-update");
	}
	
	
	/**
	 * 加载明细列表[回复投诉]
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList")
	public ModelAndView adviceReplyList(CustomerAdviceEntity customerAdvice, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object aORDER0 = customerAdvice.getAorder();
		//===================================================================================
		//查询-回复投诉
	    String hql0 = "from AdviceReplyEntity where 1 = 1 AND aORDER = ? ";
	    try{
	    	List<AdviceReplyEntity> adviceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("adviceReplyList", adviceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/replyadvice/adviceReplyList");
	}
	
}
