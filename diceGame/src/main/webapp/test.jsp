<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" action="/diceGame//api/upload.do">
	<input type="file" name="file"/>
	<input type="text" name="uid">
	<input type="submit" value="submit">
</form>

<form method="post"  action="/diceGame/api/updateMember.do">
	<input type="text" name="uid">
	<input type="text" name="params">
	<input type="submit" value="submit">
</form>

</body>
</html>