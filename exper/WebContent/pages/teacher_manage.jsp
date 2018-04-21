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
    
    <title>试验管理</title>
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

  </head>
  
  <body class="easyui-layout">
 	<div data-options="region:'north',border:false,showHeader:false"  style="height:40px" >
 		<p style="font-size: 22px;height:40px;line-height: 40px;margin: 0px">实验管理</p>
 	</div>
 	<div data-options="region:'center',border:false,showHeader:false" style="padding-bottom: 3px">
 		<table id="class_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
	
	
	<div id="toolbar_class" style="padding:2px 5px;">
		 <a onclick="class_add()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-plus fa-fw" style="margin: 2px">新增实验</a> 
        <a onclick="class_edit()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-edit fa-fw" style="margin: 2px">指派教师</a>    
    </div>
	
    <script type="text/javascript">
    	$(function(){
			$('#class_table').datagrid({
				url:'${pageContext.request.contextPath}/class/list',
				pagination: true,
				toolbar:'#toolbar_class',				
				fitColumns: true,
				singleSelect: true,
				columns:[[
					{field:'id', hidden:'true',editor:'textbox' },
					{field:'className',title:'课程名称',width:100,align:'center',
						formatter : function(value, row, index) {
							return row.pclassInfo.className;
						}},
					{field:'classDate',title:'实验时间',width:100,align:'center'},
					{field:'teacher',title:'教师',width:150,align:'center',
						formatter : function(value, row, index) {
							if(row.user){
								return row.user.username;
							}else{
								return "";
							}
						}},
					{field:'allowed',title:'人数',width:150,align:'center',
							formatter : function(value, row, index) {
								return row.pclassInfo.allowed;
							}},
					{field:'rest',title:'剩余名额',width:150,align:'center'},				
					{field:'duration',title:'时段',width:100,align:'center'},
					{field:'selectDate',title:'选课时间',width:100,align:'center'},
					{field:'experName',title:'实验名称',width:100,align:'center'},
					{field:'experInfo',title:'实验资料',width:150,align:'center'},
					{field:'experData',title:'实验数据',width:150,align:'center'},
					{field:'remark',title:'备注',width:100,align:'center'}
				]],				
			});
		});
    	
    	function class_add(){
			$('#class_dlg').dialog('open');	
			$('#class_dlg').dialog('setTitle','添加信息');
			$("#class_save").unbind('click').click(function(){
  				$.ajax({
  					type: 'post',
					url : '${pageContext.request.contextPath}/class/class_add',
					data : $('#class_form').serialize(),
					dataType : 'json',
					success : function(obj) {
						if (obj.success) {
							alert(obj.msg);
							class_close();
						} else {
							alert(obj.msg);
						}
					}
				});
			});
		}
		function class_edit(){
			var row = $('#class_table').datagrid('getSelected');
    		if(row){
    			$('#class_dlg').dialog('open');	
    			$('#class_dlg').dialog('setTitle','编辑信息');
    			$('#class_form').form('load', row);
				$("#class_save").unbind('click').click(function(){
  					$.ajax({
  						type:'post',
						url : '${pageContext.request.contextPath}/class/class_update',
						data : $('#class_form').serialize(),
						dataType : 'json',
						success : function(obj) {
							if (obj.success) {
								alert(obj.msg);
								class_close();
							} else {
								alert(obj.msg);
							}
						}
					});
				});
			}
    	}
		function class_delete(){
			var row = $('#class_table').datagrid('getSelected');
    		if(row){
    			$.messager.confirm(
    				'提示',
    				'确定要删除么?',
    				function(r) {
    					if (r) {
    						$.ajax({ 
    							type:'post',
    			    			url: '${pageContext.request.contextPath}/class/class_delete',
    			    			data : {"id":row.id},
    			    			dataType : 'json',
    			    			success : function(obj){
    			    				if(obj.success){
    			    				 	alert(obj.msg);
    			    				 	$('#class_table').datagrid('reload');
    			    				}else{
    			    					alert(obj.msg);
    			    					$('#class_table').datagrid('reload');
    			    				}
    			    			}
    			    		});
    					}
    				});  		
    			}
		}
		function class_close(){
			$('#class_form').form('reset');
			$('#class_form').form('clear');
			$('#class_dlg').dialog('close');	
			$('#class_table').datagrid('reload');
		}
    </script>
    
    <div id="class_dlg_buttons" style="width:800px;height: 40px;text-align: center">
		<button id="class_save" type="button" class="btn btn-primary btn-dialog-left">保存</button>
		<button onclick="class_close()" type="button" class="btn btn-default btn-dialog-right">取消</button>
	</div>
	
	
	<div  id="class_dlg" closed="true" class="easyui-dialog" style="width:400px;height: 450px"
	data-options="border:'thin',cls:'c1',collapsible:false,modal:true,closable:false,top:10,buttons: '#class_dlg_buttons'">
    	<form id="class_form" role="form" style="padding: 20px">
    		<div class="form-group col-md-12">
            	<label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">课程名称：</label>
                <select id="box" class="easyui-combobox" name="className" style="width:200px;">
	                  	  <option value="" selected="selected">--请选择--</option>
						  <c:forEach items="${pclasList}" var ="pcalss">
						  		  <option value="${pcalss.id }">${pcalss.className}</option>
						  </c:forEach>
				</select>
            </div>
            <div class="form-group col-md-12" >
                     <label  class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">实验时间:</label>
                		<input class="form-control easyui-datetimebox" id="starttime" name="classDate" style="display: inline-block;width: 70%" />
                  </div>
             <div class="form-group col-md-12">
            	<label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">时段：</label>
                <select id="box" class="easyui-combobox" name="duration" style="width:200px;">
	                  	  <option value="" selected="selected">--请选择--</option>
	                  	  <option value="上午" selected="selected">--上午--</option>
	                  	  <option value="下午" selected="selected">--下午--</option>
	                  	  <option value="全天" selected="selected">--全天--</option>
				</select>
            </div>
            <div class="form-group col-md-12">
            	<label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">教师：</label>
                <select id="box" class="easyui-combobox" name="teacherId" style="width:200px;">
	                  	  <option value="" selected="selected">--请选择--</option>
						  <c:forEach items="${teacherList}" var ="teacher">
						  		  <option value="${teacher.id }">${teacher.username}</option>
						  </c:forEach>
				</select>
            </div>
            <input id="id" name="id" style="display:none;"/> 
    	</form>                 
    </div>
</body>
</html>
