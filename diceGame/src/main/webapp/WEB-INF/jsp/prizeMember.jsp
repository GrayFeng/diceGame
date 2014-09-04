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
											<th>会员名称</th>
											<th>会员手机号</th>
											<th>收货地址</th>
											<th>奖品名称</th>
											<th>中奖时间</th>
										</tr>
										<c:forEach items="${page.content}" var="prizeMember">
											<tr id="id_${prizeMember.id}">
												<td class="checkbox"><input type="checkbox" name="checkbox" data="${prizeMember.id}"/></td>
												<td id="memberName_${prizeMember.id}" data='${prizeMember.memberName}'>${prizeMember.memberName}</td>
												<td id="memberMobile_${prizeMember.id}" data='${prizeMember.memberMobile}'>${prizeMember.memberMobile}</td>
												<td id="address_${prizeMember.id}" data='${prizeMember.address}'>${prizeMember.address}</td>
												<td id="prizeName_${prizeMember.id}" data='${prizeMember.prizeName}'>${prizeMember.prizeName}</td>
												<td>${prizeMember.date}</td>
											</tr>
										</c:forEach>
									</table>
									<div style="margin-top:10px;">
										<c:choose>
											<c:when test="${page.number > 1}">
												<a href="${contextPaht}/gm/prize/prizeMemberList.do?pageNum=1" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/prize/prizeMemberList.do?pageNum=${page.number - 1}" class="button"><span>上一页</span></a>
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0)" class="button"><span>首页</span></a>
												<a style="margin-left: 5px;" href="javascript:void(0)" class="button"><span>上一页</span></a>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${page.number < page.totalPages}">
												<a style="margin-left: 5px;" href="${contextPaht}/gm/prize/prizeMemberList.do?pageNum=${page.number + 1}" class="button"><span>下一页</span></a>
												<a style="margin-left: 5px;" href="${contextPaht}/gm/prize/prizeMemberList.do?pageNum=${page.totalPages}" class="button"><span>尾页</span></a>
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
</html>