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
<title>河南锐利特物联网云灌溉系统--登录</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/login.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
</head>

<body>
	<!-- head -->
	<jsp:include page="head.jsp" />
	<!-- Main content -->
	<div class="main">
		<div class="maincontent">
			<div class="left"><img src="img/loginImg.jpg" alt="" width="430px" height="300px"/></div>
			<div class="right">
				<div class="rightContext">
				<div class="login">用户登录</div>
				<div class="content">
					登录名:<span class="nameSpan"></span><input type="text" class="username" style="font-size:22px;" value="" />
					<br/>
					密码:<span class="pswSpan"></span><input type="password" class="password" style="font-size:22px;" value="" />
					<button class="loginBtn">登&nbsp;&nbsp;录</button>
					<div class="register">
						<a href="javascript:void(0);" style="float: left;" class="forgetPassword">忘记密码?</a> <a href="http://localhost:8080/TIIS/indexs/register"
							style="float: right;">还没有注册?</a>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="footer.jsp" />

</body>
</html>