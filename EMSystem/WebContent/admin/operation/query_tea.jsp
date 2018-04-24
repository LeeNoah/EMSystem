<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath }/css/list.css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
		$(function(){
			//动态从服务器获取年级select选项
			$("#selectY").one("click",function(){
				$("#selectY").empty();
				$.post("${pageContext.request.contextPath }/adminTeacher?method=selectList",
						{"select":"entryyear"},
						function(data){
							json=eval(data);
							$.each(json,function(name,value){
								$("#selectY").append("<option>"+value+"</option>");
							});
						},
						"json"
				);
			});
			//动态获取学院列表
			$("#selectA").one("click",function(){
				$.post("${pageContext.request.contextPath }/adminTeacher?method=selectList",
						{"select":"academy"},
						function(data){
							json=eval(data);
							$.each(json,function(name,value){
								var strArray = value.split("_");
								$("#selectA").append("<option value=\""+strArray[0]+"\">"+strArray[1]+"</option>");
							});
						},
						"json"
				);
			});
		});	
		function check(){
			var year = $("#selectY option:selected").val();
			var academy = $("#selectA option:selected").val();
			if(year==0 && academy==0){
				alert("请至少选一个条件进行查询！");
				return false;
			}
			return true;
		}
		
	</script>
	<style type="text/css">
		.divquery{
			font-family: cursive;
			font-size: 20px;
			color:green; 
		}
		.divErrorInfo{
		font-family: cursive;
		color:red;	
		}
		li{
			display:inline;
		}
	</style>
</head>
<body>
	<div>
		<div id="div1">查询教师信息</div>
		<div id="divSelect">
			<div class="divquery" >输入教师编号进行查询</div>
			<form action="${pageContext.request.contextPath }/adminTeacher?method=queryTeacher" method="post">
				<input type="text" name="t_no"/ required>
				<input type="submit" value="查询"/>
			</form>
			<div class="divquery">按条件进行查询</div>
			<form id="form1" action="${pageContext.request.contextPath }/adminTeacher?method=queryTeacher" method="post" onsubmit="return check()">
				<span>入职年份</span>
				<select id="selectY" name="t_entryyear" style="width:100px;">
					<option value="0" disabled selected>请选择年份</option>
				</select>
				<span>学院</span>
				<select id="selectA" name="t_acno" style="width:150px;">
					<option value="0" disabled selected>请选择学院</option>
				</select>
				<input type="submit" value="查询" />
			</form>
			<div class="divErrorInfo">${empty Info?"":Info }</div>
		</div>
		<jsp:include page="/admin/list/tea_list.jsp"></jsp:include>
		
		
		<!--分页 -->
		<div style="width: 380px; margin: 0 auto; margin-top: 20px;">
			<ul class="pagination" style="text-align: center; margin-top: 10px;">
				<!-- 上一页 -->
				<!-- 判断当前页是否是第一页 -->
				<c:if test="${pageBean.currentPage==1 }">
					<li class="disabled">
						<a href="javascript:void(0);" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pageBean.currentPage!=1 }">
					<li>
						<a href="${pageContext.request.contextPath }/adminStudent?method=allStudent&currentPage=${pageBean.currentPage-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>	
				
				
				
			
				<c:forEach begin="1" end="${pageBean.totalPage }" var="page">
					<!-- 判断当前页 -->
					<c:if test="${pageBean.currentPage==page }">
						<li class="active"><a href="javascript:void(0);">${page}</a></li>
					</c:if>
					<c:if test="${pageBean.currentPage!=page }">
						<li><a href="${pageContext.request.contextPath }/adminStudent?method=allStudent&currentPage=${page}">${page}</a></li>
					</c:if>
				
				</c:forEach>
				
				<!-- 判断当前页是否是最后一页 -->
				<c:if test="${pageBean.currentPage==pageBean.totalPage }">
					<li class="disabled">
						<a href="javascript:void(0);" aria-label="Next"> 
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
					<li>
						<a href="${pageContext.request.contextPath }/adminStudent?method=allStudent&currentPage=${pageBean.currentPage+1}" aria-label="Next"> 
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
			
			</ul>
		</div>
		<!-- 分页结束 -->
	</div>
</body>
</html>