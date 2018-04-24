<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="StyleSheet" href="${pageContext.request.contextPath }/css/dtree.css" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/dtree.js"></script>
		
		
		<style type="text/css">
			body{
				background-image: url("${pageContext.request.contextPath }/img/bgi3.jpg");
				background-size:100%; 
			}
			
			.dtree{
				font-size: 20px;
			}
		</style>
</head>
<body>
	<div class="dtree">

			<p><a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a></p>

			<script type="text/javascript">
				d = new dTree('d');
				d.add(0, -1, '管理菜单');
				d.add(1, 0, '学生管理', '', '', 'display');
				d.add(11, 1, '增加学生信息', '${pageContext.request.contextPath}/admin/operation/add_stu.jsp', '', 'display');
				d.add(12, 1, '查询学生信息', '${pageContext.request.contextPath}/adminStudent?method=allStudent', '', 'display');
				d.add(2, 0, '教师管理', '','','display');
				d.add(21, 2, '增加教师信息', '${pageContext.request.contextPath}/adminTeacher?method=addTeacherUI', '', 'display');
				d.add(22, 2, '查询教师信息', '${pageContext.request.contextPath}/adminTeacher?method=queryTeacherUI', '', 'display');
				d.add(3, 0, '课程管理', '','','display');
				d.add(31, 3, '增加课程信息', '${pageContext.request.contextPath}/admin/operation/add_cou.jsp', '', 'display');
				d.add(32, 3, '查询课程信息', '${pageContext.request.contextPath}/admin/operation/query_cou.jsp', '', 'display');
				document.write(d);
			</script>

		</div>
</body>
</html>