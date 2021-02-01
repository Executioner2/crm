<%@ page import="java.util.List" %>
<%@ page import="com.YoRHa.crm.settings.domain.DicValue" %>
<%@ page import="com.YoRHa.crm.workbench.domain.Tran" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath() + "/";

	List<DicValue> stageList = (List<DicValue>) application.getAttribute("stageList");
	Map<String, String> map = (Map<String, String>) application.getAttribute("stage2Possibility");

	Integer point = 0;  // 正常阶段与丢失阶段的分界点
	for (int i = 0; i < stageList.size(); i++) {
		String stage = stageList.get(i).getValue();
		if ("0".equals(map.get(stage))){
			point = i;
			break;

		}

	}

%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<style type="text/css">
.mystage{
	font-size: 20px;
	vertical-align: middle;
	cursor: pointer;
}
.closingDate{
	font-size : 15px;
	cursor: pointer;
	vertical-align: middle;
}
</style>
	
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});

		listTranHistory();

		//阶段提示框
		$(".mystage").popover({
            trigger:'manual',
            placement : 'bottom',
            html: 'true',
            animation: false
        }).on("mouseenter", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide")
                        }
                    }, 100);
                });
	});


	// 展示交易历史
	function listTranHistory() {
		$.ajax({
			url:"tran/listTranHistory.do",
			data:{"tid":"${tran.id}"},
			type:"get",
			success:function (resp) {
				html = "";
				$.each(resp, function (index, item) {
					html += '<tr>';
					html += '<td>'+item.stage+'</td>';
					html += '<td>'+item.money+'</td>';
					html += '<td>'+item.possibility+'</td>';
					html += '<td>'+item.expectedDate+'</td>';
					html += '<td>'+item.createTime+'</td>';
					html += '<td>'+item.createBy+'</td>';
					html += '</tr>';
				});

				$("#activityTable tbody").html(html);
			}
		});
	}

	/**
	 * 阶段图标被点击时触发，改变当前阶段
	 * @param index 下标
	 * @param stage	阶段名
	 */
	function changeStage(index, stage){
		$.ajax({
			url:"tran/changeStage.do",
			data:{
				"id":"${tran.id}",
				"stage":stage,
				"expectedDate":"${tran.expectedDate}",
				"money":"${tran.money}",
				"formerStage":$.trim($("#show-stage").html())
			},
			type:"post",
			success:function (resp) {
				if(resp.result){
					$("#show-stage").html(resp.tran.stage);
					$("#show-editBy").html(resp.tran.editBy+"&nbsp;&nbsp;");
					$("#show-editTime").html(resp.tran.editTime);
					$("#show-possibility").html(resp.tran.possibility);
					listTranHistory();
					changeIcon(index, resp.tran.possibility);
				}else{
					alert("改变阶段失败！");
				}

			}
		});

	}

	// 改变阶段图标
	function changeIcon(index, possibility){
		var point = <%=point%>;

		// 如果可能性为0线索丢失
		if (0 == possibility){
			for (var i = 0; i < <%=stageList.size()%>; i++) {
				// 小于分界点全是黑圈
				if (i < point){
					// 黑圈----------------
					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-record mystage");
					$("#"+i).css("color", "#000000");
				// 是叉
				}else{
					// 如果i等于当前下标则为红叉
					if (i == index){
						// 红叉---------------
						$("#"+i).removeClass();
						$("#"+i).addClass("glyphicon glyphicon-remove mystage");
						$("#"+i).css("color", "#FF0000");
					// 否则是黑叉
					}else{
						// 黑叉---------------
						$("#"+i).removeClass();
						$("#"+i).addClass("glyphicon glyphicon-remove mystage");
						$("#"+i).css("color", "#000000");
					}

				}

			}

		// 否则正常流程
		}else{
			for (var i = 0; i < <%=stageList.size()%>; i++) {
				// 如果i小于分界点，则是圈
				if (i < point){
					// 如果i小于index是已完成阶段
					if (i < index){
						// 已完成圈------------
						$("#"+i).removeClass();
						$("#"+i).addClass("glyphicon glyphicon-ok-circle mystage");
						$("#"+i).css("color", "#90F790");
					// 如果i等于index则是正在进行圈
					}else if (i == index){
						// 进行中圈---------------
						$("#"+i).removeClass();
						$("#"+i).addClass("glyphicon glyphicon-map-marker mystage");
						$("#"+i).css("color", "#90F790");
					// 否则是未完成圈
					}else{
						// 未完成圈---------------
						$("#"+i).removeClass();
						$("#"+i).addClass("glyphicon glyphicon-record mystage");
						$("#"+i).css("color", "#000000");
					}

				// 否则是黑叉
				}else{
					//黑叉--------------
					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-remove mystage");
					$("#"+i).css("color", "#000000");
				}

			}

		}

	}
	
	
</script>

</head>
<body>
	
	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${tran.customerId}-${tran.name} <small>￥${tran.money}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" onclick="window.location.href='edit.html';"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>

	<!-- 阶段状态 -->
	<div style="position: relative; left: 40px; top: -50px;">
		阶段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		<%

			String currentStage = ((Tran)request.getAttribute("tran")).getStage();

			// 如果当前阶段可能性为0 则丢失阶段
			if ("0".equals(map.get(currentStage))){
				for (int i = 0; i < stageList.size(); i++) {
					// 如果在分界点前都是黑色圈
					if (i < point){
						// 黑色圈---------------------
		%>
						<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
							  class="glyphicon glyphicon-record mystage"
							  data-toggle="popover" data-placement="bottom"
							  data-content="<%=stageList.get(i).getText()%>" style="color: #000000;">
						</span>
						-----------
		<%
					// 否则是叉
					}else{
						// 是当前阶段就是红叉
						if (currentStage.equals(stageList.get(i).getValue())){
							// 红叉-------------------
		%>
							<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
								  class="glyphicon glyphicon-remove mystage"
								  data-toggle="popover" data-placement="bottom"
								  data-content="<%=stageList.get(i).getText()%>" style="color: #FF0000;">
							</span>
							-----------
		<%
						// 否则是黑叉
						}else{
							// 黑叉-------------------
		%>
							<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
								  class="glyphicon glyphicon-remove mystage"
								  data-toggle="popover" data-placement="bottom"
								  data-content="<%=stageList.get(i).getText()%>" style="color: #000000;">
							</span>
							-----------
		<%
						}

					}

				}

			// 否则处于正常交易
			}else{
				// 取得当前下标
				Integer currentIndex = 0;
				for (int i = 0; i < stageList.size(); i++) {
					if (currentStage.equals(stageList.get(i).getValue())){
						currentIndex = i;
						break;
					}
				}

				for (int i = 0; i < stageList.size(); i++) {
					// 如果当前下标小于分界点是圈
					if (i < point){
						// 如果i小于当前下标则表示已完成
						if (i < currentIndex){
							// 绿色已完成阶段圈-------------------
		%>
							<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
								  class="glyphicon glyphicon-ok-circle mystage"
								  data-toggle="popover" data-placement="bottom"
								  data-content="<%=stageList.get(i).getText()%>" style="color: #90F790;">
							</span>
							-----------
		<%
						// 如果i等于当前下标则表示进行中的阶段
						}else if (currentIndex == i){
							// 进行中的图标----------------------
		%>
							<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
								  class="glyphicon glyphicon-map-marker mystage"
								  data-toggle="popover" data-placement="bottom"
								  data-content="<%=stageList.get(i).getText()%>" style="color: #90F790;">
							</span>
							-----------
		<%
						// 否则表示未完成的阶段
						}else{
							// 未完成的阶段----------------------
		%>
							<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
								  class="glyphicon glyphicon-record  mystage"
								  data-toggle="popover" data-placement="bottom"
								  data-content="<%=stageList.get(i).getText()%>" style="color: #000000;">
							</span>
							-----------
		<%
						}

					// 否则全是黑叉
					}else{
						// 黑叉------------------
		%>
						<span id="<%=i%>" onclick="changeStage('<%=i%>', '<%=stageList.get(i).getValue()%>')"
							  class="glyphicon glyphicon-remove  mystage"
							  data-toggle="popover" data-placement="bottom"
							  data-content="<%=stageList.get(i).getText()%>" style="color: #000000;">
						</span>
						-----------
		<%
					}

				}

			}

		%>
		<span class="closingDate">${tran.expectedDate}</span>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: 0px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">金额</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${tran.money}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.customerId}-${tran.name}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">预计成交日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${tran.expectedDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">客户名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.customerId}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">阶段</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="show-stage">${tran.stage}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">类型</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.type}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">可能性</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="show-possibility">${tran.possibility}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">来源</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.source}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">市场活动源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${tran.activityId}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">联系人名称</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${tran.contactsId}</b></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${tran.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${tran.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="show-editBy">${tran.editBy}&nbsp;&nbsp;</b><small id="show-editTime" style="font-size: 10px; color: gray;">${tran.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${tran.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					&nbsp;${tran.contactSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 100px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>&nbsp;${tran.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 100px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>动力节点-交易01</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		<!-- 备注2 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>动力节点-交易01</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 阶段历史 -->
	<div>
		<div style="position: relative; top: 100px; left: 40px;">
			<div class="page-header">
				<h4>阶段历史</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>阶段</td>
							<td>金额</td>
							<td>可能性</td>
							<td>预计成交日期</td>
							<td>创建时间</td>
							<td>创建人</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			
		</div>
	</div>
	
	<div style="height: 200px;"></div>
	
</body>
</html>