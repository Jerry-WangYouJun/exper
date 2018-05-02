<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String msg = (String)request.getAttribute("msg");
	if(msg == null){
		msg = "";
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
      <title>登录页面</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/common.jsp"></jsp:include>
     <style type="text/css">
    </style>
	<script type="text/javascript">
		function checkUser(){
			var userName = $("#userName").val();
			var pwd = $("#pwd").val();
			//var vcode = $("#vcode").val();
			if(userName == ""){
				alert("用户名不能为空!");
				return;
			}
			if(pwd == ""){
				alert("密码不能为空!");
				return;
			}
			document.forms[0].submit();
		}
	</script>
  </head>
  
  <body class="easyui-layout">
 	<div data-options="region:'north',border:false,showHeader:false"  style="height:130px;margin: 20px;" >
 		 <form action="${pageContext.request.contextPath}/user/checkUser" method="post">
    		<table align="left" class="login" cellspacing="1px" cellpadding="1px"  >
    		<tr style="margin-top:10px;height: 40px;">
    			<td class="loginText">用户名：</td>
    			<td><input type="text" id="userName" name="userName"/></td>
    		</tr>
    		<tr style="margin-top:10px;height: 40px;">
    			<td>密码：</td>
    			<td><input type="password" id="pwd" name = "pwd"/></td>
    		</tr>
    		<tr style="margin-top:10px;height: 40px;">
    			<td colspan="2">
    				 <input type="button" value="登录" onclick="checkUser()">
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<font color="red"><%=msg  %></font>
    			</td>
    		</tr>
    	</table>
    	</form> 
 	</div>
 	<div data-options="region:'center',border:false,showHeader:false" style="padding-bottom: 3px">
 		<table id="class_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
    <script type="text/javascript">
    $('#class_table').datagrid({
		url:'${pageContext.request.contextPath}/pclass/list',
		pagination: true,
		toolbar:'#toolbar_class',				
		fitColumns: true,
		singleSelect: true,
		columns:[[
			{field:'id', hidden:'true',editor:'textbox' },
			{field:'className',title:'课程名称',width:100,align:'center'},
			{field:'startTime',title:'开课时间',width:100,align:'center'},
			{field:'allowed',title:'人数',width:150,align:'center'},
			{field:'endTime',title:'选课时间',width:100,align:'center'}
		]],				
	});
    </script>
</body>
</html>
