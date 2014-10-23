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
<title>河南锐利特物联网云灌溉系统--系统设置</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/page/pagination.css" rel="stylesheet"	type="text/css" />
<link href="<%=basePath%>css/index.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=basePath%>js/btnEffect.js"></script>
<script type="text/javascript" src="<%=basePath%>js/systemSet.js"></script>
<script type="text/javascript" src="<%=basePath%>js/commUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>js/index.js"></script>
<script type="text/javascript" src="<%=basePath%>js/cityInfo.js"></script>

<style type="text/css">
.selectTimeButton {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #84c4dd inset;
	-webkit-box-shadow: 0 1px 2px 0 #84c4dd inset;
	box-shadow: 0 1px 2px 0 #84c4dd inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5897c3;
	background: -moz-linear-gradient(top, #6db5d5 0%, #4a91c0 100%);
	background: -webkit-linear-gradient(top, #6db5d5 0%, #4a91c0 100%);
	color: #fff;
}

.selectTimeButton1 {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #84c4dd inset;
	-webkit-box-shadow: 0 1px 2px 0 #84c4dd inset;
	box-shadow: 0 1px 2px 0 #84c4dd inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5897c3;
	background: #4a91c0;
	color: #fff;
}

.setTimeButton {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #95b8b6 inset;
	-webkit-box-shadow: 0 1px 2px 0 #95b8b6 inset;
	box-shadow: 0 1px 2px 0 #95b8b6 inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5a837e;
	background: -moz-linear-gradient(top, #7ca6a3 0%, #547b76 100%);
	background: -webkit-linear-gradient(top, #7ca6a3 0%, #547b76 100%);
	color: #fff;
}

.setTimeButton1 {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #95b8b6 inset;
	-webkit-box-shadow: 0 1px 2px 0 #95b8b6 inset;
	box-shadow: 0 1px 2px 0 #95b8b6 inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5a837e;
	background: #547b76;
	color: #fff;
}
.labList {
	height: 220px;
}

.labItem {
	height: 30px;
	padding-left: 40px;
	padding-top: 8px;
	border-top: 1px solid #c3c3c3;
	margin-left: 30px;
	margin-right: 30px;
}

.projectB {
	color: #626262;
	font-size: 15px;
	text-decoration: none;
	font-weight: 700;
}
.selectTitle {
	height: 60px;
	width: 100%;
	border: #c3c3c3 1px solid;
	margin-bottom: 10px;
	text-align: center;
	line-height: 60px;
	display: inline-block;
}
.selectTitle span {
	font-size: 16px;
	text-decoration: none;
	font-weight: 700;
	margin-right: 10px;
}
.selectContent {
	padding-top: 10px;
	height: 420px;
}

.pagination {
	height: 30px;
}

.container table {
	width: 100%;
}
.container tr {
	border: #c3c3c3 1px solid;
}

.container tr th {
	border: #c3c3c3 1px solid;
	color: #fff;
	background: #5B5B5B;
}

.container tr td {
	border: #c3c3c3 1px solid;
	background: #fff;
}

.headTD {
	text-align: center;
}

.endTD {
	text-align: center;
}
.divider {
	margin-bottom:20px;
}

.projectButton {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #84c4dd inset;
	-webkit-box-shadow: 0 1px 2px 0 #84c4dd inset;
	box-shadow: 0 1px 2px 0 #84c4dd inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5897c3;
	background: -moz-linear-gradient(top, #6db5d5 0%, #4a91c0 100%);
	background: -webkit-linear-gradient(top, #6db5d5 0%, #4a91c0 100%);
	color: #fff;
}

.projectButton1 {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #84c4dd inset;
	-webkit-box-shadow: 0 1px 2px 0 #84c4dd inset;
	box-shadow: 0 1px 2px 0 #84c4dd inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5897c3;
	background: #4a91c0;
	color: #fff;
}

.pushNoticeButton {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #84c4dd inset;
	-webkit-box-shadow: 0 1px 2px 0 #84c4dd inset;
	box-shadow: 0 1px 2px 0 #84c4dd inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5897c3;
	background: -moz-linear-gradient(top, #6db5d5 0%, #4a91c0 100%);
	background: -webkit-linear-gradient(top, #6db5d5 0%, #4a91c0 100%);
	color: #fff;
}

.pushNoticeButton1 {
	display: inline;
	-moz-box-shadow: 0 1px 2px 0 #84c4dd inset;
	-webkit-box-shadow: 0 1px 2px 0 #84c4dd inset;
	box-shadow: 0 1px 2px 0 #84c4dd inset;
	width: 130px;
	font-size: 18px;
	margin-left: 20px;
	height: 30px;
	border: 1px solid #5897c3;
	background: #4a91c0;
	color: #fff;
}
</style>
</head>

<body>
	<!-- head -->
	<jsp:include page="head.jsp" />
	<!-- head ends -->

	<!-- left -->
	<div id="sidebar">
		<div class="mainNav">
			<div class="user">
				<a title="" class="leftUserDrop"><img src="images/user.png"
					alt="" /><span> </span> </a><span>${sessionScope.user.loginname}</span>
				<ul class="leftUser">
					<li><a href="#" title="" class="sProfile">个人中心</a></li>
					<li><a href="#" title="" class="sMessages">消息</a></li>
					<li><a href="#" title="" class="sSettings">设置</a></li>
					<li><a href="#" title="" class="sLogout">退出</a></li>
				</ul>
			</div>
			<ul class="nav">
				<li><a href="http://localhost:8080/TIIS/index/main" title=""><img
						src="images/icons/mainnav/dashboard.png" alt="" /><span>状态监控</span>
				</a></li>
				<li><a href="http://localhost:8080/TIIS/indexs/statistics"
					title=""><img src="images/icons/mainnav/statistics.png" alt="" /><span>统计分析</span>
				</a></li>
				<li><a href="http://localhost:8080/TIIS/indexs/messages"><img
						src="images/icons/middlenav/dialogs.png" alt="" /><span>信息中心</span>
				</a></li>
				<li><a href="http://localhost:8080/TIIS/indexs/systemSet"
					title="" class="active"><img
						src="images/icons/color/config.png" alt="" /><span>系统设置</span> </a></li>
			</ul>
		</div>

		<div class="secNav">
			<div class="secWrapper">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							当前时间: <span class="nowTime"></span>
						</div>
					</div>
				</div>

				<div id="tab-container" class="tab-container">

					<!-- 华丽的分隔线 -->
					<div class="divider">
						<span></span>
					</div>

					<div id="general">
						<!-- 项目列表 -->
							<div class="labList">
								<div class="labItem"><a class="projectB" href="javascript:void(0);" name = "addProject">添加新项目&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem"><a class="projectB" href="javascript:void(0);" name = "addHost">添加新主机&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem" style="padding-left: 33px;"><a class="projectB" href="javascript:void(0);" name = "addChirld">搜索注册节点&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem" style="padding-left: 33px;border-bottom: 1px solid #c3c3c3;"><a class="projectB" href="javascript:void(0);" name = "setData">节点数据赋值&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
							</div>
					</div>

				</div>
				<!-- 华丽的分隔线 -->
				<div class="divider">
					<span></span>
				</div>

			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!-- left ends -->

	<!-- right -->
	<div id="content">
		<div class="contentTop">
			<span class="pageTitle"><span class="icon-screen"></span>系统设置</span>
			<ul class="quickStats">
				<li><a href="" class="blueImg"><img
						src="images/icons/quickstats/plus.png" alt="" /> </a>
					<div class="floatR">
						<strong class="blue">5489</strong><span>访问量</span>
					</div></li>
				<li><a href="" class="redImg"><img
						src="images/icons/quickstats/author.png" alt="" /> </a>
					<div class="floatR">
						<strong class="blue">4658</strong><span>在线人数</span>
					</div></li>
				<li><a href="" class="greenImg"><img
						src="images/icons/quickstats/money.png" alt="" /> </a>
					<div class="floatR">
						<strong class="blue">1289</strong><span>项目数</span>
					</div></li>
			</ul>
			<div class="clear"></div>
		</div>

		<!-- Breadcrumbs line -->
		<div class="breadLine">
			<div class="bc">
				<ul id="breadcrumbs" class="breadcrumbs">
					<li><a href="http://localhost:8080/TIIS/indexs/systemSet">系统设置</a></li>
				</ul>
			</div>
		</div>

		<!-- Main content -->
		<div class="wrapper">
			<input type="hidden" class="roleHidden" value="${sessionScope.user.role}" />
			<div class="container" style="height:450px;margin-top:30px;border:#000 0px solid;">
				<div style="font-size:14px;color:red;margin-top:10px;">
					<span class="warnningLab"></span>
				</div>
				<div style="height:450px;margin-top:30px;margin-bottom:50px;overflow-x:auto;overflow-y:auto;" id="container">
					<div style="text-align: center;font-size:16px;">
						<img src="img/yun.png" style="width:200px;height:200px;margin-top:60px;"/>
						<p style="font-weight: bold;">请进行新项目、主机、几点等信息注册，同时对更换的节点进行赋值操作!</p>
					</div>
				</div> 
			</div>
					<!-- 华丽的分隔线 -->
					<div class="divider" >
						<span></span>
					</div>
			<!-- footer -->
			<jsp:include page="footer.jsp" />
		</div>
		<!-- Main content ends -->

	</div>
</body>
</html>