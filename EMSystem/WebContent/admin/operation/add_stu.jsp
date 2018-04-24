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
			
			//动态获取年份
			$("#selectG").one("click",function(){
			var date=new Date();
			var year=date.getFullYear();
			var yearArray = new Array(year,year-1,year-2,year-3,year-4);
				$.each(yearArray,function(i,n){
					$("#selectG").append("<option>"+n+"</option>");
				});	
			});
			
			$("#s_no").click(function(){
				if($("#selectA option:selected").val()=="0" || $("#selectD option:selected").val()=="0" || $("#selectG option:selected").val()=="0"){
					alert("请务必选择前面三项再输入学号！");
				}else{
					var s1=$("#selectA option:selected").val();
					var p1=s1.substring(s1.length-2);
					var p2=s1.substring(s1.length-4,s1.length-2);
					
					var s2=$("#selectD option:selected").val();
					var p3=s2.substring(s2.length-2);
					
					var s3=$("#selectG option:selected").val();
					var p4=s3.substring(s3.length-2);
					ID=p1.concat(p2,p3,p4);
					$(this).val(ID);
					$("#divInfo").show();
				}
			});
			
			
		});
		
		function checkAll(){
			var id=$("#s_no").val();
			var check=id.substring(0,8);
			
			var reg=/[^\x00-\x80]/;
			var name=$("#s_name").val();
			if(!reg.test(name)){
				alert("请输入中文姓名！");
				$("#s_name").focus();
				$("#s_name").val("");
				return false;
				
			}
			if(check==ID){
				return true;
			}else{
				alert("学号有误，请务必按提示输入");
				return false;
			}
		}
	</script>
	
</head>

<body>
	<div class="eleOpe">
		<span>添加学生信息</span><br /><br /><br />
		<form action="${pageContext.request.contextPath }/adminStudent?method=addStudent" method="post" onsubmit="return checkAll();">
			<div>请输入学生信息</div>
			学生所属学院<select  id="selectA" style="width:120px;" required>
				<option value="0" disabled selected>请选择学院</option>
			</select>
			系别<select id="selectD" style="width:130px;" id="selectDept" name="s_dept" required>
				<option value="0" disabled selected>请选择系别</option>
			</select> <br>
			
			学生年级 <select id="selectG" name="s_grade" style="width:80px;" required>
				<option value="0" disabled selected>请选择年级</option>
			</select><br /> 
			学生学号<input type="text" name="s_no" id="s_no"  pattern="\w{8}[0-9]{3}" placeholder="输入学号的后三位编号" required/><br /> 
				<div id="divInfo" style="font-size:18px; color: green; display:none;">请务必接着已有内容输入后三位编号！</div>
			学生姓名 <input type="text" name="s_name" id="s_name" placeholder="输入姓名" required/><br /> 
			默认密码 <input type="text" name="s_password" id="s_password" value="ahu123456" readonly/><br /> 
			<input type="submit"value="提交" /> 
			<input type="reset" value="重置" />
			 <div id="info" style=" color: green;">${empty info?"":info }</div>
		</form>
		</div>
</body>
</html>