

/*************************************************** 带参分页查询 ***********************************************/

/**
 * @Title: 				queryProjectListPages 
 * @author 				杨贵松
 * @date 				2014年6月9日 下午4:08:27
 * @Description: 		分页查询项目列表函数
 * void 				返回
 */
function queryProjectListPages() {
	$(".mngContent").empty();
	var num_entries = 0;
	var provinceVal = $(".province").val();
	var cityVal = $(".city").val();
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/project/selectAllProjectCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : provinceVal+","+cityVal,
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
					callback: queryProjectListPagesCallback,
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
 * @Title: 				queryProjectListPagesCallback 
 * @author 				杨贵松
 * @date 				2014年6月9日 下午4:17:54
 * @Description: 		项目信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function queryProjectListPagesCallback(page_index, jq){
	var Str = "";
	var provinceVal = $(".province").val();
	var cityVal = $(".city").val();
	var loadI = layer.load("正在加载项目列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/project/selectAllProjectPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,"+provinceVal+","+cityVal,
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
 * @Title: 				queryHostListPages 
 * @author 				杨贵松
 * @date 				2014年6月9日 下午4:48:27
 * @Description: 		分页查询主机列表
 * void 				返回
 */
function queryHostListPages() {
	//初始化项目下拉列表
	initProjectToSelect(".projectOrder");
	
	var projectId = $(".projectSelect").val();
	$(".mngContent").empty();
	var num_entries = 0;
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/controlHost/selectAllHostCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : projectId,
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
					callback: queryHostListPagesCallback,
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
 * @Title: 				queryHostListPagesCallback 
 * @author 				杨贵松
 * @date 				2014年6月9日 下午4:57:54
 * @Description: 		主机信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function queryHostListPagesCallback(page_index, jq){
	var Str = "";
	var projectId = $(".projectSelect").val();
	var loadI = layer.load("正在加载主机列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/controlHost/selectAllHostPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,"+projectId,
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
 * @Title: 				queryEquipmentListPages 
 * @author 				杨贵松
 * @date 				2014年6月9日 下午5:08:27
 * @Description: 		分页查询节点列表
 * void 				返回
 */
function queryEquipmentListPages() {
	$(".mngContent").empty();
	var num_entries = 0;
	var projectId = $(".projectSelect").val();
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/Equipment/selectAllEquipmentCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : projectId,
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
					callback: queryEquipmentListPagesCallback,
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
 * @Title: 				queryEquipmentListPagesCallback 
 * @author 				杨贵松
 * @date 				2014年6月9日 下午5:19:54
 * @Description: 		节点信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function queryEquipmentListPagesCallback(page_index, jq){
	var Str = "";
	var projectId = $(".projectSelect").val();
	var loadI = layer.load("正在加载节点列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/Equipment/selectAllEquipmentPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,"+projectId,
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
 * @Title: 				queryUserListPages 
 * @author 				杨贵松
 * @date 				2014年6月26日 下午5:08:27
 * @Description: 		分页查询用户列表
 * void 				返回
 */
function queryUserListPages() {
	$(".mngContent").empty();
	var num_entries = 0;
	var uId = $(".projectSelect").val();
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/user/selectAllUserCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : uId,
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
					callback: queryUserListPagesCallback,
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
 * @Title: 				queryUserListPagesCallback 
 * @author 				杨贵松
 * @date 				2014年6月26日 下午5:19:54
 * @Description: 		节点信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function queryUserListPagesCallback(page_index, jq){
	var Str = "";
	var userId = $(".projectSelect").val();
	var loadI = layer.load("正在加载用户列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/user/selectAllUserPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,"+userId,
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
 * @Title: 				queryMessageListPages 
 * @author 				杨贵松
 * @date 				2014年6月26日 下午5:08:27
 * @Description: 		分页查询报警消息列表
 * void 				返回
 */
function queryMessageListPages() {
	$(".mngContent").empty();
	var num_entries = 0;
	var type = $(".projectSelect").val();
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/message/selectAllMessageCounts.json",
		dataType : "json",
		contentType : "application/json",
		data : type+",0000",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="error"){
				alert("加载报警信息列表失败!");
			}else{
				num_entries = data;
				// 创建分页
				$(".pagination").pagination(num_entries, {
					num_edge_entries: 1, //边缘页数
					num_display_entries: 2, //主体页数
					callback: queryMessageListPagesCallback,
					items_per_page: 15, //每页显示15项
					prev_text: "<<",
					next_text: ">>"
				});
			}
		},
		error : function(){
			alert("加载报警信息列表异常!");
		}
	});
};

/**
 * @Title: 				queryMessageListPagesCallback 
 * @author 				杨贵松
 * @date 				2014年6月26日 下午5:19:54
 * @Description: 		报警信息分页回调函数 
 * @param page_index
 * @param jq
 * @returns 
 * any 				返回
 */
function queryMessageListPagesCallback(page_index, jq){
	var Str = "";
	var type = $(".projectSelect").val();
	var loadI = layer.load("正在加载报警信息列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/message/selectAllMessagePages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",15,"+type+",0000",
		success : function(data){
			layer.close(loadI);
			$(".pageIndexHidden").val(page_index);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				Str += "<tr>" +
				"<td colspan='7'>未查询到报警信息列表!</td>" +
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
			alert("加载报警信息列表异常!");
		}
	});
}
