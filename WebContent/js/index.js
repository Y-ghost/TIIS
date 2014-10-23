
/**
 * @Title: 				searchEquipment 
 * @author 				杨贵松
 * @date 				2014年4月22日 下午3:01:13
 * @Description: 		搜索节点信息
 * void 				返回
 */
function searchEquipment(){
	var projectId = $(".hostOrder").val();
	if(projectId=="0000"){
		alert("请选择项目!");
	}else{
		var loadI = layer.load("该过程大约需要30秒,正努力搜索中,请等待...");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/NetWork/searchEquipment.json",
			dataType : "json",
			contentType : "application/json",
			data : projectId,
			success : function(data){
				layer.close(loadI);
				if( data=="noLogin"){
					window.location.href="http://localhost:8080/TIIS/indexs/redirect";
				}else if(data==null || data=="null"){
					alert("该项目下无主机，请先添加主机!");
				}else if(data=="error"){
					alert("搜索节点地址失败!");
				}else if(data=="0"){
					alert("该项目下未检测到节点!");
				}else{
					var address = data.split(",");
					var result="";
					for(var i=0;i<address.length;i++){
						result += "<div class='pTitle'>" +
								"节点名称：<input type='text' class='equipmentName' style='width:170px;'/> &nbsp;&nbsp;" +
								"地址：<input type='text' class='equipmentAddress' style='width:100px;' disabled='disabled' value='"+address[i]+"'/>&nbsp;&nbsp;" +
								"流量参数：<input type='text' class='flowParameter' style='width:60px;' />" +
								"</div>";
					}
					$("#contextForEquipment").empty().append(result);
					//验证输入框
					validEquipmentInput();
				}
			},
			error : function(){
				layer.close(loadI);
				alert("搜索节点异常!");
			}
		});
	}
}

/**
 * @Title: 				registeEquipment 
 * @author 				杨贵松
 * @date 				2014年4月22日 下午5:41:36
 * @Description: 		批量注册节点信息 
 * void 				返回
 */
function registeEquipment(){
	var equipment = "";
	var projectId = $(".hostOrder").val();
	$("#contextForEquipment").find("div[class=pTitle]").each(function(i){
		var name = $(this).find("input[class=equipmentName]").val();
		var address = $(this).find("input[class=equipmentAddress]").val();
		var flowParameter = $(this).find("input[class=flowParameter]").val();
		equipment += "{name:"+name+",code:"+address+",flowparameter:"+flowParameter+"};";
	});
	if(projectId=="0000"){
		alert("请选择项目!");
	}else if(equipment==""){
		alert("该项目下没有监测到节点!");
	}else  if(validContextForEquipment()){
		equipment =equipment.substring(0,equipment.length-1);
		if(confirm("特别提示：\n若该项目下已有节点注册,将会删除该节点的所有数据信息,确定要注册所有节点?")){
			addNetWorkEquipment(projectId,equipment);
		}
	}
}

/**
 * @Title: 				addNetWorkEquipment 
 * @author 				杨贵松
 * @date 				2014年4月22日 下午7:50:09
 * @Description: 		注册节点到现场控制器
 * void 				返回
 */
function addNetWorkEquipment(projectId,equipment){
	var loadI = layer.load("该过程需几秒钟,正努力注册中,请等待...");
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/NetWork/addNetWorkEquipment.json",
		dataType : "json",
		contentType : "application/json",
		data : projectId+"&"+equipment,
		success : function(data){
			if( data=="noLogin"){
				layer.close(loadI);
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data==null || data=="null"){
				layer.close(loadI);
				alert("该项目下无主机，请先添加主机!");
			}else if(data=="ok"){
				$.ajax({
					type : "post",
					url : "http://localhost:8080/TIIS/Equipment/addAllEquipment.json",
					dataType : "json",
					contentType : "application/json",
					data : projectId+"&"+equipment,
					success : function(data){
						layer.close(loadI);
						if( data=="noLogin"){
							window.location.href="http://localhost:8080/TIIS/indexs/redirect";
						}else if(data==null || data=="null"){
							alert("该项目下无主机，请先添加主机!");
						}else if(data=="ok"){
							alert("注册节点信息到成功!");
						}else {
							alert("注册节点信息到数据库失败!");
						}
					},
					error : function(){
						layer.close(loadI);
						alert("注册节点信息到数据库异常!");
					}
				});
			}else{
				layer.close(loadI);
				alert("注册节点信息到现场失败!");
			}
		},
		error : function(){
			layer.close(loadI);
			alert("注册节点信息到现场异常!");
		}
	});
}

/**
 * @Title: 				validContextForEquipment 
 * @author 				杨贵松
 * @date 				2014年5月1日 上午12:59:25
 * @Description: 		验证填写的节点名和流量参数是否正确
 * void 				返回
 */
function validContextForEquipment(){
	var flag = 0;
	$("#contextForEquipment").find("div[class=pTitle]").each(function(i){
		var name = $(this).find("input[class=equipmentName]").val();
		var eFlowParameter = $(this).find("input[class=flowParameter]").val();
		if(name==""){
			$(".warnning").html("提示：节点名称不能为空!");
			$(".warnning").css("color","#FF3030");
			$(this).find("input[class=equipmentName]").css("border","#B35D5D solid 1px");
			flag = 1;
			return false;
		}else if(eFlowParameter==""){
			$(".warnning").html("提示：流量参数不能为空!");
			$(".warnning").css("color","#FF3030");
			$(this).find("input[class=flowParameter]").css("border","#B35D5D solid 1px");
			flag = 1;
			return false;
		}else if(!(/^[0-9]*[1-9][0-9]*$/.test(eFlowParameter)) || eFlowParameter < 200 || eFlowParameter > 1000){
			$(".warnning").html("提示：流量参数为[200,1000]区间的整数!");
			$(".warnning").css("color","#FF3030");
			$(this).find("input[class=flowParameter]").css("border","#B35D5D solid 1px");
			flag = 1;
			return false;
		}
	});
	if(flag == 1){
		return false;
	}else{
		return true;
	}
}
/**
 * @Title: 				validEquipmentName 
 * @author 				杨贵松
 * @date 				2014年4月22日 下午6:09:57
 * @Description: 		校验节点信息
 * void 				返回
 */
function validEquipmentInput(){
	$("#contextForEquipment").find("div[class=pTitle]").each(function(i){
		//鼠标点击节点名字事件
		$(this).find("input[class=equipmentName]").live("focus",function(){
			$(".warnning").css("color","#FF3030");
			$(this).css("border","#00B2EE solid 1px");
		});
		$(this).find("input[class=equipmentName]").live("blur",function(){
			if($(this).val()==""){
				$(".warnning").html("提示：节点名称不能为空!");
				$(".warnning").css("color","#FF3030");
				$(this).css("border","#B35D5D solid 1px");
			}else{
				//鼠标点击节点名字事件
				$(".warnning").html("");
				$(this).css("border","#B0B0B0 solid 1px");
			}
		});
		//鼠标点击节点流量参数事件
		$(this).find("input[class=flowParameter]").live("focus",function(){
			$(this).css("border","#00B2EE solid 1px");
		});
		$(this).find("input[class=flowParameter]").live("blur",function(){
			var eFlowParameter = $(this).val();
			if(eFlowParameter==""){
				$(".warnning").html("提示：流量参数不能为空!");
				$(".warnning").css("color","#FF4500");
				$(this).css("border","#B35D5D solid 1px");
			}else if(!(/^[0-9]*[1-9][0-9]*$/.test(eFlowParameter)) || eFlowParameter < 200 || eFlowParameter > 1000){
				$(".warnning").html("提示：流量参数为[200,1000]区间的整数!");
				$(".warnning").css("color","#FF4500");
				$(this).css("border","#B35D5D solid 1px");
			}else{
				$(".warnning").html("");
				$(this).css("border","#B0B0B0 solid 1px");
			}
		});
	});
}



