package com.jinbo.customer.controller.user;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelExportOfTemplateUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.demo.entity.test.CourseEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.jinbo.customer.entity.customerservice.CustomerUserEntity;
import com.jinbo.customer.service.user.CustomerUserServiceI;

/**   
 * @Title: Controller
 * @Description: 客户
 * @author zhangdaihao
 * @date 2014-03-05 15:51:19
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/customerUserController")
public class CustomerUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerUserController.class);

	@Autowired
	private CustomerUserServiceI customerUserService;
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
	 * 客户列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "customerUser")
	public ModelAndView customerUser(HttpServletRequest request) {
		return new ModelAndView("com/jinbo/customer/user/customerUserList");
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
	public void datagrid(CustomerUserEntity customerUser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CustomerUserEntity.class, dataGrid);
		cq.addOrder("createDatetime",SortDirection.desc);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, customerUser, request.getParameterMap());
		this.customerUserService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客户
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CustomerUserEntity customerUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		customerUser = systemService.getEntity(CustomerUserEntity.class, customerUser.getId());
		message = "客户删除成功";
		try {
			deleToDepart(customerUser);
			customerUserService.delete(customerUser);
		} catch (Exception e) {
			e.printStackTrace();
			message="客户删除失败，请检查该公司是否存在用户";
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

	
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加客户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CustomerUserEntity customerUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(customerUser.getId())) {
			message = "客户更新成功";
			CustomerUserEntity t = customerUserService.get(CustomerUserEntity.class, customerUser.getId());
			try {
				if(!t.getUsername().equalsIgnoreCase(customerUser.getUsername())){
					Long num = customerUserService.getCountForJdbc("select count(*) from customer_user where username='"+customerUser.getUsername()+"'");
					if(num!=0){						
						message = "客户更新失败,用户名"+customerUser.getUsername()+"已存在!";
						j.setMsg(message);
						return j;
					}
					
				}
				MyBeanUtils.copyBeanNotNull2Bean(customerUser, t);
				t.setUpdateDatetime(DataUtils.getDate());
				t.setUpdateName(ResourceUtil.getSessionUserName().getUserName());
				try {
					copyToDepart(customerUser);
					customerUserService.saveOrUpdate(t);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(ExceptionUtil.getExceptionMessage(e));
					message="更新级联部门表失败!";					
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
				message = "客户更新失败";
			}
		} else {
			Long num = customerUserService.getCountForJdbc("select count(*) from customer_user where username='"+customerUser.getUsername()+"'");
			if(num!=0){
				message = "用户名"+customerUser.getUsername()+"已存在!";
			}else{
			message = "客户添加成功";
			customerUser.setCreateDatetime(DataUtils.getDate());
			customerUser.setCreateName(ResourceUtil.getSessionUserName().getUserName());
			customerUserService.save(customerUser);
            TSDepart ts = new TSDepart();
            ts.setSource(customerUser.getId());
            ts.setDescription(customerUser.getUsername());
            ts.setDepartname(customerUser.getRealname());
            systemService.save(ts);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 客户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CustomerUserEntity customerUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(customerUser.getId())) {
			customerUser = customerUserService.getEntity(CustomerUserEntity.class, customerUser.getId());
			req.setAttribute("customerUserPage", customerUser);
		}
		return new ModelAndView("com/jinbo/customer/user/customerUser");
	}
	
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public void exportXlsByT(CourseEntity course,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "客户模板";
			// 根据浏览器进行转码，使其支持中文文件名
			if (BrowserUtils.isIE(request)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(codedFileName,
										"UTF-8") + ".xls");//这个地方要修改成和模板一样的文件类型
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			
			// 产生工作簿对象
			Workbook workbook = null;
			CriteriaQuery cq = new CriteriaQuery(CustomerUserEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, course, request.getParameterMap());
			List<CustomerUserEntity> courses = null;//this.customerUserService.getListByCriteriaQuery(cq,false);
			Map<String,Object> map = new HashMap<String, Object>();
			workbook = ExcelExportOfTemplateUtil.exportExcel(new TemplateExportParams("export/template/客户导入模板.xls"),
					CustomerUserEntity.class, courses,map);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	/**
	 * 转入导入视图
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/jinbo/customer/user/customerUpload");
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(0);
			params.setSecondTitleRows(2);
			params.setNeedSave(true);
			try {
				List<CustomerUserEntity> listCourses = 
					(List<CustomerUserEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),CustomerUserEntity.class,params);
				for (CustomerUserEntity course : listCourses) {
					if(course.getUsername()!=null){
						customerUserService.save(course);
					}
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	/**
	 * 
	 * 导出excel
	 * @param course
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(CourseEntity course,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "客户资料导出";
			// 根据浏览器进行转码，使其支持中文文件名
			if (BrowserUtils.isIE(request)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(codedFileName,
										"UTF-8") + ".xls");//这个地方要修改成和模板一样的文件类型
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			
			// 产生工作簿对象
			Workbook workbook = null;
			CriteriaQuery cq = new CriteriaQuery(CustomerUserEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, course, request.getParameterMap());
			List<CustomerUserEntity> courses = this.customerUserService.getListByCriteriaQuery(cq,false);
			Map<String,Object> map = new HashMap<String, Object>();
			workbook = ExcelExportOfTemplateUtil.exportExcel(new TemplateExportParams("export/template/客户导入模板.xls"),
					CustomerUserEntity.class, courses,map);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				
			}
		}
	}
	public void copyToDepart(CustomerUserEntity customerUser) throws Exception{
	
		  List<TSDepart> departs = systemService.findHql("From TSDepart WHERE 1=1 AND source =?", customerUser.getId());	
	      if(departs.size()>1){
            throw new Exception();
	      }
		  try {
			TSDepart ts = departs.get(0);	      
			  ts.setDescription(customerUser.getUsername());
			  ts.setDepartname(customerUser.getRealname());
			  systemService.updateEntitie(ts);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ExceptionUtil.getExceptionMessage(e));
			throw e;
		}

	}
	public void deleToDepart(CustomerUserEntity customerUser) throws Exception{
		  List<TSDepart> departs = systemService.findHql("From TSDepart WHERE 1=1 AND source =?", customerUser.getId());	
	      if(departs.size()>1){
	    	  throw new Exception();
	      }
	      try {
			systemService.delete(departs.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		
	}
}
