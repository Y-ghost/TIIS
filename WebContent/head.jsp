<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
<meta name="keywords" content="节水灌溉,手机灌溉,智能灌溉,河南锐利特计算机科技有限公司,Rainet,锐利特科技,云灌溉"/>
<meta name="Description" content="Rainet云灌溉系统(yun.rainet.com.cn)是由河南锐利特计算机科技有限公司研发的一款远程智能灌溉监控系统，涉及到传感器技术、自动控制技术、计算机技术、无线通信技术等多种高新技术,锐利特科技一家从事物联网智能灌溉设备研发、生产、销售以及提供信息技术服务的高新技术企业。"/> 
<base href="<%=basePath%>" />

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
</head>
<body>
	<!-- head -->
	<div id="top">
		<div class="wrapper">
			<a href="http://localhost:8080/TIIS/index/index" title="Rainet" class="logo" style="float:left;"><img
				src="img/logo.png" alt="Rainet云灌溉"  height="30px" width="90px"/> <span style="color: #fff;font-size:16px;border:#fff 0px solid;"> 云灌溉</span>
			</a>
			<a href="javascript:void(0);" class="logOut" style="color:#fff;margin-right:20px;border:#000 0px solid;float:right;height:30px;margin-top:23px;">退出</a>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>