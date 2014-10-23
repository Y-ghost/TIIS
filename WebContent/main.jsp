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
<meta name="keywords" content="节水灌溉,手机灌溉,智能灌溉,河南锐利特计算机科技有限公司,rainet,锐利特,云灌溉"/>
<meta name="Description" content="Rainet云灌溉系统(yun.rainet.com.cn)是由河南锐利特计算机科技有限公司研发的一款远程智能灌溉监控系统，锐利特科技一家从事物联网智能灌溉设备研发、生产、销售以及提供信息技术服务的高新技术企业。"/> 
<base href="<%=basePath%>" />
<link rel="shortcut icon" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>河南锐利特物联网云灌溉系统--首页</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/page/pagination.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=basePath%>js/commUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>js/btnEffect.js"></script>

<style type="text/css">
	.projectOrder{
		width:180px;
	}
	.hostOrder{
		width:180px;
	}
	
	button {
		width: 150px;
		height: 35px;
		margin-left: 150px;
		color: #fff;
	}
	#controlArea{
		height: 60px;
		width: 100%;
		border: #c3c3c3 1px solid;
		margin-top: 20px;
		margin-bottom: 10px;
		font-size:16px;
		font-weight: 700;
		text-align: center;
		line-height: 60px;
		display: inline-block;
	}
	#controlArea a{
		border-left:1px solid #d9d9d9;
		padding-left:20px;
		margin-right:20px;
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
					alt="" /><span> </span> </a><span>${sessionScope.user.loginname}</span><!-- <strong>1</strong> -->
				<ul class="leftUser">
					<li><a href="javascript:void(0);" title="" class="sProfile">个人中心</a></li>
					<li><a href="javascript:void(0);" title="" class="sMessages">消息</a></li>
					<li><a href="javascript:void(0);" title="" class="sSettings">设置</a></li>
					<li><a href="javascript:void(0);" title="" class="sLogout">退出</a></li>
				</ul>
			</div>
			<ul class="nav">
				<li><a href="http://localhost:8080/TIIS/index/main" title="" class="active"><img
						src="images/icons/mainnav/dashboard.png" alt="" /><span>状态监控</span>
				</a></li>
				<li><a href="http://localhost:8080/TIIS/indexs/statistics" title=""><img
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
						<div style="font-size: 16px;border-bottom: 1px solid #c3c3c3;width:85px;text-align:center; font-weight: bold; margin-left:10px;padding:5px;margin-bottom:10px;">项目列表:</div>
						<!-- 项目列表 -->
						<div class="projectList">
						</div>
						<div class="pagination">
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
			<span class="pageTitle"><span class="icon-screen"></span>状态监控</span>
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
					<li><a href="http://localhost:8080/TIIS/index/main">状态监控</a></li>
				</ul>
			</div>
		</div>

		<!-- Main content -->
		<div class="wrapper">
			<div class="middleNavR" id="controlArea">
				<a href="javascript:void(0);" style="border-left:0px solid #d9d9d9;padding-left:0px;" class="freshEStatus">刷新节点状态</a>
				<a href="javascript:void(0);" class="openOrCloseE" name="开阀">开关全部节点阀门</a>
				<a href="javascript:void(0);" class="setTimeLen">设置时间段</a>
				<a href="javascript:void(0);" class="setModel">设置控制模式</a>
				<a href="javascript:void(0);" class="setParameter">设置自控参数</a>
			</div>
			<!-- <ul class="middleNavR">
				<li><a href="javascript:void(0);" title="刷新节点状态" class="freshEStatus" id="tipLab"><img
						src="images/icons/middlenav/create.png" alt="" /> </a></li>
				<li><a href="javascript:void(0);" title="开启或者关闭节点阀门" class="openOrCloseE"  id="tipLab" name="开阀"><img
						src="images/icons/middlenav/upload0.png" alt="" /> </a></li>
				<li><a href="javascript:void(0);" title="设置时间段" class="setTimeLen" id="tipLab" ><img
						src="images/icons/middlenav/add.png" alt="" /> </a></li>
				<li><a href="javascript:void(0);" title="设置控制模式" class="setModel" id="tipLab"><img
						src="images/icons/middlenav/dialogs.png" alt="" /> </a>
				</li>
				<li><a href="javascript:void(0);" title="设置自控参数" class="setParameter" id="tipLab"><img
						src="images/icons/middlenav/stats.png" alt="" /> </a></li>
			</ul> -->

			<div class="equipmentList" style="height: 600px;">
			</div>
			<!-- footer -->
			<jsp:include page="footer.jsp" />
		</div>
		<!-- Main content ends -->

	</div>

	<div class="timeLen">
		<div class="loginTitle">
			<div class="span">时间段设置</div>
			<div class="close">取消</div>
		</div>
		<div class="context">
			<div class="pTitle" style="height:30px;margin-top:10px;">
				<input type="checkbox" class="clickVal1" style="width:20px;height:20px;"/>时间一
				<input type="checkbox" class="clickVal2" style="width:20px;height:20px;"/>时间二
				<input type="checkbox" class="clickVal3" style="width:20px;height:20px;"/>时间三
				<a href="javascript:void(0);" style="margin-left:80px;text-decoration: underline;" class="freshTimeLen">刷新</a>
			</div>
			<div class="pTitle">
				时间一：<select disabled="disabled" style="width:70px;" class="startHour1"></select>
				<select disabled="disabled" style="width:70px;" class="startMinute1"></select> --
				<select disabled="disabled" style="width:70px;" class="endHour1"></select>
				<select disabled="disabled" style="width:70px;" class="endMinute1"></select>
			</div>
			<div class="pTitle">
				时间二：<select disabled="disabled" style="width:70px;" class="startHour2"></select>
				<select disabled="disabled" style="width:70px;" class="startMinute2"></select> --
				<select disabled="disabled" style="width:70px;" class="endHour2"></select>
				<select disabled="disabled" style="width:70px;" class="endMinute2"></select>
			</div>
			<div class="pTitle">
				时间三：<select disabled="disabled" style="width:70px;" class="startHour3"></select>
				<select disabled="disabled" style="width:70px;" class="startMinute3"></select> --
				<select disabled="disabled" style="width:70px;" class="endHour3"></select>
				<select disabled="disabled" style="width:70px;" class="endMinute3"></select> 
			</div>
		</div>
		<div class="btn">
			<button class="sideB bLightBlue" id="setTimeLen">设置时段</button>
		</div>
	</div>
	<div class="model">
		<div class="loginTitle">
			<div class="span">控制模式设置</div>
			<div class="close">取消</div>
		</div>
		<div class="context">
			<div class="pTitle" style="height:30px;margin-top:10px;">
				<a href="javascript:void(0);" style="text-decoration: underline;" class="freshModel">刷新</a>
			</div>
			<div class="pTitle" style="height:30px;margin-top:10px;">
				<input type="radio" name="radioModel" value="F0" checked="checked" style="width:20px;height:20px;"/>自动模式<br/>
				<input type="radio" name="radioModel" value="F1" style="width:20px;height:20px;"/>时段模式<br/>
				<input type="radio" name="radioModel" value="F2" style="width:20px;height:20px;"/>手动模式
			</div>
		</div>
		<div class="btn">
			<button class="sideB bLightBlue" id="setRadioModel">设置模式</button>
		</div>
	</div>
	<div class="parameter">
		<div class="loginTitle">
			<div class="span">自控参数设置</div>
			<div class="close">取消</div>
		</div>
		<div class="context">
			<div class="pTitle" >
				<a href="javascript:void(0);" style="text-decoration: underline;" class="freshParameter">刷新</a>
			</div>
			<div class="pTitle">
				土壤湿度：<input class="soilHumidity" id="inputText" type="text" style="width:80px;"/> % <input type="text"  style="width:164px;font-size:14px;color:red;border:#000 solid 0px;" readonly="readonly" value=" * 湿度范围为[10~100]!"/>
			</div>
			<div class="pTitle" >
				湿度上限：<input class="upTemperature" id="inputText" type="text" style="width:80px;"/> ℃ <input type="text"  style="width:160px;font-size:14px;color:red;border:#000 solid 0px;" readonly="readonly" value="* 温度范围为[-50~60]!"/>
			</div>
			<div class="pTitle" >
				温度下限：<input class="downTemperature" id="inputText" type="text" style="width:80px;"/> ℃ <input type="text"  style="width:160px;font-size:14px;color:red;border:#000 solid 0px;" readonly="readonly" value="* 温度范围为[-50~60]!"/>
			</div>
		</div>
		<div class="btn">
			<button class="sideB bLightBlue" id="setParameter">设置自控参数</button>
		</div>
	</div>
	<!-- 弹框end -->
</body>
</html>