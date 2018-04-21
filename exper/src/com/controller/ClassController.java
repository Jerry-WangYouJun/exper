package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.model.Grid;
import com.github.pagehelper.PageHelper;
import com.pojo.ClassInfo;
import com.pojo.OptionInfo;
import com.pojo.ParentClassInfo;
import com.pojo.User;
import com.service.ClassServiceImpl;
import com.service.OptionServiceImpl;
import com.service.ParentClassServiceImpl;
import com.service.UserServiceImpl;

@Controller
@RequestMapping("/class")
public class ClassController {
	@Resource(name="classServiceImpl")
	private ClassServiceImpl classService;
	
	@Resource(name="parentClassServiceImpl")
	private ParentClassServiceImpl pclassService;
	
	@Resource(name="userServiceImpl")
	private UserServiceImpl userService;
	
	@Resource(name="optionServiceImpl")
	private OptionServiceImpl optionService;
	
	@ResponseBody
	@RequestMapping("/list")
	public Grid queryList(HttpServletRequest request,Model model){
		
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String className = request.getParameter("className");
		String experName = request.getParameter("experName");
		String teacherId = request.getParameter("teacherId");
		
		Map params = new HashMap();
		params.put("className", className);
		params.put("experName", experName);
		if(request.getSession().getAttribute("roleId").toString().equals("2")) {
			teacherId = request.getSession().getAttribute("userId").toString();
		}
		params.put("teacherId", teacherId);
		
		//mysql分页插件
		PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		
		Grid grid = new Grid();
		
		List<ClassInfo> results = this.classService.findClassWhereSql(params);
		for (ClassInfo classInfo : results) {
			if(!StringUtils.isEmpty(classInfo.getTeacherId())) {
				User user =  userService.getUserById(classInfo.getTeacherId());
				classInfo.setUser(user);
			}
			if(!StringUtils.isEmpty(classInfo.getClassName())) {
				ParentClassInfo pclass =  pclassService.getClassById(Integer.valueOf(classInfo.getClassName()));
				classInfo.setPclassInfo(pclass);
			}
			if(request.getSession().getAttribute("roleId").toString().equals("3")) {
				params.put("userId", request.getSession().getAttribute("userId").toString());
				params.put("classId", classInfo.getId());
				List<OptionInfo> option = optionService.findOptionWhereSql(params);
				if(option != null && option.size()>0)
					classInfo.setOption(option.get(0));
			}
		}
		Long total = this.classService.findClassCountByWhere(params);
		Map param = new HashMap();
		param.put("role", 2);
		List<User> teacherList = userService.findUserWhereSql(param);
		request.getSession().setAttribute("teacherList", teacherList);
		List<ParentClassInfo> pclasList = pclassService.findClassWhereSql(new HashMap());
		request.getSession().setAttribute("pclasList", pclasList);
		grid.setRows(results);
		grid.setTotal(total);
		return grid;
	}
	
	@RequestMapping("/class_input")
	public String addInput(Map<String, Object> map){
		map.put("class", new ClassInfo());
		return "class_add";
	}
	
	@ResponseBody
	@RequestMapping(value="/class_add",method=RequestMethod.POST)
	public Map addClass(ClassInfo info){
		Map<String,Object> result = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(info.getClassName())) {
			ParentClassInfo pclass =  pclassService.getClassById(Integer.valueOf(info.getClassName()));
			info.setPclassInfo(pclass);
			info.setRest(pclass.getAllowed());
		}
		int rows = this.classService.inserClass(info);
		if (rows > 0) {
			result.put("success", true);
			result.put("msg", "保存成功!");
		}else{
			result.put("success", false);
			result.put("msg", "保存失败，请联系管理员!");
		}
		return result;
	}
	
	@RequestMapping(value="class_update_input/{id}",method=RequestMethod.GET)
	public String updateInput(@PathVariable("id") Integer id,Map<String, Object> map){
		ClassInfo info = this.classService.getClassById(id);
		map.put("class", info);
		return "class_update";
	}
	
	@ResponseBody   //转成JSON字符串
	@RequestMapping(value="/class_update",method=RequestMethod.POST)
	public Map updateClass(ClassInfo info){
		ClassInfo  classInfo = classService.getClassById(info.getId());
		Map<String,Object> result = new HashMap<String,Object>();
		classInfo.setClassDate(info.getClassDate());
		classInfo.setTeacherId(info.getTeacherId());
		classInfo.setDuration(info.getDuration());
		classInfo.setSelectDate(info.getSelectDate());
		int rows = this.classService.updateClass(classInfo);
		if (rows > 0) {
			result.put("success", true);
			result.put("msg", "修改成功！");
		}else{
			result.put("success", false);
			result.put("msg", "修改失败！");
		}
		return result;
	}
	
	@ResponseBody   //转成JSON字符串
	@RequestMapping(value="/class_exper",method=RequestMethod.POST)
	public Map updateExper(ClassInfo info){
		ClassInfo  classInfo = classService.getClassById(info.getId());
		Map<String,Object> result = new HashMap<String,Object>();
		classInfo.setExperData(info.getExperData());
		classInfo.setExperInfo(info.getExperInfo());
		classInfo.setExperName(info.getExperName());
		classInfo.setRemark(StringUtils.isEmpty(info.getRemark()) ?  "" : (info.getRemark()+";")
		+ (StringUtils.isEmpty(classInfo.getRemark())?"":classInfo.getRemark()));
		int rows = this.classService.updateClass(classInfo);
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
	@RequestMapping(value="/class_delete",method=RequestMethod.POST)
	public Map deleteClass( @RequestParam("id")String id){
		Map map = new HashMap();
		int rows = this.classService.deleteClass(Integer.valueOf(id));
		if (rows > 0) {
			map.put("success", true);
			map.put("msg", "删除成功！");
		}else{
			map.put("success", false);
			map.put("msg", "删除失败！");
		}
		return map;
	}
}
