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
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>河南锐利特物联网云灌溉系统--信息中心</title>
<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/index.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/page/pagination.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/messages.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=basePath%>js/messages.js"></script>
<script type="text/javascript" src="<%=basePath%>js/commUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>js/btnEffect.js"></script>
<script type="text/javascript" src="<%=basePath%>js/cityInfo.js"></script>
<script type="text/javascript" src="<%=basePath%>js/infoManager.js"></script>
<script type="text/javascript" src="<%=basePath%>js/queryMessages.js"></script>

<style type="text/css">
button {
	width: 150px;
	height: 35px;
	margin-left: 150px;
	color: #fff;
}
.bottom{
	padding-top:40px;
}
.equipmentInfo {
	width: 450px;
	height: 300px;
	display: none;
}
.listUserInfo {
	width: 500px;
	height: 320px;
	display: none;
}
.msgInfo {
	width: 450px;
	height: 300px;
	display: none;
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
					<li><a href="http://localhost:8080/TIIS/indexs/statistics" title=""><img
						src="images/icons/mainnav/statistics.png" alt="" /><span>统计分析</span>
					</a></li>
					<li><a href="http://localhost:8080/TIIS/indexs/messages" class="active"><img
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
							<!-- 项目列表 -->
							<div class="labList">
								<div class="labItem"><a class="projectB" href="javascript:void(0);" name = "projectMng">项目信息管理&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem"><a class="projectB" href="javascript:void(0);" name = "hostMng">主机信息管理&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem"><a class="projectB" href="javascript:void(0);" name = "equipmentMng">节点信息管理&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem"><a class="projectB" href="javascript:void(0);" name = "msgMng">报警信息管理&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
								<div class="labItem" style="border-bottom: 1px solid #c3c3c3;"><a class="projectB" href="javascript:void(0);" name = "userMng">用户信息管理&nbsp;&nbsp;<img src="images/elements/control/rightArrow.png"/></a></div>
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
				<span class="pageTitle"><span class="icon-screen"></span>信息中心</span>
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
							<a href="http://localhost:8080/TIIS/indexs/messages">信息中心</a>
						</li>
					</ul>
				</div>
			</div>

			<!-- Main content -->
			<div class="wrapper">
				<div class="rightContent">
					<div class="selectTitle" id="selectTitle">
						<span></span>
						<select class="province" style="width:130px;font-size:18px;"></select>
						<select class="city" style="margin-left:20px;width:130px;font-size:18px;"></select>
						<select class="projectSelect" style="width:150px;font-size:18px;"><option value="0000">-请选择项目-</option></select>
						<button class="projectButton" id="projectButton">查询</button>
						<button class="hostButton" id="hostButton">查询</button>
						<button class="equipmentButton" id="equipmentButton">查询</button>
						<button class="msgButton" id="msgButton">查询</button>
					</div>
					<div class="selectContent">
						<div class="hiddenpars" id="dyn">
							<table cellpadding="0" cellspacing="0" border="0" >
								<!-- 查询区域 -->
								<thead class="headContent">
								</thead>
								<!-- 列表区域 -->
								<tbody class="mngContent">
								</tbody>
							</table>
						</div>
 							<div class="userContext1" id="userContext" >
 								<div class="userCenter">
										<div class="userSpan" style="color:red;text-align:center;font-size:14px;">
										</div>
										<div class="pTitle">
											<div class="titleLab">登录名：</div>
											<div class="valueLab">
												<input type="text" disabled="disabled" class="loginName"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">用户名：</div>
											<div class="valueLab">
												<input type="text" class="userName"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">电话：</div>
											<div class="valueLab">
												<input type="text" class="phone"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">邮箱：</div>
											<div class="valueLab">
												<input type="text" class="email"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">地址：</div>
											<div class="valueLab">
												<input type="text" class="address"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">网址：</div>
											<div class="valueLab">
												<input type="text" class="website"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">创建时间：</div>
											<div class="valueLab">
												<input type="text" disabled="disabled" class="createtime"/>
											</div>
										</div>
										<div class="pTitle">
											<div class="titleLab">修改时间：</div>
											<div class="valueLab">
												<input type="text" disabled="disabled" class="modifytime"/>
											</div>
										</div>
									<button class="sideB bSea" id="modifyUser">修改用户</button>
									<br/>
									<a href="http://localhost:8080/TIIS/indexs/modifyPassword" style="margin-left:226px;text-decoration:underline;">修改密码</a>
								</div>
							</div>
					</div>
					<div class="pagination" id="pageIndex"></div>
					<!-- 华丽的分隔线 -->
					<div class="divider" >
						<span></span>
					</div>
				</div>
				<div class="projectInfo">
					<div class="loginTitle">
						<div class="span">项目信息</div>
						<div class="close">取消</div>
					</div>
					<div class="context">
						<!-- 用于标注当前页码 -->
						<input type="hidden" class="pageIndexHidden"/>
						<!-- 用于标注当前选中的id -->
						<input type="hidden" class="idHidden"/>
						<!-- 用于标注当前节点code -->
						<input type="hidden" class="codeHidden"/>
						<div class="pTitle">项目名称：<input type="text" class="projectName"/> </div>
						<div class="pTitle">负责单位：<input type="text" class="department"/> </div>
						<div class="pTitle">项目地址：<select class="provinceItem"></select><select class="cityItem"></select> </div>
						<div class="pTitle">详细地址：<input type="text" class="addressItem"/></div>
						<div class="pTitle">项目面积：<input type="text" class="area" style="width:100px;"/><input type="text" readonly="readonly" style="width:160px;font-size:14px;border:#000 solid 0px;" value="&nbsp;&nbsp;(平方米)"/> </div>
					</div>
					<div class="btn">
						<button class="sideB bLightBlue" id="modifyProject">修改项目信息</button>
					</div>
				</div>
				<div class="hostInfo">
					<div class="loginTitle">
						<div class="span">主机信息</div>
						<div class="close">取消</div>
					</div>
					<div class="context">
						<div class="pTitle">所属项目：<select class="projectOrder" style="width:268px;"></select></div>
						<div class="pTitle">主机编号：<input class="hostCode" type="text" style="width:100px;"/> <input type="text"  style="width:160px;font-size:14px;color:red;border:#000 solid 0px;" readonly="readonly" value="* 主机编号为8位纯数字!"/></div>
					</div>
					<div class="btn">
						<button class="sideB bSea" id="modifyHost">更新主机</button>
					</div>
				</div>
				<div class="equipmentInfo">
					<div class="loginTitle">
						<div class="span">节点信息</div>
						<div class="close">取消</div>
					</div>
					<div class="context">
						<div class="pTitle">所属项目：<select class="hostOrder" style="width:265px;"></select> </div>
						<div class="pTitle">节点名称：<input type="text" class="name"/> </div>
						<div class="pTitle">节点地址：<input type="text" style="width:100px;" class="code"/> <input type="text" style="width:153px;font-size:13px;color:red;border:#000 solid 0px;" readonly="readonly" value="* 节点地址为4位纯数字!"/></div>
						<div class="pTitle">流量参数：<input type="text" class="flowParameter" style="width:60px;" />
						 <input type="text" style="width:194px;font-size:13px;color:red;border:#000 solid 0px;" readonly="readonly" value="* 参数为[200,1000]区间的整数!"/></div>
					</div>
					<div class="btn">
						<button class="sideB bGreen" id="modifyEquipment">更新节点</button>
					</div>
				</div>
				<div class="listUserInfo">
					<div class="loginTitle">
						<div class="span">用户信息</div>
						<div class="close">取消</div>
					</div>

					<div class="context" style="height:220px;">
						<div class="pTitle" style="margin-top:10px;height:32px;line-height:32px;">
							登&nbsp;&nbsp;录&nbsp;&nbsp;名：<input type="text" readonly="readonly" class="lLoginName" style="width:140px;"/>
							用&nbsp;户&nbsp;名：<input type="text" class="lUserName" style="width:140px;"/> 
						</div>
						<div class="pTitle" style="height:32px;line-height:32px;">
							电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：<input type="text" class="lPhone" style="width:360px;"/></div>
						<div class="pTitle" style="height:32px;line-height:32px;">
							邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="text" class="lMail" style="width:360px;"/>
						</div>
						<div class="pTitle" style="height:32px;line-height:32px;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：<input type="text" class="lAddress"  style="width:360px;"/></div>
						<div class="pTitle" style="height:32px;line-height:32px;">网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：<input type="text" class="lWebSet"  style="width:360px;" /></div>
						<div class="pTitle" style="height:32px;line-height:32px;">
							<input type="hidden" class="lUserId" />
							创建时间：<input type="text" class="lCreateTime" readonly="readonly" style="width:135px;"/>
							修改时间：<input type="text" class="lModifyTime" readonly="readonly" style="width:135px;"/>
						</div>
					</div>
					<div class="btn">
						<button class="sideB bGreen" id="selectUser">更新用户信息</button>
					</div>
				</div>
				<div class="msgInfo">
					<div class="loginTitle">
						<div class="span">报警信息</div>
						<div class="close">取消</div>
					</div>
					<div class="context" style="margin-top:30px;">
						<div class="pTitle">类型：<input class="msgType" type="text" style="width:260px;" readonly="readonly"/></div>
						<div class="pTitle" style="height:45px;"><div style="margin-left:64px;width:48px;float: left;border:#c3c3c3 solid 0px;">内容：</div><textarea class="msgTexts" style="color:#000;width:265px;resize: none;border:#c3c3c3 solid 1px;float: left;" readonly="readonly" rows="3"></textarea></div>
						<div class="pTitle">状态：<input class="msgStatus" type="text" style="width:260px;" readonly="readonly"/></div>
						<div class="pTitle">时间：<input class="msgTime" type="text" style="width:260px;" readonly="readonly"/></div>
					</div>
				</div>
				<div class="userInfo">
					<!-- 用户标注用户session -->
					<input type="hidden" class="sessionHidden" value="${sessionScope.user.role}"/>
					<input type="hidden" class="userIdHidden" value="${sessionScope.user.id}"/>
				</div>
				<!-- footer -->
				<jsp:include page="footer.jsp" />
			</div>
			<!-- Main content ends -->

		</div>
	</body>
</html>