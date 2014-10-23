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
<title>河南锐利特物联网云灌溉系统--修改密码</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/md5.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".logOut").html("");
		validPsw();
		if($(".userName").html()==""){
			window.location.href="http://localhost:8080/TIIS/indexs/redirectLogin";
		}
		//忘记密码,发送邮件找回
		$("#modifyPsd").click(function(){
			var passwordTemp = $(".passwordTemp").val();
			var oldPassword = hex_md5($(".oldPassword").val()).toUpperCase();
			var password = $(".password").val();
			var rePassword = $(".rePassword").val();
			var userName = $(".userName").html();
			if($(".oldPassword").val()==""){
				$(".pswSpan").html("请输入原密码!");
			}else if(password==""){
				$(".pswSpan").html("新密码不能为空!");
			}else if((passwordTemp!=oldPassword)||$(".oldPassword").css("border")=="1px solid rgb(179, 93, 93)"){
				$(".pswSpan").html("原密码输入错误!");
			}else if(rePassword!=password){
				$(".pswSpan").html("两次密码输入的不一样!");
			}else if($(".password").css("border")=="1px solid rgb(179, 93, 93)"){
				$(".pswSpan").html("密码输入错误!");
			}else{
				var loadI = layer.load("正在修改登录密码...");
				$.ajax({
					type : "post",
					url : "http://localhost:8080/TIIS/user/modifyPassword.json",
					dataType : "json",
					contentType : "application/json",
					data : userName+","+password,
					success : function(data){
						layer.close(loadI);
						if(data=="null"){
							$(".pswSpan").html("该用户不存在或已被删除!");
						}else if(data=="ok"){
							alert("修改密码成功，需重新登录!");
							window.location.href="http://localhost:8080/TIIS/indexs/redirectLogin";
						}
					},
					error : function(){
						alert("修改密码异常!");
					}
				});
			}
		});
	});
	
	//验证密码
	function validPsw(){
		var psw = "";
		$(".password").focus(function(){
			$(".pswSpan").html("");
			if($(".password").val()==""){
				$(".pswSpan").html("(6-16个字符，请使用字母加数字或符号的组合密码。)");
				$(".pswSpan").css("color","#00B2EE");
			}
			$(this).css("border","#00B2EE solid 1px");
		});
		$(".password").blur(function(){
			$(".pswSpan").html("");
			$(".pswSpan").css("color","red");
			var pswObj = $(this);
			psw = pswObj.val();
			if(psw==""){
				$(".pswSpan").html("密码不能为空!");
				pswObj.css("border","#B35D5D solid 1px");
			}else{
				validPassword();
			}
		});
		$(".rePassword").focus(function(){
			$(this).css("border","#00B2EE solid 1px");
		});
		$(".rePassword").blur(function(){
			$(this).css("border","#B0B0B0 solid 1px");
		});
		$(".oldPassword").focus(function(){
			$(this).css("border","#00B2EE solid 1px");
		});
		$(".oldPassword").blur(function(){
			var pswObj = $(this);
			psw = pswObj.val();
			if(psw==""){
				$(".pswSpan").html("原密码不能为空!");
				pswObj.css("border","#B35D5D solid 1px");
			}else{
				$(this).css("border","#B0B0B0 solid 1px");
			}
		});
	}

	//验证密码 
	function validPassword() {
		var password = $(".password").val();
		var num = 0;
		var number = 0 ;
		var letter = 0 ;
		var bigLetter = 0 ;
		var chars = 0 ;
		
		if (password.search(/[0-9]/) != -1) {
			num += 1;
			number =1;
		}
		if (password.search(/[A-Z]/) != -1) {
			num += 1;
			bigLetter = 1 ;
		}
		if (password.search(/[a-z]/) != -1) {
			num += 1;
			letter = 1 ;
		}
		if (password.search(/[^A-Za-z0-9]/) != -1) {
			num += 1;
			chars = 1 ;
		}
		if (num >= 2 && (password.length >= 6 && password.length <= 16)) {
			$(".pswSpan").html("");
			$(".password").css("border","#B0B0B0 solid 1px");
		}else if(password.length < 6 || password.length > 16){
			$(".pswSpan").html("密码由6-16个字符组成!");
			$(".password").css("border","#B35D5D solid 1px");
		}else if(num == 1){
			if(number==1){
				$(".pswSpan").html("不能全为数字!");
			}
			if(letter==1){
				$(".pswSpan").html("不能全为字母!");
			}
			if(bigLetter==1){
				$(".pswSpan").html("不能全为字母!");
			}
			if(chars==1){
				$(".pswSpan").html("不能全为字符!");
			}
			$(".password").css("border","#B35D5D solid 1px");
		}
	}
</script>
<style type="text/css">
.maincontent {
	padding-top: 120px;
	height: 400px;
}

.passwordErrContent {
	width: 700px;
	height: 360px;
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
#modifyPsd {
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
					<div style="padding:5px;font-size:19px;"><span style="color:#000;font-size:24px;">Rainet云灌溉</span>&nbsp;&nbsp; - &nbsp;修改密码</div>
				</div>
				<!-- <div class="line">
				</div> -->
				<div class="rights">
					<span class="pswSpan" style="color:red;font-size:14px;"></span>
					<br/>
					<input type="hidden" class="passwordTemp" style="color:red;" value="${sessionScope.user.password}"/>
					登录名：<span class="userName" style="padding-left:10px;font-size:22px;" >${sessionScope.user.loginname}</span>
					<input type="text"  style="width:130px;border:#000 solid 0px;" readonly="readonly"></input><br/>
					<input type="text" style="width:50px;border:#000 solid 0px;font-size:22px;" readonly="readonly" />原密码：<input type="password" class="oldPassword"/>
					<br/>
					<input type="text" style="width:50px;border:#000 solid 0px;font-size:22px;" readonly="readonly" />新密码：<input type="password" class="password"/>
					<br/>
					重新输入新密码：<input type="password" class="rePassword"/>
					<br/>
					<br/>
					<button class="sideB bLightBlue" id="modifyPsd">修改密码</button>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="/footer.jsp" />
</body>
</html>