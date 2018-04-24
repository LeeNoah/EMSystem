<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<style type="text/css">
		html,body{
			width:100%;
			height:100%;
			margin: 0px;
			background-image: url("${pageContext.request.contextPath}/img/bgi2.jpg");
			background-size: 100%;
		}
		#admin{
			height: 300px;
			position:fixed;
			left:42%;
			top:30%;
			margin-left:width/3;
			margin-top:height/3;
			text-align:center; 
		}
		#admin1{
			font-family:serif;
			font-size: 30px;
		}
		#form{
			font-family:serif;
			font-size: 20px;
		}
	</style>

</head>
<body>
	<div id="admin">
		<div id="admin1">管理员登录</div><br>
		<form id="form" action="${pageContext.request.contextPath }/adminStudent?method=login" method="post">
			账号<input type="text" name="m_id"/><br><br>
			密码<input type="password" name="m_password"/><br><br>
			<input type="submit" value="登录">
			<input type="reset" value="重置">
		</form>
		<span style="color:red;">${empty Info?"":Info }</span>
	</div>
</body>
</html>