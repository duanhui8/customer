package org.jeecgframework.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownUitls {
	/**
	 * 附件预览读取
	 * 
	 * @return
	 */
	@Autowired
	private SystemService systemService;
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public void viewFile(String realPath,String title,String extend,HttpServletRequest request, HttpServletResponse response) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile.setExtend(extend);
		uploadFile.setTitleField(title);
		uploadFile.setRealPath(realPath);
		//uploadFile.setView(true);
		systemService.viewOrDownloadFile(uploadFile);
	}
}
