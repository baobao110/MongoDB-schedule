<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();


String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

 
	response.sendRedirect(basePath+"pages/pt/common/login.html");  

	/* response.sendRedirect("/ay/login/toLogin.do"); */

%>

