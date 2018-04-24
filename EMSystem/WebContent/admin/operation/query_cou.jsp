<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath }/css/list.css">
	
	<style type="text/css">
		.display td{
			width:50px;
		}
	</style>
</head>
<body>
	<div>
		<div id="div1">查询课程信息(未实现功能)</div>
		<div>
			<select>
				<option>所属院系</option>
				<option>计科</option>
				<option>电子</option>
			</select>
			<select>
				<option>类型</option>
				<option>必修</option>
				<option>选修</option>
			</select>
			课程编号<input type="text" name="c_no" />
			<input type="button" value="查询" />
		</div>
		<table class="display_table" border="1px" cellspacing="0px" cellpadding="5px">
			<tr>
				<td>课程名称</td>
				<td>课程编号</td>
				<td>所属院系</td>
				<td>任课教师</td>
				<td>学分</td>
				<td>课程类型</td>
				<td>课程时长</td>
				<td><input type="button" value="更新"/></td>
				<td><input type="button" value="删除"/></td>
			</tr>
		</table>
	</div>
</body>
</html>