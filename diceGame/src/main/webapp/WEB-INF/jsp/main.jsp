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
								<a href="javascript:getUser()" class="button"><span>修改用户信息</span></a>
								<div class="dropdown modify_user_dropdown" style="display: none;">
									<div class="arrow"></div>
									<div class="content">
										<form action="${contextPaht}/gm/user/update.do" id="modifyUser" method="post">
											<p>
												<label for="name">昵称:</label>
												<input type="text" class="text w_22" name="name" id="name" maxlength="50">
											</p>
											<p>
												<label for="name">性别:</label>
												<input name="sex" id="radio_2" value="1" type="radio" checked="checked">男
												<input name="sex" id="radio_2" value="2" type="radio">女
											</p>
											<p>
												<label for="name">增加积分:</label>
												<input type="text" class="text w_22" name="score" id="score" maxlength="10">
											</p>
											<p>
												<label for="name">密码:</label>
												<input type="text" class="text w_22" name="password" id="password" maxlength="10">
											</p>
											<p>
												<label for="name">确认密码:</label>
												<input type="text" class="text w_22" name="confirm_password" id="confirm_password" maxlength="10">
											</p>
											<input type="hidden" class="text w_22" name="id" id="userId">
										</form>
										<a href="javascript:modifyUser()" class="button green right"><small class="icon check"></small><span>保存</span></a>
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
											<th class="checkbox"></th>
											<th>昵称</th>
											<th>手机号</th>
											<th>性别</th>
											<th>积分</th>
											<th>状态</th>
										</tr>
										<c:forEach items="${page.content}" var="member">
											<tr id="id_${member.id}">
												<td class="checkbox">
													<input type="checkbox" name="checkbox" data="${member.id}"/>
												</td>
												<td id="name_${member.id}" data="${member.name}">${member.name}</td>
												<td id="mobile_${member.id}" data="${member.mobile}">${member.mobile}</td>
												<c:choose>
													<c:when test="${member.sex == 2}">
														<td id="sex_${member.id}" data="${member.sex}">女</td>
													</c:when>
													<c:otherwise>
														<td id="sex_${member.id}" data="${member.sex}">男</td>
													</c:otherwise>
												</c:choose>
											<td id="score_${member.id}" data="${member.account.scoreAmount}">${member.account.scoreAmount}</td>
											<td>正常</td>
										</tr>
										</c:forEach>
									</table>
									<div style="margin-top:10px;">
										<c:choose>
											<c:when test="${page.number > 1}">
												<a href="${contextPaht}/gm/user/list.do?pageNum=1" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/user/list.do?pageNum=${page.number - 1}" class="button"><span>上一页</span></a>
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0)" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="javascript:void(0)" class="button"><span>上一页</span></a>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${page.number < page.totalPages}">
												<a style="margin-left: 5px;" href="${contextPaht}/gm/user/list.do?pageNum=${page.number + 1}" class="button"><span>下一页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/user/list.do?pageNum=${page.totalPages}" class="button"><span>尾页</span></a>
											</c:when>
											<c:otherwise>
												<a style="margin-left: 5px;" href="javascript:void(0)" class="button"><span>下一页</span></a>
												<a style="margin-left: 5px;" href="javascript:void(0)" class="button"><span>尾页</span></a>
											</c:otherwise>
										</c:choose>
										<span class="strong" style="line-height: 28px;margin-left: 10px;">页数:${page.number}/${page.totalPages}</span>
									</div>
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
		$(function(){
			$("input[type=checkbox]").click(function(){
				$("input[type=checkbox]").removeAttr("checked");
				$(this).attr('checked',true);
			});
		});
		function getUser(){
			var id = '';
			$("input[type=checkbox]").each(function(i,checkbox){
				if($(checkbox).attr('checked') == true){
					id = $(checkbox).attr('data');
					return false;
				}
			});
			if(!id){
				alert('请先选择一个用户');
				return;
			}
			$("#name").val($("#name_"+id).attr('data'));
			if($("#sex_"+id).attr('data') == 2){
				$('input:radio').eq(1).attr('checked', 'true');
			}else{
				$('input:radio').eq(0).attr('checked', 'true');
			}
			$("#userId").val(id);
			$(".modify_user_dropdown").stop().slideToggle();
		}
		
		function modifyUser(){
			var name = $("#name").val();
			var password = $("#password").val();
			var confirm_password = $("#confirm_password").val();
			if(!name){
				alert('昵称不能为空');
				return;
			}
			if(password != confirm_password){
				alert('两次密码不一致');
				return;
			}
			$('#modifyUser').submit();
		}
	</script>
</html>