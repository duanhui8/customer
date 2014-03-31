package com.jinbo.customer.controller.deptservice;
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
import org.jeecgframework.core.util.ADVICESTATUS;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UpdateUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.page.deptservice.DeptSerPage;
import com.jinbo.customer.service.deptservice.DeptSerServiceI;

import freemarker.template.utility.DateUtil;

/**   
 * @Title: Controller
 * @Description: 客户投诉
 * @author onlineGenerator
 * @date 2014-03-10 14:03:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/deptSerController")
public class DeptSerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DeptSerController.class);

	@Autowired
	private DeptSerServiceI deptSerService;
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
	@RequestMapping(params = "deptSer")
	public ModelAndView deptSer(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/deptservice/deptSerList");
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
	public void datagrid(CustomerSerEntity deptSer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerSerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, deptSer);
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
		cq.ge("astatus", ADVICESTATUS.等待部门处理);
		cq.le("astatus", ADVICESTATUS.等待反馈结果);
		cq.eq("aadept", ResourceUtil.getSessionUserName().getTSDepart().getId());
		cq.add();
		this.deptSerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客户投诉
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerSerEntity deptSer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		deptSer = systemService.getEntity(CustomerSerEntity.class, deptSer.getId());
		message = "客户投诉删除成功";
		try{
			deptSerService.delete(deptSer);
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
				CustomerSerEntity deptSer = systemService.getEntity(CustomerSerEntity.class,
				id
				);
				deptSerService.delete(deptSer);
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
	public AjaxJson doAdd(CustomerSerEntity deptSer,DeptSerPage deptSerPage, HttpServletRequest request) {
		List<ServiceReplyEntity> deptReplyList =  deptSerPage.getdeptReplyList();
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			deptSerService.addMain(deptSer, deptReplyList);
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
	@RequestMapping(params = "doUpdate")//处理方法
	@ResponseBody
	public AjaxJson doUpdate(CustomerSerEntity deptSer,DeptSerPage deptSerPage, HttpServletRequest request) {
		List<ServiceReplyEntity> deptReplyList =  deptSerPage.getdeptReplyList();
		AjaxJson j = new AjaxJson();
		message = "回复成功";
		try{
			CustomerSerEntity dept = systemService.getEntity(CustomerSerEntity.class, deptSer.getId());
		    if(dept.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待部门处理)){
		    	 dept.setAstatus(ADVICESTATUS.等待反馈结果);
				    dept.setDeDatetime(DataUtils.getDate());
				    dept.setDeName(ResourceUtil.getSessionUserName().getTSDepart().getDepartname());
				    systemService.updateEntitie(dept);
				    ServiceReplyEntity de = new ServiceReplyEntity();
				        de.setAcontent(request.getParameter("acontent"));
		     			de.setCreateDatetime(DataUtils.getDate());
						de.setAorder(dept.getAorder());
						de.setCreateName(ResourceUtil.getSessionUserName().getUserName());
						de.setCreateDept(ResourceUtil.getSessionUserName().getTSDepart().getDepartname());
						deptSerService.save(de);
					
				
		    }else if(dept.getAstatus().equalsIgnoreCase(ADVICESTATUS.等待反馈结果)){
					if(dept.getId()!=null||dept.getId().length()>0){
						List<ServiceReplyEntity> old = systemService.findHql("FROM ServiceReplyEntity e WHERE e.aorder=?", dept.getAorder());
						if(old!=null&&old.size()==1){
							ServiceReplyEntity ol = old.get(0);
							 ol.setAcontent(request.getParameter("acontent"));
							 systemService.updateEntitie(ol);
						}else{
							message = "修改回复失败";
						}
					}
					
		    }
		   
	//		String adviceId = deptSer.getId();
//			ServiceReplyEntity dereply = new ServiceReplyEntity();
//			deptSerService.updateMain(deptSer, deptReplyList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回复客户投诉失败";
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
	public ModelAndView goAdd(CustomerSerEntity deptSer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(deptSer.getId())) {
			deptSer = deptSerService.getEntity(CustomerSerEntity.class, deptSer.getId());
			req.setAttribute("deptSerPage", deptSer);
		}
		return new ModelAndView("com/jinbo/customer/deptservice/deptSer-add");
	}
	
	/**
	 * 客户投诉编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerSerEntity deptSer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(deptSer.getId())) {
			deptSer = deptSerService.getEntity(CustomerSerEntity.class, deptSer.getId());
			List<ServiceReplyEntity> old = systemService.findHql("FROM ServiceReplyEntity e WHERE e.aorder=?", deptSer.getAorder());
            if(old!=null&&old.size()==1){
            	req.setAttribute("deptreply", old.get(0));
            }
			req.setAttribute("deptSerPage", deptSer);
		}
		return new ModelAndView("com/jinbo/customer/deptreply/deptReplyList");
	}
	/**
	 * 客户投诉编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goQuery")
	public ModelAndView goQuery(CustomerSerEntity deptSer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(deptSer.getId())) {
			deptSer = deptSerService.getEntity(CustomerSerEntity.class, deptSer.getId());
			req.setAttribute("deptSerPage", deptSer);
		}
		return new ModelAndView("com/jinbo/customer/deptservice/deptSer-update");
	}
	
	/**
	 * 加载明细列表[部门回复]
	 * 
	 * @return
	 */
	@RequestMapping(params = "deptReplyList")
	public ModelAndView deptReplyList(CustomerSerEntity deptSer, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object aORDER0 = deptSer.getAorder();
		//===================================================================================
		//查询-部门回复
	    String hql0 = "from ServiceReplyEntity e where 1 = 1 AND e.aorder = ? ";
	    try{
	    	List<ServiceReplyEntity> ServiceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("deptReplyList", ServiceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/deptreply/deptReplyList2");
	}
	
}
