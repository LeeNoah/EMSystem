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
			var name=$("#t_name").val();
			if(!reg.test(name)){
				alert("请输入中文姓名！");
				$("#t_name").focus();
				$("#t_name").val("");
				return false;
				
			}else{
				return true;
			}
		}
	</script>
	
	
</head>

<body>
	<div class="eleOpe">
		<span>修改教师信息</span><br /><br /><br />
		<form action="${pageContext.request.contextPath }/adminTeacher?method=updateTeacher" method="post" onsubmit="return checkAll()">
			<div>请修改教师信息</div>
			教师专业<select  id="selectA" style="width:120px;" name="t_acno" required>
				<option  value="${teacher.teacher.t_acno }" disabled selected>${teacher.ac_name }</option>
			</select><br>
			
			入职年份<input type="text" name="t_entryyear" readonly value="${teacher.teacher.t_entryyear }"/><br /> 
			教师编号<input type="text" name="t_no" id="t_no" readonly value="${teacher.teacher.t_no }"/><br /> 
			教师姓名 <input type="text" name="t_name" id="t_name" placeholder="输入姓名" value="${teacher.teacher.t_name }" required/><br /> 
			教师密码 <input type="text" name="t_password" id="t_password" pattern="\w{6,12}" placeholder="输入6~12位密码" value="${teacher.teacher.t_password }" required/><br /> 
			<input type="submit"value="确认修改" /> 
			<input type="reset" value="重置" />
			 <div id="info" style=" color: red;">${empty ErrorInfo?"":ErrorInfo }</div>
		</form>
		</div>
</body>
</html>