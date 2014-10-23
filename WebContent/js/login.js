/**
 * @author 				杨贵松
 * @date 				2014年3月14日 下午11:17:38
 * @Description: 		用户登录
 */
var i = 0 ;
$(document).ready(function(){
	/* 判断用户名密码 */
	valid();
	$(".username").focus();
	/* 用户登录 */
	$(".loginBtn").click(function() {
		if(i == 0){
			i = 1;
			login("web");
		}
	});
	$(".wapLoginBtn").click(function() {
		if(i == 0){
			i = 1;
			login("wap");
		}
	});
	$("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
        	if(i == 0){
        		i = 1;
    			login("web");
    		}
        }
    });
	//去除退出字样
	$(".logOut").html("");
	//忘记密码
	$(".forgetPassword").click(function(){
		window.location.href="http://localhost:8080/TIIS/indexs/forgetPasswordMail";
	});
	
});

/*********************************************** functions *************************************************/
/**
 * 判断用户名密码
 */
function valid(){
	validLoginName();
	validPsw();
}

/**
 * 判断用户名
 */
function validLoginName(){
	var loginName ="";
	$(".username").focus(function(){
		$(".nameSpan").html("");
		$(this).css("border","#00B2EE solid 1px");
	});
	$(".username").blur(function(){
		var userObj = $(this);
		userObj.css("border","#B0B0B0 solid 1px");
		loginName = userObj.val();
		if(loginName==""){
			$(".nameSpan").html("登录名不能为空!");
			userObj.css("border","#B35D5D solid 1px");
		}else{
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/index/validUserName.json",
				dataType : "json",
				contentType : "application/json",
				data : loginName,
				success : function(data){
					if(data=="null"){
						$(".nameSpan").html("登录名不存在!");
						userObj.css("border","#B35D5D solid 1px");
					}else if(data=="ok"){
						userObj.css("border","#B0B0B0 solid 1px");
					}
				},
				error : function(){
					alert("校验登录名异常!");
				}
			});
		}
	});
}

/**
 * 判断密码
 */
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
}

/**
 * @Title: 				validPsw 
 * @author 				杨贵松
 * @date 				2014年3月25日 上午3:03:23
 * @Description: 		验证密码 
 * void 				返回
 */
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
/**
 * 用户登录
 */
function login(mark){
	if($(".username").css("border")=="1px solid rgb(179, 93, 93)"||$(".password").css("border")=="1px solid rgb(179, 93, 93)"){
		alert("用户名或密码输入错误!");
		i = 0;
	}else if($(".username").val()==""||$(".password").val()==""){
		alert("用户名和密码不能为空!");
		i = 0;
	}else {
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/index/login.json",
			dataType : "json",
			contentType : "application/json",
			data : $(".username").val()+","+$(".password").val(),
			success : function(data){
				i = 0;
				if(data=="null"){
					$(".nameSpan").html("用户不存在!");
					userObj.css("border","#B35D5D solid 1px");
				}else if(data=="pswError"){
					$(".password").val("");
					$(".pswSpan").html("密码错误!");
					$(".pswSpan").css("color","red");
					pswObj.css("border","#B35D5D solid 1px");
				}else if(data=="ok" && mark == "web"){
					window.location.href="http://localhost:8080/TIIS/index/main";
				}else if(data=="ok" && mark == "wap"){
					window.location.href="http://localhost:8080/TIIS/index/main";
				}else{
					alert("登录失败!");
				}
			},
			error : function(){
				alert("登录异常!");
				i = 0;
			}
		});
	}
}
