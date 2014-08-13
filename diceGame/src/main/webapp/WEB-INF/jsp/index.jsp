<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="common/common.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<title>管理系统-用户登录</title>
		<link rel="stylesheet" href="${staticURL}/css/style.css" type="text/css"/>
	</head>
	<body>
		<div id="wrapper_login">
			<div id="menu">
				<div id="left"></div>
				<div id="right"></div>
				<h2>管理员登录</h2>
				<div class="clear"></div>		
			</div>
			<div id="desc">
				<div class="body">
					<div class="col w10 last bottomlast">
						<form action="#">
							<p>
								<label for="username">用户名:</label>
								<input type="text" name="username" id="username" value="" size="40" class="text" />
								<br />
							</p>
							<p>
								<label for="password">密&nbsp;&nbsp;码:</label>
								<input type="password" name="password" id="password" value="" size="40" class="text" />
								<br />
							</p>
							<p class="last">
								<a href="javascript:void(0)" class="button loginBtn">
									<small class="icon play"></small><span>登录</span>
								</a>
								<br />
							</p>
							<div class="clear"></div>
						</form>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div id="body_footer">
					<div id="bottom_left"><div id="bottom_right"></div></div>
				</div>
			</div>		
		</div>
	</body>
	<script src="${staticURL}/js/jquery.js" type="text/javascript"></script>
	<script src="${staticURL}/js/global.js" type="text/javascript"></script>
	<script src="${staticURL}/js/modal.js" type="text/javascript"></script>	
	<script>
		$(function(){
			var contextPaht = "${contextPaht}";
			$(".loginBtn").click(function(){
				var userName = $.trim($("#username").val());
				var password = $.trim($("#password").val());
				if(userName && password){
					 $.ajax({
					        url: contextPaht + "/gm/login.do",
					        data:{userName:userName,password:password},
					        type: "post",
					        cache: false,
					        dataType:"json",
					        success: function(data) {
					          if(data.status == 200){
					        	  window.location.href = contextPaht + "/gm/user/list.do";
					          }else{
					        	  alert("用户名或密码错误");
					          }
					        }
					    });
				}else{
					alert("请输入用户名密码");
				}
			});
		});
	</script>	
</html>