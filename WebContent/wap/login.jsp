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
<title>Rainet云灌溉系统--用户登录</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/login.js"></script>

<style type="text/css">
.main {
	min-height: 100%;
	margin: 0px auto;
}

.maincontent {
	width: 98%;
	padding-top:20%;
	border: #B0B0B0 solid 0px;
}

.login {
	text-align: center;
	font-size: 22px;
	font-weight: 700;
	padding-top: 20%;
	height: 40px;
	margin: 0px auto;
	color:#0A0A0A;
}

.content {
	height: 300px;
	width: 98%;
	font-size: 14px;
	margin: 0px auto;
	padding-left: 8px;
}

.bottom {
	width: 100%;
	height: 60px;
	margin: 0px auto;
	text-align: center;
}

.wapLoginBtn {
	width: 98%;
	height: 40px;
	margin-top: 20px;
	font-size: 20px;
	font-weight: 700;
	background-color: #B35D5D;
	color: #fff;
}


.content span {
	color:red;
	margin-right:3px;
}
.register {
	width: 95%;
	height: 40px;
	margin-top: 10px;
}
input {
	color: #B0B0B0;
	width: 95%;
	height: 35px;
	border: #B0B0B0 solid 1px;
	padding-left: 3px;
}
a {
	color:#00B2EE;	
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
					<div class="login">用户登录</div>
					
					<div class="content">
						登录名:<span class="nameSpan"></span><input type="text" class="username" style="font-size:22px;" value="" />
						<div style="height:10px;"></div>
						密码:<span class="pswSpan"></span><input type="password" class="password" style="font-size:22px;" value="" />
						<div style="height:15px;"></div>
						<button class="wapLoginBtn">登&nbsp;&nbsp;录</button>
						<div class="register">
							<a href="javascript:void(0);" style="float: left;" class="forgetPassword">忘记密码?</a> 
							<a href="http://localhost:8080/TIIS/indexs/register" style="float: right;margin-right:5px;">还没有注册?</a>
						</div>
					</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>