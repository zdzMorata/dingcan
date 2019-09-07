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

article dl{
	width:250px;
	height:250px;
	float:left;
	margin-top:10px;
}
article dl dt{
	float:left;
	width:250px;
	height:150px;
}
article dl dt img{
	width:190px;
	height:200px;	 
}
article dl dd{
	float:left;
	margin-top:5px;
}
article dl dd.title{
	width:120px;
	font-weight:bold;
	margin-top:50px;
}
article dl dd.money{
	width:120px;
	color:red;
}
article dl dd.detail{
	width:120px;
}
article dl .link{
	width:200px;
}
article dl .link span{
	padding-left:30px;
}
article dl .link a{
	margin-left:5px;
}
article dl .link a:last-child{
	margin-left:10px;
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
					"text" : "点菜",
					"attributes" : {
						"url" : "<iframe width='100%' height='100%' src='diancai.jsp'></iframe>"
					}
				} ];
	
		var resuserTreeData = [
		       				{
		       					"id" : 1,
		       					"text" : "套餐",
		       					"attributes" : {
		       						"url" : "<iframe width='100%' height='100%' src='taocan.jsp'></iframe>"
		       					}
		       				} ];
		
		showTree('resfoodTree', resfoodTreeData);//菜品管理树 
		showTree('resuserTree', resuserTreeData);//套餐管理树 
		paiHang();
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
	
	function paiHang(){
		$.post("doPaiHang.action",{op:'PaiHang'},function(data){
			if(data!=null){
				showFood(data);
			}else{
				$.messager.alert("提示信息", "暂无推荐");
			}
		});
	}
	
	function showFood(data){
		var food =$.parseJSON(data);
		for (var i=0; i<food.length;i++ ){
			//alert(food[i]);
			$.post('doPaiHang.action',{op:'addPaiHangCart',cid:food[i]},function(data){
				var foods =$.parseJSON(data);
				var str ="";
				for(var i=0;i<foods.length;i++){
					str+='<dl><dt><img src="'+foods[i].cphoto+'" style="width:200px;height:200px;"/></dt><dd class="title">'+foods[i].cname+'</dd>'
					+'<dd class="money">现价:人名币'+foods[i].cprice+'元</dd><dd class="link">'
					+'</dd></dl>'
				}
				$('article').append(str);
			});
		}
	}
</script>
</head>
<body class="easyui-layout">
	
	<div data-options="region:'west'" style="width: 120px;">
		<div class="easyui-accordion" data-options="fit:true,width:'200px'">
			<div title="菜品" style="overflow: auto; padding: 10px;">
				<ul id="resfoodTree" class="easyui-tree">
				</ul>
			</div>
			<div title="套餐" style="overflow: auto; padding: 10px;">
				<ul id="resuserTree" class="easyui-tree">
				</ul>
			</div>
		</div>




	</div>
	<div data-options="region:'center'"  style="padding: 5px; background: #eee;">
		<div id="tt" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="菜品排行">
			<h1 style="color: red;width: 200px;">热销榜前10</h1>
			<a href="back/index.jsp" style="padding-left: 1200px">后台登录</a>
				<article>
				
				</article>
			</div>
		</div>
	</div>
	
	
</body>
</html>