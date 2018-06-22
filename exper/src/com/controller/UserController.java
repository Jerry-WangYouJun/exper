package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.model.Grid;
import com.github.pagehelper.PageHelper;
import com.pojo.User;
import com.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name="userServiceImpl")
	private UserServiceImpl userService;
	
	@RequestMapping("/checkUser")
	public void checkUser(HttpServletRequest request,HttpServletResponse response) {

		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		User user = this.userService.findUserForLogin(userName,pwd);
		RequestDispatcher rd = null;
		if (user == null) {
			request.setAttribute("msg", "用户名或者密码错误!");
			rd = request.getRequestDispatcher("/main.jsp");
			
		}else{
			request.getSession().setAttribute("userId", user.getId());
			request.getSession().setAttribute("userName", user.getUsername());
			request.getSession().setAttribute("roleId", user.getRole());
			request.getSession().setAttribute("loged", true);
			rd = request.getRequestDispatcher("/index.jsp");
		}
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/loginOut")
	public Map loginOut(HttpServletRequest request){
		request.getSession().removeAttribute("userId");
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("roleId");
		request.getSession().removeAttribute("loged");
		Map<String,Object> result = new HashMap<String,Object>();
			result.put("success", true);
			result.put("msg", "退出成功!");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Grid queryUsers(HttpServletRequest request,Model model){
		
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userNo = request.getParameter("userNo");
		String userName = request.getParameter("userName");
		String deptId = request.getParameter("deptId");
		String position = request.getParameter("position");
		
		Map params = new HashMap();
		params.put("userno", userNo);
		params.put("username", userName);
		params.put("deptId", deptId);
		params.put("position", position);
		
		//mysql分页插件
		PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
		
		Grid grid = new Grid();
		
		List<User> results = this.userService.findUserWhereSql(params);
		Long total = this.userService.findUserCountByWhere(params);
		grid.setRows(results);
		grid.setTotal(total);
		
		return grid;
		
	}
	
	@RequestMapping("/user_input")
	public String addInput(Map<String, Object> map){
		map.put("user", new User());
		return "user_add";
	}
	
	@ResponseBody
	@RequestMapping(value="/user_add",method=RequestMethod.POST)
	public Map addUser(User user){
		Map<String,Object> result = new HashMap<String,Object>();
		int rows = this.userService.inserUser(user);
		if (rows > 0) {
			result.put("success", true);
			result.put("msg", "保存成功!");
		}else{
			result.put("success", false);
			result.put("msg", "保存失败，请联系管理员!");
		}
		return result;
	}
	
	@RequestMapping(value="user_update_input/{id}",method=RequestMethod.GET)
	public String updateInput(@PathVariable("id") Integer id,Map<String, Object> map){
		User user = this.userService.getUserById(id);
		map.put("user", user);
		return "user_update";
	}
	
	@ResponseBody   //转成JSON字符串
	@RequestMapping(value="/user_update",method=RequestMethod.POST)
	public Map updateUser(User user){
		Map map = new HashMap();
		int rows = this.userService.updateUser(user);
		if (rows > 0) {
			map.put("success", true);
			map.put("msg", "修改用户成功！");
		}else{
			map.put("success", false);
			map.put("msg", "修改用户失败！");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping(value="/user_delete",method=RequestMethod.POST)
	public Map deleteUser(@RequestParam("id") String id){
		Map map = new HashMap();
		int rows = this.userService.deleteUser(id);
		if (rows > 0) {
			map.put("success", true);
			map.put("msg", "删除用户成功！");
		}else{
			map.put("success", false);
			map.put("msg", "删除用户失败！");
		}
		return map;
	}
}
