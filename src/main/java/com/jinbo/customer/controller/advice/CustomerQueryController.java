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
import org.jeecgframework.core.util.ADVICESTATUS;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.EvaluateEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.service.advice.CustomerAdviceServiceI;

/**   
 * @Title: Controller
 * @Description: 查询
 * @author onlineGenerator
 * @date 2014-03-18 10:47:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/customerQueryController")
public class CustomerQueryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerQueryController.class);

	@Autowired
	private CustomerAdviceServiceI customerQueryService;
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
	 * 查询列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "customerQuery")
	public ModelAndView customerQuery(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/selfquery/customerQueryList");
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
	public void datagrid(CustomerSerEntity customerQuery,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerSerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerQuery);
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
		cq.eq("astatus", ADVICESTATUS.已完成);
		cq.add();
		this.customerQueryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除查询
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerSerEntity customerQuery, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		customerQuery = systemService.getEntity(CustomerSerEntity.class, customerQuery.getId());
		message = "查询删除成功";
		try{
			customerQueryService.delete(customerQuery);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "查询删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除查询
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "查询删除成功";
		try{
			for(String id:ids.split(",")){
				CustomerSerEntity customerQuery = systemService.getEntity(CustomerSerEntity.class,
				id
				);
				customerQueryService.delete(customerQuery);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "查询删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
		/**
		 * 查询是否评价
		 * 
		 * @return
		 */
		 @RequestMapping(params = "doQueryPing")
		@ResponseBody
		public AjaxJson doQuery(String ids,HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			message = "0";
			try{
				String id = request.getParameter("id");
				//获取评价实体
				List<Object> ev = (List<Object>) systemService.findHql("FROM EvaluateEntity e WHERE 1=1 AND e.advice_id=?", id);//systemService.getEntity(EvaluateEntity.class, id);
				CustomerSerEntity en = systemService.getEntity(CustomerSerEntity.class, id);
				// 判断投诉是否完成
				if(en.getAstatus().equalsIgnoreCase("4")){
					//判断投诉关联的评价
					if(ev.size()>0){
						if(ev.size()==1){			
							if(((EvaluateEntity) ev.get(0)).getDeptpj()==null||((EvaluateEntity) ev.get(0)).getKefupj()==null){
							message = "1";
							}
						}
					}else{
						
						message = "1";
					}
					
				}
		//		systemService.getEntity(entityName, id)
			}catch(Exception e){
				e.printStackTrace();
				message = "2";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}

	/**
	 * 添加查询
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doSaveEval")
	@ResponseBody
	public AjaxJson doSaveEval(EvaluateEntity evaluateEntity,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "评价成功";
		try{
	        System.out.println("部门的评价是;"+evaluateEntity.getDeptpj());	        
	        EvaluateEntity ev = new EvaluateEntity();
	        ev.setCreateDatetime(DataUtils.getDate());
	        ev.setCreateName(ResourceUtil.getSessionUserName().getUserName());
	        ev.setAdvice_id(evaluateEntity.getAdvice_id());
	        ev.setDeptpj(evaluateEntity.getDeptpj());
	        ev.setKefupj(evaluateEntity.getKefupj());
	        ev.setNote(evaluateEntity.getNote());
	        ev.setUserid(ResourceUtil.getSessionUserName().getId());
	        CustomerSerEntity cus =  systemService.getEntity(CustomerSerEntity.class, evaluateEntity.getAdvice_id());
	        ev.setKefuid(cus.getKefuid());
	        ev.setDepid(cus.getAadept());

	   //     ev.setKefuid(cus.get);
	        systemService.save(ev);
			//systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "评价失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新查询
	 * 
	 * @param ids
	 * @return
	 */
	/*@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(CustomerSerEntity customerQuery,CustomerQueryPage customerQueryPage, HttpServletRequest request) {
		List<ServiceReplyEntity> adviceReplyList =  customerQueryPage.getAdviceReplyList();
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try{
			customerQueryService.updateMain(customerQuery, adviceReplyList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新查询失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
*/
	/**
	 * 查询新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CustomerSerEntity customerQuery, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerQuery.getId())) {
			customerQuery = customerQueryService.getEntity(CustomerSerEntity.class, customerQuery.getId());
			req.setAttribute("customerQueryPage", customerQuery);
		}
		return new ModelAndView("com/jinbo/customer/selfquery/customerQuery-add");
	}
	
	/**
	 * 查询新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goEva")
	public ModelAndView goEva(CustomerSerEntity customerQuery, HttpServletRequest req) {
		return new ModelAndView("com/jinbo/customer/selfquery/evaluate");
	}
	
	/**
	 * 查询编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerSerEntity customerQuery, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerQuery.getId())) {
			customerQuery = customerQueryService.getEntity(CustomerSerEntity.class, customerQuery.getId());
			req.setAttribute("customerQueryPage", customerQuery);
		}
		return new ModelAndView("com/jinbo/customer/selfquery/customerQuery-update");
	}
	
	
	/**
	 * 加载明细列表[selfqueryreply]
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList")
	public ModelAndView adviceReplyList(CustomerSerEntity customerQuery, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object aORDER0 = customerQuery.getAorder();
		//===================================================================================
		//查询-selfqueryreply
	    String hql0 = "from ServiceReplyEntity e where 1 = 1 AND e.aorder = ? ";
	    try{
	    	List<ServiceReplyEntity> adviceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("adviceReplyList", adviceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/selfquery/adviceReplyList");
	}
	
}
