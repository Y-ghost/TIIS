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
<title>河南锐利特物联网智能灌溉系统</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet"
	type="text/css" />

<style type="text/css">
.maincontent {
	padding-top: 120px;
	height: 600px;
}

.passwordErrContent {
	width: 900px;
	height: 450px;
	margin: 0px auto;
	background: #fff;
}
.lefts {
	text-align: center;
}
.rights {
	text-align: center;
}

.bottom {
	width: 450px;
	height: 60px;
	margin: 0px auto;
}
</style>
</head>

<body>
	<!-- head -->
	<jsp:include page="/head.jsp" />
	<!-- Main content -->
	<div class="main">
		<div class="maincontent">
			<div class="passwordErrContent">
				<div class="lefts">
					<img src="img/500.png" alt="" width="430px" height="300px" />
				</div>
				<div class="rights">
					<br/>
					<h3 style="padding:5px;">服务器发生错误!</h3>
					<h6 style="padding:5px;">抱歉，出错了，但是有人在修理了!</h6>
					<a href="http://localhost:8080/TIIS/index/index" style="font-size:18px;">返回首页  >></a>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="/footer.jsp" />
</body>
</html>