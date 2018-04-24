<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<link rel="StyleSheet" type="text/css" href="${pageContext.request.contextPath }/css/add.css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
		var ID="";
		$(function(){
			
			
			$("#t_entryyear").click(function(){
				$("#divInfo1").show();
			});
			
			$("#t_no").click(function(){
				if($("#selectA option:selected").val()=="0" || $("#t_entryyear").val()==""){
					alert("请务必填写前面两项再输入编号！");
				}else{
					var s1=$("#selectA option:selected").val();
					var p1=s1.substring(s1.length-2);
					var p2=s1.substring(s1.length-4,s1.length-2);
					
					var s2=$("#t_entryyear").val();
					var p3=s2.substring(s2.length-2);
					ID=p1.concat(p2,p3);
					$(this).val(ID);
					$("#divInfo2").show();
				}
			});
			
			
		});
		
		function checkAll(){
			var checkYear = $("#t_entryyear").val();
			var date = new Date();
			var currYear = date.getFullYear();
			var id=$("#t_no").val();
			var checkID=id.substring(0,6);
			
			var reg=/[^\x00-\x80]/;
			var name=$("#t_name").val();
			
			if(!reg.test(name)){
				alert("请输入中文姓名！");
				$("#t_name").focus();
				$("#t_name").val("");
				return false;
				
			}
			if(checkYear<1985 || checkYear>currYear){
				alert("输入年份不在要求范围之内，请重新输入！");
				return false;
			}
			
			if(checkID==ID){
				return true;
			}else{
				alert("教师编号有误，请务必按提示输入");
				return false;
			}
		}
	</script>
	
</head>

<body>
	<div class="eleOpe">
		<span>添加教师信息</span><br /><br /><br />
		<form action="${pageContext.request.contextPath }/adminTeacher?method=addTeacher" method="post" onsubmit="return checkAll();">
			<div>请输入学生信息</div>
			教师所属学院<select  id="selectA" style="width:120px;" name="t_acno" required>
				<option value="0" disabled selected>请选择学院</option>
				<c:forEach items="${ac_list }" var="academy">
					<option value="${academy.ac_no }">${academy.ac_name }</option>
				</c:forEach>
			</select><br>
			教师入职年份<input type="text" name="t_entryyear" id="t_entryyear"  pattern="[0-9]{4}" placeholder="输入四位数年份" required/><br /> 
			<div id="divInfo1" style="font-size:15px;color:green;display:none;">输入年份介于1985到当前年份</div>
			教师编号<input type="text" name="t_no" id="t_no"  pattern="\w{6}[0-9]{3}" placeholder="输入后三位编号" required/><br /> 
				<div id="divInfo2" style="font-size:18px; color: green; display:none;">请务必接着已有内容输入后三位编号！</div>
			教师姓名 <input type="text" name="t_name" id="t_name" placeholder="输入姓名" required/><br /> 
			教师密码 <input type="text" name="t_password" id="t_password" value="ahu123456" readonly/><br /> 
			<input type="submit"value="提交" /> 
			<input type="reset" value="重置" />
			 <div id="info" style=" color: green;">${empty info?"":info }</div>
		</form>
		</div>
</body>
</html>