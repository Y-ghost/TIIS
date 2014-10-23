/**
 * @author 				杨贵松
 * @date 				2014年3月26日 上午11:17:38
 * @Description: 		用户登录
 */
$(document).ready(function(){
	validLoginName();
	validPsw();
	validUserName();
	validEmail();
	//改变编辑框颜色
	inputFocus();
	//去掉退出字样
	$(".logOut").html("");
	//用户注册
	register();
	
});

/*********************************************** functions *************************************************/

/**
 * @Title: 				register 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:17
 * @Description: 		用户注册 
 * void 				返回
 */
function register(){
	$(".registerBtn").click(function(){
		var user = "{loginname:'"+$(".loginName").val()
					+"',password:'"+$(".password").val()
					+"',email:'"+$(".email").val()
					+"',username:'"+$(".username").val()
					+"',address:'"+$(".address").val()
					+"',phone:'"+$(".phone").val()+"'}";
		if($(".loginName").val()==""||$(".password").val()==""||$(".email").val()==""||$(".username").val()==""){
			alert("标红星的项不能为空!");
		}else if($(".loginname").css("border")=="1px solid rgb(179, 93, 93)"||$(".password").css("border")=="1px solid rgb(179, 93, 93)"||$(".email").css("border")=="1px solid rgb(179, 93, 93)"||$(".username").css("border")=="1px solid rgb(179, 93, 93)"){
			alert("用户信息填写错误!");
		}else{
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/index/register.json",
				dataType : "json",
				contentType : "application/json",
				data : user,
				success : function(data){
					if(data=="ok"){
						alert("注册成功，请登录!");
						window.location.href="http://localhost:8080/TIIS/index/login";
					}else{
						alert("注册失败，请稍后重试!");
					}
				},
				error : function(){
					alert("注册异常，请稍后重试!");
				}
			});
		}
	});
}

/**
 * @Title: 				validUserName 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:17
 * @Description: 		验证登录名 
 * void 				返回
 */
function validUserName(){
	$(".username").focus(function() {
		$(".userNameSpan").html("");
		$(this).css("border", "#00B2EE solid 1px");
	});
	$(".username").blur(function() {
		$(".userNameSpan").html("");
		$(".userNameSpan").css("color", "red");
		var pswObj = $(this);
		psw = pswObj.val();
		if (psw == "") {
			$(".userNameSpan").html("用户名不能为空!");
			pswObj.css("border", "#B35D5D solid 1px");
		} else {
			pswObj.css("border", "#B0B0B0 solid 1px");
		}
	});
}

/**
 * @Title: 				validEmail 
 * @author 				杨贵松
 * @date 				2014年4月23日 下午4:22:07
 * @Description: 		验证邮箱
 * void 				返回
 */
function validEmail(){
	$(".email").focus(function() {
		$(".emailSpan").html("");
		if ($(this).val() == "") {
			$(".emailSpan").html("邮箱用来找回密码，请填写有效的邮箱!");
			$(".emailSpan").css("color", "#00B2EE");
		}
		$(this).css("border", "#00B2EE solid 1px");
	});
	$(".email").blur(function() {
		$(".emailSpan").html("");
		$(".emailSpan").css("color", "red");
		var mailObj = $(this);
		email = mailObj.val();
		var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (email == "") {
			$(".emailSpan").html("邮箱不能为空!");
			mailObj.css("border", "#B35D5D solid 1px");
		}else if(!emailReg.test(email)) {
			$(".emailSpan").html("请输入有效的邮箱!");
			mailObj.css("border", "#B35D5D solid 1px");
		} else {
			mailObj.css("border", "#B0B0B0 solid 1px");
		}
	});
}
/**
 * @Title: 				validLoginName 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:17
 * @Description: 		验证登录名 
 * void 				返回
 */
function validLoginName(){
	var loginName = "";
	$(".loginName").focus(function() {
		$(".nameSpan").html("");
		if ($(this).val() == "") {
			$(".nameSpan").html("登录名是唯一存在的登录凭证，一旦注册将不能更改!");
			$(".nameSpan").css("color", "#00B2EE");
		}
		$(this).css("border", "#00B2EE solid 1px");
	});
	$(".loginName").blur(function() {
		$(".nameSpan").html("");
		$(".nameSpan").css("color", "red");
		var userObj = $(this);
		userObj.css("border", "#B0B0B0 solid 1px");
		loginName = userObj.val();
		if (loginName == "") {
			$(".nameSpan").html("登录名不能为空!");
			userObj.css("border", "#B35D5D solid 1px");
		} else {
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/index/validUserName.json",
				dataType : "json",
				contentType : "application/json",
				data : loginName,
				success : function(data) {
					if (data == "null") {
						userObj.css("border", "#B0B0B0 solid 1px");
					} else if (data == "ok") {
						$(".nameSpan").html("登录名已存在!");
						userObj.css("border", "#B35D5D solid 1px");
					}
				},
				error : function() {
					alert("校验登录名异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				validPsw 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:17
 * @Description: 		验证密码
 * void 				返回
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
 * @Title: 				inputFocus 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:06
 * @Description: 		设置input触发效果
 * void 				返回
 */
function inputFocus(){
	$(".address").focus(function(){
		$(this).css("border","#00B2EE solid 1px");
	});
	$(".address").blur(function(){
		$(this).css("border","#B0B0B0 solid 1px");
	});
	$(".phone").focus(function(){
		$(this).css("border","#00B2EE solid 1px");
	});
	$(".phone").blur(function(){
		$(this).css("border","#B0B0B0 solid 1px");
	});
}