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
<title>河南锐利特物联网云灌溉系统--重置密码失败</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".logOut").html("");
		$(".returnIndex").click(function(){
			window.location.href="http://localhost:8080/TIIS/index/index";
		});
	});
</script>
<style type="text/css">
.maincontent {
	padding-top: 120px;
	height: 400px;
}

.passwordErrContent {
	width: 700px;
	height: 350px;
	margin: 0px auto;
	background: #fff;
}
.lefts {
	text-align: center;
}
.title {
	padding:10px;
	background: #c3c3c3;
	text-align: left;
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
				<div class="title">
					<div style="padding:5px;font-size:19px;"><span style="color:#000;font-size:24px;">Rainet云灌溉</span>&nbsp;&nbsp; - &nbsp;用户提示</div>
				</div>
				<div class="lefts">
					<img src="img/warning.png" alt="" width="150px" height="150px" />
				</div>
				<div class="rights">
					<h5 style="padding:5px;" style="color:#000;font-size:16px;">找回密码的链接错误或已过期。</h5>
					<br/>
					<br/>
					<a href="javascript:void(0);" class="returnIndex" style="font-size:18px;">返回云灌溉首页 >></a>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="/footer.jsp" />
</body>
</html>