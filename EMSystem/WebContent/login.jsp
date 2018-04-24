<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--pageEncoding 是jsp页面编码 --%>
    <%--contentType是web页面显示编码 --%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
		<style type="text/css">
			#father{
				width: 100%;
				height: 100%;
				font-size: 20px;
				background-color: deepskyblue;
				position: absolute;
				background-image: url(img/bgi2.jpg);
				background-size: 100%;
			}
			#son1{
				font-size: 30px;
				margin: 10px;
				font-family:georgia;	
				text-align: center;
			}
			#login{
				width: 750px;
				margin-top: 50px;
				float: right;
			}
			#img{
			}
		</style>
</head>
<body>
	<div id="father">
			<div id="son1">
				欢迎使用我的教务管理系统！
			</div>
			<div id="login">
				<form action="${pageContext.request.contextPath }/login" method="post">
					用户名：<input type="text" name="username" required="required"/><br />
					密码：<input type="password" name="password" required="required"/><br />
					<input type="radio" name="user" value="manager"/>管理员
					<input type="radio" name="user" value="teacher"/>教师
					<input type="radio" name="user" value="student" checked="checked"/>学生<br />
					<input type="submit" value="登录"/>
					<input type="reset" value="重置" />
				</form>
				<div><%=request.getAttribute("LoginInfo")==null?"":request.getAttribute("LoginInfo") %></div>
			</div>
			<div id="img">
				<img src="img/kung_fu_bunny.jpg" width="400" height="250"/>
			</div>
		</div>
</body>
</html>