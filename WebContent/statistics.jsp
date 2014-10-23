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
		<base href="<%=basePath%>"/>
		<link rel="shortcut icon" href="images/favicon.ico" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
		<title>河南锐利特物联网云灌溉系统--统计分析</title>
		<link href="css/styles.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/layer/layer.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/highcharts.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/statistics.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/commUtil.js"></script>
		
		<style type="text/css">
			.statisticStyle{
				margin:10px;
				padding:5px;
				border:#c3c3c3 1px solid;
			}
			.statisticTitle{
				font-size: 14px;
				font-weight: bold;
				padding:5px;
			}
			.styleRadio{
				text-align: center;
				line-height:40px;
				height:40px;
			}
			.statisticObject{
				margin:10px;
				padding:5px;
				border:#c3c3c3 1px solid;
			}
			.objectSelect{
				text-align: center;
				padding-top:10px;
				height:30px;
			}
			.projectVal{
				width:180px;
				cursor:pointer;
			}
			.statisticCircle{
				margin:10px;
				padding:5px;
				border:#c3c3c3 1px solid;
			}
			.circleText{
				text-align: center;
				padding-top:10px;
				height:80px;
			}
			.circleYear{
				width:75px;
				cursor:pointer;
			}
			.circleMouth{
				width:65px;
				cursor:pointer;
			}
			.statisticButton{
				padding-top:25px;
				margin-left:13px;
				width:200px;
				height:40px;
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
							alt="" /><span>
					</span>
					</a><span>${sessionScope.user.loginname}</span>
					<ul class="leftUser">
						<li>
							<a href="#" title="" class="sProfile">个人中心</a>
						</li>
						<li>
							<a href="#" title="" class="sMessages">消息</a>
						</li>
						<li>
							<a href="#" title="" class="sSettings">设置</a>
						</li>
						<li>
							<a href="#" title="" class="sLogout">退出</a>
						</li>
					</ul>
				</div>
				<ul class="nav">
					<li><a href="http://localhost:8080/TIIS/index/main" title=""><img
						src="images/icons/mainnav/dashboard.png" alt="" /><span>状态监控</span>
					</a></li>
					<li><a href="http://localhost:8080/TIIS/indexs/statistics" title="" class="active"><img
						src="images/icons/mainnav/statistics.png" alt="" /><span>统计分析</span>
					</a></li>
					<li><a href="http://localhost:8080/TIIS/indexs/messages"><img
						src="images/icons/middlenav/dialogs.png" alt="" /><span>信息中心</span>
					</a></li>
					<li><a href="http://localhost:8080/TIIS/indexs/systemSet" title=""><img
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
							<!-- 统计条件 -->
							<div class="statisticStyle">
								<span class="statisticTitle">统计方式</span>
								<br/>
								<div class="styleRadio">
									<input type="radio" name="styleVal" value="0" style="cursor:pointer;"/> 年
									<input type="radio" name="styleVal" value="1" checked="checked" style="margin-left:30px;cursor:pointer;"/> 月
								</div>
							</div>
							<!-- 统计对象 -->
							<div class="statisticObject">
								<span class="statisticTitle">统计对象</span>
								<br/>
								<div class="objectSelect">
									<select class="projectVal">
									</select>
								</div>
							</div>
							<!-- 统计周期 -->
							<div class="statisticCircle">
								<span class="statisticTitle">统计周期</span>
								<br/>
								<div class="circleText">
									开始:&nbsp;&nbsp;<select class="circleYear" id="circleYear1"></select>
									<select class="circleMouth" id="circleMouth1"></select>
									<br/>
									<br/>
									结束:&nbsp;&nbsp;<select class="circleYear" id="circleYear2"></select>
									<select class="circleMouth" id="circleMouth2"></select>
								</div>
							</div>
							<!-- 统计按钮 -->
							<div class="statisticButton">
								<button class="sideB bLightBlue" id="statisticSubmit">统计</button>
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
				<span class="pageTitle"><span class="icon-screen"></span>统计分析</span>
				<ul class="quickStats">
					<li>
						<a href="" class="blueImg"><img
								src="images/icons/quickstats/plus.png" alt="" />
						</a>
						<div class="floatR">
							<strong class="blue">5489</strong><span>访问量</span>
						</div>
					</li>
					<li>
						<a href="" class="redImg"><img
								src="images/icons/quickstats/author.png" alt="" />
						</a>
						<div class="floatR">
							<strong class="blue">4658</strong><span>在线人数</span>
						</div>
					</li>
					<li>
						<a href="" class="greenImg"><img
								src="images/icons/quickstats/money.png" alt="" />
						</a>
						<div class="floatR">
							<strong class="blue">1289</strong><span>项目数</span>
						</div>
					</li>
				</ul>
				<div class="clear"></div>
			</div>

			<!-- Breadcrumbs line -->
			<div class="breadLine">
				<div class="bc">
					<ul id="breadcrumbs" class="breadcrumbs">
						<li>
							<a href="http://localhost:8080/TIIS/indexs/statistics">统计分析</a>
						</li>
					</ul>
				</div>
			</div>

			<!-- Main content -->
			<div class="wrapper">
				<div style="font-size:14px;color:red;margin-top:10px;">
					<span class="warnningLab"></span>
				</div>
				<div style="height:450px;margin-top:30px;margin-bottom:50px;overflow-x:auto;overflow-y:auto;" id="container">
					<div style="text-align: center;font-size:16px;">
						<img src="img/yun.png" style="width:200px;height:200px;margin-top:60px;"/>
						<p style="font-weight: bold;">请选择您的统计条件，进行统计!</p>
					</div>
				</div>

				<!-- footer -->
				<jsp:include page="footer.jsp" />
			</div>
			<!-- Main content ends -->

		</div>
	</body>
</html>