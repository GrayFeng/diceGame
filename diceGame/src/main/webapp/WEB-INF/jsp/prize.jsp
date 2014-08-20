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
								<a href="javascript:void(0)" class="dropdown_button"><span>添加奖品</span></a>
								<div class="dropdown" style="display: none;">
									<div class="arrow"></div>
									<div class="content">
										<form action="${contextPaht}/gm/prize/add.do" id="addPrize" method="post">
											<p>
												<label for="name">奖品名称:</label>
												<input type="text" class="text w_22" name="name" id="name" maxlength="50">
											</p>
											<p>
												<label for="name">库存数量:</label>
												<input type="text" class="text w_22" name="stock" id="stock" maxlength="10">
											</p>
											<p>
												<label for="name">中奖概率:</label>
												<input type="text" class="text w_22" name="probability" id="probability" maxlength="5">
											</p>
										</form>
										<a href="javascript:addPrize()" class="button green right"><small class="icon check"></small><span>保存</span></a>
										<a class="button red mr right close"><small class="icon cross"></small><span>取消</span></a>
										<div class="clear"></div>
									</div>
								</div>
								
								<!-- 修改奖品信息 -->
								<a href="javascript:getPrize()" class="button"><span>修改奖品</span></a>
								<div class="dropdown modify_dropdown" style="left: 70px;display: none;">
									<div class="arrow"></div>
									<div class="content">
										<form action="${contextPaht}/gm/prize/modify.do" id="modifyPrize" method="post">
											<p>
												<label for="name">奖品名称:</label>
												<input type="text" class="text w_22" name="name" id="modify_name" maxlength="50">
											</p>
											<p>
												<label for="name">库存数量:</label>
												<input type="text" class="text w_22" name="stock" id="modify_stock" maxlength="10">
											</p>
											<p>
												<label for="name">中奖概率:</label>
												<input type="text" class="text w_22" name="probability" id="modify_probability" maxlength="5">
											</p>
											<input type="hidden" name="id" id="modify_id">
										</form>
										<a href="javascript:modifyPrize()" class="button green right"><small class="icon check"></small><span>保存</span></a>
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
											<th>名称</th>
											<th>库存</th>
											<th>概率</th>
										</tr>
										<c:forEach items="${page.content}" var="prize">
											<tr id="id_${prize.id}">
												<td class="checkbox"><input type="checkbox" name="checkbox" data="${prize.id}"/></td>
												<td id="name_${prize.id}" data='${prize.name}'>${prize.name}</td>
												<td id="stock_${prize.id}" data='${prize.stock}'>${prize.stock}</td>
												<td id="probability_${prize.id}" data='${prize.probability}'>${prize.probability}%</td>
											</tr>
										</c:forEach>
									</table>
									<div style="margin-top:10px;">
										<c:choose>
											<c:when test="${page.number > 1}">
												<a href="${contextPaht}/gm/prize/list.do?pageNum=1" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/prize/list.do?pageNum=${page.number - 1}" class="button"><span>上一页</span></a>
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0)" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="javascript:void(0)" class="button"><span>上一页</span></a>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${page.number < page.totalPages}">
												<a style="margin-left: 5px;" href="${contextPaht}/gm/prize/list.do?pageNum=${page.number + 1}" class="button"><span>下一页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/prize/list.do?pageNum=${page.totalPages}" class="button"><span>尾页</span></a>
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
		function addPrize(){
			var name = $("#name").val();
			var stock = $("#stock").val();
			var probability = $("#probability").val();
			if(!name || !stock || !probability){
				alert('请完整输入奖品信息');
				return;
			}
			if(!/^\d+$/.test(stock)){
				alert('库存数量必须为数字');
				return;
			}
			if(!/^\d+|\d+.\d+$/.test(probability) || probability > 100){
				alert('概率必须为100内整数或小数');
				return;
			}
			$('#addPrize').submit();
		}
		
		function modifyPrize(){
			var name = $("#modify_name").val();
			var stock = $("#modify_stock").val();
			var probability = $("#modify_probability").val();
			if(!name || !stock || !probability){
				alert('请完整输入奖品信息');
				return;
			}
			if(!/^\d+$/.test(stock)){
				alert('库存数量必须为数字');
				return;
			}
			if(!/^\d+|\d+.\d+$/.test(probability) || probability > 100){
				alert('概率必须为100内整数或小数');
				return;
			}
			$('#modifyPrize').submit();
		}
		
		function getPrize(){
			var id = '';
			$("input[type=checkbox]").each(function(i,checkbox){
				if($(checkbox).attr('checked') == true){
					id = $(checkbox).attr('data');
					return false;
				}
			});
			if(!id){
				alert('请先选择一个奖品');
				return;
			}
			$("#modify_name").val($("#name_"+id).attr('data'));
			$("#modify_stock").val($("#stock_"+id).attr('data'));
			$("#modify_probability").val($("#probability_"+id).attr('data'));
			$("#modify_id").val(id);
			$(".modify_dropdown").stop().slideToggle();
		}
	</script>
</html>