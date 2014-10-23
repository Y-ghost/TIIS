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
<title>河南锐利特物联网云灌溉系统--找回密码邮件已发送成功</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".logOut").html("");
		//忘记密码,发送邮件找回
		$("#nextBtn").click(function(){
			parent.window.opener = null;
			parent.window.open("", "_self");
			parent.window.close();
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
	height: 300px;
	margin: 0px auto;
	background: #fff;
}
.lefts {
	padding:10px;
	background: #c3c3c3;
	text-align: left;
}
/* .line {
	margin-left: 50px;
	margin-right: 50px;
	border-bottom:#c3c3c3 solid 1px ;
} */
.rights {
	font-size:19px;
	text-align: center;
}
.rights input {
	padding-left:3px;
	margin:10px;
	width:260px;
	height:26px;
	font-size:14px;
	line-height:14px;
	border:#c3c3c3 solid 1px ;
}
#nextBtn {
	width: 180px;
	height: 40px;
	font-size:18px;
	margin-left:260px;
	text-align: center;
}
.bottom {
	width: 450px;
	height: 60px;
	margin: 0px auto;
}
.rights input{
	padding-left:3px;
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
					<div style="padding:5px;font-size:19px;"><span style="color:#000;font-size:24px;">Rainet云灌溉</span>&nbsp;&nbsp; - &nbsp;找回密码</div>
				</div>
				<!-- <div class="line">
				</div> -->
				<div class="rights">
					<br/>
					<br/>
					<br/>
					<span style="color:#000;font-size:16px;">您的申请已提交成功，请查看您的<span style="color:red;">${sessionScope.email}</span>邮箱！</span>
					<br/>
					<br/>
					<br/>
					<br/>
					<button class="sideB bLightBlue" id="nextBtn">关闭此页</button>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="/footer.jsp" />
</body>
</html>