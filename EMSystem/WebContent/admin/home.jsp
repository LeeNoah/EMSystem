<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教务系统后台管理页面</title>
</head>
<frameset rows="15%,*,5%" frameborder="0">
		<frame src="top.jsp" />
		<frameset cols="20%,*">
			<frame src="${pageContext.request.contextPath }/admin/left.jsp" />
			<frame  style="background-image: url('${pageContext.request.contextPath }/img/bgi3.jpg'); background-size : 100%; " name="display" src="welcome.jsp" />
		</frameset>
		<frame src="${pageContext.request.contextPath }/admin/bottom.jsp" />
	</frameset>
</html>