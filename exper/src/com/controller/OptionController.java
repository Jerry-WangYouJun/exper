package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.common.CodeUtil;
import com.common.DateUtils;
import com.core.model.Grid;
import com.github.pagehelper.PageHelper;
import com.pojo.ClassInfo;
import com.pojo.OptionInfo;
import com.pojo.User;
import com.service.ClassServiceImpl;
import com.service.OptionServiceImpl;
import com.service.UserServiceImpl;

@Controller
@RequestMapping("/option")
public class OptionController {
	
	@Resource(name="classServiceImpl")
	private ClassServiceImpl classService;
	
	@Resource(name="optionServiceImpl")
	private OptionServiceImpl optionService;
	
	@Resource(name="userServiceImpl")
	private UserServiceImpl userService;
	
	@ResponseBody
	@RequestMapping("/list")
	public Grid queryList(HttpServletRequest request,Model model){
		
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String optionName = request.getParameter("optionName");
		String experName = request.getParameter("experName");
		
		Map params = new HashMap();
		if(request.getSession().getAttribute("roleId").toString().equals("3")) {
			params.put("userId", request.getSession().getAttribute("userId").toString());
		}
		
		//mysql分页插件
		PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		
		Grid grid = new Grid();
		List<OptionInfo> result = new ArrayList<>(); 
		List<OptionInfo> results = this.optionService.findOptionWhereSql(params);
		Long total = this.optionService.findCountByWhere(params);
		for(OptionInfo info : results  ) {
			  ClassInfo  classInfo =  classService.getClassById(info.getClassId());
			  info.setClassInfo(classInfo);
			  User user =  userService.getUserById(info.getUserId());
			  info.setUser(user);
		}
		if(request.getSession().getAttribute("roleId").toString().equals("2")) {
			String teacherId = request.getSession().getAttribute("userId").toString();
			for(OptionInfo info : results  ) {
				  if(Integer.valueOf(teacherId).equals(info.getClassInfo().getTeacherId())) {
					  result.add(info);
				  }
			}
			grid.setRows(result);
			grid.setTotal(Long.valueOf(result.size()));
			return grid ;
		}
		grid.setRows(results);
		grid.setTotal(total);
		return grid;
		
	}
	
	@RequestMapping("/option_input")
	public String addInput(Map<String, Object> map){
		map.put("option", new OptionInfo());
		return "option_add";
	}
	
	@ResponseBody
	@RequestMapping(value="/option_add",method=RequestMethod.POST)
	public Map addOption(OptionInfo info){
		Map<String,Object> result = new HashMap<String,Object>();
		int rows = this.optionService.insert(info);
		if (rows > 0) {
			result.put("success", true);
			result.put("msg", "保存成功!");
		}else{
			result.put("success", false);
			result.put("msg", "保存失败，请联系管理员!");
		}
		return result;
	}
	
	@RequestMapping(value="option_update_input/{id}",method=RequestMethod.GET)
	public String updateInput(@PathVariable("id") Integer id,Map<String, Object> map){
		OptionInfo info = this.optionService.getOptionById(id);
		map.put("option", info);
		return "option_update";
	}
	
	@ResponseBody   //转成JSON字符串
	@RequestMapping(value="/option_update",method=RequestMethod.POST)
	public Map updateOption(OptionInfo info){
		Map<String,Object> result = new HashMap<String,Object>();
		int rows = this.optionService.update(info);
		if (rows > 0) {
			result.put("success", true);
			result.put("msg", "修改成功！");
		}else{
			result.put("success", false);
			result.put("msg", "修改失败！");
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/option_select",method=RequestMethod.POST)
	public Map selectOption( @RequestParam("id")Integer id , HttpServletRequest request){
		Map map = new HashMap();
		OptionInfo  info = new OptionInfo();
		info.setClassId(id);
		info.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
		info.setStates("1");
		int rows = this.optionService.insert(info);
		if (rows > 0) {
			
			classService.updateClassRest(id);
			map.put("success", true);
			map.put("msg", "选课成功！");
		}else{
			map.put("success", false);
			map.put("msg", "删除失败！");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/option_cancel",method=RequestMethod.POST)
	public Map cancelOption( @RequestParam("id")Integer id , HttpServletRequest request){
		Map map = new HashMap();
		OptionInfo  info = optionService.getOptionById(id);
		ClassInfo classInfo =classService.getClassById(info.getClassId());
		String now = DateUtils.formatDate("yyyy-MM-dd");
		long rest  = DateUtils.getBetweenDays(now, DateUtils.formatDate("yyyy-MM-dd", classInfo.getClassDate()));
		Calendar ca = Calendar.getInstance();
		int hours = ca.get(Calendar.HOUR_OF_DAY);
		if((rest <= 1 && hours > 16 && hours != 0)|| rest == 0) {
			map.put("success", false);
			map.put("msg", "即将开始上课，不能取消！");
			return map;
		}
		info.setStates("0");
		int rows = this.optionService.update(info);
		if (rows > 0) {
			classService.cancelClass(id);
			map.put("success", true);
			map.put("msg", "课程取消！");
		}else{
			map.put("success", false);
			map.put("msg", "取消失败！");
		}
		return map;
	}
	@RequestMapping("/option_situation")
	public void  instSituation(OptionInfo info ,  HttpServletRequest request , HttpServletResponse response 
			,@RequestParam("upfile") MultipartFile[] files) {
		PrintWriter out;
		try {
			OptionInfo infoTemp = optionService.getOptionById(info.getId());
			infoTemp.setSituation(info.getSituation());
			if(!StringUtils.isEmpty(info.getRemark())) {
				infoTemp.setRemark(info.getRemark() + ";" 
						+  (infoTemp.getRemark()==null?"":infoTemp.getRemark()));
			}
			for(MultipartFile file:files){
				infoTemp.setImageName(file.getOriginalFilename());
				String realPath=request.getServletContext().getRealPath("/uploadFile");
				CodeUtil.SaveFileFromInputStream(file , realPath);
			}
			infoTemp.setStates("2");
			optionService.update(infoTemp);
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("msg", "操作成功");
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/option_judge")
	public void  instJudge(OptionInfo info ,  HttpServletRequest request , HttpServletResponse response 
			) {
		PrintWriter out;
		try {
			OptionInfo infoTemp = optionService.getOptionById(info.getId());
			infoTemp.setJudge(info.getJudge());
			if(!StringUtils.isEmpty(info.getRemark())) {
				infoTemp.setRemark(info.getRemark() + ";" 
			+  (infoTemp.getRemark()==null?"":infoTemp.getRemark()));
			}
			infoTemp.setStates("3");
			optionService.update(infoTemp);
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("msg", "操作成功");
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
