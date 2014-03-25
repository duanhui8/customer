package com.jinbo.customer.controller.systemreport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
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
import org.jeecgframework.core.constant.Globals;
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
 * @Description: 系统报表
 * @author onlineGenerator
 * @date 2014-03-18 10:47:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/systemReportController")
public class SystemReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemReportController.class);

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
	 * 客户评价
	 * 
	 * @return
	 */
	@RequestMapping(params = "goEvaluate")
	public ModelAndView goEvaluate(CustomerSerEntity customerQueryController, HttpServletRequest req) {
	    	DecimalFormat df = new DecimalFormat("0.00");		
			Long con = systemService.getCountForJdbc("select count(deptpj) from evaluate where deptpj is not null");
			double feichang = 0,manyi=0,yiban=0,jiaocha=0,hencha=0;
		    if(con!=null&&con!=0){
		    	List<EvaluateEntity> lists = systemService.findHql("FROM EvaluateEntity e WHERE e.deptpj !=null");
				for(EvaluateEntity en : lists){
	            	String pj = en.getDeptpj();
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
				System.out.println(df.format(yiban/cont));
				msp.put("jiaocha", Double.parseDouble(df.format(jiaocha/cont))*100);
				msp.put("hencha",Double.parseDouble(df.format(hencha/cont))*100);
				req.setAttribute("ping1", msp);
				Map<String,Double> map = fillKefu();
				if(map!=null){
					req.setAttribute("ping", map);
				}
		    }else{
		    	return new ModelAndView("com/jinbo/customer/customerquery/nulleva");
		    }
			
			
			/*			customerQueryController = customerQueryControllerService.getEntity(CustomerSerEntity.class, customerQueryController.getId());
			 */
		
		return new ModelAndView("com/jinbo/customer/systemreport/customerReport");
	}
	private Map<String, Double> fillKefu() {
		DecimalFormat df = new DecimalFormat("0.00");		
		Long con = systemService.getCountForJdbc("select count(kefupj) from evaluate where kefupj is not null");
		double feichang = 0,manyi=0,yiban=0,jiaocha=0,hencha=0;
	    if(con!=null&&con!=0){
	    	List<EvaluateEntity> lists = systemService.findHql("FROM EvaluateEntity e WHERE e.kefupj !=null");
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
		return msp;
	}else{
		Map<String,Double> msp = new HashMap<String, Double>();
		msp.put("feichang", 0.0);
		msp.put("manyi", 0.0);
		msp.put("yiban", 100.0);
		msp.put("jiaocha", 0.0);
		msp.put("hencha",0.0);
		return null;
	}
	
	
	    
}
	
	

	/**
	 * 部门排行
	 * 
	 * @return
	 */
	@RequestMapping(params = "goEvaluate2")
	public ModelAndView goEvaluate2(CustomerSerEntity customerQueryController, HttpServletRequest req) {
	    	    Map<String,Double> ma = MaxDept();
	    	    Map<String,Double> min = MaxDept();
		//		req.setAttribute("ping1", msp);
				Map<String,Double> map = fillKefu();

					req.setAttribute("ping", map);
		
		    //	return new ModelAndView("com/jinbo/customer/customerquery/nulleva");
		    
			
			
			/*			customerQueryController = customerQueryControllerService.getEntity(CustomerSerEntity.class, customerQueryController.getId());
			 */
		
		return new ModelAndView("com/jinbo/customer/systemreport/deptpai");
	}

	private Map<String, Double> MaxDept() {
		DecimalFormat df = new DecimalFormat("0.00");		
		List<EvaluateEntity> ens = systemService.findByQueryString("From EvaluateEntity e WHERE e.deptpj!=null and e.deptpj='0' or e.deptpj='1'");
		double feichang = 0,manyi=0,yiban=0,jiaocha=0,hencha=0;
		Map<String,Integer>  deptor = new HashMap<String,Integer>();
	    for(EvaluateEntity en : ens){
	    	Integer in = deptor.get(en.getDepid());
	    	if(in==null||in==0){
	    		in=1;
	    		deptor.put(en.getDepid(), in);
	    	}else{
	    		++in;
	    		deptor.put(en.getDepid(), in);
	    	}
	    	}
	    TreeMap<String, Integer> map = new TreeMap<String, Integer>(deptor);
	     Set<Entry<String,Integer>> sets =  map.entrySet();
	    for(Entry en : sets){
	    	System.out.println(en.getKey()+":"+en.getValue());
	    }
	
/*		if(con!=null&&con!=0){
	    	List<EvaluateEntity> lists = systemService.findHql("FROM EvaluateEntity e WHERE e.deptpj !=null");
			for(EvaluateEntity en : lists){
            	String pj = en.getDeptpj();
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
            }*/
/*			double cont  = con;
			Map<String,Double> msp = new HashMap<String, Double>();
			msp.put("feichang", Double.parseDouble(df.format(feichang/cont))*100);
			msp.put("manyi", Double.parseDouble(df.format(manyi/cont))*100);
			msp.put("yiban", Double.parseDouble(df.format(yiban/cont))*100);
			System.out.println(df.format(yiban/cont));
			msp.put("jiaocha", Double.parseDouble(df.format(jiaocha/cont))*100);
			msp.put("hencha",Double.parseDouble(df.format(hencha/cont))*100);*/
		return null;
	}
}
