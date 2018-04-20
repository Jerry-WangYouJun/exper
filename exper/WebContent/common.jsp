<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="<%=path%>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="<%=path%>/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="<%=path%>/vendor/dist/css/sb-admin-2.css" rel="stylesheet">
<link href="<%=path%>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<script src="<%=path%>/vendor/jquery/jquery.min.js"></script>

<script src="<%=path%>/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/js/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<script src="<%=path%>/vendor/metisMenu/metisMenu.min.js"></script>
<script src="<%=path%>/vendor/dist/js/sb-admin-2.js"></script>

<script src="<%=path%>/vendor/easyui/jquery.easyui.min.js"></script>
<script src="<%=path%>/vendor/easyui/locale/easyui-lang-zh_CN.js"></script>
<link href="<%=path%>/vendor/easyui/themes/material/easyui.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/vendor/easyui/themes/icon.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/css/dialog_head.css" rel="stylesheet" type="text/css">