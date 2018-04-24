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
				var arrays=$("input:checkbox[name=delMulTea]:checked");
				var str = "";
				var para = "";
				if(arrays.length==0){
					alert("请至少选择一项进行删除");
					return ;
				}
				if(!confirm("确定全部删除？")){
					return ;
				}
				$.each(arrays,function(index,con){
						str+="t_no=\""+con.value+"\",&";
				});
				
				para=str.substring(0, str.length-1);//去除最后的&
				location.href="${pageContext.request.contextPath }/adminTeacher?method=delMulTeacher&"+para;
			});
		});
		
		function DelStudent(t_no){
			var isDel= confirm("确认删除？");
			if(isDel){
				window.location.href="${pageContext.request.contextPath }/adminTeacher?method=delTeacher&t_no="+t_no;
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
				<td width="18%">教师编号</td>
				<td width="10%">入职年份</td>
				<td width="25%">学院</td>
				<td><input type="button" id="btnMulSelect" value="多选删除"/></td>
				<td><input type="button" id="btnMulDel" value="确定删除"/></td>
			</tr>
			<c:if test="${isAll==false }">
				<c:forEach items="${tea_list }" var="tea"> 
				<tr>
				<td width="3%"><input type="checkbox" class="selectOne"  name="delMulTea" value="${tea.teacher.t_no }" /></td>
				<td>${tea.teacher.t_name }</td>
				<td>${tea.teacher.t_no }</td>
				<td>${tea.teacher.t_entryyear }</td>
				<td>${tea.ac_name }</td>
				<td width="10%">
					<a href="${pageContext.request.contextPath }/adminTeacher?method=updateTeacherUI&t_no=${tea.teacher.t_no }">
						<input type="button" class="btnupdate" value="更新"/>
					</a>
				</td >
				<td width="10%">
					<a href="javascript:void(0);" onclick="DelStudent('${tea.teacher.t_no }')">
						<input type="button" class="btndelete" value="删除"/>
					</a>				
				</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${isAll==true }">
				<c:forEach items="${pageBean.displayList }" var="tea"> 
				<tr>
				<td width="3%"><input type="checkbox" class="selectOne"  name="delMulTea"  value="${tea.teacher.t_no }"/></td>
				<td>${tea.teacher.t_name }</td>
				<td>${tea.teacher.t_no }</td>
				<td>${tea.teacher.t_entryyear }</td>
				<td>${tea.ac_name }</td>
				<td width="10%">
					<a href="${pageContext.request.contextPath }/adminTeacher?method=updateTeacherUI&t_no=${tea.teacher.t_no }">
						<input type="button" class="btnupdate" value="更新"/>
					</a>
				</td >
				<td width="10%">
					<a href="javascript:void(0);" onclick="DelStudent('${tea.teacher.t_no }')">
						<input type="button" class="btndelete" value="删除"/>
					</a>				
				</td>
			</tr>
			</c:forEach>
			</c:if>
			
		</table>
		
</body>
</html>