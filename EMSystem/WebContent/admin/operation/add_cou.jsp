<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath }/css/add.css">

</head>

<body>
	<div class="eleOpe">
		<span>添加课程信息(未实现功能)</span><br /><br /><br />
		<form action="" method="post">
			<div>请输入课程信息</div>
			课程编号<input type="text" name="c_no" /><br /> 
			课程名称 <input type="text" name="c_name" /><br /> 
			任课教师编号 <input type="text" name="c_tno" /><br /> 
			课程学分<input type="text" name="s_credit" /><br /> 
			课程类型 <input type="text" name="c_type" /><br />
			课程时长 <input type="text" name="c_period" /><br /> 
			<input type="submit"value="提交" /> 
			<input type="reset" value="重置" />
		</form>
		</div>
</body>
</html>