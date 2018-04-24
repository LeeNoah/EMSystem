<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath }/css/list.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>	
	
	<script type="text/javascript">
		$(function(){
			$("#btnMulSelect").click(function(){
				$("#selectAll").toggle();
				$(".selectOne").toggle();
				$("#btnMulDel").toggle();
			});
			$("#selectAll").click(function(){
				$(".selectOne").prop("checked",this.checked);
			});
			$("#btnMulDel").click(function(){
				var arrays = document.getElementsByName("mulDelStu");
				var str = "";
				var para = "";
				var count=0;
				
				$.each(arrays,function(index,con){
					if(arrays[index].checked){
						count++;
						str+="s_no=\""+con.value+"\",&";
					}
				});
				if(count==0){
					alert("请至少选择一项进行删除");
					return ;
				}
				if(!confirm("确定全部删除？")){
					return ;
				}
				para=str.substring(0, str.length-1);//去除最后的&
				location.href="${pageContext.request.contextPath }/adminStudent?method=delMulStudent&"+para;
			});
		});
		
		function DelStudent(s_no){
			var isDel= confirm("确认删除？");
			if(isDel){
				window.location.href="${pageContext.request.contextPath }/adminStudent?method=delStudent&s_no="+s_no;
			}
		}
	</script>
	<style type="text/css">
		#btnMulDel{
			display:none;
		}
		#selectAll{
			display:none;
		}
		.selectOne{
			display:none;
		}
	</style>
</head>


<body>
	<table width="1200px" border="1px" cellspacing="0px" cellpadding="5px">
			<tr>
				<td width="3%"><input type="checkbox" id="selectAll" /></td>
				<td width="10%">姓名</td>
				<td width="18%">学号</td>
				<td width="10%">年级</td>
				<td width="25%">学院</td>
				<td width="25%">专业</td>
				<td><input type="button" id="btnMulSelect" value="多选删除"/></td>
				<td><input type="button" id="btnMulDel" value="确定删除"/></td>
			</tr>
			<c:if test="${isAll==false }">
				<c:forEach items="${stu_list }" var="stu"> 
				<tr>
				<td width="3%"><input type="checkbox" class="selectOne"  name="mulDelStu"  value="${stu.student.s_no }"/></td>
				<td>${stu.student.s_name }</td>
				<td>${stu.student.s_no }</td>
				<td>${stu.student.s_grade }</td>
				<td>${stu.ac_name }</td>
				<td>${stu.d_name }</td>
				<td width="10%">
					<a href="${pageContext.request.contextPath }/adminStudent?method=updateStudentUI&s_no=${stu.student.s_no }&s_academyName=${stu.ac_name }&s_deptName=${stu.d_name }">
						<input type="button" class="btnupdate" value="更新"/>
					</a>
				</td >
				<td width="10%">
					<a href="javascript:void(0);" onclick="DelStudent('${stu.student.s_no }')">
						<input type="button" class="btndelete" value="删除"/>
					</a>				
				</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${isAll==true }">
				<c:forEach items="${pageBean.displayList }" var="stu"> 
				<tr>
				<td width="3%"><input type="checkbox" class="selectOne"  name="mulDelStu"  value="${stu.student.s_no }"/></td>
				<td>${stu.student.s_name }</td>
				<td>${stu.student.s_no }</td>
				<td>${stu.student.s_grade }</td>
				<td>${stu.ac_name }</td>
				<td>${stu.d_name }</td>
				<td width="10%">
					<a href="${pageContext.request.contextPath }/adminStudent?method=updateStudentUI&s_no=${stu.student.s_no }&s_academyName=${stu.ac_name }&s_deptName=${stu.d_name }">
						<input type="button" class="btnupdate" value="更新"/>
					</a>
				</td >
				<td width="10%">
					<a href="javascript:void(0);" onclick="DelStudent('${stu.student.s_no }')">
						<input type="button" class="btndelete" value="删除"/>
					</a>				
				</td>
			</tr>
			</c:forEach>
			</c:if>
			
		</table>
		
</body>
</html>