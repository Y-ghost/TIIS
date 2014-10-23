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
<meta http-equiv="Refresh" content="3;URL=http://localhost:8080/TIIS/indexs/systemSet" />
<title>河南锐利特物联网云灌溉系统--成功跳转</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function run(){
	var s = document.getElementById("dd");
	if(s.innerHTML == 0){
		return false;
	}
	s.innerHTML = s.innerHTML * 1 - 1;
}
window.setInterval("run();", 1000);
</script>
</head>

<body style="height:100%;">
	<!-- head -->
	<jsp:include page="head.jsp" />
	<center>
		<div style="padding-top:80px;">
			<font style="color:red;font-size:11pt;"> 
				上传成功！
			</font>
			<span id="dd" style="color:blue;font-size:11pt;">3</span>
			<font style="color:red;font-size:11pt;">
				秒后关闭 <br/> 
				如果没有自动关闭，请点击 <a href="http://localhost:8080/TIIS/indexs/systemSet" style="color:blue;font-size:11pt;cursor:pointer;">这里<label style="font-size:8pt;cursor:pointer;"></label></a>
			</font>
		</div>
	</center>

</body>
</html>