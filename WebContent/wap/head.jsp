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
		<meta name="keywords" content="农业智能灌溉,手机智能灌溉,手机控制智能灌溉,河南锐利特信息技术有限公司,rainet,云灌溉系统,锐利特,自动化智能灌溉"/>
		<meta name="Description" content="Rainet云灌溉系统涉及到传感器技术、自动控制技术、计算机技术、无线通信技术等多种高新技术，在软件系统和硬件系统的协同工作的基础上，加入了土壤温湿度传感器和水流量传感器，进行用水量评估。用户可以通过Web端浏览器或者移动App对节点限定土壤湿度进行设置，节点中的微型计算机芯片采集到的湿度信号并与设定的温湿度值进行比较，判断是否需灌溉，并在灌溉过程中采集水流量，通过“湿度-用水量”寻找到植物最佳灌溉平衡点，实现智能化灌溉。"> 
		<base href="<%=basePath%>">

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
</head>
<body>
	<!-- head -->
	<div id="top">
		<div class="wrapper">
			<a href="http://192.168.0.100:8080/TIIS/wap/login" title="Rainet" class="logo"><img
				src="img/logo.png" alt="Rainet云灌溉"  height="30px" width="90px"/> <span style="color: #fff;font-size:16px;border:#fff 0px solid;"> 云灌溉</span>
			</a>
			<a href="javascript:void(0);" class="logOut" style="color:#fff;margin-left:20px;">退出</a>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>