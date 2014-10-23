/**
 * @Title: 				commUtil.js
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:43:15
 * @Description: 		TIIS公共js方法集合
 * 
 */

/************************************************* comm Functions ****************************************************/

/**
 * 用户退出
 */
function exit(){
	$(".logOut").click(function(){
		if(confirm("退出系统?")){
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/index/exit.json",
				dataType : "json",
				contentType : "application/json",
				success : function(data){
					if(data=="ok"){
						window.location.href="http://localhost:8080/TIIS/index/login";
					}else if(data=="error"){
						alert("退出系统失败!");
					}
				},
				error : function(){
					alert("退出系统异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				myTimer 
 * @author 				杨贵松
 * @date 				2014年7月26日 下午12:00:57
 * @Description: 		报警消息框提醒
 * void 				返回
 */
function msgWarning() {
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/message/selectAllMessageCounts.json",
		dataType : "json",
		async : false,
		contentType : "application/json",
		data : "0000,0",
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data > 0){
				layer.confirm("您有 <a href='http://localhost:8080/TIIS/indexs/messages' style='text-decoration:underline;cursor:pointer;'>" + data+'</a> 条报警消息,是否现在查看?',function(index){
				    window.location.href="http://localhost:8080/TIIS/indexs/messages";
				});
			}
		},
		error : function(){
			
		}
	});
	window.setTimeout("msgWarning()", 600000);//设置循环时间
}

/**
 * @Title: 				initPagination 
 * @author 				杨贵松
 * @date 				2014年4月1日 下午4:50:43
 * @Description: 		项目分页函数
 * void 				返回
 */
function initPagination() {
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
					callback: pageselectCallback,
					items_per_page: 14, //每页显示15项
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
 * @Title: 				pageselectCallback 
 * @author 				杨贵松
 * @date 				2014年4月1日 下午4:51:05
 * @Description: 		分页回调函数 
 * @param page_index
 * @param jq 
 * void 				返回
 */
function pageselectCallback(page_index, jq){
	var Str = "";
	var loadI = layer.load("正在加载项目列表...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/project/selectAllProjectPages.json",
		dataType : "json",
		contentType : "application/json",
		data : page_index+",14,-1,-1",
		success : function(data){
			layer.close(loadI);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
			}else{
				var pListStr = "";
				$(data).each(function(i,item){
					if(item.name.length>13){
						pListStr = item.name.substring(0,13)+"..";
					}else{
						pListStr = item.name;
					}
					Str += "<div class='projectItem'><a class='projectA' href='javascript:void(0);' name='"+item.id+"' title = '"+item.name+"'><img src='images/elements/control/headSubmit.png' />&nbsp;&nbsp;"+pListStr+"</a></div>";
				});
				$(".projectList").empty().append(Str); //装载对应分页的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载项目列表异常!");
		}
	});
}

/**
 * @Title: 				initDatePicker 
 * @author 				杨贵松
 * @date 				2014年4月1日 下午6:25:28
 * @Description: 		汉化jQuery datepicker插件
 * void 				返回
 * 
 * 2014-04-08 18：28：00备注：由于jQuery-ui.min.js样式冲突，暂停止使用
 */
function initDatePicker(){
	$.datepicker.regional['zh-CN'] = {
	        clearText: '清除',
	        clearStatus: '清除已选日期',
	        closeText: '关闭',
	        closeStatus: '不改变当前选择',
	        prevText: '<上月',
	        prevStatus: '显示上月',
	        prevBigText: '<<',
	        prevBigStatus: '显示上一年',
	        nextText: '下月>',
	        nextStatus: '显示下月',
	        nextBigText: '>>',
	        nextBigStatus: '显示下一年',
	        currentText: '今天',
	        currentStatus: '显示本月',
	        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	        monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
	        monthStatus: '选择月份',
	        yearStatus: '选择年份',
	        weekHeader: '周',
	        weekStatus: '年内周次',
	        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
	        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
	        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
	        dayStatus: '设置 DD 为一周起始',
	        dateStatus: '选择 m月 d日, DD',
	        dateFormat: 'yy-mm-dd',
	        firstDay: 1,
	        initStatus: '请选择日期',
	        isRTL: false
	    };
	    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
}

/**
 * @Title: 				timeStamp2String 
 * @author 				杨贵松
 * @date 				2014年4月3日 下午4:31:17
 * @Description: 		格式化格里尼治时间
 * @param time
 * @returns {String} 
 * String 				返回
 */
function timeStamp2String(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1)
			: datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime
			.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime
			.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
			: datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
			: datetime.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
}

/**
 * @Title: 				initProjectToSelect 
 * @author 				杨贵松
 * @date 				2014年4月4日 上午11:50:58
 * @Description: 		初始化加载下拉列表的项目信息  
 * void 				返回
 */
function initProjectToSelect(selectObj){
	var loadI = layer.load("正在加载项目信息...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/project/selectAllProject.json",
		dataType : "json",
		contentType : "application/json",
		success : function(data){
			layer.close(loadI);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
			}else{
				var pListStr = "";
				pListStr += "<option value='0000'>-请选择项目-</option>";
				$(data).each(function(i,item){
					pListStr += "<option value='"+item.id+"'>"+item.name+"</option>";
				});
				$(selectObj).empty().append(pListStr); //装载对应项目的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载项目信息异常!");
		}
	});
}

/**
 * @Title: 				initUserToSelect 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午10:14:44
 * @Description: 		初始化加载下拉列表的用户信息
 * void 				返回
 */
function initUserToSelect(selectObj){
	var loadI = layer.load("正在加载用户信息...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/user/selectAllUser.json",
		dataType : "json",
		contentType : "application/json",
		success : function(data){
			layer.close(loadI);
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="null"){
				alert("未查询到用户信息，请添加新用户!");
			}else{
				var pListStr = "";
				pListStr += "<option value='0000'>-请选择用户-</option>";
				$(data).each(function(i,item){
					pListStr += "<option value="+item.id+">"+item.loginname+"</option>";
				});
				$(selectObj).empty().append(pListStr); //装载对应项目的内容
			}
		},
		error : function(){
			layer.close(loadI);
			alert("加载用户信息异常!");
		}
	});
}

/**
 * @Title: 				time 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午5:23:06
 * @Description: 		时间生成工厂 
 * @param hour
 * @param minute 
 * void 				返回
 */
function time(hour, minute) {
	var hourId = $(hour);
	var minuteId = $(minute);
	hourId.empty();
	minuteId.empty();
	// 小时生成0-23
	var hourVal = new Option("-小时-", "24");
	hourId.append(hourVal);
	for ( var i = 0; i < 24; i++) {
		if (i < 10) {
			var hourVal = new Option("0" + i, "0" + i);
			hourId.append(hourVal);
		} else {
			var hourVal = new Option(i, i);
			hourId.append(hourVal);
		}
	}
	// 分钟生成0-59
	var minuteVal = new Option("-分钟-", "60");
	minuteId.append(minuteVal);
	for ( var i = 0; i < 60; i++) {
		if (i < 10) {
			var minuteVal = new Option("0" + i, "0" + i);
			minuteId.append(minuteVal);
		} else {
			var minuteVal = new Option(i, i);
			minuteId.append(minuteVal);
		}
	}
}

/**
 * @Title: 				checkVal 
 * @author 				杨贵松
 * @date 				2014年4月7日 下午10:54:39
 * @Description: 		判断时间段是否选中
 * @param clickVal
 * @param hour1
 * @param hour2
 * @param minute1
 * @param minute2 
 * void 				返回
 */
function checkVal(clickVal, hour1, hour2, minute1, minute2) {
	$("." + clickVal).click(function() {
		var number = clickVal.substring(clickVal.length - 1, clickVal.length);
		if ($(this).attr("checked") == "checked" && number == "1") {
			$("." + hour1).attr("disabled", false);
			$("." + hour2).attr("disabled", false);
			$("." + minute1).attr("disabled", false);
			$("." + minute2).attr("disabled", false);
		} else if ($(this).attr("checked") == undefined && number == "1") {
			$("." + hour1).attr("disabled", true);
			$("." + hour2).attr("disabled", true);
			$("." + minute1).attr("disabled", true);
			$("." + minute2).attr("disabled", true);

			if (confirm("确定清除后面所有时间段的设置?")) {
				//清除后续的所有时间段选项
			for (var i = number; i <= 3; i++) {

				var h1 = "startHour" + i;
				var h2 = "endHour" + i;
				var m1 = "startMinute" + i;
				var m2 = "endMinute" + i;

				$(".clickVal" + i).attr("checked", false);

				$("." + h1).attr("disabled", true);
				$("." + h2).attr("disabled", true);
				$("." + m1).attr("disabled", true);
				$("." + m2).attr("disabled", true);

				var objHour1 = $("." + h1);
				objHour1.get(0).options[0].selected = true;
				var objminute1 = $("." + m1);
				objminute1.get(0).options[0].selected = true;
				var objHour2 = $("." + h2);
				objHour2.get(0).options[0].selected = true;
				var objminute2 = $("." + m2);
				objminute2.get(0).options[0].selected = true;
			}
		} else {
			$(this).attr("checked", true);
		}
		} else if ($(".clickVal" + (number - 1)).attr("checked") == undefined) {
			$("." + clickVal).attr("checked", false);
			alert("需按顺序选择时段!");
		} else if ($(this).attr("checked") == "checked") {
			$("." + hour1).attr("disabled", false);
			$("." + hour2).attr("disabled", false);
			$("." + minute1).attr("disabled", false);
			$("." + minute2).attr("disabled", false);
		} else if ($(this).attr("checked") == undefined && number != "1") {
			if (confirm("确定清除后面所有时间段的设置?")) {
				//清除后续的所有时间段选项
			for (i = number; i <= 3; i++) {

				var h1 = "startHour" + i;
				var h2 = "endHour" + i;
				var m1 = "startMinute" + i;
				var m2 = "endMinute" + i;

				$(".clickVal" + i).attr("checked", false);

				$("." + h1).attr("disabled", true);
				$("." + h2).attr("disabled", true);
				$("." + m1).attr("disabled", true);
				$("." + m2).attr("disabled", true);

				var objHour1 = $("." + h1);
				objHour1.get(0).options[0].selected = true;
				var objminute1 = $("." + m1);
				objminute1.get(0).options[0].selected = true;
				var objHour2 = $("." + h2);
				objHour2.get(0).options[0].selected = true;
				var objminute2 = $("." + m2);
				objminute2.get(0).options[0].selected = true;
			}
		} else {
			$(this).attr("checked", true);
		}

	}
}	);
}

/**
 * @Title: 				nowTime 
 * @author 				杨贵松
 * @date 				2014年4月8日 下午5:33:51
 * @Description: 		将系统时间显示到页面 
 * void 				返回
 */
function nowTime() {
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/system/findNowLocalTime.json",
		dataType : "json",
		contentType : "application/json",
		success : function(data){
				if(data=="noLogin"){
					$(".nowTime").html("登录超时!");
				}else if(data!="error"){
					var dataTemp = new Date(data);
					$(".nowTime").html(showLocale(dataTemp));
				}else{
					$(".nowTime").html("获取系统时间失败!");
				}
		},
		error : function(){
			$(".nowTime").html("获取系统时间异常!");
		}
	});
	window.setTimeout("nowTime()", 1000);
}

/**
 * @Title: 				showLocale 
 * @author 				杨贵松
 * @date 				2014年4月8日 下午5:33:36
 * @Description: 		格式化日期时间
 * @param objD
 * @returns {String} 
 * String 				返回
 */
function showLocale(objD) {
	var str, colorhead, colorfoot;
	var yy = objD.getYear();
	var MM = objD.getMonth() + 1;
	var dd = objD.getDate();
	var hh = objD.getHours();
	var mm = objD.getMinutes();
	var ss = objD.getSeconds();
	var ww = objD.getDay();
	if (ww > 0 && ww < 6) {
		colorhead = "<font>";
	} else {
		colorhead = "<font>";
	}
	var strWeekDay = "星期日,星期一,星期二,星期三,星期四,星期五,星期六".split(",");
	colorfoot = "</font>";
	str = colorhead + (yy < 1900 ? 1900 + yy : yy) + "年" + fillZero(MM) + MM
			+ "月" + fillZero(dd) + dd + "日  " + strWeekDay[ww] + " "
			+ fillZero(hh) + hh + ":" + fillZero(mm) + mm + ":" + fillZero(ss)
			+ ss + colorfoot;
	return str;
}

/**
 * @Title: 				fillZero 
 * @author 				杨贵松
 * @date 				2014年4月8日 下午5:33:24
 * @Description: 		将时间或分钟中小于十的数字转换成'0'+intVal的字符串，如1准换成01
 * @param intVal
 * @returns 
 * any 				返回
 */
function fillZero(intVal) {
	return intVal < 10 ? '0' : '';
}



/************************************************* Map自定义函数 ******************************************************/

function Map() {
	this.container = new Object();
}

Map.prototype.put = function(key, value) {
	this.container[key] = value;
};

Map.prototype.get = function(key) {
	return this.container[key];
};

Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		keyset[count] = key;
		count++;
	}
	return keyset;
};

Map.prototype.size = function() {
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		count++;
	}
	return count;
};

Map.prototype.remove = function(key) {
	delete this.container[key];
};

Map.prototype.toString = function() {
	var str = "";
	for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
		str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
	}
	return str;
};


//自定义Map函数使用实例
/**
var map = new Map();
map.put("a", "aaa");
map.put("b", "bbb");
map.put("cc", "cccc");
map.put("c", "ccc");
map.remove("cc");
var array = map.keySet();
for ( var i in array) {
	document.write("key:(" + array[i] + ") <br>value: (" + map.get(array[i])
			+ ") <br>");
}
*/