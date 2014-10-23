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
<title>Rainet云灌溉系统--节点列表</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/login.js"></script>

<style type="text/css">
.main {
	min-height: 100%;
	font-size:14px;
	margin: 0px auto;
}

.maincontent {
	width: 100%;
	height: 400px;
	padding-top:20%;
	border: #B0B0B0 solid 0px;
}
.project {
	text-align: center;
	font-size: 22px;
	font-weight: 700;
	height: 40px;
	margin: 0px auto;
	color:#0A0A0A;
	border-bottom:#c3c3c3 1px solid;
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
	margin-right:20px;
	height:60%;
	margin-top:2px;
	width:60px;
	float:right;
	color:#000;
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
	<jsp:include page="head.jsp" />
	<!-- head ends -->
	<!-- Main content -->
	<div class="main">
		<div class="maincontent">
			<div class="project">项目列表</div>
		
			<div class="content">
				<img src="img/equipment.png" class="pImg" /> <span class="labSpan">01号节点</span><button class="clickLab" >开启</button>
			</div>
			<div class="content">
				<img src="img/equipment.png" class="pImg" /> <span class="labSpan">02号节点</span><button class="clickLab" >开启</button>
			</div>
			<div class="content">
				<img src="img/equipment.png" class="pImg" /> <span class="labSpan">03号节点</span><button class="clickLab" >开启</button>
			</div>
			<div class="content">
				<img src="img/equipment.png" class="pImg" /> <span class="labSpan">04号节点</span><button class="clickLab" >开启</button>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>