/**
 * @author 				杨贵松
 * @date 				2014年4月1日 上午11:17:38
 * @Description: 		信息中心
 */
//全局序号
var num = 0;
$(document).ready(function(){
	/************************************** 退出 *************************************/
	exit();
	//初始化信息中心，默认为项目信息管理
	initProjectList();
	/************************************** 获取服务器当前时间 *************************/
	nowTime();
	//关闭弹框
	closeOnclick();
	
	//菜单的导航栏鼠标划过效果
	$(".projectB").hover(function() {
		$(this).css("color", "#2b6893");
		$(this).css("font-size", "16px");
		$(this).find("img").attr("src","images/elements/control/headSubmit.png");
	}, function() {
		$(this).css("color", "#626262");
		$(this).css("font-size", "15px");
		$(this).find("img").attr("src","images/elements/control/rightArrow.png");
	});
	
	//鼠标滑过按钮效果
	$("#projectButton").btnEffect("projectButton","projectButton1",true);
	$("#hostButton").btnEffect("hostButton","hostButton1",true);
	$("#equipmentButton").btnEffect("equipmentButton","equipmentButton1",true);
	$("#msgButton").btnEffect("msgButton","msgButton1",true);
	
	//根据项目归属地查询项目列表
	$("#projectButton").live("click",function(){
		var provinceVal = $(".province").val();
		if(provinceVal==-1 || provinceVal == null){
			alert("请先选择查询条件!");
		}else{
			queryProjectListPages();
		}
	});
	
	//根据项目查询监控主机信息
	$("#hostButton").live("click",function(){
		var projectId = $(".projectSelect").val();
		if(projectId=="0000"){
			alert("请先选择项目!");
		}else{
			queryHostListPages();
		}
	});
	
	//根据项目查询节点信息
	$("#equipmentButton").live("click",function(){
		var projectId = $(".projectSelect").val();
		if(projectId=="0000"){
			alert("请先选择项目!");
		}else{
			queryEquipmentListPages();
		}
	});
	//根据项目查询节点信息
	$("#msgButton").live("click",function(){
		var title = $(".selectTitle").find("span").text();
		var projectId = $(".projectSelect").val();
		
		if( title == "用户信息查询："){
			if(projectId=="0000"){
				alert("请先选择项目!");
			}else {
				queryUserListPages();
			}
		}else if( title == "报警信息查询："){
			queryMessageListPages();
		}
	});
	
	
	//导航栏点击事件
	$(".projectB").live("click",function(){
		var id = $(this).attr("name");
		$(".breadcrumbs").find("li[class=pTitleLi]").remove();
		$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+$(this).text().trim()+"</a></li>");
		num = 0;
		
		if(id=="projectMng"){
			initProjectList();
		}else if(id=="hostMng"){
			initHostList();
		}else if(id=="equipmentMng"){
			initEquipmentList();
		}else if(id=="msgMng"){
			initMessageList();
		}else if(id=="userMng"){
			var roleId = $(".sessionHidden").val();
			
			//清空列表
			$(".mngContent").empty();
			if(roleId=="0"){
				initUserList();
			}else if(roleId=="1"){
				userInfo();
			}
		}
	});
	
	//项目信息查删改
	projectInfoMng();
	//主机信息查删改
	hostInfoMng();
	//节点信息查删改
	equipmentInfoMng();
	//报警信息查删改
	messageInfoMng();
	//用户信息查删改
	userInfoMng();
	
//	$("#equipmentButton").click(function() {
//		showLayer('.projectInfo');
//	});
});

/*********************************************** functions *************************************************/

/**
 * 项目信息管理,获取项目列表
 */
function initProjectList(){
	var projectMng = $("a[class=projectB][name=projectMng]");
	var id = projectMng.attr("name");
	$(".breadcrumbs").find("li[class=pTitleLi]").remove();
	$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+projectMng.text().trim()+"</a></li>");
	//加载项目信息分页列表
	$(".selectTitle").find("span").text("项目信息查询：");
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$("#dyn").removeClass("hiddenpars2").addClass("hiddenpars");
	$("#pageIndex").removeClass("pagination1").addClass("pagination");
	$("#userContext").removeClass("userContext").addClass("userContext1");
	
	$(".selectTitle").find("select").css("display","none");
	$(".selectTitle").find("button").css("display","none");
	$(".province").css("display","inline");
	$(".city").css("display","inline");
	$(".projectButton").css("display","inline");
	$(".rightContent").css("height","600px");
	
	$.initProv(".province",".city","-省份-","-城市-");
	$(".headContent").empty().append("<tr><th width='5%'>序号</th>" +
			"<th width='22%'>项目名称</th>" +
			"<th width='22%'>项目单位</th>" +
			"<th width='21%'>项目地址</th>" +
			"<th width='15%'>创建时间</th>" +
			"<th width='15%'>操作</th></tr>");
	initPaginationForProject();
}

/**
 * @Title: 				initPagination 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:08:27
 * @Description: 		项目列表分页函数
 * void 				返回
 */
function initPaginationForProject() {
	$(".mngContent").empty();
	var num_entries = 0;
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/project/selectAllProjectCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : "-1,-1",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="error"){
				alert("加载项目列表失败!");
			}else{
				num_entries = data;
				// 创建分页
				$(".pagination").pagination(num_entries, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 2, //主体页数
					callback: pageselectCallbackForProject,
					items_per_page: 15, //每页显示15项
					prev_text: "<<",
					next_text: ">>"
				});
			}
		},
		error : function(){
			alert("加载项目列表异常!");
		}
	});
};

/**
 * @Title: 				pageselectCallbackForProject 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:17:54
 * @Description: 		项目信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function pageselectCallbackForProject(page_index, jq){
	var Str = "";
	var loadI = layer.load("正在加载项目列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/project/selectAllProjectPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,-1,-1",
		success : function(data){
			layer.close(loadI);
			$(".pageIndexHidden").val(page_index);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				Str += "<tr>" +
				"<td colspan='6'>未查询到项目列表，请添加新项目!</td>" +
				"</tr>";
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}else{
				var pName = "";
				var pDepartment = "";
				var pAddress = "";
				num = 15 * page_index;
				$(data).each(function(i,item){
					num = num + 1;
					if(item.name.length>13){
						pName = item.name.substring(0,13)+"..";
					}else{
						pName = item.name;
					}
					if(item.department.length>13){
						pDepartment = item.department.substring(0,13)+"..";
					}else{
						pDepartment = item.department;
					}
					var addr = "";
					if(item.address==null){
						addr = item.province+item.city;
					}else{
						addr = item.province+item.city+item.address;
					}
					if(addr.length>13){
						pAddress = addr.substring(0,13)+"..";
					}else{
						pAddress = addr;
					}
					Str += "<tr>" +
							"<td class='headTD' >"+num+"</td>" +
							"<td>"+pName+"</td>" +
							"<td>"+pDepartment+"</td>" +
							"<td>"+pAddress+"</td>" +
							"<td>"+timeStamp2String(item.createtime)+"</td>" +
							"<td class='endTD'><a href='javascript:void(0);' class='selectProject' name='"+item.id+"'>查看 |</a><a href='javascript:void(0);'class='modifyProject' name='"+item.id+"'> 修改 |</a><a href='javascript:void(0);' class='deleteProject' name='"+item.id+"'> 删除</a></td>" +
							"</tr>";
				});
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载项目列表异常!");
		}
	});
}

/**
 * 主机信息管理,获取主机列表
 */
function initHostList(){
	var hostMng = $("a[class=projectB][name=hostMng]");
	var id = hostMng.attr("name");
	$(".breadcrumbs").find("li[class=pTitleLi]").remove();
	$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+hostMng.text().trim()+"</a></li>");
	//加载主机信息分页列表
	$(".selectTitle").find("span").text("主机信息查询：");
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$(".selectTitle").find("select").css("display","none");
	$(".selectTitle").find("button").css("display","none");
	$(".projectSelect").css("display","inline");
	
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$("#dyn").removeClass("hiddenpars2").addClass("hiddenpars");
	$("#pageIndex").removeClass("pagination1").addClass("pagination");
	$("#userContext").removeClass("userContext").addClass("userContext1");
	
	$(".hostButton").css("display","inline");
	$(".rightContent").css("height","600px");
	
	//初始化项目下拉列表
	initProjectToSelect(".projectSelect");
	//初始化查询区域
	$(".headContent").empty().append("<tr><th width='8%'>序号</th>" +
			"<th width='15%'>主机编号</th>" +
			"<th width='27%'>所属项目名称</th>" +
			"<th width='15%'>创建时间</th>" +
			"<th width='15%'>修改时间</th>" +
			"<th width='20%'>操作</th></tr>");
	
	initPaginationForHost();
}

/**
 * @Title: 				initPagination 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:08:27
 * @Description: 		主机列表分页函数
 * void 				返回
 */
function initPaginationForHost() {
	//初始化项目下拉列表
	initProjectToSelect(".projectOrder");
	
	$(".mngContent").empty();
	var num_entries = 0;
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/controlHost/selectAllHostCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : "0000",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="error"){
				alert("加载主机列表失败!");
			}else{
				num_entries = data;
				// 创建分页
				$(".pagination").pagination(num_entries, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 2, //主体页数
					callback: pageselectCallbackForHost,
					items_per_page: 15, //每页显示15项
					prev_text: "<<",
					next_text: ">>"
				});
			}
		},
		error : function(){
			alert("加载主机列表异常!");
		}
	});
};

/**
 * @Title: 				pageselectCallbackForProject 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:17:54
 * @Description: 		主机信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function pageselectCallbackForHost(page_index, jq){
	var Str = "";
	var loadI = layer.load("正在加载主机列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/controlHost/selectAllHostPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,0000",
		success : function(data){
			layer.close(loadI);
			$(".pageIndexHidden").val(page_index);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				Str += "<tr>" +
				"<td colspan='6'>未查询到主机列表，请添加新主机!</td>" +
				"</tr>";
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}else{
				var pName = "";
				num = 15 * page_index;
				$(data).each(function(i,item){
					num = num + 1;
					if(item.project.name.length>17){
						pName = item.project.name.substring(0,17)+"..";
					}else{
						pName = item.project.name;
					}
					Str += "<tr>" +
					"<td class='headTD' >"+num+"</td>" +
					"<td>"+item.code+"</td>" +
					"<td>"+pName+"</td>" +
					"<td>"+timeStamp2String(item.createtime)+"</td>" +
					"<td>"+timeStamp2String(item.modifytime)+"</td>" +
					"<td class='endTD'><a href='javascript:void(0);' class='selectHost' name='"+item.id+"'>查看 |</a><a href='javascript:void(0);'class='modifyHost' name='"+item.id+"'> 修改 |</a><a href='javascript:void(0);' class='deleteHost' name='"+item.id+"'> 删除</a></td>" +
					"</tr>";
				});
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载主机列表异常!");
		}
	});
}


/**
 * 节点信息管理,获取节点列表
 */
function initEquipmentList(){
	var equipmentMng = $("a[class=projectB][name=equipmentMng]");
	var id = equipmentMng.attr("name");
	$(".breadcrumbs").find("li[class=pTitleLi]").remove();
	$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+equipmentMng.text().trim()+"</a></li>");
	//加载项目信息分页列表
	$(".selectTitle").find("span").text("节点信息查询：");
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$(".selectTitle").find("select").css("display","none");
	$(".selectTitle").find("button").css("display","none");
	$(".projectSelect").css("display","inline");
	
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$("#dyn").removeClass("hiddenpars2").addClass("hiddenpars");
	$("#pageIndex").removeClass("pagination1").addClass("pagination");
	$("#userContext").removeClass("userContext").addClass("userContext1");
	
	$(".equipmentButton").css("display","inline");
	$(".rightContent").css("height","600px");
	
	//初始化项目下拉列表
	initProjectToSelect(".projectSelect");
	//初始化查询区域
	$(".headContent").empty().append("<tr><th width='7%'>序号</th>" +
			"<th width='13%'>节点名称</th>" +
			"<th width='8%'>节点编号</th>" +
			"<th width='8%'>流量参数</th>" +
			"<th width='20%'>所属项目名称</th>" +
			"<th width='15%'>创建时间</th>" +
			"<th width='15%'>修改时间</th>" +
			"<th width='14%'>操作</th></tr>");
	
	initPaginationForEquipment();
}

/**
 * @Title: 				initPagination 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:08:27
 * @Description: 		节点列表分页函数
 * void 				返回
 */
function initPaginationForEquipment() {
	$(".mngContent").empty();
	var num_entries = 0;
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/Equipment/selectAllEquipmentCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : "0000",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="error"){
				alert("加载节点列表失败!");
			}else{
				num_entries = data;
				// 创建分页
				$(".pagination").pagination(num_entries, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 2, //主体页数
					callback: pageselectCallbackForEquipment,
					items_per_page: 15, //每页显示15项
					prev_text: "<<",
					next_text: ">>"
				});
			}
		},
		error : function(){
			alert("加载节点列表异常!");
		}
	});
};

/**
 * @Title: 				pageselectCallbackForProject 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:17:54
 * @Description: 		节点信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function pageselectCallbackForEquipment(page_index, jq){
	var Str = "";
	var loadI = layer.load("正在加载节点列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/Equipment/selectAllEquipmentPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,0000",
		success : function(data){
			layer.close(loadI);
			$(".pageIndexHidden").val(page_index);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				Str += "<tr>" +
				"<td colspan='8'>未查询到节点列表，请添加新节点!</td>" +
				"</tr>";
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}else{
				var eName = "";
				var pName = "";
				num = 15 * page_index;
				$(data).each(function(i,item){
					num = num + 1;
					if(item.name.length>10){
						eName = item.name.substring(0,10)+"..";
					}else{
						eName = item.name;
					}
					if(item.project.name.length>13){
						pName = item.project.name.substring(0,13)+"..";
					}else{
						pName = item.project.name;
					}
					Str += "<tr>" +
					"<td class='headTD' >"+num+"</td>" +
					"<td>"+eName+"</td>" +
					"<td>"+item.code+"</td>" +
					"<td>"+item.flowparameter+"</td>" +
					"<td>"+pName+"</td>" +
					"<td>"+timeStamp2String(item.modifytime)+"</td>" +
					"<td>"+timeStamp2String(item.createtime)+"</td>" +
					"<td class='endTD'><a href='javascript:void(0);' class='selectEquipment' name='"+item.id+"'>查看 |</a><a href='javascript:void(0);'class='modifyEquipment' name='"+item.id+"'> 修改 |</a><a href='javascript:void(0);' class='deleteEquipment' name='"+item.id+"'> 删除</a></td>" +
					"</tr>";
				});
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载节点列表异常!");
		}
	});
}


/**
 * 报警信息管理,获取报警列表
 */
function initMessageList(){
	var msgMng = $("a[class=projectB][name=msgMng]");
	var id = msgMng.attr("name");
	$(".breadcrumbs").find("li[class=pTitleLi]").remove();
	$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+msgMng.text().trim()+"</a></li>");
	//加载项目信息分页列表
	$(".selectTitle").find("span").text("报警信息查询：");
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$(".selectTitle").find("select").css("display","none");
	$(".selectTitle").find("button").css("display","none");
	$(".projectSelect").css("display","inline");
	
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$("#dyn").removeClass("hiddenpars2").addClass("hiddenpars");
	$("#pageIndex").removeClass("pagination1").addClass("pagination");
	$("#userContext").removeClass("userContext").addClass("userContext1");
	
	$(".msgButton").css("display","inline");
	$(".rightContent").css("height","600px");
	
	//初始化查询区域
	$(".headContent").empty().append("<tr><th width='10%'>序号</th>" +
			"<th width='25%'>消息标题</th>" +
			"<th width='15%'>消息类型</th>" +
			"<th width='20%'>接收时间</th>" +
			"<th width='15%'>消息状态</th>" +
			"<th width='15%'>操作</th></tr>");
	
	initPaginationForMessage();
}

/**
 * @Title: 				initPagination 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:08:27
 * @Description: 		报警列表分页函数
 * void 				返回
 */
function initPaginationForMessage() {
	$(".projectSelect").empty().append("<option value='0000'>-请选择类型-</option><option value='0'>采集异常</option><option value='1'>湿度报警</option>");
	var num_entries = 0;
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/message/selectAllMessageCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : "0000,0000",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="error"){
				alert("加载报警列表失败!");
			}else{
				num_entries = data;
				// 创建分页
				$(".pagination").pagination(num_entries, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 2, //主体页数
					callback: pageselectCallbackForMessage,
					items_per_page: 15, //每页显示15项
					prev_text: "<<",
					next_text: ">>"
				});
			}
		},
		error : function(){
			alert("加载报警列表异常!");
		}
	});
};

/**
 * @Title: 				pageselectCallbackForProject 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:17:54
 * @Description: 		报警信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function pageselectCallbackForMessage(page_index, jq){
	$(".mngContent").empty();
	var Str = "";
	var loadI = layer.load("正在加载报警列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/message/selectAllMessagePages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,0000,0000", //最后一个参数为status = "0000" 表示查询所有已读和未读的报警信息
		success : function(data){
			layer.close(loadI);
			$(".pageIndexHidden").val(page_index);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				Str += "<tr>" +
				"<td colspan='5'>未查询到报警列表!</td>" +
				"</tr>";
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}else{
				var logcontext = "";
				var logType = "";
				var logStatus = "";
				num = 15 * page_index;
				$(data).each(function(i,item){
					num = num + 1;
					if(item.logcontext.length>15){
						logcontext = item.logcontext.substring(0,15)+"..";
					}else{
						logcontext = item.logcontext;
					}
					if(item.logtype=="0"){
						logType = "采集异常";
					}else if(item.logtype=="1"){
						logType = "湿度报警";
					}
					
					if(item.logstatus=="0"){
						logStatus = "未读";
					}else if(item.logstatus=="1"){
						logStatus = "已读";
					}

					Str += "<tr>" +
					"<td class='headTD' >"+num+"</td>" +
					"<td>"+logcontext+"</td>" +
					"<td>"+logType+"</td>" +
					"<td>"+timeStamp2String(item.logtime)+"</td>" +
					"<td>"+logStatus+"</td>" +
					"<td class='endTD'><a href='javascript:void(0);' class='selectSystemLog' name='"+item.id+"'>查看 |</a><a href='javascript:void(0);' class='markSystemLog' name='"+item.id+"'> 标记为已读</a></td>" +
					"</tr>";
				});
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载报警列表异常!");
		}
	});
}

/**
 * 用户信息管理,获取用户列表
 */
function initUserList(){
	var userMng = $("a[class=projectB][name=userMng]");
	var id = userMng.attr("name");
	$(".breadcrumbs").find("li[class=pTitleLi]").remove();
	$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+userMng.text().trim()+"</a></li>");
	//加载项目信息分页列表
	$(".selectTitle").find("span").text("用户信息查询：");
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$(".selectTitle").find("select").css("display","none");
	$(".selectTitle").find("button").css("display","none");
	$(".projectSelect").empty().append("<option value='0000'>-请选择用户-</option>");
	$(".projectSelect").css("display","inline");
	
	$("#selectTitle").removeClass("selectTitle2").addClass("selectTitle");
	$("#dyn").removeClass("hiddenpars2").addClass("hiddenpars");
	$("#pageIndex").removeClass("pagination1").addClass("pagination");
	$("#userContext").removeClass("userContext").addClass("userContext1");
	
	$(".msgButton").css("display","inline");
	$(".rightContent").css("height","600px");
	initPaginationForUser();
}

/**
 * @Title: 				initPagination 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:08:27
 * @Description: 		用户列表分页函数
 * void 				返回
 */
function initPaginationForUser() {
	//初始化用户下拉列表
	initUserToSelect(".projectSelect");
	//初始化查询区域
	$(".headContent").empty().append("<tr><th width='8%'>序号</th>" +
			"<th width='16%'>登录名</th>" +
			"<th width='16%'>用户名</th>" +
			"<th width='15%'>电话</th>" +
			"<th width='15%'>邮箱</th>" +
			"<th width='15%'>创建时间</th>" +
			"<th width='15%'>操作</th></tr>");
	$(".mngContent").empty();
	var num_entries = 0;
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/user/selectAllUserCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : "0000",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="error"){
				alert("加载用户列表失败!");
			}else{
				num_entries = data;
				// 创建分页
				$(".pagination").pagination(num_entries, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 2, //主体页数
					callback: pageselectCallbackForUser,
					items_per_page: 15, //每页显示15项
					prev_text: "<<",
					next_text: ">>"
				});
			}
		},
		error : function(){
			alert("加载用户列表异常!");
		}
	});
};

/**
 * @Title: 				pageselectCallbackForProject 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:17:54
 * @Description: 		用户信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function pageselectCallbackForUser(page_index, jq){
	$(".mngContent").empty();
	var Str = "";
	var loadI = layer.load("正在加载用户列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/user/selectAllUserPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,0000",
		success : function(data){
			layer.close(loadI);
			$(".pageIndexHidden").val(page_index);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				Str += "<tr>" +
				"<td colspan='7'>未查询到用户列表，请添加新用户!</td>" +
				"</tr>";
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}else{
				var loginName = "";
				var userName = "";
				var email = "";
				var phone ="";
				num = 15 * page_index;
				$(data).each(function(i,item){
					num = num + 1;
					if(item.loginname.length>13){
						loginName = item.loginname.substring(0,13)+"..";
					}else{
						loginName = item.loginname;
					}
					if(item.username.length>13){
						userName = item.username.substring(0,13)+"..";
					}else{
						userName = item.username;
					}
					if(item.email!==null){
						if(item.email.length>18){
							email = item.email.substring(0,18)+"..";
						}else{
							email = item.email;
						}
					}else{
						email = "";
					}
					
					if(item.phone!=null){
						phone = item.phone;
					}else{
						phone = "";
					}
					
					Str += "<tr>" +
					"<td class='headTD' >"+num+"</td>" +
					"<td>"+loginName+"</td>" +
					"<td>"+userName+"</td>" +
					"<td>"+phone+"</td>" +
					"<td>"+email+"</td>" +
					"<td>"+timeStamp2String(item.createtime)+"</td>" +
					"<td class='endTD'><a href='javascript:void(0);' class='selectUser' name='"+item.id+"'>查看 |</a><a href='javascript:void(0);'class='modifyUser' name='"+item.id+"'> 修改 |</a><a href='javascript:void(0);' class='deleteUser' name='"+item.id+"'> 删除</a></td>" +
					"</tr>";
				});
				$(".mngContent").empty().append(Str); //装载对应分页的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载用户列表异常!");
		}
	});
}

/**
 * @Title: 				userInfo 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午9:25:20
 * @Description: 		用户信息管理 
 * void 				返回
 */
function userInfo(){
	$("#selectTitle").removeClass("selectTitle").addClass("selectTitle2");
	$("#dyn").removeClass("hiddenpars").addClass("hiddenpars2");
	$("#pageIndex").removeClass("pagination").addClass("pagination1");
	$("#userContext").removeClass("userContext1").addClass("userContext");
	$(".rightContent").css("height","450px");
	
	//清空列表
	$(".mngContent").empty();
	var loadI = layer.load("正在加载用户信息...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/user/selectUserInfo.json",
		dataType : "json",
		contentType : "application/json",
		success : function(data){
			layer.close(loadI);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				alert("查询个人用户信息失败!");
			}else{
				var email = "";
				if(data.email!==null){
					email = data.email;
				}else{
					email = "";
				}
				var phone = "";
				if(data.phone!=null){
					phone = data.phone;
				}else{
					phone = "";
				}
				var address = "";
				if(data.address!=null){
					address = data.address;
				}else{
					address = "";
				}
				
				$(".loginName").val(data.loginname);
				$(".userName").val(data.username);
				$(".phone").val(phone);
				$(".email").val(email);
				$(".address").val(address);
				$(".website").val(data.website);
				$(".createtime").val(timeStamp2String(data.createtime));
				$(".modifytime").val(timeStamp2String(data.createtime));
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载个人用户信息异常!");
		}
	});
}