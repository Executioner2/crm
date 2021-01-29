<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
	+ request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">



	<script type="text/javascript">
		$(function(){

			activityList(1, 2);

			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});

			//打开模态框
			$("#activityAddBtn").click(function () {
				//查询用户
				$.ajax({
					url:"activity/listUser.do",
					type:"get",
					success:function (resp) {
						var html = "";
						$.each(resp, function (index, item) {
							html += "<option value='"+item.id+"'>"+item.name+"</option>";
						})
						$("#create-marketActivityOwner").html(html);
						$("#create-marketActivityOwner option[value=${user.id}]").prop("selected",true);

						$("#createActivityModal").modal("show");
					}
				});
			});


			//保存活动
			$("#createBtn").click(function () {
				$.ajax({
					url:"activity/add.do",
					data:{
						owner:$("#create-marketActivityOwner").val(),
						name:$.trim($("#create-marketActivityName").val()),
						startDate:$("#create-startTime").val(),
						endDate:$("#create-endTime").val(),
						cost:$.trim($("#create-cost").val()),
						description:$.trim($("#create-describe").val())
					},
					type:"post",
					success:function (resp) {
						//刷新当前显示记录
						if(resp){
							$("#create-activityForm")[0].reset();
							activityList(1 ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						}else{
							alert("市场活动添加失败！");
						}
					}
				});
			});

			//查询
			$("#searchBtn").click(function () {
				$("#hidden-name").val($("#search-name").val());
				$("#hidden-owner").val($("#search-owner").val());
				$("#hidden-startTime").val($("#search-startTime").val());
				$("#hidden-endTime").val($("#search-endTime").val());

				activityList(1 ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
			});

			//全选
			$("#qx").click(function () {
				$("input[name=xz]").prop("checked", this.checked);
			});

			//选择
			$("#activityListTable").on("click", $("input[name=xz]"), function () {
				$("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
			});

			//删除
			$("#activityDeleteBtn").click(function () {
				var $xz = $("input[name=xz]:checked");
				var data = "";

				if($xz.length == 0){
					alert("请至少选择一项活动！");
					return false;
				}

				if(confirm("确认要删除选择项？")){
					for (var i = 0; i < $xz.length; i++) {
						data += "id=" + $($xz[i]).val() + "&";
					}
					data = data.substring(0, data.length - 1);

					$.ajax({
						url:"activity/delete.do?"+data,
						type:"post",
						success:function (resp) {
							if(resp.result){
								activityList(1 ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else{
								alert(resp.msg);
							}
						}
					});
				}

			});


			//打开修改市场活动的模态框
			$("#activityEditBtn").click(function () {

				//判断选择项
				var $xz = $("input[name=xz]:checked");
				if($xz.length == 0){
					alert("请至少选择一项活动");
					return false;
				}

				if($xz.length > 1){
					alert("一次只能修改一项活动");
					return false;
				}

				//获取到要修改的数据
				$.ajax({
					url:"activity/query.do",
					data:{id:$($xz).val()},
					type:"get",
					success:function (resp) {
						var html = "";
						$.each(resp.users, function (index, item) {
							html += "<option value='"+item.id+"'>"+item.name+"</option>";
						})
						$("#edit-marketActivityOwner").html(html);
						$("#edit-marketActivityOwner option[value="+resp.activity.owner+"]").prop("selected", true);
						$("#edit-id").val(resp.activity.id);
						$("#edit-marketActivityName").val(resp.activity.name);
						$("#edit-startTime").val(resp.activity.startDate);
						$("#edit-endTime").val(resp.activity.endDate);
						$("#edit-cost").val(resp.activity.cost);
						$("#edit-describe").val(resp.activity.description);

						$("#editActivityModal").modal("show");
					}
				});
			});


			//修改市场活动
			$("#updateBtn").click(function () {

				$.ajax({
					url:"activity/update.do",
					data:{
						id:$("#edit-id").val(),
						owner:$("#edit-marketActivityOwner").val(),
						name:$.trim($("#edit-marketActivityName").val()),
						startDate:$("#edit-startTime").val(),
						endDate:$("#edit-endTime").val(),
						cost:$.trim($("#edit-cost").val()),
						description:$.trim($("#edit-describe").val())
					},
					type:"post",
					success:function (resp) {
						//刷新当前显示记录
						if(resp){
							$("#edit-activityForm")[0].reset();
							activityList($("#activityPage").bs_pagination('getOption', 'currentPage')
									,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						}else{
							alert("市场活动添加失败！");
						}
					}
				});
			});

		});

		//活动list
		function activityList(pageNo, pageSize) {

			$("#qx").prop("checked", false);

			$("#search-name").val($("#hidden-name").val());
			$("#search-owner").val($("#hidden-owner").val());
			$("#search-startTime").val($("#hidden-startTime").val());
			$("#search-endTime").val($("#hidden-endTime").val());


			$.ajax({
				url:"activity/list.do",
				data:{
					name:$.trim($("#search-name").val()),
					owner:$.trim($("#search-owner").val()),
					startDate:$("#search-startTime").val(),
					endDate:$("#search-endTime").val(),
					pageNo:pageNo,
					pageSize:pageSize
				},
				type:"get",
				success:function (resp) {
					var html = "";
					$.each(resp.activities, function (index, item) {
						html += '<tr class="active">';
						html += '<td><input type="checkbox" name="xz" value='+item.id+'></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'activityRemark/show.do?id='+item.id+'\';">'+item.name+'</a></td>';
						html += '<td>'+item.owner+'</td>';
						html += '<td>'+item.startDate+'</td>';
						html += '<td>'+item.endDate+'</td></tr>';
					});
					$("#activityListTable tbody").html(html);

					$("#activityPage").bs_pagination({
						currentPage: pageNo, // 页码
						rowsPerPage: pageSize, // 每页显示的记录条数
						maxRowsPerPage: 20, // 每页最多显示的记录条数
						totalPages: resp.pages, // 总页数
						totalRows: resp.total, // 总记录条数

						visiblePageLinks: 3, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage : function(event, data){
							activityList(data.currentPage , data.rowsPerPage);
						}
					});
				}
			});
		}

	</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="create-activityForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="createBtn" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="edit-activityForm">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="updateBtn" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>


	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startTime">
	<input type="hidden" id="hidden-endTime">

	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startTime" readonly/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endTime" readonly/>
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="activityAddBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="activityEditBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="activityDeleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover" id="activityListTable">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>

			<%--分页框--%>
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage"></div>

			</div>
			
		</div>
		
	</div>
</body>
</html>