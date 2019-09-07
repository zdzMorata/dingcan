<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=path%>">
<meta charset="UTF-8">
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="back/easyui/easyui.css" />
<link type="text/css" rel="stylesheet" href="back/easyui/icon.css" />
<style type="text/css">
ul, li {
	list-style: none;
}

a {
	text-decoration: none;
}

.header {
	height: 100px;
	background: url(back/images/main_bg.jpg) no-repeat 0px 0px;
}

.header ul {
	padding-top: 70px;
	height: 30px;
}

.header>ul>li {
	float: right;
	height: 30px;
	line-height: 30px;
	margin-right: 200px;
}

.header>ul>li:last-child {
	margin-right: 50px;
}

.header>ul>li>a {
	color: #F60;
	font-weight: bold;
}

.header>ul>li>a:hover {
	color: #FF0;
}

</style>
<script type="text/javascript" src="back/easyui/jquery.min.js"></script>
<script type="text/javascript" src="back/easyui/jquery.easyui.min.js"></script>
<!-- 支持中文 -->
<script type="text/javascript" src="back/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		var resfoodTreeData = [
				{
					"id" : 1,
					"text" : "菜品管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/foodfind.jsp'></iframe>"
					}
				} ];
		var resuserTreeData = [
				{
					"id" : 1,
					"text" : "套餐管理",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='back/packagefind.jsp'></iframe>"
					}
				} ];
		

		
		showTree('resfoodTree', resfoodTreeData);//菜品管理树 
		showTree('resuserTree',resuserTreeData);//套餐管理树 
		
	});

	function showTree(treeId, treeData) {
		$("#" + treeId).tree({
			data : treeData,
			onClick : function(node) {
				if(node &&node.attributes){
					openTab(node);
				}
			}
		});
	}
	
	function openTab(node){
		if( $('#tt').tabs("exists",node.text)){
			$('#tt').tabs("select",node.text);
		}else{
			$('#tt').tabs("add",{
				title:node.text,
				content:node.attributes.url,
				selected:true,
				closable:true,
				
			});
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 40px;">
		<a href="ResIndex.jsp" style="float: right; padding-right: 40px;padding-top: 10px;">返回点菜界面</a>
	</div>
	<div data-options="region:'west'" style="width: 120px;">
		<div class="easyui-accordion" data-options="fit:true,width:'200px'">
			<div title="菜品管理" style="overflow: auto; padding: 10px;">
				<ul id="resfoodTree" class="easyui-tree">
				</ul>
			</div>
			<div title="套餐管理" style="overflow: auto; padding: 10px;">
				<ul id="resuserTree" class="easyui-tree">
				</ul>
			</div>
		</div>




	</div>
	<div data-options="region:'center'"  style="padding: 5px; background: #eee;">
		<div id="tt" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="welcome" style="padding: 20px; display: none;">欢迎进入后台管理:</div>
		</div>
	</div>
</body>
</html>