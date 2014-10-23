/**
 * @author 				杨贵松
 * @date 				2014年4月1日 上午11:17:38
 * @Description: 		统计分析
 */
$(document).ready(function(){
	/************************************** 退出 *************************************/
	exit();
	/************************************** 初始化统计周期年月 *************************/
	initCanendle();
	/************************************** 获取服务器当前时间 *************************/
	nowTime();
	/************************************** 初始化项目下拉列表 *************************/
	initProjectList(".projectVal");
	//分页
//	initPagination();
	/************************************** 统计分析 *************************/
	$("#statisticSubmit").click(function(){
		statisticSubmit();
	});
});

/*********************************************** functions *************************************************/

/**
 * @Title: 				
 * @author 				杨贵松
 * @date 				2014年6月13日 下午4:53:33
 * @Description: 		统计分析主函数
 * void 				返回
 */
function statisticSubmit(){
	var circleYear1 = parseInt($("#circleYear1").val(),10);
	var circleYear2 = parseInt($("#circleYear2").val(),10);
	var circleMouth1 = parseInt($("#circleMouth1").val(),10);
	var circleMouth2 = parseInt($("#circleMouth2").val(),10);
	
	var styleVal = $("input:radio[checked]").val();
	var projectVal = $(".projectVal").val();
	
	var dateStr = "";
	var circleTitle = new Array();
	var circleValue = new Array();
	
	if(styleVal==1){
		if(circleYear1==circleYear2){
			for(var i = circleMouth1 ; i<=circleMouth2 ; i++){
				if(i<10){
					dateStr += circleYear1 + "-0" + i + ",";
				}else{
					dateStr += circleYear1 + "-" + i + ",";
				}
			}
		}else if(circleYear2 >circleYear1){
			for(var i = circleMouth1 ; i<= 12 ; i++){
				if(i<10){
					dateStr += circleYear1 + "-0" + i + ",";
				}else{
					dateStr += circleYear1 + "-" + i + ",";
				}
			}
			for(var i = 1 ; i<=circleMouth2 ; i++){
				if(i<10){
					dateStr += circleYear2 + "-0" + i + ",";
				}else{
					dateStr += circleYear2 + "-" + i + ",";
				}
			}
		}
	}else{
		for(var i = circleYear1 ; i<=circleYear2 ; i++){
			dateStr += i + ",";
		}
	}
	
	var dateArray = dateStr.split(",");
	
	if(circleYear1 == "0000" || circleYear2 == "0000"){
		$(".warnningLab").html("提示：请选择正确的统计周期，开始周期要小于结束周期 !");
		$(".statisticCircle").css("border","#B35D5D 1px solid");
	}else if(circleYear1>circleYear2){
		$(".warnningLab").html("提示：请选择正确的统计周期，开始周期要小于结束周期 !");
		$(".statisticCircle").css("border","#B35D5D 1px solid");
	}else if(styleVal == 1 && (circleMouth1 == "0000" || circleMouth2 == "0000")){
		$(".warnningLab").html("提示：请选择正确的统计周期，开始周期要小于结束周期 !");
		$(".statisticCircle").css("border","#B35D5D 1px solid");
	}else if(styleVal == 1 && (circleYear1==circleYear2 && circleMouth1 >circleMouth2)){
		$(".warnningLab").html("提示：请选择正确的统计周期，开始周期要小于结束周期 !");
		$(".statisticCircle").css("border","#B35D5D 1px solid");
	}else if(styleVal == 0 && (circleYear2-circleYear1>12)){
		$(".warnningLab").html("提示：统计周期不得超过12年或者12个月 !");
		$(".statisticCircle").css("border","#B35D5D 1px solid");
	}else if(styleVal == 1 && (((circleYear2-circleYear1)*12+1+(circleMouth2-circleMouth1))>12)){
		$(".warnningLab").html("提示：统计周期不得超过12年或者12个月 !");
		$(".statisticCircle").css("border","#B35D5D 1px solid");
	}else{
		$(".warnningLab").html("");
		$(".statisticCircle").css("border","#c3c3c3 1px solid");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/statistic/statisticToWater.json",
			dataType : "json",
			contentType : "application/json",
			data : styleVal+";"+projectVal+";"+dateStr,
			success : function(data){
				if(data=="noLogin"){
					window.location.href="http://localhost:8080/TIIS/indexs/redirect";
				}else if(data=="error"){
					$(".warnningLab").html("统计失败，请重试 !");
					$(".statisticCircle").css("border","#B35D5D 1px solid");
				}else{
					for(var i=0;i<dateArray.length;i++){
						$.each(data,function(key,values){  
							if(dateArray[i]==key){
								circleTitle.push([key]);
								circleValue.push([values]);
							}
						});  
					}
					highCharts(circleTitle,circleValue);
				}
			},
			error : function(){
				$(".warnningLab").html("统计分析异常 !");
				$(".statisticCircle").css("border","#B35D5D 1px solid");
			}
		});
		
	}
}
/**
 * @Title: 				
 * @author 				杨贵松
 * @date 				2014年6月13日 下午4:08:33
 * @Description: 		初始化统计周期的年月
 * void 				返回
 */
function highCharts(circleTitle,circleValue) {
    $('#container').highcharts({
        chart: {
        	backgroundColor:'#F5F5F5',
			plotBackgroundColor: 'rgba(255, 255, 255, .9)',
			plotShadow: true,
			plotBorderWidth: 1,
		    width:876,
		    height:450,
            type: 'column',
            margin: [ 50, 50, 80, 80]
        },
        title: {
            text: '用水量统计',
            style: {
	            color: '#333',
	            fontWeight: 'bold',
	            fontSize: '16px',
	            fontFamily: 'Verdana, sans-serif'
	         }
        },
        xAxis: {
            categories: circleTitle,
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                	color: '#333',
     	            fontWeight: 'bold',
     	            fontSize: '13px',
     	            fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            minorTickInterval: 'auto',
		    lineColor: '#000',
		    lineWidth: 1,
		    tickWidth: 1,
		    tickColor: '#000',
            title: {
                text: '用水量 (L)',
                style: {
    	            color: '#333',
    	            fontWeight: 'bold',
    	            fontSize: '14px',
    	            fontFamily: 'Verdana, sans-serif'
    	         }
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '用水量: <b>{point.y:.2f} L</b>',
        },
        plotOptions: {//柱状图宽度
        	column: {
        		pointPadding: 0.2,
        		borderWidth: 0,
        		pointWidth: 40
        	}
        },
        series: [{
            name: 'Population',
            data: circleValue,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
}

/**
 * @Title: 				initProjectList 
 * @author 				杨贵松
 * @date 				2014年6月13日 上午11:50:58
 * @Description: 		初始化加载下拉列表的项目信息  
 * void 				返回
 */
function initProjectList(selectObj){
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
				alert("您还没有注册项目信息，请先添加项目!");
			}else{
				var pListStr = "";
				pListStr += "<option value='0000'>全部</option>";
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
 * @Title: 				initCanendle 
 * @author 				杨贵松
 * @date 				2014年6月13日 下午4:08:33
 * @Description: 		初始化统计周期的年月
 * void 				返回
 */
function initCanendle(){
	var startYear = 2012;
	var mydate=new Date(); 
	var nowYear = mydate.getFullYear() ; 
	var year = "<option value='0000'>选择年</option>";
	while(nowYear >= startYear){
		year += "<option value='"+nowYear+"'>"+nowYear+"&nbsp;年</option>";
		nowYear = nowYear - 1;
	}
	var startMouth = 1;
	var mouth = "<option value='0000'>选择月</option>";
	while(startMouth <= 12){
		if(startMouth < 10){
			mouth += "<option value='0"+startMouth+"'>0"+startMouth+"&nbsp;月</option>";
		}else{
			mouth += "<option value='"+startMouth+"'>"+startMouth+"&nbsp;月</option>";
		}
		startMouth = startMouth + 1;
	}
	$(".circleYear").empty().append(year);
	$(".circleMouth").empty().append(mouth);
}

/**
 * @Title: 				
 * @author 				杨贵松
 * @date 				2014年6月13日 下午4:08:33
 * @Description: 		改变统计周期方式:年、月
 * 当改变复选框的值后，IE在等待失去焦点，但是click事件是立即触发的，
 * 因此利用click事件让复选框失去焦点，这样就会触发chang事件了，
 * 然后再把焦点重新转移到该复选框上。
 * 
 * void 				返回
 */
$(function() {
	if ($.browser.msie) {
		$('input:radio').click(function() {
			this.blur();
			this.focus();
		});
	}
	$('input:radio').change(function() { 
		initCanendle();
		if($(this).val()==0){
			$(".circleMouth").css("display","none");
		}else{
			$(".circleMouth").css("display","inline");
		}
	}); 
});