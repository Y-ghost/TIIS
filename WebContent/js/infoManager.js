/**
 * @Title: 				infoManager.js
 * @author 				杨贵松
 * @date 				2014年4月6日 下午01:43:15
 * @Description: 		项目、主机、节点等信息管理
 * 
 */

/************************************************* Functions ****************************************************/
//弹框全局变量i
var i = 0;
/**
 * @Title: 				showLayer 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:38:52
 * @Description: 		弹框初始化
 * @param mark 
 * void 				返回
 */
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
function showLayerForUser(mark) {
	i = $.layer({
		type : 1,
		title : false,
		closeBtn : true,
		border : [ 0 ],
		offset : [ '150px', '' ],
		area : [ '500px', '320px' ],
		page : {
			dom : mark
		}
	});
}

/**
 * @Title: 				closeOnclick 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:38:45
 * @Description: 		关闭弹框 
 * void 				返回
 */
function closeOnclick(){
	//关闭弹框
	$(".close").click(function() {
		layer.close(i);
	});
}
/**
 * @Title: 				projectInfoMng 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:29:59
 * @Description: 		项目信息查删改  
 * void 				返回
 */
function projectInfoMng(){
	//查看项目信息
	$(".selectProject").live("click",function() {
		var id = $(this).attr("name");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/project/selectProjectById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".projectInfo").find("input").attr("disabled",true);
					$(".projectInfo").find("select").attr("disabled",true);
					$("#modifyProject").css("display","none");
					$.initProv(".provinceItem",".cityItem","-省份-","-城市-");
					if(data=="null"){
						alert("查看的项目信息不存在!");
					}else if(data!="error"){
						$(".projectName").val(data.name);
						$(".department").val(data.department);
						$(".provinceItem").val(data.province);
						$.initCities($(".provinceItem"),$(".cityItem"));//先初始化城市，才可加载城市信息
						$(".cityItem").val(data.city);
						$(".addressItem").val(data.address);
						$(".area").val(data.area);
						showLayer('.projectInfo');
					}else{
						alert("查看项目信息失败!");
					}
			},
			error : function(){
				alert("查看项目信息异常!");
			}
		});
	});
	
	//修改项目信息
	$(".modifyProject").live("click",function() {
		var id = $(this).attr("name");
		$(".idHidden").val(id);
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/project/selectProjectById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".projectInfo").find("input").attr("disabled",false);
					$(".projectInfo").find("select").attr("disabled",false);
					$("#modifyProject").css("display","inline");
					$.initProv(".provinceItem",".cityItem","-省份-","-城市-");
					if(data=="null"){
						alert("查看的项目信息不存在!");
					}else if(data!="error"){
						$(".projectName").val(data.name);
						$(".department").val(data.department);
						$(".provinceItem").val(data.province);
						$.initCities($(".provinceItem"),$(".cityItem"));//先初始化城市，才可加载城市信息
						$(".cityItem").val(data.city);
						$(".addressItem").val(data.address);
						$(".area").val(data.area);
						showLayer('.projectInfo');
					}else{
						alert("查看项目信息失败!");
					}
			},
			error : function(){
				alert("查看项目信息异常!");
			}
		});
	});
	//修改项目
	modifyProject();
	//删除项目信息
	$(".deleteProject").live("click",function(){
		if(confirm("您确定要删除该项目,删除后将无法恢复?")){
			var id = $(this).attr("name");
			var pageNum = $(".pageIndexHidden").val();
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/project/deleteProjectById.json",
				dataType : "json",
				contentType : "application/json",
				data : id+",1",
				success : function(data){
						if(data=="ok"){
							pageselectCallbackForProject(pageNum,null);
						}else{
							if(confirm("该项目下关联着主机和节点等信息,点击确认,将会删除该项目下所有关联信息,删除后将无法恢复?")){
								var loadI = layer.load("正在删除项目及其关联的信息,由于数据量较大,该操作可能要几秒钟或者几分钟,请耐心等待...");
								$.ajax({
									type : "post",
									url : "http://localhost:8080/TIIS/project/deleteProjectById.json",
									dataType : "json",
									contentType : "application/json",
									data : id+",2",
									success : function(data){
											layer.close(loadI);
											if(data=="ok"){
												pageselectCallbackForProject(pageNum,null);
											}else{
												alert("删除该项目失败!");
											}
									},
									error : function(){
										alert("删除该项目异常!");
									}
								});
							}
						}
				},
				error : function(){
					alert("删除该项目异常!");
				}
			});
		}
	});
	
}

/**
 * @Title: 				modifyProject 
 * @author 				杨贵松
 * @date 				2014年4月8日 下午9:44:46
 * @Description: 		修改项目信息
 * void 				返回
 */
function modifyProject(){
	$("#modifyProject").click(function(){
		var id = $(".idHidden").val();
		var pName = $(".projectName").val();
		var pDepartment = $(".department").val();
		var pProvince = $(".provinceItem").val();
		var pCity = $(".cityItem").val();
		var pAddress = $(".addressItem").val();
		var pArea = $(".area").val();
		
		var pageNum = $(".pageIndexHidden").val();
		
		//组装project对象
		var project = "{id:"+id+",name:"+pName+",department:"+pDepartment+",province:"+pProvince+",city:"+pCity+",address:"+pAddress+",area:"+pArea+"}";
		if(pName=="" || pDepartment=="" || pAddress =="" || pProvince==-1 || pCity==-1 || pArea==""){
			alert("项目信息不能有空项!");
		}else if(!(/^(([0-9]+\.[0-9]{0,2})|([0-9]*[1-9][0-9]*))$/.test(pArea))){
			alert("项目面积只能是整数或者最多保留两位的小数，如：1234或者1234.00");
		}else if(confirm("确定修改该项目信息?")){
			//判断项目名称是否已存在
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/project/validProjectOtherName.json",
				dataType : "json",
				contentType : "application/json",
				data : id+","+pName,
				success : function(data){
						if(data=="noLogin"){
							window.location.href="http://localhost:8080/TIIS/indexs/redirect";
						}else if(data=="ok"){
							//修改项目信息
							$.ajax({
								type : "post",
								url : "http://localhost:8080/TIIS/project/modifyProject.json",
								dataType : "json",
								contentType : "application/json",
								data : project,
								success : function(data){
									if(data=="noLogin"){
										window.location.href="http://localhost:8080/TIIS/indexs/redirect";
									}else if(data=="ok"){
										layer.close(i);
										alert("修改项目信息成功!");
										pageselectCallbackForProject(pageNum,null);
									}else{
										alert("修改项目信息失败!");
									}
								},
								error : function(){
									alert("修改项目信息异常!");
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
 * @Title: 				hostInfoMng 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:29:59
 * @Description: 		主机信息查删改  
 * void 				返回
 */
function hostInfoMng(){
	//查看主机信息
	$(".selectHost").live("click",function() {
		var id = $(this).attr("name");
		//异步加载项目信息下拉列表
		initProjectToSelect(".projectOrder");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/controlHost/selectHostById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".hostInfo").find("input").attr("disabled",true);
					$(".hostInfo").find("select").attr("disabled",true);
					$("#modifyHost").css("display","none");
					if(data=="null"){
						alert("查看的主机信息不存在!");
					}else if(data!="error"){
						$(".projectOrder").val(data.projectid);
						$(".hostCode").val(data.code);
						showLayer('.hostInfo');
					}else{
						alert("查看主机信息失败!");
					}
			},
			error : function(){
				alert("查看主机信息异常!");
			}
		});
	});
	//修改主机信息
	$(".modifyHost").live("click",function() {
		var id = $(this).attr("name");
		$(".idHidden").val(id);
		//异步加载项目信息下拉列表
		initProjectToSelect(".projectOrder");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/controlHost/selectHostById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".hostInfo").find("input").attr("disabled",false);
					$(".hostInfo").find("select").attr("disabled",false);
					$("#modifyHost").css("display","inline");
					if(data=="null"){
						alert("查看的主机信息不存在!");
					}else if(data!="error"){
						$(".projectOrder").val(data.projectid);
						$(".hostCode").val(data.code);
						showLayer('.hostInfo');
					}else{
						alert("查看主机信息失败!");
					}
			},
			error : function(){
				alert("查看主机信息异常!");
			}
		});
	});
	//修改主机
	modifyHost();
	//删除主机信息
	$(".deleteHost").live("click",function(){
		if(confirm("您确定要删除该主机,删除后将无法恢复?")){
			var id = $(this).attr("name");
			var pageNum = $(".pageIndexHidden").val();
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/controlHost/deleteHostById.json",
				dataType : "json",
				contentType : "application/json",
				data : id+",1",
				success : function(data){
						if(data=="ok"){
							pageselectCallbackForHost(pageNum,null);
						}else{
							if(confirm("该主机下关联着主机和节点等信息,点击确认,将会删除该主机下所有关联信息,删除后将无法恢复?")){
								var loadI = layer.load("正在删除主机及其关联的信息,由于数据量较大,该操作可能要几秒钟或者几分钟,请耐心等待...");
								$.ajax({
									type : "post",
									url : "http://localhost:8080/TIIS/controlHost/deleteHostById.json",
									dataType : "json",
									contentType : "application/json",
									data : id+",2",
									success : function(data){
											layer.close(loadI);
											if(data=="ok"){
												pageselectCallbackForHost(pageNum,null);
											}else{
												alert("删除该主机失败!");
											}
									},
									error : function(){
										alert("删除该主机异常!");
									}
								});
							}
						}
				},
				error : function(){
					alert("删除该主机异常!");
				}
			});
		}
	});
	
}

/**
 * @Title: 				modifyHost 
 * @author 				杨贵松
 * @date 				2014年4月8日 下午11:47:31
 * @Description: 		修改主机信息
 * @param id 
 * void 				返回
 */
function modifyHost(){
	$("#modifyHost").click(function(){
		var hProjectId = $(".projectOrder").val();
		var hCode = $(".hostCode").val();
		var id = $(".idHidden").val();
		
		var pageNum = $(".pageIndexHidden").val();
		
		//组装host对象
		var host = "{id:"+id+",projectid:"+hProjectId+",code:"+hCode+"}";
		
		if(hProjectId=="0000" || hCode=="" ){
			alert("主机信息不能有空项!");
		}else if(!(/^\d{8}$/.test(hCode))){
			alert("主机编号为八位纯数字!");
		}else{
			//判断主机编号是否已存在
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/controlHost/validOtherHostCode.json",
				dataType : "json",
				contentType : "application/json",
				data : id+","+hCode,
				success : function(data){
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="ok"){
						//修改主机信息
						$.ajax({
							type : "post",
							url : "http://localhost:8080/TIIS/controlHost/modifyHost.json",
							dataType : "json",
							contentType : "application/json",
							data : host,
							success : function(data){
								if(data=="noLogin"){
									window.location.href="http://localhost:8080/TIIS/indexs/redirect";
								}else if(data=="ok"){
									layer.close(i);
									alert("修改控制器主机信息成功!");
									pageselectCallbackForHost(pageNum,null);
								}else{
									alert("修改控制器主机信息失败!");
								}
							},
							error : function(){
								alert("修改控制器主机信息异常!");
							}
						});
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
 * @Title: 				equipmentInfoMng 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:29:59
 * @Description: 		节点信息查删改  
 * void 				返回
 */
function equipmentInfoMng(){
	//查看节点信息
	$(".selectEquipment").live("click",function() {
		var id = $(this).attr("name");
		//异步加载项目信息下拉列表
		initProjectToSelect(".hostOrder");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/Equipment/selectEquipmentById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".equipmentInfo").find("input").attr("disabled",true);
					$(".equipmentInfo").find("select").attr("disabled",true);
					$("#modifyEquipment").css("display","none");
					if(data=="null"){
						alert("查看的节点信息不存在!");
					}else if(data!="error"){
						$(".hostOrder").val(data.project.id);
						$(".name").val(data.name);
						$(".code").val(data.code);
						$(".flowParameter").val(data.flowparameter);
						showLayer('.equipmentInfo');
					}else{
						alert("查看节点信息失败!");
					}
			},
			error : function(){
				alert("查看节点信息异常!");
			}
		});
	});
	//修改节点信息
	$(".modifyEquipment").live("click",function() {
		var id = $(this).attr("name");
		$(".idHidden").val(id);
		//异步加载项目信息下拉列表
		initProjectToSelect(".hostOrder");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/Equipment/selectEquipmentById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".equipmentInfo").find("input").attr("disabled",false);
					$(".equipmentInfo").find("select").attr("disabled",false);
					$("#modifyEquipment").css("display","inline");
					if(data=="null"){
						alert("查看的节点信息不存在!");
					}else if(data!="error"){
						$(".hostOrder").val(data.project.id);
						$(".name").val(data.name);
						$(".code").val(data.code);
						$(".flowParameter").val(data.flowparameter);
						showLayer('.equipmentInfo');
						$(".codeHidden").val(data.code);
					}else{
						alert("查看节点信息失败!");
					}
			},
			error : function(){
				alert("查看节点信息异常!");
			}
		});
	});
	//修改节点
	modifyEquipment();
	//删除节点信息
	$(".deleteEquipment").live("click",function(){
		if(confirm("您确定要删除该节点,删除后将无法恢复?")){
			var id = $(this).attr("name");
			var pageNum = $(".pageIndexHidden").val();
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/Equipment/deleteEquipmentById.json",
				dataType : "json",
				contentType : "application/json",
				data : id+",1",
				success : function(data){
						if(data=="ok"){
							pageselectCallbackForEquipment(pageNum,null);
						}else{
							if(confirm("该节点下关联着节点和节点等信息,点击确认,将会删除该节点下所有关联信息,删除后将无法恢复?")){
								var loadI = layer.load("正在删除节点及其关联的信息,由于数据量较大,该操作可能要几秒钟或者几分钟,请耐心等待...");
								$.ajax({
									type : "post",
									url : "http://localhost:8080/TIIS/Equipment/deleteEquipmentById.json",
									dataType : "json",
									contentType : "application/json",
									data : id+",2",
									success : function(data){
											layer.close(loadI);
											if(data=="ok"){
												pageselectCallbackForEquipment(pageNum,null);
											}else{
												alert("删除该节点失败!");
											}
									},
									error : function(){
										alert("删除该节点异常!");
									}
								});
							}
						}
				},
				error : function(){
					alert("删除该节点异常!");
				}
			});
		}
	});
	
}

/**
 * @Title: 				modifyEquipment 
 * @author 				杨贵松
 * @date 				2014年4月9日 上午9:26:45
 * @Description: 		修改节点信息
 * @param id 
 * void 				返回
 */
function modifyEquipment(){
	$("#modifyEquipment").live("click",function(){
		var eProjectId = $(".hostOrder").val();
		var eName = $(".name").val();
		var eCode = $(".code").val();
		var eFlowParameter = $(".flowParameter").val();
		var id =$(".idHidden").val();
		var oldCode =$(".codeHidden").val();
		
		//组装host对象
		var equipment = "{id:"+id+",project:{id:"+eProjectId+"},name:"+eName+",code:"+eCode+",flowparameter:"+eFlowParameter+"}";
		
		if(eProjectId=="0000" || eName == "" || eCode=="" || eFlowParameter == ""){
			alert("节点信息不能有空项!");
		}else if(!(/^\d{4}$/.test(eCode))){
			alert("节点地址为四位纯数字!");
		}else if(!(/^[0-9]*[1-9][0-9]*$/.test(eFlowParameter)) || eFlowParameter < 200 || eFlowParameter > 1000){
			alert("流量参数为[200,1000]区间的整数!");
		}else{
			//判断节点编号是否已存在
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/Equipment/validOtherEquipmentNameAndCode.json",
				dataType : "json",
				contentType : "application/json",
				data : id+","+eName+","+eCode ,
				success : function(data){
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="ok"){
						if(oldCode==eCode){
							if(confirm("确定要修改节点信息?")){
								modifyDataBaseEquipment(equipment);
							}
						}else{
							modifyNetWorkEquipment(oldCode,equipment);
						}
					}else{
						alert("节点名称或编号已存在!");
					}
				},
				error : function(){
					alert("验证节点编号异常!");
				}
			});
		}
	});
}

/**
 * @Title: 				modifyNetWorkEquipment 
 * @author 				杨贵松
 * @date 				2014年4月9日 上午11:28:56
 * @Description: 		修改现场节点设备地址
 * void 				返回
 */
function modifyNetWorkEquipment(oldCode,equipment){
	if(confirm("节点地址改变，确定要修改现场节点设备信息?")){
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/NetWork/modifyEquipment.json",
			dataType : "json",
			contentType : "application/json",
			data : equipment.project.id+","+oldCode+","+equipment.code,
			success : function(data){
				if(data=="noLogin"){
					window.location.href="http://localhost:8080/TIIS/indexs/redirect";
				}else if(data=="ok"){
					modifyDataBaseEquipment(equipment);
				}else{
					alert("修改现场节点信息失败,请重试!");
				}
			},
			error : function(){
				alert("修改现场节点信息异常,请稍后重试!");
			}
		});
	}
}

/**
 * @Title: 				modifyDataBaseEquipment 
 * @author 				杨贵松
 * @date 				2014年4月9日 上午11:27:54
 * @Description: 		修改数据库节点信息
 * void 				返回
 */
function modifyDataBaseEquipment(equipment){
	var pageNum = $(".pageIndexHidden").val();
	//修改节点信息
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/Equipment/modifyEquipment.json",
		dataType : "json",
		contentType : "application/json",
		data : equipment,
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else if(data=="ok"){
				layer.close(i);
				alert("修改节点信息成功!");
				pageselectCallbackForEquipment(pageNum,null);
			}else{
				alert("修改节点信息失败!");
			}
		},
		error : function(){
			alert("修改节点信息异常!");
		}
	});
}
/**
 * @Title: 				messageInfoMng 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:29:59
 * @Description: 		报警信息查删改  
 * void 				返回
 */
function messageInfoMng(){
	$(".selectSystemLog").live("click",function(){
		var id = $(this).attr("name");
		var status = $(this).parent().parent().find("td").eq(4).html();
		//修改节点信息
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/message/selectMessageById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
				if(data=="noLogin"){
					window.location.href="http://localhost:8080/TIIS/indexs/redirect";
				}else if(data=="error"){
					alert("查看报警信息失败!");
				}else{
					var logType = "";
					if(data.logtype=="0"){
						logType = "采集异常";
					}else if(data.logtype=="1"){
						logType = "湿度报警";
					}
					
					$(".msgType").val(logType);
					$(".msgTexts").html(data.logcontext);
					$(".msgStatus").val("已读");
					$(".msgTime").val(timeStamp2String(data.logtime));
					layer.close(i);
					showLayer('.msgInfo');
					if(status=="未读"){
						markSystemLog(id);
					}
				}
			},
			error : function(){
				alert("查看报警信息异常!");
			}
		});
	});
	
	$(".markSystemLog").live("click",function(){
		var id = $(this).attr("name");
		var pageNum = $(".pageIndexHidden").val();
		var status = $(this).parent().parent().find("td").eq(4).html();
		if(status=="已读"){
			alert("不用标记!");
		}else{
			//标记报警信息
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/message/markSystemLog.json",
				dataType : "json",
				contentType : "application/json",
				data : id,
				success : function(data){
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="ok"){
						alert("标记成功!");
						pageselectCallbackForMessage(pageNum,null);
					}else{
						alert("标记报警信息失败!");
					}
				},
				error : function(){
					alert("标记报警信息异常!");
				}
			});
		}
	});
	
}

/**
 * @Title: 				markSystemLog 
 * @author 				杨贵松
 * @date 				2014年7月24日 下午12:35:27
 * @Description: 		查看消息后标记为已读
 * @param id 
 * void 				返回
 */
function markSystemLog(id){
	var pageNum = $(".pageIndexHidden").val();
	$.ajax({
		type : "post",
		url : "http://localhost:8080/TIIS/message/markSystemLog.json",
		dataType : "json",
		contentType : "application/json",
		data : id,
		success : function(data){
			if(data=="noLogin"){
				window.location.href="http://localhost:8080/TIIS/indexs/redirect";
			}else{
				pageselectCallbackForMessage(pageNum,null);
			}
		},
		error : function(){
		}
	});
}

/**
 * @Title: 				userInfoMng 
 * @author 				杨贵松
 * @date 				2014年4月6日 下午4:29:59
 * @Description: 		用户信息查删改  
 * void 				返回
 */
function userInfoMng(){
	validUserName();
	validEmail();
	inputFocus();
	//修改用户个人信息
	$("#modifyUser").live("click",function(){
		var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		
		var id = $(".userIdHidden").val();
		var loginName = $(".loginName").val();
		var userName = $(".userName").val();
		var email = $(".email").val();
		var phone = $(".phone").val();
		var address = $(".address").val();
		var website = $(".website").val();
		var userStr = "{id:"+id+",loginname:"+loginName+",username:'"+userName+"',email:'"+email+"',phone:'"+phone+"',address:'"+address+"',website:'"+website+"'}";
		
		if(userName == ""){
			$(".userSpan").html("提示：用户名不能为空!");
		}else if(email == ""){
			$(".userSpan").html("提示：邮箱不能为空!");
		}else if(!emailReg.test(email)){
			$(".userSpan").html("提示：请填写有效的邮箱!");
		}else if(confirm("确定要修改该用户信息?")){
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/user/modifyUserInfo.json",
				dataType : "json",
				contentType : "application/json",
				data : userStr,
				success : function(data){
					if(data=="noLogin"){
						window.location.href="http://localhost:8080/TIIS/indexs/redirect";
					}else if(data=="ok"){
						alert("修改个人用户信息成功!");
					}else{
						alert("修改个人用户信息失败!");
					}
				},
				error : function(){
					alert("修改个人用户信息异常!");
				}
			});
		}
	});
	selectUser();
	modifyUser();
	deleteUser();
}

/**
 * @Title: 				selectUser 
 * @author 				杨贵松
 * @date 				2014年6月25日 下午1:18:14
 * @Description: 		管理员查询用户信息  
 * void 				返回
 */
function selectUser(){
	$(".selectUser").live("click",function(){
		var id = $(this).attr("name");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/user/selectUserInfoById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
					$(".listUserInfo").find("input").attr("disabled",true);
					$("#selectUser").css("display","none");
					if(data=="null"){
						alert("查看的用户信息不存在!");
					}else if(data=="error"){
						alert("查看用户信息失败!");
					}else{
						$(".lUserId").val(data.id);
						$(".lLoginName").val(data.loginname);
						$(".lUserName").val(data.username);
						$(".lPhone").val(data.phone);
						$(".lMail").val(data.email);
						$(".lAddress").val(data.address);
						$(".lWebSet").val(data.website);
						$(".lCreateTime").val(timeStamp2String(data.createtime));
						$(".lModifyTime").val(timeStamp2String(data.createtime));
						showLayerForUser('.listUserInfo');
					}
			},
			error : function(){
				alert("查看用户信息异常!");
			}
		});
	});
}

/**
 * @Title: 				modifyUser 
 * @author 				杨贵松
 * @date 				2014年6月25日 下午1:18:14
 * @Description: 		管理员修改用户信息  
 * void 				返回
 */
function modifyUser(){
	$("#selectUser").live("click",function(){
		modifyUserUtil();
	});
	$(".modifyUser").live("click",function(){
		var id = $(this).attr("name");
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/user/selectUserInfoById.json",
			dataType : "json",
			contentType : "application/json",
			data : id,
			success : function(data){
				$(".listUserInfo").find("input").attr("disabled",false);
				$("#selectUser").css("display","inline");
				if(data=="null"){
					alert("查看的用户信息不存在!");
				}else if(data=="error"){
					alert("查看用户信息失败!");
				}else{
					$(".lUserId").val(data.id);
					$(".lLoginName").val(data.loginname);
					$(".lUserName").val(data.username);
					$(".lPhone").val(data.phone);
					$(".lMail").val(data.email);
					$(".lAddress").val(data.address);
					$(".lWebSet").val(data.website);
					$(".lCreateTime").val(timeStamp2String(data.createtime));
					$(".lModifyTime").val(timeStamp2String(data.createtime));
					showLayerForUser('.listUserInfo');
				}
			},
			error : function(){
				alert("查看用户信息异常!");
			}
		});
	});
}


/**
 * @Title: 				modifyUserUtil 
 * @author 				杨贵松
 * @date 				2014年6月26日 下午3:47:47
 * @Description: 		管理员修改单个用户信息公用方法
 * void 				返回
 */
function modifyUserUtil(){
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var pageNum = $(".pageIndexHidden").val();
	num = 15 * pageNum;
	
	var id = $(".lUserId").val();
	var loginName = $(".lLoginName").val();
	var userName = $(".lUserName").val();
	var email = $(".lMail").val();
	var phone = $(".lPhone").val();
	var address = $(".lAddress").val();
	var website = $(".lWebSet").val();
	var userStr = "{id:"+id+",loginname:"+loginName+",username:'"+userName+"',email:'"+email+"',phone:'"+phone+"',address:'"+address+"',website:'"+website+"'}";
	
	if(userName == ""){
		alert("提示：用户名不能为空!");
	}else if(email == ""){
		alert("提示：邮箱不能为空!");
	}else if(!emailReg.test(email)){
		alert("提示：请填写有效的邮箱!");
	}else if(confirm("确定要修改该用户信息?")){
		$.ajax({
			type : "post",
			url : "http://localhost:8080/TIIS/user/modifyUserInfo.json",
			dataType : "json",
			contentType : "application/json",
			data : userStr,
			success : function(data){
				if(data=="noLogin"){
					window.location.href="http://localhost:8080/TIIS/indexs/redirect";
				}else if(data=="ok"){
					layer.close(i);
					alert("修改个人用户信息成功!");
					pageselectCallbackForUser(pageNum,null);
				}else{
					alert("修改个人用户信息失败!");
				}
			},
			error : function(){
				alert("修改个人用户信息异常!");
			}
		});
	}
}

/**
 * @Title: 				deleteUser 
 * @author 				杨贵松
 * @date 				2014年6月26日 下午3:59:15
 * @Description: 		删除用户信息  
 * void 				返回
 */
function deleteUser(){
	$(".deleteUser").live("click",function(){
		if(confirm("您确定要删除该用户,删除后将无法恢复?")){
			var id = $(this).attr("name");
			var pageNum = $(".pageIndexHidden").val();
			num = 15 * pageNum;
			$.ajax({
				type : "post",
				url : "http://localhost:8080/TIIS/user/deleteUserInfoById.json",
				dataType : "json",
				contentType : "application/json",
				data : id+",1",
				success : function(data){
						if(data=="ok"){
							pageselectCallbackForUser(pageNum,null);
						}else{
							if(confirm("该用户关联有项目等信息,点击确认,将会删除该用户下所有关联信息,删除后将无法恢复?")){
								var loadI = layer.load("正在删除用户及其关联的信息,由于数据量较大,该操作可能要几秒钟或者几分钟,请耐心等待...");
								$.ajax({
									type : "post",
									url : "http://localhost:8080/TIIS/user/deleteUserInfoById.json",
									dataType : "json",
									contentType : "application/json",
									data : id+",2",
									success : function(data){
											layer.close(loadI);
											if(data=="ok"){
												pageselectCallbackForUser(pageNum,null);
											}else{
												alert("删除该用户失败!");
											}
									},
									error : function(){
										alert("删除该用户异常!");
									}
								});
							}
						}
				},
				error : function(){
					alert("删除该用户异常!");
				}
			});
		}
	});
}
/**
 * @Title: 				validUserName 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:17
 * @Description: 		验证登录名 
 * void 				返回
 */
function validUserName(){
	$(".userName").focus(function() {
		$(".userSpan").html("");
		$(this).css("border", "#00B2EE solid 1px");
	});
	$(".userName").blur(function() {
		$(".userSpan").html("");
		$(".userSpan").css("color", "red");
		var nameObj = $(this);
		name = nameObj.val();
		if (name == "") {
			$(".userSpan").html("提示：用户名不能为空!");
			nameObj.css("border", "#B35D5D solid 1px");
		} else {
			nameObj.css("border", "#B0B0B0 solid 1px");
		}
	});
}

/**
 * @Title: 				validEmail 
 * @author 				杨贵松
 * @date 				2014年4月23日 下午4:22:07
 * @Description: 		验证邮箱
 * void 				返回
 */
function validEmail(){
	$(".email").focus(function() {
		$(".userSpan").html("");
		if ($(this).val() == "") {
			$(".userSpan").html("提示：邮箱用来找回密码，请填写有效的邮箱!");
			$(".userSpan").css("color", "#00B2EE");
		}
		$(this).css("border", "#00B2EE solid 1px");
	});
	$(".email").blur(function() {
		$(".userSpan").html("");
		$(".userSpan").css("color", "red");
		var mailObj = $(this);
		email = mailObj.val();
		var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (email == "") {
			$(".userSpan").html("提示：邮箱不能为空!");
			mailObj.css("border", "#B35D5D solid 1px");
		}else if(!emailReg.test(email)) {
			$(".userSpan").html("提示：请输入有效的邮箱!");
			mailObj.css("border", "#B35D5D solid 1px");
		} else {
			mailObj.css("border", "#B0B0B0 solid 1px");
		}
	});
}

/**
 * @Title: 				inputFocus 
 * @author 				杨贵松
 * @date 				2014年3月30日 下午11:14:06
 * @Description: 		设置input触发效果  
 * void 				返回
 */
function inputFocus(){
	$("input").focus(function(){
		$(this).css("border","#00B2EE solid 1px");
	});
	$("input").blur(function(){
		$(this).css("border","#B0B0B0 solid 1px");
	});
}

