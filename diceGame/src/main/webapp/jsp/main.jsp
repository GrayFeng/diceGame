<%@ page language="java" contentType="text/html; charset=utf-8"%>
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
								<a href="" class="dropdown_button"><span>用户列表</span></a>
							</div>
						</div>
						<div class="modules_right"></div>
					</div>
					<div id="desc">
						<div class="body">
							<div id="table" class="help">
							<h1>Table:</h1>
							<div class="col w10 last">
								<div class="content">
									<table>
										<tr>
											<th class="checkbox"><input type="checkbox" name="checkbox" /></th>
											<th>Th #1</th>
											<th>Th #2</th>
											<th>Th #3</th>
											<th>Th #4</th>
											<th>Th #5</th>
											<th>Th #6</th>
										</tr>
										<tr id="id_1">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_2">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_3">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_4">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_5">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_6">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_7">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_8">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_9">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
										<tr id="id_10">
											<td class="checkbox"><input type="checkbox" name="checkbox" /></td>
											<td>Lorem</td>
											<td>Ipsum</td>
											<td>Dolor</td>
											<td>Sit</td>
											<td>Amez</td>
											<td>Consectetur</td>
										</tr>
									</table>
								</div>							
							</div>
						</div>
					</div>
						<div class="clear"></div>
						<div id="body_footer">
							<div id="bottom_left"><div id="bottom_right"></div></div>
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
</html>