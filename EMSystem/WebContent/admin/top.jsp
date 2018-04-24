<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#ele1{
		float: right;
	
	}
	#ele2{
		font-family: monospace;
		font-size: 50px;
		text-align: center;
	}
	body{
		background-image: url("${pageContext.request.contextPath }/img/bgi3.jpg");
	}
	
</style>
</head>
<body>
	<div id="ele1">管理员：<span>${manager.m_id }</span></div>
	<div id="ele2">教务管理系统后台界面</div>
</body>
</html>