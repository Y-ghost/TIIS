/**
 * @author 				杨贵松
 * @date 				2014年4月1日 上午11:17:38
 * @Description: 		用户登录
 */
$(document).ready(function(){
	// 退出 
	exit();
	// 获取服务器当前时间
	nowTime();
	//分页
	//initPagination();	
	//主机校时
	validControlHostTime();
	//按钮效果
	$("#projectButton").btnEffect("projectButton","projectButton1",true);
	$("#pushNoticeButton").btnEffect("pushNoticeButton","pushNoticeButton1",true);
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
	/************************************** 弹框初始化 ******************************/
	$("#searchEquipment").btnEffect("equipmentButton","equipmentButton1",true);
	$("#searchEquipment").live("click",function(){
		searchEquipment();
	});
	$("#registeEquipment").live("click",function(){
		registeEquipment();
	});
	/************************************** 添加项目 *****************************/
	//导航栏点击事件
	$(".projectB").live("click",function(){
		var id = $(this).attr("name");
		$(".breadcrumbs").find("li[class=pTitleLi]").remove();
		$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+$(this).text().trim()+"</a></li>");
		num = 0;
		
		if(id=="addProject"){
			/************************************** 添加项目 *****************************/
			addProject();
		}else if(id=="addHost"){
			/************************************** 添加主机 *****************************/
			addHost();
		}else if(id=="addChirld"){
			/************************************** 添加节点 *****************************/
			$(".container").empty().append("<div class='equipmentInfos'>" +
						"<div class='warnning' style='text-align:center;font-size:14px;'></div>" +
						"<div class='pTitle' style='border:#c3c3c3 1px solid;width:706px;margin-left:80px;font-size:16px;margin-bottom:5px;margin-top:5px;'>所属项目：<select class='hostOrder' style='width:168px;'><option value='0000'>-请选择项目-</option></select>" +
						"<button class='equipmentButton' id='searchEquipment' style='width:80px;'>搜&nbsp;&nbsp;索</button>" +
						"</div>" +
						"<div class='context' id='contextForEquipment' style='height:255px;width:700px;margin-left:80px;padding:3px; border:#c3c3c3 1px solid;overflow:auto;'>" +
						"</div>" +
						"<div class='btn' style='margin-top:10px;padding-left:340px;'>" +
						"<button style='font-size:14px;padding:10px;width:180px;' class='sideB bGreen' id='registeEquipment'>注册新节点</button>" +
						"</div>" +
						"</div>");
			initProjectToSelect(".hostOrder");
		}else if(id=="adminUse"){
			/************************************** 加载管理员操作界面 *****************************/
			$(".container").empty().append("<select class='projectSelect' style='width: 260px; font-size: 18px;height:40px;'></select>" +
					"<button class='selectTimeButton' id='selectTimeButton'>查询时间</button>" +
					"<button class='setTimeButton' id='setTimeButton'>主机校时</button><div style='height:20px'></div>"+
					"<br/><input class='pushNotice' style='width: 300px; font-size: 16px;height:30px;border:#c3c3c3 1px solid;'/>" +
					"<button class='pushNoticeButton' id='pushNoticeButton'>发送</button>"+
					"<br/><br/><form enctype='multipart/form-data' action='http://localhost:8080/TIIS/Files/upload' method='post'>" +
					"<input type='file' name='file' style='width:200px;height:40px;'/>" +
					"<input type='submit' value='上传'  style='margin-left:100px;width:100px;height:40px;'/>" +
					"</form>"+
					"<button class='pushTestNoticeButton' id='pushTestNoticeButton'>发送测试通知</button>");
			$("#selectTimeButton").btnEffect("selectTimeButton","selectTimeButton1",true);
			$("#setTimeButton").btnEffect("setTimeButton","setTimeButton1",true);
			//初始化项目下拉框
			initProjectToSelect(".projectSelect");
			
		}else if(id=="setData"){
			/******************************************** 赋值 ***************************************/
			$(".container").empty().append("<div class='selectTitle' id='selectTitle'>" +
					"<span>节点赋值：</span>" +
					"<select class='projectS' style='width:130px;font-size:18px;'><option value='0000'>-请选择项目-</option></select>" +
					"<select class='equipmentS' style='margin-left:20px;width:130px;font-size:18px;'><option value='0000'>-请先选择项目-</option></select>" +
					"<button class='projectButton' id='projectButton'>查询</button>" +
					"</div>" +
					"<div class='selectContent'>" +
					"<div class='hiddenpars' id='dyn'>" +
					"<table cellpadding='0' cellspacing='0' border='0' >" +
					"<!-- 查询区域 -->" +
					"<thead class='headContent'>" +
					"<tr>" +
					"<th width='10%'>序号</th>" +
					"<th width='15%'>节点名称</th>" +
					"<th width='10%'>节点编号</th>" +
					"<th width='30%'>所属项目名称</th>" +
					"<th width='10%'>实时数据</th>" +
					"<th width='15%'>数据库最新数据</th>" +
					"<th width='10%'>操作</th>" +
					"</tr>" +
					"</thead>" +
					"<!-- 列表区域 -->" +
					"<tbody class='mngContent' >" +
					"</tbody>" +
					"</table>" +
					"</div></div>");
			//初始化项目下拉框
			initProjectToSelect(".projectS");
			//项目节点二级联动
			projectFindEquipments();
		}
	});
	//查询节点数据
	selectData();
	//节点赋值
	setData();
	
	//监听键盘，只允许输入和粘贴数字和小数点
	keyControl();

	$("#pushNoticeButton").live("click",function(){
		var notice = $(".pushNotice").val();
		if(notice==""){
			alert("通知内容不能为空!");
		}else{
			if(confirm("确认要发送该通知给所有用户？")){
				var loadI = layer.load("正在向所有用户推送通知...");
				$.ajax({
					type : "post",
					url : "http://localhost:8080/TIIS/NetWorkExt/pushAllMsg.json",
					dataType : "json",
					contentType : "application/json",
					data : notice,
					success : function(data){
						layer.close(loadI);
						if(data=="noLogin"){
							window.location.href="http://localhost:8080/TIIS/indexs/redirect";
						}else if(data=="ok"){
							alert("发送成功!");
						}else{
							alert("发送失败!");
						}
					},
					error : function(){
						layer.close(loadI);
						alert("发送异常!");
					}
				});
			}
		}
	});
	//测试发送消息通知
	$("#pushTestNoticeButton").live("click",function(){
		if(confirm("确认要发送该通知给所有用户？")){
			var loadI = layer.load("正在向所有用户推送通知...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWorkExt/pushTestMsg.json",
				dataType : "json",
				contentType : "application/json",
				success : function(data){
					layer.close(loadI);
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="ok"){
						alert("发送成功!");
					}else{
						alert("发送失败!");
					}
				},
				error : function(){
					layer.close(loadI);
					alert("发送异常!");
				}
			});
		}
	});
});

/*********************************************** functions *************************************************/

/**
 * @Title: 				validControlHostTime 
 * @author 				杨贵松
 * @date 				2014年4月27日 下午4:26:41
 * @Description: 		控制器主机校时 
 * void 				返回
 */
function validControlHostTime(){
	
	var role = $(".roleHidden").val();
	if(role!=0){
	}else{
		$(".labList").append("<div class='labItem' id='adminUse' style='padding-left: 33px;border-top: 0px solid #c3c3c3;border-bottom: 1px solid #c3c3c3;'><a class='projectB' href='javascript:void(0);' name = 'adminUse'>查询主机时间&nbsp;&nbsp;<img src='images/elements/control/rightArrow.png'/></a></div>");
	}
	//查询现场主机时间
	$("#selectTimeButton").live("click",function(){
		var projectId = $(".projectSelect").val();
		if(projectId=="0000"){
			$(".warnningSpan").empty().append("提示：请先选择项目!");
		}else{
			$(".warnningSpan").empty();
			var loadI = layer.load("正在查询现场主机时间...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWorkExt/selectControlHostTime.json",
				dataType : "json",
				contentType : "application/json",
				data : projectId,
				success : function(data){
					layer.close(loadI);
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="null"){
						$(".warnningSpan").empty().append("提示：该项目下未监测到主机信息!");
					}else if(data=="error"){
						$(".warnningSpan").empty().append("提示：查询现场主机时间失败!");
					}else{
						$(".contentTable").empty().append(data);
					}
				},
				error : function(){
					layer.close(loadI);
					$(".warnningSpan").empty().append("查询现场主机时间异常!");
				}
			});
		}
	});
	//校验现场主机时间
	$("#setTimeButton").live("click",function(){
		var projectId = $(".projectSelect").val();
		if(projectId=="0000"){
			$(".warnningSpan").empty().append("提示：请先选择项目!");
		}else{
			$(".warnningSpan").empty();
			if(confirm("确定校验该主机时间?")){
				var loadI = layer.load("正在校验现场主机时间...");
				$.ajax({
					type : "post",
					url : "http://localhost:8080/TIIS/NetWorkExt/setControlHostTime.json",
					dataType : "json",
					contentType : "application/json",
					data : projectId,
					success : function(data){
						layer.close(loadI);
						if(data=="noLogin"){
							window.location.href="http://localhost:8080/TIIS/indexs/redirect";
						}else if(data=="null"){
							$(".warnningSpan").empty().append("提示：该项目下未监测到主机信息!");
						}else if(data=="ok"){
							$(".warnningSpan").empty();
							alert("校验现场主机时间成功!");
						}else{
							$(".warnningSpan").empty().append("提示：校验现场主机时间失败!");
						}
					},
					error : function(){
						layer.close(loadI);
						$(".warnningSpan").empty().append("校验现场主机时间异常!");
					}
				});
			}
		}
	});
}


/**
 * @Title: 				addProject 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午2:53:17
 * @Description: 		添加项目
 * void 				返回
 */
function addProject(){
	$(".container").empty().append("<div class='projectInfos' style='border:#000 0px solid;'>" +
			"<div class='loginTitle' style='color: #000; background: url(images/backgrounds/body.jpg);'>" +
			"<div class='span' style='text-align: center;width:96%;'>添加新项目信息</div>" +
			"</div>" +
			"<div class='context' style='height:220px;'>" +
			"<div class='pTitle'>项目名称：<input type='text' class='projectName'/> </div>" +
			"<div class='pTitle'>负责单位：<input type='text' class='department'/> </div>" +
			"<div class='pTitle'>项目地址：<select class='province' style='margin-right:6px;'></select><select class='city'></select> </div>" +
			"<div class='pTitle'>详细地址：<input type='text' class='address'/></div>" +
			"<div class='pTitle'>项目面积：<input type='text' class='area' style='width:193px;'/><span >&nbsp;&nbsp;(平方米)</span> </div>" +
			"</div>" +
			"<div class='btn' style='padding-left:360px;'>" +
			"<button class='sideB bLightBlue' id='registeProject' style='font-size:14px;padding:10px;width:180px;'>添加新项目</button>" +
			"</div>" +
			"</div>");
	$.initProv(".province",".city","-省份-","-城市-");
	
	$("#registeProject").click(function(){
		var pName = $(".projectName").val();
		var pDepartment = $(".department").val();
		var pProvince = $(".province").val();
		var pCity = $(".city").val();
		var pAddress = $(".address").val();
		var pArea = $(".area").val();
		
		//组装project对象
		var project = "{name:"+pName+",department:"+pDepartment+",province:"+pProvince+",city:"+pCity+",address:"+pAddress+",area:"+pArea+"}";
		
		if(pName=="" || pDepartment=="" || pAddress =="" || pProvince==-1 || pCity==-1 || pArea==""){
			alert("项目信息不能有空项!");
		}else if(!(/^(([0-9]+\.[0-9]{0,2})|([0-9]*[1-9][0-9]*))$/.test(pArea))){
			alert("项目面积只能是整数或者最多保留两位的小数，如：1234或者1234.00");
		}else{
			//判断项目名称是否已存在
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/project/validProjectName.json",
				dataType : "json",
				contentType : "application/json",
				data : pName,
				success : function(data){
						if(data=="noLogin"){
							window.location.href="http://localhost:8080/TIIS/indexs/redirect";
						}else if(data=="ok"){
							//添加项目信息
							$.ajax({
								type : "post",
								url : "http://localhost:8080/TIIS/project/addProject.json",
								dataType : "json",
								contentType : "application/json",
								data : project,
								success : function(data){
									if(data=="noLogin"){
										window.location.href="http://localhost:8080/TIIS/indexs/redirect";
									}else if(data=="ok"){
										alert("添加项目信息成功!");
										initPagination();
									}else{
										alert("添加项目信息失败!");
									}
								},
								error : function(){
									alert("添加项目信息异常!");
								}
							});
						}else{
							alert("项目名已存在!");
						}
				},
				error : function(){
						alert("验证项目名异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				addHost 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午2:55:42
 * @Description: 		添加主机   
 * void 				返回
 */
function addHost(){
	$(".container").empty().append("<div class='hostInfos'>" +
			"<div class='loginTitle' style='color: #000; background: url(images/backgrounds/body.jpg);'>" +
			"<div class='span' style='text-align: center;width:96%;'>添加新主机信息</div>" +
			"</div>" +
			"<div class='context' style='height:100px;'>" +
			"<div class='pTitle'>所属项目：<select class='projectOrder' style='width:268px;'><option value='0000'>-请选择项目-</option></select></div>" +
			"<div class='pTitle'>主机编号：<input class='hostCode' type='text' style='width:100px;'/> <span style='color:red;'>* 主机编号为8位纯数字!</span> </div>" +
			"</div>" +
			"<div class='btn' style='padding-left:340px;'>" +
			"<button class='sideB bSea' id='registeHost' style='font-size:14px;padding:10px;width:180px;'>添加新主机</button>" +
			"</div>" +
			"</div>");
	//初始化项目下拉框
	initProjectToSelect(".projectOrder");
	
	$("#registeHost").click(function(){
		var hProjectId = $(".projectOrder").val();
		var hCode = $(".hostCode").val();
		
		//组装host对象
		var host = "{projectid:"+hProjectId+",code:"+hCode+"}";
		
		if(hProjectId=="0000" || hCode=="" ){
			alert("主机信息不能有空项!");
		}else if(!(/^\d{8}$/.test(hCode))){
			alert("主机编号为八位纯数字!");
		}else{
			//判断主机编号是否已存在
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/controlHost/validHostCode.json",
				dataType : "json",
				contentType : "application/json",
				data : host,
				success : function(data){
						if(data=="noLogin"){
							window.location.href="http://localhost:8080/TIIS/indexs/redirect";
						}else if(data=="ok"){
							//添加主机信息
							$.ajax({
								type : "post",
								url : "http://localhost:8080/TIIS/controlHost/addHost.json",
								dataType : "json",
								contentType : "application/json",
								data : host,
								success : function(data){
									if(data=="noLogin"){
										window.location.href="http://localhost:8080/TIIS/indexs/redirect";
									}else if(data=="ok"){
										alert("添加控制器主机信息成功!");
									}else{
										alert("添加控制器主机信息失败!");
									}
								},
								error : function(){
									alert("添加控制器主机信息异常!");
								}
							});
						}else if(data == "NotOnly"){
							alert("一个项目下只能注册一台控制主机!");
						}else{
							alert("控制器主机编号已存在!");
						}
				},
				error : function(){
						alert("验证控制器主机编号异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				selectData 
 * @author 				杨贵松
 * @date 				2014年6月28日 下午4:59:03
 * @Description: 		查询节点数据
 * void 				返回
 */
function selectData(){
	//赋值查询
	$("#projectButton").live("click",function(){
		var len = 0;
		var Str ="";
		var projectId = $(".projectS").val();
		var eId = $(".equipmentS").val();
		if(projectId=="0000"){
			alert("请选择项目进行查询!");
		}else{
			var loadI = layer.load("正在查询该项目下的节点数据，该过程大概需10秒左右，请耐心等待...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWorkExt/selectData.json",
				dataType : "json",
				contentType : "application/json",
				data : projectId+","+eId,
				success : function(data){
					layer.close(loadI);
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="null"){
						alert("提示：该项目下未监测到主机信息!");
					}else if(data=="error"){
						$(".mngContent").empty();
						alert("提示：查询该项目下的节点数据失败!");
					}else{

						$(data).each(function(i,item){
							len += 27;
							var tmpStr = "";
							if(item.times<item.eData){
								tmpStr = "<td style='color:red;'>"+item.times+"</td>";
							}else{
								tmpStr = "<td >"+item.times+"</td>";
							}
							Str += "<tr>" +
								"<td class='headTD'>"+(parseInt(i) +1)+"</td>" +
								"<td >"+item.name+"</td>" +
								"<td >"+item.code+"</td>" +
								"<td >"+item.project.name+"</td>" +
								tmpStr +
								"<td >"+item.eData+"</td>" +
								"<td class='endTD'><a href='javascript:void(0);' class='setData' name='"+item.project.id+"'>赋值</a></td>" +
								"</tr>";
						});
						if(len<450){
							len = 450;
						}
						$(".container").css("height",len+"px");
						$(".mngContent").empty().append(Str); //装载对应分页的内容
					}
				},
				error : function(){
					layer.close(loadI);
					$(".mngContent").empty();
					alert("查询该项目下的节点数据异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				setData 
 * @author 				杨贵松
 * @date 				2014年07月03日 上午11:39:03
 * @Description: 		节点数据赋值
 * void 				返回
 */
function setData(){
	//赋值查询
	$(".setData").live("click",function(){
		var projectId = $(this).attr("name");
		var eCode = $(this).parent().parent().find("td").eq(2).html();
		var eData = $(this).parent().parent().find("td").eq(5).html();
		if(confirm("确定赋值?")){
			var loadI = layer.load("正在赋值，请耐心等待...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWorkExt/setData.json",
				dataType : "json",
				contentType : "application/json",
				data : projectId+","+eCode+","+eData,
				success : function(data){
					layer.close(loadI);
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="null"){
						alert("提示：该项目下未监测到主机信息!");
					}else if(data=="ok"){
						alert("提示：赋值成功!");
					}else{
						alert("提示：赋值失败!");
					}
				},
				error : function(){
					layer.close(loadI);
					alert("赋值异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				projectFindEquipments 
 * @author 				杨贵松
 * @date 				2014年6月30日 下午4:34:31
 * @Description: 		项目节点二级联动
 * void 				返回
 */
function projectFindEquipments(){
	$(".projectS").live("change",function(){
		var projectId = $(this).children('option:selected').val();
		if(projectId=="0000"){
			$(".equipmentS").empty().append("<option value='0000'>-请选择节点-</option>");
		}else{
			$.ajax({
				type : "post",
				async : true,
				url : "http://localhost:8080/TIIS/Equipment/selectEquipmentByPId.json",
				dataType : "json",
				contentType : "application/json",
				data : projectId,
				success : function(data){
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="null"){
						$(".equipmentS").empty().append("<option value='0000'>-请选择节点-</option>");
					}else if(data=="error"){
						$(".equipmentS").empty().append("<option value='0000'>-请选择节点-</option>");
					}else{
						var str = "<option value='0000'>-请选择节点-</option>";
						$(data).each(function(i,item){
							str += "<option value='"+item.id+"'>"+item.name+"</option>";
						});
						$(".equipmentS").empty().append(str);
					}
				},
				error : function(){
					$(".equipmentS").empty().append("<option value='0000'>-请选择节点-</option>");
				}
			});
		}
	});
}
/**
 * @Title: 				keyControl 
 * @author 				杨贵松
 * @date 				2014年3月31日 上午12:38:30
 * @Description: 		监听键盘，只允许输入和粘贴数字和小数点
 * void 				返回
 */
function keyControl(){
	//监听键盘，只允许输入和粘贴数字和小数点
	$(".area").keypress(function(event) {
		var keyCode = event.which;
		if (keyCode == 46 || (keyCode >= 48 && keyCode <=57))
			return true;
		else
			return false;
	}).focus(function() {
		this.style.imeMode='disabled';
	});
}

