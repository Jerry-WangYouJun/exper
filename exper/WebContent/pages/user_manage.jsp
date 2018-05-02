<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    
    
   <jsp:include page="/common.jsp"></jsp:include>

  </head>
  
  <body class="easyui-layout">
 	<div data-options="region:'north',border:false,showHeader:false"  style="height:40px" >
 		<p style="font-size: 22px;height:40px;line-height: 40px;margin: 0px">用户管理</p>
 	</div>
 	<div data-options="region:'center',border:false,showHeader:false" style="padding-bottom: 3px">
 		<table id="user_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
	
	
	<div id="toolbar_user" style="padding:2px 5px;">
	<a onclick="user_add()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-plus fa-fw" style="margin: 2px">新增用户</a>    
        <a onclick="user_edit()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-edit fa-fw" style="margin: 2px">修改用户</a>    
        <a onclick="user_delete()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-remove fa-fw" style="margin: 2px">删除</a>
    </div>
	
    <script type="text/javascript">
    	$(function(){
			$('#user_table').datagrid({
				url:'${pageContext.request.contextPath}/user/list',
				pagination: true,
				toolbar:'#toolbar_user',				
				fitColumns: true,
				singleSelect: true,
				columns:[[
					{field:'username',title:'姓名',width:100,align:'center'},
					{field:'role',title:'角色',width:100,align:'center',
						formatter: function(value,row,index){
							switch(value){
								case '1':
									return '管理员';
								break;
								case '2':
									return '教师';
								break;
								case '3':
									return '学生';
								break;
							}							
						}}
					
				]],				
			});
		});
		function user_add(){
			$('#user_dlg').dialog('open');	
			$('#user_dlg').dialog('setTitle','添加用户');
			$("#user_save").unbind('click').click(function(){
  				$.ajax({
  					type:'post',
					url : '${pageContext.request.contextPath}/user/user_add',
					data : $('#user_form').serialize(),
					dataType : 'json',
					success : function(obj) {
						if (obj.success) {
							alert(obj.msg);
							user_close();
						} else {
							alert(obj.msg);
						}
					}
				});
			});
		}
		function user_edit(){
			var row = $('#user_table').datagrid('getSelected');
    		if(row){
    			$('#user_dlg').dialog('open');	
    			$('#user_dlg').dialog('setTitle','编辑用户');
    			$('#user_form').form('load', row);
    			//updateCombox(roleId,row.roleId);
    			//updateCombox(companyId,row.companyId);
				$("#user_save").unbind('click').click(function(){
  					$.ajax({
  						type:'post',
						url : '${pageContext.request.contextPath}/user/user_update',
						data : $('#user_form').serialize(),
						dataType : 'json',
						success : function(obj) {
							if (obj.success) {
								alert(obj.msg);
								user_close();
							} else {
								alert(obj.msg);
							}
						}
					});
				});
			}
    	}
		function user_delete(){
			var row = $('#user_table').datagrid('getSelected');
			if(row.userName=="admin"){
				alert("为保证数据完整性，admin用户不能被删除");
				return ;
			}
    		if(row){
    			$.messager.confirm(
    				'提示',
    				'确定要删除么?',
    				function(r) {
    					if (r) {
    						$.ajax({ 
    							type:'POST',
    			    			url: '${pageContext.request.contextPath}/user/user_delete',
    			    			data : {"id":row.id},
    			    			dataType : 'json',
    			    			success : function(obj){
    			    				if(obj.success){
    			    				 	alert(obj.msg);
    			    				 	$('#user_table').datagrid('reload');
    			    				}else{
    			    					alert(obj.msg);
    			    					$('#user_table').datagrid('reload');
    			    				}
    			    			}
    			    		});
    					}
    				});  		
    			}
		}
		function user_close(){
			$('#user_form').form('reset');
			$('#user_form').form('clear');
			$('#user_dlg').dialog('close');	
			$('#user_table').datagrid('reload');
		}
		
		$(function(){
			$("#box").combobox({  
			       onChange: function () {  
			           var newPtion = $("#box").combobox('getText')  
			           if (newPtion == "学生") {  
			                $("#className").text("班级：");
			                $("#className").after('<input name="position" id="position"  style="display: inline-block;width: 70%">');
			                
			           }else{
			        	   	    $("#className").text("");
			        	   	 $("#position").remove();
			           }  
			       }  
			   })  
		});
    </script>
    
    <div id="user_dlg_buttons" style="width:800px;height: 40px;text-align: center">
		<button id="user_save" type="button" class="btn btn-primary btn-dialog-left">保存</button>
		<button onclick="user_close()" type="button" class="btn btn-default btn-dialog-right">取消</button>
	</div>
	
	
	<div  id="user_dlg" closed="true" class="easyui-dialog" style="width:400px;height: 400px"
	data-options="border:'thin',cls:'c1',collapsible:false,modal:true,closable:false,top:50,buttons: '#user_dlg_buttons'">
    	<form id="user_form" role="form" style="padding: 20px">
    			<div class="form-group col-md-12">
            		<label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">用户名：</label>
                <input name="username" id="username" type="text" style="display: inline-block;width: 70%">
            </div>
            <div class="form-group col-md-12">
            <label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">角色：</label>
                <select id="box" class="easyui-combobox" name="role" style="width:200px;">
						  <option value="1">管理员</option>
						  <option value="2">教师</option>
						  <option value="3">学生</option>
				</select>
            </div>
            <div class="form-group col-md-12">
            		<label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">密码：</label>
                <input name="pwd" type="password" style="display: inline-block;width: 70%">
            </div>
            <div class="form-group col-md-12">
            		<label class="col-md-4" id="className" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%"></label>
            </div>
            <input id="id" name="id" style="display:none;" /> 
    	</form>                 
    </div>
</body>
</html>
