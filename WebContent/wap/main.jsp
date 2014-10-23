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
<title>Rainet云灌溉系统--项目列表</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/main.js"></script>

<style type="text/css">
.main {
	min-height: 100%;
	font-size:14px;
	margin: 0px auto;
}
body{background: white;}

.maincontent {
	width: 100%;
	height: 400px;
	border: #B0B0B0 solid 0px;
}
.head {
	line-height:48px;
	text-align: center;
	width:100%;
	height:48px;
	border-bottom:#c3c3c3 1px solid;
	background: url(images/backgrounds/top.jpg) repeat-x 0 0;
}
.content {
	height: 50px;
	width: 100%;
	line-height:50px;
	font-size: 18px;
	background-color: #fff;
	border-bottom:#c3c3c3 1px solid;
}

.bottom {
	width: 100%;
	height: 60px;
	margin: 0px auto;
	text-align: center;	
}
.clickLab {
	margin-right:10px;
	padding-top:10px;
	float:right;
	width:40px;
	text-align:center;
	color:#00B2EE;
}

.pImg {
	height:90%;
	margin-top:2px;
	margin-left:10px;
	float:left;
}

.labSpan {
	margin-left:10px;
	float:left;
	font-weight: 700;
	color:#000;
}

</style>
</head>

<body>
	<!-- head -->
	<div  class="head" >
		<div style="border:#000 0px solid;float:left;margin-left:20px;">
			<a href="http://192.168.0.100:8080/TIIS/wap/login" title="Rainet">
				<img src="img/logo.png" style="margin-top:7px;"  alt="Rainet云灌溉"  height="30px" width="90px"/>
			</a>
			
		</div>
		<span style="color: #fff;font-size:16px;border:#000 0px solid;float:left;height:30px;margin-top:7px;"> 云灌溉</span>
		<div style="font-size:14px;color:#fff;float:right;margin-right:20px;height:30px;margin-top:7px;">
			<a style="color:#fff;">退出</a><a style="color:#fff;margin-left:10px;">设置</a>
		</div>
	</div>
	<!-- head ends -->
	<!-- Main content -->
	<div class="project">
		<div style="height:50px;float:left;">项目列表</div><div style="border-bottom: #000 1px solid;float:left;"></div>
	</div>
	<div class="main">
		<div class="maincontent">
			<div class="content">
				<img src="img/project.png" class="pImg" /> <span class="labSpan">万科办公区</span><a href="http://192.168.0.100:8080/TIIS/wap/equipment.jsp?pId=1" class="clickLab" ><img src="img/arrow.png" style="width:30px;height:30px;" /></a>
			</div>
			<div class="content">
				<img src="img/project.png" class="pImg" /> <span class="labSpan">河南希芳阁前台绿墙..</span><a href="http://192.168.0.100:8080/TIIS/wap/equipment.jsp?pId=1" class="clickLab"><img src="img/arrow.png" style="width:30px;height:30px;" /></a>
			</div>
			<div class="content">
				<img src="img/project.png" class="pImg" /> <span class="labSpan">莫奈花园..</span><a href="http://192.168.0.100:8080/TIIS/wap/equipment.jsp?pId=1" class="clickLab"><img src="img/arrow.png" style="width:30px;height:30px;" /></a>
			</div>
			<div class="content">
				<img src="img/project.png" class="pImg" /> <span class="labSpan">河南锐利特计算机科技..</span><a href="http://192.168.0.100:8080/TIIS/wap/equipment.jsp?pId=1" class="clickLab"><img src="img/arrow.png" style="width:30px;height:30px;" /></a>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>