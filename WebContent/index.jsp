<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<link rel="shortcut icon" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>河南锐利特物联网云灌溉系统--index跳转</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		window.location.href="http://localhost:8080/TIIS/index/index";
	});
</script>
</head>

<body>
</body>
</html>