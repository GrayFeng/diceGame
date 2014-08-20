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
								<a href="${contextPaht}/gm/game/clear.do" class="button"><span>清空房间信息</span></a>
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
											<th>房间号</th>
											<th>当前人数</th>
											<th>最大人数</th>
											<th>底注</th>
											<th>状态</th>
										</tr>
										<c:forEach items="${page.content}" var="room">
											<tr id="id_${room.boardNo}">
												<td class="checkbox">
													<input type="checkbox" name="checkbox" data="${room.boardNo}"/>
												</td>
												<td id="no_${room.boardNo}" data="${room.boardNo}">${room.boardNo}</td>
												<td id="gamerNum_${room.gamerNum}" data="${room.gamerNum}">${room.gamerNum}</td>
												<td id="maxGamerNum_${room.maxGamerNum}" data="${room.maxGamerNum}">${room.maxGamerNum}</td>
												<td id="score_${room.score}" data="${room.score}">${room.score}</td>
												<td>
													<c:choose>
														<c:when test="${room.status==0}">等待</c:when>
														<c:when test="${room.status==1}">开始</c:when>
														<c:when test="${room.status==2}">摇骰盅</c:when>
														<c:when test="${room.status==3}">竞猜</c:when>
														<c:when test="${room.status==4}">开盅</c:when>
														<c:when test="${room.status==5}">结束</c:when>
														<c:when test="${room.status==6}">关闭</c:when>
														<c:otherwise>未知</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</table>
									<div style="margin-top:10px;">
										<c:choose>
											<c:when test="${page.number > 1}">
												<a href="${contextPaht}/gm/game/roomList.do?pageNum=1" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/game/roomList.do?pageNum=${page.number - 1}" class="button"><span>上一页</span></a>
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0)" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="javascript:void(0)" class="button"><span>上一页</span></a>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${page.number < page.totalPages}">
												<a style="margin-left: 5px;" href="${contextPaht}/gm/game/roomList.do?pageNum=${page.number + 1}" class="button"><span>下一页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/game/roomList.do?pageNum=${page.totalPages}" class="button"><span>尾页</span></a>
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
	</script>
</html>