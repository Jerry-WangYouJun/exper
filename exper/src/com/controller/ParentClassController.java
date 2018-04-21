package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.model.Grid;
import com.github.pagehelper.PageHelper;
import com.pojo.ParentClassInfo;
import com.service.ParentClassServiceImpl;

@Controller
@RequestMapping("/pclass")
public class ParentClassController {
	@Resource(name="parentClassServiceImpl")
	private ParentClassServiceImpl classService;
	
	@ResponseBody
	@RequestMapping("/list")
	public Grid queryList(HttpServletRequest request,Model model){
		
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String className = request.getParameter("className");
		
		Map params = new HashMap();
		params.put("className", className);
		
		//mysql分页插件
		PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		
		Grid grid = new Grid();
		
		List<ParentClassInfo> results = this.classService.findClassWhereSql(params);
		Long total = this.classService.findClassCountByWhere(params);
		grid.setRows(results);
		grid.setTotal(total);
		
		return grid;
		
	}
	
	@RequestMapping("/class_input")
	public String addInput(Map<String, Object> map){
		map.put("class", new ParentClassInfo());
		return "class_add";
	}
	
	@ResponseBody
	@RequestMapping(value="/class_add",method=RequestMethod.POST)
	public Map addClass(ParentClassInfo info){
		Map<String,Object> result = new HashMap<String,Object>();
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
		ParentClassInfo info = this.classService.getClassById(id);
		map.put("class", info);
		return "class_update";
	}
	
	@ResponseBody   //转成JSON字符串
	@RequestMapping(value="/class_update",method=RequestMethod.POST)
	public Map updateClass(ParentClassInfo info){
		Map<String,Object> result = new HashMap<String,Object>();
		int rows = this.classService.updateClass(info);
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
