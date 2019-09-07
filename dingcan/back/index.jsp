<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%
    String path =request.getScheme()+"://"+request.getServerName()+":"
    	+request.getServerPort()+request.getContextPath()+"/";
    %>
    
<!DOCTYPE html>
<html>
<head>
<base href="<%=path%>">
<meta charset="UTF-8">
<title>管理员登录</title>
<link type="text/css" rel="stylesheet" href="back/easyui/easyui.css"/>
<link type="text/css" rel="stylesheet" href="back/easyui/icon.css"/>
<style type="text/css">
*{
margin: 0 auto;
padding: 0;
font-size:14px;
}
li{
list-style: none;
text-align: center;
width:300px;
height:30px;
margin-top: 10px;
}
li:first-child{
	height:80px;
}
body{
margin: 0 auto;
width:420px;
height: 450px;
background: #ccc;
}
#login{
	margin: 0 auto;
	width: 420px;
	height: 350px;
	position: relative;
	top:150px;
}

</style>
<script type="text/javascript" src="back/easyui/jquery.min.js"></script>
<script type="text/javascript" src="back/easyui/jquery.easyui.min.js"></script>
<!-- 支持中文 -->
<script type="text/javascript" src="back/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	 
<div id="login">
	<div class="easyui-panel" title="用户登录"  data-options="fit:true">
		<form id="login_form" method="post">
			<li><img src="back/images/login.jpg" height="60"/></li>
			<li>用户名： <input class="easyui-validatebox" data-options="required:true" name="oname"/></li>
			<li>密&nbsp;&nbsp;码： <input class="easyui-validatebox" type="password" name="opwd"/></li>
			<li><a class="easyui-linkbutton login" href="javascript:void(0)" style="width:150px">登    录</a></li>
		</form>	
	</div>
</div>
<script type="text/javascript">
	//提交表单
	$("#login a.login").click(function(){
		
		//获取用户名和密码
		var name= $("#login_form input[name=\"oname\"]").val();
		var pwd = $("#login_form input[name='opwd']").val();
		
		console.log(name+"----"+pwd);
		
		$("#login_form").form("submit",{
			url:'admin.action?op=login&name='+name+'&passwd='+pwd,
			onSubmit:function(){
				//表单提交时验证所有的文本框是否合法
				//return false 阻止表单提交
			},
			success:function(data){
				if('success'==data){
					window.location.href='back/main.jsp';
				}else{
					window.location.href='back/index.jsp';
				}
			}
		});
	});
</script>
	 
</body>
</html>