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
<title>河南锐利特物联网云灌溉系统--找回密码</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".logOut").html("");
		createCode();
		validLoginName();
		$(".validArea").click(function() {
			createCode();
		});
		//忘记密码,发送邮件找回
		$("#nextBtn").click(function(){
			var loginName = $(".loginName").val();
			var validAreaValue = $(".validArea").val();
			var validValue = $(".validCode").val();
			if($(".loginName").css("border")=="1px solid rgb(179, 93, 93)"){
			}else if(validValue==""){
				$(".nameSpan").html("验证码不能为空!");
				createCode();
			}else if(validAreaValue.toString().toUpperCase()!=validValue.toString().toUpperCase()){
				$(".nameSpan").html("验证码错误!");
				createCode();
			}else{
				var loadI = layer.load("正在发送找回密码验证邮件...");
				$.ajax({
					type : "post",
					url : "http://tiis.rainet.com.cn/user/i_forget_password.json",
					dataType : "json",
					contentType : "application/json",
					data : loginName,
					success : function(data){
						layer.close(loadI);
						var d = data.split(",");
						if(data=="userNameIsNull"){
							$(".nameSpan").html("此账号不存在或已被删除!");
						}else if(d[0]=="ok"){
							window.location.href="http://tiis.rainet.com.cn/indexs/3GForgotPassword?email="+d[1];
						}else{
							$(".nameSpan").html("发送邮件失败!");
						}
					},
					error : function(){
						$(".nameSpan").html("发送邮件异常!");
					}
				});
			}
		});
	});
	//验证码生成器	
	var code; 
	function createCode() {
		code = "";
		var codeLength = 4;//验证码的长度
		var selectChar = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
		for ( var i = 0; i < codeLength; i++) {
			var charIndex = Math.floor(Math.random() * 33);
			code += selectChar[charIndex];
		}
		if (code.length != codeLength) {
			createCode();
		}
		$(".validArea").val(code);
	}
	
	//验证输入的值
	function validLoginName(){
		var loginName ="";
		$(".validCode").focus(function(){
			$(this).css("border","#00B2EE solid 1px");
		});
		$(".validCode").blur(function(){
			var userObj = $(this);
			userObj.css("border","#B0B0B0 solid 1px");
		});
		
		$(".loginName").focus(function(){
			$(".nameSpan").html("");
			$(this).css("border","#00B2EE solid 1px");
		});
		$(".loginName").blur(function(){
			var userObj = $(this);
			userObj.css("border","#B0B0B0 solid 1px");
			loginName = userObj.val();
			if(loginName==""){
				$(".nameSpan").html("登录名不能为空!");
				userObj.css("border","#B35D5D solid 1px");
			}else{
				$.ajax({
					type : "post",
					url : "http://tiis.rainet.com.cn/index/validUserName.json",
					dataType : "json",
					contentType : "application/json",
					data : loginName,
					success : function(data){
						if(data=="null"){
							$(".nameSpan").html("此账号不存在或已被删除!");
							userObj.css("border","#B35D5D solid 1px");
						}else if(data=="ok"){
							userObj.css("border","#B0B0B0 solid 1px");
						}else{
							$(".nameSpan").html("校验登录名失败!");
						}
					},
					error : function(){
						alert("校验登录名异常!");
					}
				});
			}
		});
	}
</script>
<style type="text/css">
.mian{
width: 100%;
}
.maincontent {
	padding-top: 120px;
	height: 400px;
	width: 100%;
}

.passwordErrContent {
	width: 100%;
	height: 300px;
	margin: 0px auto;
	background: #fff;
}
.lefts {
	width: 95%;
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
	width:200px;
	height:26px;
	font-size:14px;
	line-height:14px;
	border:#c3c3c3 solid 1px ;
}
#nextBtn {
	width: 95%;
	height: 40px;
	font-size:18px;
	margin-left:5px;
	text-align: center;
}
.bottom {
	width: 100%;
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
					<span class="nameSpan" style="color:red;font-size:14px;"></span>
					<br/>
					登录名：<input type="text" class="loginName"/>
					<br/>
					验证码：<input type="text" class="validCode" style="width:105px;"/>
					<input type="text" class="validArea" style="width:70px;font-size:18px;color:red;border:#000 solid 0px;height:35px;letter-spacing:3px;line-height:35px; background: #c3c3c3" readonly="readonly" value=""/>
					<input type="text"  style="width:73px;border:#000 solid 0px;" readonly="readonly"/>
					<br/>
					<button class="sideB bLightBlue" id="nextBtn">下一步</button>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="/footer.jsp" />
</body>
</html>