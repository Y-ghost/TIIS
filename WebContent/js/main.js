/**
 * @author 				杨贵松
 * @date 				2014年3月14日 下午11:17:38
 * @Description: 		首页相关function 
 */
var i = 0;
$(document).ready(function() {
	
	/************************************** 退出 *************************************/
	exit();
	/************************************** 获取服务器当前时间 *************************/
	nowTime();
	/************************************** 初始化加载项目列表 ************************/
	//分页
	initPagination();
	/************************************** 报警消息弹框 ************************/
	window.setTimeout("msgWarning()", 1000);//设置循环时间
	/************************************** 弹框初始化 ******************************/
	function showLayer(mark) {
		i = $.layer({
			type : 1,
			title : false,
			closeBtn : true,
			border : [ 0 ],
			offset : [ '150px', '' ],
			area : [ '450px', '300px' ],
			page : {
				dom : mark
			}
		});
	}
	//关闭弹框 
	$(".close").click(function() {
		layer.close(i);
	});
	
	
	/************************************** 节点状态tip弹框 *********************/
	$(".tipN").live("mouseenter",function() {
		var name = $(this).attr("name");
		var flag = name.split(",");
		layer.tips("温度为：" + flag[0] + "℃ <br/> 湿度为：" + flag[1] + " %"+" <br/> 状态为：" + flag[2] , this, {
			style : [ 'background-color:#0FA6D8; color:#fff', '#0FA6D8' ],
			time : 2,
			maxWidth : 129
		});
	});
	$('#tipLab').live("mouseenter",function() {
		var name = $(this).attr("title");
		layer.tips(name , this, {
			style : [ 'background-color:#ba6d6d; color:#fff', '#ba6d6d' ],
			time : 2,
			maxWidth : 140
		});
	});
	
	/**************************** 获取选择的项目下的节点列表及状态 ****************************/
	$(".projectA").live("click",function(){
//		alert($(".projectA").eq(1).attr("name"));
		var id = $(this).attr("name");
		$(".breadcrumbs").find("li[class=pTitleLi]").remove();
		$(".breadcrumbs").append("<li class='pTitleLi' id='"+id+"'><a href='javascript:void(0)'>"+$(this).text().trim()+"</a></li>");
		getEquipmentList(id);
	});
	
	/****************************** 刷新所有节点状态 *******************************/
	$(".freshEStatus").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else if(confirm("确定要刷新该项目下的所有节点状态?")){
			getEquipmentList(id);
		}
	});
	
	/****************************** 开关所有节点阀门 ***********************************/
	$(".openOrCloseE").live("click",function(){
		openOrClose();
	});
	
	/************************* 获取选择的项目下的节点列表及状态 **************************/
	$(".tipN").live("click",function(){
		var object = $(this);
		openOrCloseEquipment(object);
	});
	
	/************************************** 设置时间段 *************************************/
	$(".setTimeLen").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else{
			showLayer('.timeLen');
			$(".timeLen").find("select").empty();
			$(".timeLen").find("select").attr("disabled",true);
			$(".timeLen").find("input[type=checkbox]").attr("checked",false);
			
			time(".startHour1",".startMinute1");
			time(".endHour1",".endMinute1");
			time(".startHour2",".startMinute2");
			time(".endHour2",".endMinute2");
			time(".startHour3",".startMinute3");
			time(".endHour3",".endMinute3");
			
			checkVal("clickVal1","startHour1","startMinute1","endHour1","endMinute1");
			checkVal("clickVal2","startHour2","startMinute2","endHour2","endMinute2");
			checkVal("clickVal3","startHour3","startMinute3","endHour3","endMinute3");
		}
	});
	setTimeLen();
	/************************************** 设置控制模式 *************************************/
	$(".setModel").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else{
			$(":radio[name=radioModel][value=F0]").attr("checked",true);
			showLayer('.model');
		}
	});
	setModel();
	/************************************** 设置自控参数 *************************************/
	$(".setParameter").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else{
			showLayer('.parameter');
			$(".parameter").find("input[type=text]").empty();
		}
	});
	setParameter();
	/************************************** 刷新 *************************************/
	//查询时间段
	$(".freshTimeLen").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else{
			var loadId = layer.load("请耐心等待几秒，努力查询时段中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/selectTimesLen.json",
				dataType : "json",
				contentType : "application/json",
				data : id,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机信息!");
					}else if(data==null || data=="error"){
						alert("查询时段失败!");
					} else{
						var m1,m2,m3,m4,m5,m6;
						if(data.substring(0, 2)=="24" && data.substring(2, 4)=="00"){
							m1 = "60";
						}else{
							m1 = data.substring(2, 4);
						}
						if(data.substring(4, 6)=="24" && data.substring(6, 8)=="00"){
							m2 = "60";
						}else{
							m2 = data.substring(6, 8);
						}
						if(data.substring(8, 10)=="24" && data.substring(10, 12)=="00"){
							m3 = "60";
						}else{
							m3 = data.substring(10, 12);
						}
						if(data.substring(12, 14)=="24" && data.substring(14, 16)=="00"){
							m4 = "60";
						}else{
							m4 = data.substring(14, 16);
						}
						if(data.substring(16, 18)=="24" && data.substring(18, 20)=="00"){
							m5 = "60";
						}else{
							m5 = data.substring(18, 20);
						}
						if(data.substring(20, 22)=="24" && data.substring(22, 24)=="00"){
							m6 = "60";
						}else{
							m6 = data.substring(22, 24);
						}
						
						$(".startHour1").val(data.substring(0, 2)) ;
						$(".startMinute1").val(m1) ;
						$(".endHour1").val(data.substring(4, 6)) ;
						$(".endMinute1").val(m2) ;
						$(".startHour2").val(data.substring(8, 10));
						$(".startMinute2").val(m3) ;
						$(".endHour2").val(data.substring(12, 14)) ;
						$(".endMinute2").val(m4) ;
						$(".startHour3").val(data.substring(16, 18));
						$(".startMinute3").val(m5) ;
						$(".endHour3").val(data.substring(20, 22));
						$(".endMinute3").val(m6);
					}
				},
				error : function(){
					layer.close(loadId);
					alert("查询时段异常!");
				}
			});
		}
	});
	//查询控制模式
	$(".freshModel").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else{
			var loadId = layer.load("请耐心等待几秒，努力查询控制模式中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/selectModel.json",
				dataType : "json",
				contentType : "application/json",
				data : id,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机信息!");
					}else if(data==null || data=="error"){
						alert("查询模式失败!");
					} else{
						$(".model").find("input[name=radioModel]").each(function(){
							if($(this).val()==data){
								$(this).attr("checked","checked");
							}
						});
					}
				},
				error : function(){
					layer.close(loadId);
					alert("查询模式异常!");
				}
			});
		}
	});
	//查询自控参数
	$(".freshParameter").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		if(id==undefined){
			alert("请先选择项目!");
		}else{
			var loadId = layer.load("请耐心等待几秒，努力查询自控参数中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/selectParameter.json",
				dataType : "json",
				contentType : "application/json",
				data : id ,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机信息!");
					}else if(data==null || data=="error"){
						alert("查询自控参数失败!");
					} else{
						var parameterData = data.split(",");
						$(".soilHumidity").val(parameterData[0]);
						$(".upTemperature").val(parameterData[1]);
						$(".downTemperature").val(parameterData[2]);
					}
				},
				error : function(){
					layer.close(loadId);
					alert("查询自控参数异常!");
				}
			});
		}
	});
});
	
/*********************************************** functions *************************************************/

/**
 * @Title: 				getEquipmentList 
 * @author 				杨贵松
 * @date 				2014年3月14日 下午11:17:38
 * @Description: 		获取选择的项目下的节点列表及状态 
 * @param id 
 * void 				返回
 */
function getEquipmentList(id){
	var loadI = layer.load("请耐心等待几秒，努力加载中...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/NetWork/selectEquipmentsStatus.json",
		dataType : "json",
		contentType : "application/json",
		data : "eId="+id,
		success : function(data){
			layer.close(loadI);
			$(".equipmentList").html("");
			var listStr = "";
			if( data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="noRight"){
				alert("对不起您没有操作权限!");
			}else if(data==null || data=="null" || data=="error"){
				alert("该项目下无节点，请先添加节点!");
			}else{
				$(data).each(function(i,item){
					if(i%7==0 || i==0){
						listStr += "<ul class='middleNavR'>"; 
					}
					var T = 0.0;
					var M = 0;
					if(item.soiltemperatrue!=null){
						T = item.soiltemperatrue;
					}
					
					if(item.soilhumidity==-1){
						M = "异常";
					}else if(item.soilhumidity!=null){
						M = item.soilhumidity;
					}
					//判断水阀运行状态
					var status = item.status;
					var strong = "";
					if(status==null||status==""||status == "null"){
						strong = "<strong style='background: -webkit-linear-gradient(top, #DC143C 0%, #DC143C 100%);'>异常</strong>";
					}else if("等待出水,出水正常,阀门开启".indexOf(status)>=0){
						strong = "<strong style='background: -webkit-linear-gradient(top, green 0%, green 100%);'>浇水</strong>";
					}else if("等待关水,关水正常,阀门关闭".indexOf(status)>=0){
						strong = "<strong style='background: -webkit-linear-gradient(top, #0FA6D8 0%, #0FA6D8 100%);'>运行</strong>";
					}else if("供水故障,水阀故障".indexOf(status)>=0){
						strong = "<strong>故障</strong>";
					}
					
					listStr += "<li><a href='javascript:void(0);' title='"+item.name+"' class='tipN' name='"+T+','+M+','+item.status+','+item.id+"' ><img src='img/upload.png' /> <br />"+item.name+"</a>"
								+strong
								+"</li>";
						
					if(i%7>=6){
						listStr += "</ul>"; 
					}
				});
			}
			$(".equipmentList").append(listStr);
		},
		error : function(){
				layer.close(loadI);
				alert("加载节点列表异常!");
		}
	}); 
}


/**
 * @Title: 				openOrClose 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午4:46:00
 * @Description: 		开关阀 
 * void 				返回
 */
function openOrClose(){
	var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
	var status = $(".openOrCloseE").attr("name");
	if(id!=undefined){//判断所有阀是不是都为开启状态
		var openMark = 0;
		var closeMark = 0;
		var flag = 0;
		$("a[class=tipN]").each(function(i,item){
			flag ++;
			var sts = $(this).attr("name").split(",")[2];
			if("供水故障,等待出水,出水正常,阀门开启".indexOf(sts)>=0){
				closeMark ++;
			}else if(sts == "null" || "水阀故障,等待关水,关水正常,阀门关闭".indexOf(sts)>=0){
				openMark ++;
			}
		});
		if(closeMark == flag){
			status ="关阀";
		}else if(openMark == flag){
			status ="开阀";
		}
	}
	
	if(id==undefined){
		alert("请先选择项目!");
	}else if(status=="开阀"){
		if(confirm("确定要开启该项目下的所有节点阀门?")){
			var loadId = layer.load("请耐心等待几秒，努力开启中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/equipmentAllOpen.json",
				dataType : "json",
				contentType : "application/json",
				data : "pId="+id,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机或节点信息!");
					}else if(data==null || data=="error"){
						alert("开启节点阀门失败!");
					}else if (data=="开启") {
						$(".openOrCloseE").attr("name","关阀");
						$(".openOrCloseE").attr("title","关闭节点阀门");
						alert("开启节点阀门成功!");
						getEquipmentList(id);
					} else if (data=="关闭") {
						alert("开启节点阀门失败!");
					} else if (data=="非手动模式") {
						alert("当前为非手动模式，操作无效!");
					} else {
						alert("开启节点阀门异常!");
					}
				},
				error : function(){
					layer.close(loadId);
					alert("开启节点阀门异常!");
				}
			});
		}
	}else if(status=="关阀"){
		if(confirm("确定要关闭该项目下的所有节点阀门?")){
			var loadId = layer.load("请耐心等待几秒，努力关闭中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/equipmentAllClose.json",
				dataType : "json",
				contentType : "application/json",
				data : "pId="+id,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机或节点信息!");
					}else if(data==null || data=="error"){
						alert("关闭节点阀门异常!");
					}else if (data=="开启") {
						alert("关闭节点阀门失败!");
					} else if (data=="关闭") {
						$(".openOrCloseE").attr("name","开阀");
						$(".openOrCloseE").attr("title","开启节点阀门");
						alert("关闭节点阀门成功!");
						getEquipmentList(id);
					} else if (data=="非手动模式") {
						alert("当前为非手动模式，操作无效!");
					} else {
						alert("关闭节点阀门异常!");
					}
				},
				error : function(){
					layer.close(loadId);
					alert("关闭节点阀门异常!");
				}
			});
		}
	}
}

/**
 * @Title: 				openOrCloseEquipment 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午4:48:53
 * @Description: 		单节点开关
 * @param object 
 * void 				返回
 */
function openOrCloseEquipment(object){
	var str = object.attr("name").split(",");
	var status = str[2];
	var id = str[3];
	if(status=="null"||status==""||"水阀故障,等待关水,关水正常,阀门关闭".indexOf(status)>=0){
		if(confirm("确定开启该节点阀门?")){
			var loadId = layer.load("请耐心等待几秒，努力开启中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/equipmentOpen.json",
				dataType : "json",
				contentType : "application/json",
				data : "eId="+id,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机或节点信息!");
					}else if(data==null || data=="error"){
						alert("单节点开阀异常!");
					}else if (data=="开启") {
						object.removeAttr("name");
						object.attr("name",str[0]+","+str[1]+",阀门开启,"+id);
						alert("单节点开阀成功!");
					} else if (data=="关闭") {
						alert("单节点开阀失败!");
					} else if (data=="非手动模式") {
						alert("当前为非手动模式，操作无效!");
					} else {
						alert("单节点开阀异常!");
					}
				},
				error : function(){
					layer.close(loadId);
					alert("单节点开阀异常!");
				}
			});
		}
	}else if(status=="null"||status==""||"供水故障,等待出水,出水正常,阀门开启".indexOf(status)>=0){
		if(confirm("确定关闭该节点阀门?")){
			var loadId = layer.load("请耐心等待几秒，努力关闭中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/equipmentClose.json",
				dataType : "json",
				contentType : "application/json",
				data : "eId="+id,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机或节点信息!");
					}else if(data==null || data=="error"){
						alert("单节点关阀异常!");
					}else if (data=="开启") {
						alert("单节点关阀失败!");
					} else if (data=="关闭") {
						object.removeAttr("name");
						object.attr("name",str[0]+","+str[1]+",阀门关闭,"+id);
						alert("单节点关阀成功!");
					} else if (data=="非手动模式") {
						alert("当前为非手动模式，操作无效!");
					} else {
						alert("单节点关阀异常!");
					}
				},
				error : function(){
					layer.close(loadId);
					alert("单节点关阀异常!");
				}
			});
		}
	}
}

/**
 * @Title: 				setTimeLen 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午5:00:26
 * @Description: 		设置时间段  
 * void 				返回
 */
function setTimeLen(){
	//设置时段
	$("#setTimeLen").click(function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		var m1,m2,m3,m4,m5,m6;
		if($(".startMinute1").val()=="60"){
			m1="00";
		}else{
			m1=$(".startMinute1").val();
		}
		if($(".endMinute1").val()=="60"){
			m2="00";
		}else{
			m2=$(".endMinute1").val();
		}
		if($(".startMinute2").val()=="60"){
			m3="00";
		}else{
			m3=$(".startMinute2").val();
		}
		if($(".endMinute2").val()=="60"){
			m4="00";
		}else{
			m4=$(".endMinute2").val();
		}
		if($(".startMinute3").val()=="60"){
			m5="00";
		}else{
			m5=$(".startMinute3").val();
		}
		if($(".endMinute3").val()=="60"){
			m6="00";
		}else{
			m6=$(".endMinute3").val();
		}
		var times = $(".startHour1").val() + "," + m1 + ","
					+ $(".endHour1").val() + "," + m2 + ","
					+ $(".startHour2").val() + "," + m3 + ","
					+ $(".endHour2").val() + "," + m4 + ","
					+ $(".startHour3").val() + "," + m5 + ","
					+ $(".endHour3").val() + "," + m6;
		
		if (times == "24,00,24,00,24,00,24,00,24,00,24,00") {
			alert("未设置时间段!");
		}else if ($(".startHour1").val() == "24" && $(".endHour1").val() != "24") {
			alert("请选择时段一的开始时间!");
		} else if ($(".clickVal1").attr("checked") == "checked" && (parseInt($(".startHour1").val(), 10) >  parseInt($(".endHour1").val(), 10) || (parseInt($(".endHour1").val(), 10)*60 +parseInt($(".endMinute1").val(), 10)) - (parseInt($(".startHour1").val(), 10)*60 +parseInt($(".startMinute1").val(), 10)) < 1 )) {
			alert("请选择时段一的正确时间格式,开始时间要小于结束时间，时段间隔不能低于1分钟!");
		}else if ($(".startHour1").val() != "24" && $(".endHour1").val() == "24") {
			alert("请选择时段一的结束时间!");
		} else if ($(".startHour2").val() == "24" && $(".endHour2").val() != "24") {
			alert("请选择时段二的开始时间!");
		} else if ($(".clickVal2").attr("checked") == "checked" && (parseInt($(".startHour2").val(), 10) >  parseInt($(".endHour2").val(), 10)|| parseInt($(".startHour2").val(), 10) <  parseInt($(".endHour1").val(), 10)||(parseInt($(".endHour2").val(), 10)*60 +parseInt($(".endMinute2").val(), 10)) - (parseInt($(".startHour2").val(), 10)*60 +parseInt($(".startMinute2").val(), 10)) < 1 || (parseInt($(".startHour2").val(), 10)*60 +parseInt($(".startMinute2").val(), 10))-(parseInt($(".endHour1").val(), 10)*60 +parseInt($(".endMinute1").val(), 10)) < 1 )) {
			alert("请选择时段二的正确时间格式,开始时间要小于结束时间，时段间隔不能低于1分钟!");
		}else if ($(".startHour2").val() != "24" && $(".endHour2").val() == "24") {
			alert("请选择时段二的结束时间!");
		} else if ($(".startHour3").val() == "24" && $(".endHour3").val() != "24") {
			alert("请选择时段三的开始时间!");
		} else if ($(".clickVal3").attr("checked") == "checked" && (parseInt($(".startHour3").val(), 10) >  parseInt($(".endHour3").val(), 10)|| parseInt($(".startHour3").val(), 10) <  parseInt($(".endHour2").val(), 10)||(parseInt($(".endHour3").val(), 10)*60 +parseInt($(".endMinute3").val(), 10)) - (parseInt($(".startHour3").val(), 10)*60 +parseInt($(".startMinute3").val(), 10)) < 1 || (parseInt($(".startHour3").val(), 10)*60 +parseInt($(".startMinute3").val(), 10))-(parseInt($(".endHour2").val(), 10)*60 +parseInt($(".endMinute2").val(), 10)) < 1 || ((23-parseInt($(".endHour3").val(), 10))*60 +60-parseInt($(".endMinute3").val(), 10))+(parseInt($(".startHour1").val(), 10)*60 +parseInt($(".startMinute1").val(), 10)) < 1)) {
			alert("请选择时段三的正确时间格式,开始时间要小于结束时间，时段间隔不能低于1分钟!");
		}else if ($(".startHour3").val() != "24" && $(".endHour3").val() == "24") {
			alert("请选择时段三的结束时间!");
		} else if (confirm("您确定要设置时段控制吗?")) {
			setTimesLenTemp(id,times);
		}
	});
}

/**
 * @Title: 				setTimesLenTemp 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午11:34:29
 * @Description: 		设置时间段到主机 
 * void 				返回
 */
function setTimesLenTemp(id,times){
	var loadId = layer.load("请耐心等待几秒，努力设置时段中...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/NetWork/setTimesLen.json",
		dataType : "json",
		contentType : "application/json",
		data : id+","+times,
		success : function(data){
			layer.close(loadId);
			if( data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data == "null"){
				alert("该项目下未查询到主机信息!");
			}else if(data==null || data=="error"){
				alert("设置时段失败!");
			} else if (data=="ok") {
				layer.close(i);
				alert("设置时段成功!");
			} else if (data=="非手动模式") {
				alert("当前为非手动模式，操作无效!");
			} else {
				alert("设置时段异常!");
			}
		},
		error : function(){
			layer.close(loadId);
			alert("设置时段异常!");
		}
	});
}

/**
 * @Title: 				setModel 
 * @author 				杨贵松
 * @date 				2014年4月8日 下午5:00:59
 * @Description: 		设置控制模式
 * @param id 
 * void 				返回
 */
function setModel(){
	$("#setRadioModel").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		var model = $(":radio[name=radioModel][checked]").val();
		var loadId = layer.load("请耐心等待几秒，努力设置模式中...");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/NetWork/setModel.json",
			dataType : "json",
			contentType : "application/json",
			data : id+","+model,
			success : function(data){
				layer.close(loadId);
				if( data=="noLogin"){
					window.location.href="http://localhost:8080/TIIS/indexs/redirect";
				}else if(data == "null"){
					alert("该项目下未查询到主机信息!");
				}else if(data==null || data=="error"){
					alert("设置模式失败!");
				} else if (data=="F0") {
					layer.close(i);
					alert("设置模式完成，当前模式为：自动模式!");
				} else if (data=="F1") {
					layer.close(i);
					alert("设置模式完成，当前模式为：时段模式!");
				} else if (data=="F2") {
					layer.close(i);
					alert("设置模式完成，当前模式为：手动模式!");
				} else if (data=="F3") {
					layer.close(i);
					alert("设置模式完成，当前模式为：强制开启模式(强制开启1分钟)!");
				} else if (data=="非手动模式") {
					alert("当前为非手动模式，操作无效!");
				} else {
					alert("设置模式异常!");
				}
			},
			error : function(){
				layer.close(loadId);
				alert("设置模式异常!");
			}
		});
	});
}

/**
 * @Title: 				setParameter 
 * @author 				杨贵松
 * @date 				2014年5月8日 下午5:29:19
 * @Description: 		设置自控参数 
 * @param id 
 * void 				返回
 */
function setParameter(){
	$(".parameter").find("input[id=inputText]").focus(function(){
		$(this).css("border","#00B2EE solid 1px");
	});
	$(".parameter").find("input[id=inputText]").blur(function(){
		$(this).css("border","#B0B0B0 solid 1px");
	});
	$("#setParameter").live("click",function(){
		var id = $(".breadcrumbs").find("li[class=pTitleLi]").attr("id");
		
		var soilHumidity  = $(".soilHumidity").val();
		var upTemperature  = $(".upTemperature").val();
		var downTemperature  = $(".downTemperature").val();
		var ReValue = /^(\+\d+|\d+|\-\d+|\d+|\+\d+|\-\d+)$/;
		
		if(soilHumidity==""  ){
			$(".soilHumidity").css("border", "#B35D5D solid 1px");
			alert("土壤湿度不能为空!");
		}else if(upTemperature==""){
			$(".upTemperature").css("border", "#B35D5D solid 1px");
			alert("湿度上限不能为空!");
		}else if(downTemperature==""){
			$(".downTemperature").css("border", "#B35D5D solid 1px");
			alert("湿度下限不能为空!");
		}else if(parseInt(soilHumidity,10)<10 || parseInt(soilHumidity,10) > 100){
			$(".soilHumidity").css("border", "#B35D5D solid 1px");
			alert("湿度范围为[10~100]!");
		}else if(!ReValue.test(soilHumidity)){
			$(".soilHumidity").css("border", "#B35D5D solid 1px");
			alert("请输入[10~100]的整数!");
		}else if(parseInt(upTemperature,10)<-50 || parseInt(upTemperature,10) > 60){
			$(".upTemperature").css("border", "#B35D5D solid 1px");
			alert("温度范围为[-50~60]!");
		}else if(!ReValue.test(upTemperature)){
			$(".upTemperature").css("border", "#B35D5D solid 1px");
			alert("请输入[-50~60]的整数!");
		}else if(parseInt(downTemperature,10)<-50 || parseInt(downTemperature,10) > 60){
			$(".downTemperature").css("border", "#B35D5D solid 1px");
			alert("温度范围为[-50~60]!");
		}else if(!ReValue.test(downTemperature)){
			$(".downTemperature").css("border", "#B35D5D solid 1px");
			alert("请输入[-50~60]的整数!");
		}else if(confirm("确定设置?")){
			var dataStr = id+","+soilHumidity+","+upTemperature+","+downTemperature;
			var loadId = layer.load("请耐心等待几秒，努力设置自控参数中...");
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/NetWork/setParameter.json",
				dataType : "json",
				contentType : "application/json",
				data : dataStr,
				success : function(data){
					layer.close(loadId);
					if( data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data == "null"){
						alert("该项目下未查询到主机信息!");
					}else if(data==null || data=="error"){
						alert("设置自控参数失败!");
					} else if (data=="ok") {
						alert("设置自控参数成功!");
						layer.close(i);
					} else {
						alert("设置自控参数异常!");
					}
				},
				error : function(){
					layer.close(loadId);
					alert("设置自控参数异常!");
				}
			});
		}
	});
}
