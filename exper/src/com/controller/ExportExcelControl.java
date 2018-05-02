package com.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public  String  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response,String className) throws Exception {
		
		OutputStream os = null;  
		Workbook wb = null;    
		
		try {
			Map params = new HashMap();
			params.put("className", className);
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

	@RequestMapping("/testHttpMessageDown")
	public ResponseEntity<byte[]> download(HttpServletRequest request,String fileName) throws IOException {
		String osName =  System.getProperty("os.name");
	    	String path =  System.getProperty("user.dir")  ;
	    	if(osName.toUpperCase().startsWith("MAC")) {
	//    		int  splitIndex = System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"));
	//    		path = System.getProperty("user.dir").substring(0, splitIndex);
	    		path="/Users/wangyoujun/Desktop/smd/WebContent/image/";
	    	}else{
	    		path="D:\\uploadFile\\";
	    	}
		File file = new File(path +  fileName);
	    byte[] body = null;
	    InputStream is = new FileInputStream(file);
	    body = new byte[is.available()];
	    is.read(body);
	    HttpHeaders headers = new HttpHeaders();
	    System.out.println(file.getName());
	    fileName = processFileName(request, fileName);
	    headers.add("Content-Disposition", "attchement; filename=" + fileName);
	    HttpStatus statusCode = HttpStatus.OK;
	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
	    return entity;
	}
	
	public static String processFileName(HttpServletRequest request, String fileNames) {
		   String codedfilename = null;
		   try {
		      String agent = request.getHeader("USER-AGENT");
		      if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
		            && -1 != agent.indexOf("Trident")) {// ie
		         String name = java.net.URLEncoder.encode(fileNames, "UTF8");
		         codedfilename = name;
		      } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
		         codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
		      }
		   } catch (Exception e) {
		      e.printStackTrace();
		   }
		   return codedfilename;
		}

		//ｈｔｔｐＣｏｎｖｅｒＢｙｔｅｓ　方法
		public static byte[] httpConverBytes(String path,HttpServletRequest request) {
		   BufferedInputStream in = null;
		   ByteArrayOutputStream out = null;
		   URLConnection conn = null;
		   int httpResult=0;
		   try {
		      StringBuffer sb = new StringBuffer();
		      for(int i=0;i<path.length();i++){
		         char a=path.charAt(i);//ｕｒｌ路径的中文部分重新编码　很重要
		         if(a>127){//将中文UTF-8编码
		            sb.append(URLEncoder.encode(String.valueOf(a), "utf-8"));
		         }else{
		            sb.append(String.valueOf(a));
		         }
		      }
		      URL url = new URL(sb.toString()); //创建URL
		      URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码urlconn.connect(); 
		      HttpURLConnection httpconn = (HttpURLConnection) urlconn;
		      httpResult = httpconn.getResponseCode();
		      in = new BufferedInputStream(httpconn.getInputStream());

		      if (httpResult != HttpURLConnection.HTTP_OK){  //不等于HTTP_OK说明连接不成功
		         System.out.print("连接失败！");
		      }else {
		         out = new ByteArrayOutputStream(1024);
		         byte[] temp = new byte[1024];
		         int size = 0;
		         while ((size = in.read(temp)) != -1) {
		            out.write(temp, 0, size);
		         }
		         byte[] content = out.toByteArray();
		         return content;
		      }
		   } catch (Exception e) {
		      e.printStackTrace();
		   }
		   finally {
		      try {
		         in.close();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		      try {
		         out.close();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		   }
		   return null;
		}
}
