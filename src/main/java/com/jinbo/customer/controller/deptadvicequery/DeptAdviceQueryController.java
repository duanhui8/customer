package com.jinbo.customer.controller.deptadvicequery;
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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.page.deptadvicequery.DeptAdviceQueryPage;
import com.jinbo.customer.service.deptadvicequery.DeptAdviceQueryServiceI;

/**   
 * @Title: Controller
 * @Description: 客户投诉
 * @author onlineGenerator
 * @date 2014-03-13 14:26:00
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/deptAdviceQueryController")
public class DeptAdviceQueryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DeptAdviceQueryController.class);

	@Autowired
	private DeptAdviceQueryServiceI deptAdviceQueryService;
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
	@RequestMapping(params = "deptAdviceQuery")
	public ModelAndView deptAdviceQuery(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/deptadvicequery/deptAdviceQueryList");
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
	public void datagrid(CustomerSerEntity deptAdviceQuery,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerSerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, deptAdviceQuery);
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
		cq.eq("astatus", "3");
		cq.add();
		this.deptAdviceQueryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客户投诉
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerSerEntity deptAdviceQuery, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		deptAdviceQuery = systemService.getEntity(CustomerSerEntity.class, deptAdviceQuery.getId());
		message = "客户投诉删除成功";
		try{
			deptAdviceQueryService.delete(deptAdviceQuery);
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
				CustomerSerEntity deptAdviceQuery = systemService.getEntity(CustomerSerEntity.class,
				id
				);
				deptAdviceQueryService.delete(deptAdviceQuery);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
	public AjaxJson doAdd(CustomerSerEntity deptAdviceQuery,DeptAdviceQueryPage deptAdviceQueryPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  deptAdviceQueryPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			deptAdviceQueryService.addMain(deptAdviceQuery, adviceReplyList);
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
	public AjaxJson doUpdate(CustomerSerEntity deptAdviceQuery,DeptAdviceQueryPage deptAdviceQueryPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  deptAdviceQueryPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try{
			deptAdviceQueryService.updateMain(deptAdviceQuery, adviceReplyList);
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
	public ModelAndView goAdd(CustomerSerEntity deptAdviceQuery, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(deptAdviceQuery.getId())) {
			deptAdviceQuery = deptAdviceQueryService.getEntity(CustomerSerEntity.class, deptAdviceQuery.getId());
			req.setAttribute("deptAdviceQueryPage", deptAdviceQuery);
		}
		return new ModelAndView("com/jinbo/customer/deptadvicequery/deptAdviceQuery-add");
	}
	
	/**
	 * 客户投诉编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerSerEntity deptAdviceQuery, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(deptAdviceQuery.getId())) {
			deptAdviceQuery = deptAdviceQueryService.getEntity(CustomerSerEntity.class, deptAdviceQuery.getId());
			req.setAttribute("deptAdviceQueryPage", deptAdviceQuery);
		}
		return new ModelAndView("com/jinbo/customer/deptadvicequery/deptAdviceQuery-update");
	}
	
	
	/**
	 * 加载明细列表[回复]
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList")
	public ModelAndView adviceReplyList(CustomerSerEntity deptAdviceQuery, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object aORDER0 = deptAdviceQuery.getAorder();
		//===================================================================================
		//查询-回复
	    String hql0 = "from ServiceReplyEntity e where 1 = 1 AND e.aorder = ? ";
	    try{
	    	List<ServiceReplyEntity> ServiceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("adviceReplyList", ServiceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/deptadvicequery_reply/adviceReplyList");
	}
	
}
