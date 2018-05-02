package com.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;


public class CodeUtil {
	  
		
		public static  File getExcelDemoFile(String fileDir) throws Exception{
			String classDir = null;
			String fileBaseDir = null;
			File file = null;
			classDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			fileBaseDir = classDir.substring(0, classDir.lastIndexOf("classes"));
			
			file = new File(fileBaseDir+fileDir);
			if(!file.exists()){
				throw new Exception("文件不存在");
			}
			return file;
		}
		
		//tomcat配置 - <Context docBase="D:/nwfile/img" path="/img" />
	    public static void SaveFileFromInputStream(MultipartFile pic , String realPath ) throws IOException
	    {      
	    	String osName =  System.getProperty("os.name");
        	String path =  System.getProperty("user.dir")  ;
        	if(osName.toUpperCase().startsWith("MAC")) {
//        		int  splitIndex = System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"));
//        		path = System.getProperty("user.dir").substring(0, splitIndex);
        		path="/Users/wangyoujun/Desktop/smd/WebContent/image";
        	}else{
        		path="D:\\uploadFile";
        	}
        	InputStream stream = pic.getInputStream();
	        FileOutputStream fs=new FileOutputStream( path + "/"+ pic.getOriginalFilename());
	        byte[] buffer =new byte[1024*1024];
	        int bytesum = 0;
	        int byteread = 0; 
	        while ((byteread=stream.read(buffer))!=-1)
	        {
	           bytesum+=byteread;
	           fs.write(buffer,0,byteread);
	           fs.flush();
	        } 
	        fs.close();
	        stream.close();      
	    }
}
