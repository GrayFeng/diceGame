<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	if(path == null){
		path = "";
	}
	request.setAttribute("staticURL",path+"/static");
	request.setAttribute("contextPaht",path);
%>