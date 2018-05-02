<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>课程管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    
   <jsp:include page="/common.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>

  </head>
  
  <body class="easyui-layout">
 	<div data-options="region:'north',border:false,showHeader:false"  style="height:100px" >
 		<p style="font-size: 22px;height:40px;line-height: 40px;margin: 0px">实验管理</p>
 		<form action="${pageContext.request.contextPath}/user/checkUser" method="post">
    		<table align="left" class="login" cellspacing="1px" cellpadding="1px"  >
    		<tr style="margin-top:10px;height: 40px;">
    			<td class="loginText">班级：</td>
    			<td><input type="text" id="className" name="className"/></td>
    			<td colspan="2">
    				 <input type="button" value="查询" onclick="queryList()">
    			</td>
    		</tr>
    	</table>
    	</form>
 	</div>
 	<div data-options="region:'center',border:false,showHeader:false" style="padding-bottom: 3px">
 		<table id="option_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
	
	
	<div id="toolbar_option" style="padding:2px 5px;">
			     	<a onclick="class_export()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-plus fa-fw" style="margin: 2px">导出实验信息</a>
    </div>
	
    <script type="text/javascript">
    
    function queryList(){
 		 var className = $("#className").val();
 		$("#option_table").datagrid("load",{ 'className':className});
 		//window.location.href =  '${pageContext.request.contextPath}/excel/export?className='+className;
 	 }
    function  class_export(){
		 var className = $("#className").val();
		 window.location.href =  '${pageContext.request.contextPath}/excel/export?className='+className;
	}
    	$(function(){
			$('#option_table').datagrid({
				url:'${pageContext.request.contextPath}/option/list',
				pagination: true,
				toolbar:'#toolbar_option',				
				fitColumns: true,
				singleSelect: true,
				columns:[[
					{field:'id', hidden:'true',editor:'textbox' },
					{field:'userInfo',title:'学生',width:100,align:'center',
						formatter : function(value, row, index) {
							if(row.user){
								 return row.user.username;
							}else{
								return "";
							}
						}},
						{field:'className',title:'班级',width:100,align:'center',
							formatter : function(value, row, index) {
								if(row.user){
									 return row.user.position;
								}else{
									return "";
								}
							}},
					{field:'experName',title:'实验名',width:100,align:'center',
						formatter : function(value, row, index) {
							return row.classInfo.pclassInfo.className;
						}},
					{field:'experTime',title:'实验时间',width:100,align:'center',
							formatter : function(value, row, index) {
								return row.classInfo.classDate;
							}},
					{field:'states',title:'实验状态',width:100,align:'center',
						formatter : function(value, row, index) {
							if (value == '1') {
								return "已选课";
							} else if (value == '0') {
								return "已取消";
							} else if(value =='2' ){
								 return "已提交报告";
							} else if(value == "3"){
								return "教师已评价";
							}
						}},
					{field:'judge',title:'教师评价',width:150,align:'center'},
					{field:'situation',title:'学生试验情况',width:150,align:'center'},
					{field:'imageName',title:'实验图片',width:150,align:'center',formatter:function(value,row,index){
						if(value){
							 return '<img style="width:100px;length:100px" border="1" src="${pageContext.request.contextPath}/uploadFile/' + value +'"/>'; 
						}else{
							return "";
						}
					}},				
					{field:'remark',title:'备注',width:100,align:'center'}
				]],				
			});
		});
    </script>
    
</body>
</html>
