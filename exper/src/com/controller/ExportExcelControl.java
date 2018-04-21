package com.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.ExportExcelUtil;
import com.pojo.ClassInfo;
import com.pojo.OptionInfo;
import com.pojo.User;
import com.service.ClassServiceImpl;
import com.service.OptionServiceImpl;
import com.service.UserServiceImpl;

 
@Controller
@RequestMapping("/excel")  
public class ExportExcelControl {
	
	@Resource(name="classServiceImpl")
	private ClassServiceImpl classService;
	
	@Resource(name="optionServiceImpl")
	private OptionServiceImpl optionService;
	
	@Resource(name="userServiceImpl")
	private UserServiceImpl userService;
	
	@RequestMapping("hello")
	public String getUpoadPage(){
		return "hello";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/export",method={RequestMethod.GET,RequestMethod.POST})
	public  String  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OutputStream os = null;  
		Workbook wb = null;    
		
		try {
			Map params = new HashMap();
			List<OptionInfo> results = this.optionService.findOptionWhereSql(params);
			for(OptionInfo info : results  ) {
				  ClassInfo  classInfo =  classService.getClassById(info.getClassId());
				  info.setClassInfo(classInfo);
				  if(!StringUtils.isEmpty(classInfo.getTeacherId())) {
						User user =  userService.getUserById(classInfo.getTeacherId());
						classInfo.setUser(user);
					}
				  User user =  userService.getUserById(info.getUserId());
				  info.setUser(user);
			}
			String realPath=request.getServletContext().getRealPath("");
			ExportExcelUtil util = new ExportExcelUtil();
			File file =util.getExcelDemoFile("/课程安排.xlsx" , realPath );
			String sheetName="sheet1";  
			wb = util.writeNewExcel(file, sheetName,results); 
			
			String fileName="实验课程信息.xlsx";
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
		    os = response.getOutputStream();
			wb.write(os);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			os.flush();
			os.close();
			wb.close();
		} 
		return null;
	}


}
