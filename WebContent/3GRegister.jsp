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
<title>河南锐利特物联网云灌溉系统--用户注册</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/register.js"></script>

<style type="text/css">
.main {
	min-height: 100%;
	margin: 0px auto;
}

.maincontent {
	width: 100%;
	min-height: 600px;
	padding-top:70px;
	border: #B0B0B0 solid 0px;
}

.register {
	text-align: center;
	font-size: 22px;
	font-weight: 700;
	padding-top: 20px;
	height: 40px;
	margin: 0px auto;
	color:#0A0A0A;
}

.content {
	height: 300px;
	width: 100%;
	margin: 0px auto;
	padding-left: 8px;
}

.bottom {
	width: 100%;
	height: 60px;
	margin: 0px auto;
}

.registerBtn {
	width: 95%;
	height: 40px;
	margin-top: 20px;
	font-size: 20px;
	font-weight: 700;
	background-color: #B35D5D;
	color: #fff;
}

.loginRE {
	width: 95%;
	height: 40px;
	margin-top: 20px;
}

.content span {
	color:red;
	margin-right:3px;
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
					<div class="register">用户注册</div>
					<div class="content">
						<span class="spanLab">*</span>登录名:<span class="nameSpan"></span><input type="text" class="loginName" style="font-size: 22px;"
							value="" /> <br /> 
						<span class="spanLab">*</span>密码:<span class="pswSpan"></span><input type="password" class="password"
							style="font-size: 22px;" value="" /><br /> 
						<span class="spanLab">*</span>用户名:<span class="userNameSpan"></span><input type="text" class="username" style="font-size: 22px;"
							value="" /> <br /> 
						地址:<input type="text" class="address" style="font-size: 22px;"
							value="" /> <br />
						<span class="spanLab">*</span>邮箱:<span class="emailSpan"></span><input type="text" class="email" style="font-size: 22px;"
							value="" /> <br /> 
						电话:<input type="text" class="phone" style="font-size: 22px;"
							value="" /> <br /> 
						<!-- 网址:<input type="text" class="website" style="font-size: 22px;"
							value="" /> <br />  -->
						<button class="registerBtn">注&nbsp;&nbsp;册</button>
						<div class="loginRE">
							<a href="http://tiis.rainet.com.cn/index/login" style="float: left;">已有账号，返回登录!</a>
						</div>
					</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>