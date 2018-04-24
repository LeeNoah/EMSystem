<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			
			//动态获取学院列表
			$("#selectA").one("click",function(){
				//alert("ss");
				$.post("${pageContext.request.contextPath }/adminStudent?method=selectList",
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
			$("#selectA").change(function(){
				$("#selectD").removeAttr("disabled");
				$("#selectD").empty();
				
			});
			$("#selectD").focus(function(){
				$("#selectD").empty();
				if($("#selectA option:selected").val()==0){
					$(this).attr("disabled","disabled");				
				}else{
					$.post("${pageContext.request.contextPath }/adminStudent?method=selectList",
							{"select":"dept","ac_no":$("#selectA option:selected").val()},
							function(data){
								json=eval(data);
								$.each(json,function(name,value){
									var strArray = value.split("_");
									$("#selectD").append("<option value=\""+strArray[0]+"\">"+strArray[1]+"</option>");
								});
							},
							"json"
					);
				}
			});
		});
		
		function checkAll(){
			var reg=/[^\x00-\x80]/;
			var name=$("#s_name").val();
			if(!reg.test(name)){
				alert("请输入中文姓名！");
				$("#s_name").focus();
				$("#s_name").val("");
				return false;
				
			}else{
				return true;
			}
		}
		
	</script>
	
</head>

<body>
	<div class="eleOpe">
		<span>修改学生信息</span><br /><br /><br />
		<form action="${pageContext.request.contextPath }/adminStudent?method=updateStudent" method="post" onsubmit="return checkAll()">
			<div>请修改学生信息</div>
			学生专业<select  id="selectA" style="width:120px;" required>
				<option value="0" disabled selected>${s_academyName }</option>
			</select>
			系别<select id="selectD" style="width:130px;" id="selectDept" name="s_dept" required>
				<option value="0" disabled selected>${s_deptName }</option>
			</select> <br>
			
			学生年级<input type="text" name="s_grade" readonly value="${student.s_grade }"/><br /> 
			学生学号<input type="text" name="s_no" id="s_no" readonly value="${student.s_no }"/><br /> 
			学生姓名 <input type="text" name="s_name" id="s_name" placeholder="输入姓名" value="${student.s_name }" required/><br /> 
			学生密码 <input type="text" name="s_password" id="s_password" pattern="\w{6,12}" placeholder="输入6~12位密码" value="${student.s_password }" required/><br /> 
			<input type="submit"value="确认修改" /> 
			<input type="reset" value="重置" />
			 <div id="info" style=" color: red;">${empty ErrorInfo?"":ErrorInfo }</div>
		</form>
		</div>
</body>
</html>