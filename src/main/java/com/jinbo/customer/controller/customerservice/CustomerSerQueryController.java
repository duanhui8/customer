package com.jinbo.customer.controller.customerservice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
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
import org.jeecgframework.core.common.model.json.Highchart;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import com.jinbo.customer.entity.customerservice.CustomerSerEntity;
import com.jinbo.customer.entity.customerservice.EvaluateEntity;
import com.jinbo.customer.entity.customerservice.ServiceReplyEntity;
import com.jinbo.customer.service.customerservice.CustomerSerServiceI;
/**   
 * @Title: Controller
 * @Description: 客服查询
 * @author onlineGenerator
 * @date 2014-03-18 11:22:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/customerSerQueryController")
public class CustomerSerQueryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerSerQueryController.class);

	@Autowired
	private CustomerSerServiceI customerQueryControllerService;
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
	 * 客服查询列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "customerQueryController")
	public ModelAndView customerQueryController(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/customerquery/customerQuery");
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
	public void datagrid(CustomerSerEntity customerQueryController,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		System.out.println("进入查询列表");
		CriteriaQuery cq = new CriteriaQuery(CustomerSerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerQueryController);
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
		cq.eq("astatus", "4");
		cq.eq("slName", ResourceUtil.getSessionUserName().getUserName());
		cq.add();
		this.customerQueryControllerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客服查询
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(CustomerSerEntity customerQueryController, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		customerQueryController = systemService.getEntity(CustomerSerEntity.class, customerQueryController.getId());
		message = "客服查询删除成功";
		try{
			customerQueryControllerService.delete(customerQueryController);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客服查询删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除客服查询
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "客服查询删除成功";
		try{
			for(String id:ids.split(",")){
				CustomerSerEntity customerQueryController = systemService.getEntity(CustomerSerEntity.class,
				id
				);
				customerQueryControllerService.delete(customerQueryController);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "客服查询删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加客服查询
	 * 
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "doAdd")
//	@ResponseBody
//	public AjaxJson doAdd(CustomerSerEntity customerQueryController,CustomerQueryControllerPage customerQueryControllerPage, HttpServletRequest request) {
//		List<AdviceReplyEntity> adviceReplyList =  customerQueryControllerPage.getAdviceReplyList();
//		AjaxJson j = new AjaxJson();
//		message = "添加成功";
//		try{
//			customerQueryControllerService.addMain(customerQueryController, adviceReplyList);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "客服查询添加失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}
	/**
	 * 更新客服查询
	 * 
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "doUpdate")
//	@ResponseBody
//	public AjaxJson doUpdate(CustomerQueryControllerEntity customerQueryController,CustomerQueryControllerPage customerQueryControllerPage, HttpServletRequest request) {
//		List<AdviceReplyEntity> adviceReplyList =  customerQueryControllerPage.getAdviceReplyList();
//		AjaxJson j = new AjaxJson();
//		message = "更新成功";
//		try{
//			customerQueryControllerService.updateMain(customerQueryController, adviceReplyList);
//			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "更新客服查询失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}

	/**
	 * 客服查询新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(CustomerSerEntity customerQueryController, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerQueryController.getId())) {
			customerQueryController = customerQueryControllerService.getEntity(CustomerSerEntity.class, customerQueryController.getId());
			req.setAttribute("customerQueryControllerPage", customerQueryController);
		}
		return new ModelAndView("com/jinbo/customer/customerservice/customerQueryController-add");
	}
	
	/**
	 * 评价
	 * 
	 * @return
	 */
	@RequestMapping(params = "goEvaluate")
	public ModelAndView goEvaluate(CustomerSerEntity customerQueryController, HttpServletRequest req) {
	    	DecimalFormat df = new DecimalFormat("0.00");		
			Long con = systemService.getCountForJdbc("select count(kefupj) from evaluate where kefuid ='"+ResourceUtil.getSessionUserName().getId()+"'");
			double feichang = 0,manyi=0,yiban=0,jiaocha=0,hencha=0;
		    if(con!=null&&con!=0){
		    	List<EvaluateEntity> lists = systemService.findHql("FROM EvaluateEntity e WHERE e.kefuid =?",ResourceUtil.getSessionUserName().getId());
				for(EvaluateEntity en : lists){
	            	String pj = en.getKefupj();
	            	if(pj.equalsIgnoreCase("0")){
	            		feichang++;
	            	}else if(pj.equalsIgnoreCase("1")){           
	            		manyi++;
	            		
	            	}else if(pj.equalsIgnoreCase("2")){
	            		yiban++;
	            		
	            	}else if(pj.equalsIgnoreCase("3")){
	            		jiaocha++;
	            		
	            	}else if(pj.equalsIgnoreCase("4")){
	            		hencha++;
	            	}
	            }
				double cont  = con;
				Map<String,Double> msp = new HashMap<String, Double>();
				msp.put("feichang", Double.parseDouble(df.format(feichang/cont))*100);
				msp.put("manyi", Double.parseDouble(df.format(manyi/cont))*100);
				msp.put("yiban", Double.parseDouble(df.format(yiban/cont))*100);
				msp.put("jiaocha", Double.parseDouble(df.format(jiaocha/cont))*100);
				msp.put("hencha",Double.parseDouble(df.format(hencha/cont))*100);
				req.setAttribute("ping", msp);
		    }else{
		    	return new ModelAndView("com/jinbo/customer/customerquery/nulleva");
		    }
			
			
			/*			customerQueryController = customerQueryControllerService.getEntity(CustomerSerEntity.class, customerQueryController.getId());
			 */
		
		return new ModelAndView("com/jinbo/customer/customerquery/myevaluate");
	}
			
	
	
	/**
	 * 客服查询编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(CustomerSerEntity customerQueryController, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerQueryController.getId())) {
			customerQueryController = customerQueryControllerService.getEntity(CustomerSerEntity.class, customerQueryController.getId());
			req.setAttribute("customerQueryControllerPage", customerQueryController);
		}
		return new ModelAndView("com/jinbo/customer/customerquery/customerQuery-update");
	}
	
	
	/**
	 * 加载明细列表[客服查看]
	 * 
	 * @return
	 */
	@RequestMapping(params = "adviceReplyList")
	public ModelAndView adviceReplyList(CustomerSerEntity customerQueryController, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object aORDER0 = customerQueryController.getAorder();
		//===================================================================================
		//查询-客服查看
	    String hql0 = "from ServiceReplyEntity e where 1 = 1 AND e.aorder = ? ";
	    try{
	    	List<ServiceReplyEntity> adviceReplyEntityList = systemService.findHql(hql0,aORDER0);
			req.setAttribute("adviceReplyList", adviceReplyEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jinbo/customer/customerquery/adviceReplyList");
	}
	
}
