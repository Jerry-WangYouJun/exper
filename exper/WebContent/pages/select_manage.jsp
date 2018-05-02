<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>选课管理</title>
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
 		<p style="font-size: 22px;height:40px;line-height: 40px;margin: 0px">课程管理</p>
 	</div>
 	<div data-options="region:'center',border:false,showHeader:false" style="padding-bottom: 3px">
 		<table id="class_table" class="easyui-datagrid" fit="true" ></table>
 	</div>
	
	
	<div id="toolbar_class" style="padding:2px 5px;">
     	<a onclick="class_select()" class="easyui-linkbutton"  plain="true" iconCls="fa fa-plus fa-fw" style="margin: 2px">选课</a>
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
					{field:'duration',title:'时段',width:100,align:'center'},
					{field:'teacher',title:'教师',width:150,align:'center',
						formatter : function(value, row, index) {
							if(row.user){
								return row.user.username;
							}else{
								return "";
							}
						}},
					{field:'rest',title:'剩余名额',width:150,align:'center'},				
					{field:'selectDate',title:'选课时间',width:100,align:'center'},
					{field:'remark',title:'备注',width:100,align:'center'}
				]],				
			});
		});
		function class_select(){
			var row = $('#class_table').datagrid('getSelected');
    		if(row){
    			$('#class_dlg').dialog('open');	
    			$('#class_dlg').dialog('setTitle','添加信息');
    			$("#class_save").unbind('click').click(function(){
    				$.messager.confirm(
    	    				'提示',
    	    				'确定要选这门课?',
    	    				function(r) {
    	    					if (r) {
    	    						$.ajax({ 
    	    							type:'post',
    	    			    			url: '${pageContext.request.contextPath}/option/option_select',
    	    			    			data : {"id":row.id , "timeZone":$("#timeZone").val()},
    	    			    			dataType : 'json',
    	    			    			success : function(obj){
    	    			    				if(obj.success){
    	    			    				 	alert(obj.msg);
    	    			    				 	class_close();
    	    			    				 	$('#class_table').datagrid('reload');
    	    			    				}else{
    	    			    					alert(obj.msg);
    	    			    					$('#class_table').datagrid('reload');
    	    			    				}
    	    			    			}
    	    			    		});
    	    					}
    	    				}); 
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
            	<label class="col-md-4" style="display: inline-block;height: 34px;line-height: 34px;text-align: left;width: 30%">教师：</label>
                <select name="timeZone" id="timeZone" 
                    		class="form-control select2 easyui-combobox" style="width: 70%;height: 86%" editable="false">
                	<option value="1">第一节</option>
                	<option value="2">第二节</option>
                <option value="3">第三节</option>
                	<option value="4">第四节</option>
                </select>
            </div>
            <input id="id" name="id" style="display:none;"/> 
    	</form>                 
    </div>
</body>
</html>
