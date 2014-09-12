<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common/common.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<title>后台管理系统</title>
		<link rel="stylesheet" href="${staticURL}/css/style.css" type="text/css"/>
	</head>
	<body>
	<!-- header start -->
		<%@include file="common/header.jsp"%>
	<!-- header end -->
		<div id="wrapper">
			<div id="minwidth">
				<div id="holder">
					<%@include file="common/menu.jsp"%>
					<div id="submenu">
						<div class="modules_left">
							<div class="module buttons">
								<a href="javascript:void(0)" class="dropdown_button"><span>更新管理</span></a>
								<div class="dropdown" style="display: none;">
									<div class="arrow"></div>
									<div class="content">
										<form action="${contextPaht}/gm/sys/addVersionInfo.do" id="addVersion" method="post">
											<p>
												<label for="name">程序类型:</label>
												<input type="radio" name="channel" id="channel" value="100" checked="checked">安卓
												<input type="radio" name="channel" id="channel" value="200">IOS
											</p>
											<p>
												<label for="name">版本代码:</label>
												<input type="text" class="text w_22" name="newver" id="newver" maxlength="50">
											</p>
											<p>
												<label for="name">版本号:</label>
												<input type="text" class="text w_22" name="version_name" id="version_name" maxlength="10">
											</p>
											<p>
												<label for="name">新包地址:<br/></label>
												<input type="text" class="text w_22" name="address" id="address">
											</p>
											<p>
												<label for="name">更新信息:</label>
												<textarea rows="5" cols="35" name="msg" id="msg"></textarea>
											</p>
										</form>
										<a href="javascript:addVersion()" class="button green right"><small class="icon check"></small><span>保存</span></a>
										<a class="button red mr right close"><small class="icon cross"></small><span>取消</span></a>
										<div class="clear"></div>
									</div>
								</div>
								
								<!-- 修改管理密码 -->
								<a href="javascript:void(0)" class="dropdown_button"><span>密码修改</span></a>
								<div class="dropdown modify_dropdown" style="left: 70px;display: none;">
									<div class="arrow"></div>
									<div class="content">
										<form action="#" id="modifyPwd" method="post">
											<p>
												<label for="name">原密码:</label>
												<input type="password" class="text w_22" name="oldPassword" id="oldPassword" maxlength="16">
											</p>
											<p>
												<label for="name">新密码:</label>
												<input type="password" class="text w_22" name="newPassword" id="newPassword" maxlength="16">
											</p>
											<p>
												<label for="name">确认密码:</label>
												<input type="password" class="text w_22" name="confirmPassword" id="confirmPassword" maxlength="16">
											</p>
											<input type="hidden" name="id" id="modify_id">
										</form>
										<a href="javascript:modifyPwd()" class="button green right"><small class="icon check"></small><span>修改</span></a>
										<a class="button red mr right close"><small class="icon cross"></small><span>取消</span></a>
										<div class="clear"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="modules_right"></div>
					</div>
					<div id="desc">
						<div class="body">
							<div id="table" class="help">
							<h1></h1>
							<div class="col w10 last">
								<div class="content">
									<table>
										<tr>
											<th>程序类型</th>
											<th>版本代码</th>
											<th>版本号</th>
											<th>包地址</th>
											<th>更新信息</th>
										</tr>
										<c:forEach items="${versionList}" var="versionInfo">
											<tr>
												<c:choose>
													<c:when test="${versionInfo.channel == '100'}">
														<td id="stock">安卓版</td>
													</c:when>
													<c:otherwise>
														<td id="stock">IOS版</td>
													</c:otherwise>
												</c:choose>
												<td id="stock">${versionInfo.newver}</td>
												<td id="name">${versionInfo.version_name}</td>
												<td id="name">${versionInfo.address}</td>
												<td id="probability">${versionInfo.msg}</td>
											</tr>
										</c:forEach>
									</table>
								</div>							
							</div>
						</div>
					</div>
						<div class="clear"></div>
						<div id="body_footer">
							<div id="bottom_left">
								<div id="bottom_right"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="common/footer.jsp"%>
	</body>
	<script src="${staticURL}/js/jquery.js" type="text/javascript"></script>
	<script src="${staticURL}/js/global.js" type="text/javascript"></script>
	<script src="${staticURL}/js/modal.js" type="text/javascript"></script>
	<script type="text/javascript">
		var errorMsg = '${errorMsg}';
		$(function(){
			if(errorMsg){
				alert(errorMsg);
			}
		});
		function addVersion(){
			var newver = $("#newver").val();
			var version_name = $("#version_name").val();
			var address = $("#address").val();
			var msg = $("#msg").val();
			if(!newver || !version_name || !address || !msg){
				alert('请完整输入更新信息');
				return;
			}
			if(!/^\d+$/.test(newver)){
				alert('更新代码必须为数字');
				return;
			}
			if(probability < 1000){
				alert('更新代码不能大于1000');
				return;
			}
			$('#addVersion').submit();
		}
		
		function modifyPwd(){
			var oldPassword = $("#oldPassword").val();
			var newPassword = $("#newPassword").val();
			var confirmPassword = $("#confirmPassword").val();
			if(!oldPassword){
				alert('请输入原密码');
				return;
			}
			if(!newPassword){
				alert('请输入新密码');
				return;
			}
			if(newPassword != confirmPassword){
				alert('新密码输入不一致');
				return;
			}
			 $.ajax({
			        url: "${contextPaht}/gm/sys/modifyPassword.do",
			        data:{oldPassword:oldPassword,newPassword:newPassword},
			        type: "post",
			        cache: false,
			        dataType:"json",
			        success: function(data) {
		        	  $(".modify_dropdown").hide();
				      $("#oldPassword").val('');
					  $("#newPassword").val('');
					  $("#confirmPassword").val('');
			          if(data.status == 200){
			        	  alert("修改成功");
			          }else{
			        	  alert(data.msg);
			          }
			        }
			});
		}
	</script>
	<%session.setAttribute("errorMsg", null); %>
</html>