<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head> 
<style type="text/css">

article{
	width:1000px;
	float:left;
	margin-left:5px;
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
}
article dl dd{
	float:left;
	margin-top:5px;
}
article dl dd.title{
	width:120px;
	font-weight:bold;
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
<meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="easyui/demo.css">
    
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="back/easyui/easyui-lang-zh_CN.js"></script>
    
    <script type="text/javascript">
		$(function(){
			<%
				String leix = request.getParameter("leix");
				String caixi=request.getParameter("caixi");
				if (leix==null){
					leix="肉类";
				}
			%>
			var leix="<%=leix%>";
			var caixi="<%=caixi%>";
			if (leix!=null){
				$.post("diancai.action?leix="+leix,{op:'findLeiX'},function(data){
					var foods =$.parseJSON(data);
					console.log(foods);
					showFood(foods);
				});
			}
			if (caixi!=null){
				$.post("diancai111.action?caixi="+caixi,{op:'findCaiXi'},function(data){
					var foods =$.parseJSON(data);
					console.log(foods);
					showFood(foods);
				});
			}
		});
		
		
		// 添加购物车
		var count=0;
		function addCart(cid){
			$.post('diancai.action',{op:'addCart',cid:cid},function(data){
				var foods =$.parseJSON(data);
				console.log(foods);
				var str ="";
				for(var i=0;i<foods.length;i++){
					str+='<dl id="'+count+'" class="'+cid+'" style="width:200px;height:200px;"><dt style="width:150px;height:150px;">'
					+'<img src="'+foods[i].cphoto+'" style="width:150px;height:150px;"/></dt><dd class="title">'+foods[i].cname+'</dd>'
					+'<dd class="money">现价:人名币'+foods[i].cprice+'元</dd><dd class="link">'
					+'<button onclick="javascript:doDel('+count+','+cid+')">删除</button></dd></dl>'
				}
				$('article:first').append(str);
			});
			count+=1;
			$('article:last').empty();
			
			doTuiJian(cid);
		};
		
		var c=[];
		//显示推荐菜品
		function doTuiJian(cid){
			
			c.push(cid)
			var ss=unique(c);
			console.log(ss);
			$.post('TuiJian.action',{op:'tuijian',cids:ss},function(data){
				if(data!=null){
					doAddTuiJian(data);
				}else{
					$.messager.alert("提示信息", "暂无推荐");
				}
			});
		};
		
		
		function doAddTuiJian(data){
			var food =$.parseJSON(data);
			for (var i=0; i<food.length;i++ ){
				//alert(food[i]);
				$.post('TuiJian.action',{op:'addTuiJianCart',cid:food[i]},function(data){
					var foods =$.parseJSON(data);
					var str ="";
					for(var i=0;i<foods.length;i++){
						str+='<dl><dt><img src="'+foods[i].cphoto+'"/></dt><dd class="title">'+foods[i].cname+'</dd>'
						+'<dd class="money">现价:人名币'+foods[i].cprice+'元</dd><dd class="link">'
						+'<a href="javascript:addCart('+foods[i].cid+')"><img src="images/buy_cn.gif"/></a>'
						+'</dd></dl>'
					}
					$('article:last').append(str);
				});
			}
		}
		
		//删除购物车中的订单
		function doDel(count,cid){
			s =count.toString();
			var cidd = document.getElementById(""+count).getAttribute("class");
			for(var i=0; i<c.length; i++) {
			    if(c[i] == cidd) {
			    	c.splice(i, 1);
			    }
			    
			}
			console.log(s);
			$("#"+s).remove();
			console.log($("#"+s));
			doTuiJian()
		}
		
		
		
		
		//根据搜索框中输入的菜名搜索
		function selectFood(){
			var cname = document.getElementById("cname").value;
			$.post("diancai.action",{op:'selectFood',cname:cname},function(data){
				//console.log(data);
				var foods =$.parseJSON(data);
				console.log(foods);
				$('article').eq(-2).empty();
				showFood(foods);
			});
		};

		
		//显示在页面中的菜品信息
		function showFood(foods){
			var str ="";
			for(var i=0;i<foods.length;i++){
				str+='<dl><dt><img src="'+foods[i].cphoto+'"/></dt><dd class="title">'+foods[i].cname+'</dd>'
				+'<dd class="money">现价:人名币'+foods[i].cprice+'元</dd><dd class="link">'
				+'<a href="javascript:addCart('+foods[i].cid+')"><img src="images/buy_cn.gif"/></a>'
				+'</dd></dl>'
			}
			$('article').eq(-2).append(str);
		};
		
		
		//提交订单
		function sumbit(){
			var name = document.getElementById('name').value;
			var tel = document.getElementById('tel').value;
			var num = document.getElementById('num').value;
			console.log(count);
			var cc=[];
			for (var i=1;i<=count;i++){
				var cid = document.getElementById(""+i).getAttribute("class");
				cc.push(cid);
			}
			var ss=unique(cc);
			console.log(ss);
			$.post("add.action",{op:'addOrder',name:name,tel:tel,num:num,ss:ss},function(data){
				if(data=="success"){
					$.messager.alert("提示信息", "提交订单成功");
				}else{
					$.messager.alert("提示信息", "提交订单成功");
				}
			});
		}
		
		//对获取到的cid进行去重
		function unique(arr){
			var hash=[];
			for (var i = 0; i < arr.length; i++) {
			     if(hash.indexOf(arr[i])==-1){
			      	hash.push(arr[i]);
				}
			}
			return hash;
		}
		
	</script>
</head>
<body>
	

    <div id="cc" class="easyui-layout" style="width:1330px;height:700px;">
        <div data-options="region:'north'" style="height:70px">
        	<ul>
        		<span>用户名:<input type="text" id="name" class="name"></span>
        		<span>电话号码:<input type="tel" id="tel" class="tel"></span>
        		<span>桌数<input type="number" id="num" class="num"></span>
        		<span style="padding-left: 200px;"><button onclick="javascript:sumbit()">确认订单</button></span>
        	</ul>
        </div>
        
        
        
        <div data-options="region:'east'" title="购物车"style="width:200px;">
        	<div>
        		<article style="width: 150px;">
        		
        		</article>
        	</div>
        </div>
        
        
        
        <div data-options="region:'west'" title="分类" style="width:120px;">
        	<div id="select">
        		<input id="cname" type="text" class="cname" style="width: 120px;" placeholder="请输入菜品名称">
        		<span>
        			<button onclick="javascript:selectFood()">搜索</button>
        		</span>
        	</div>
        	<div>
        		<h3>类别</h3>
	        	<div id="class-title" title="类别">
	        		<div style="padding-left: 20px; padding-top:10px; float: left;">
	        			<a href="diancai.jsp?leix=肉类">肉类</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?leix=其他类">其他类</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?leix=鱼类">鱼类</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?leix=海鲜类">海鲜类</a>
	        		</div>
	        	</div>
        	</div>
        	<div>
        		<h3>菜系</h3>
	        	<div id="class-title" title="菜系">
	        		<div style="padding-left: 20px; padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=徽菜">徽菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=苏菜">苏菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=粤菜">粤菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=川菜">川菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=浙菜">浙菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=鲁菜">鲁菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=湘菜">湘菜</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="diancai.jsp?caixi=闽菜">闽菜</a>
	        		</div>
	        	</div>
        	</div>
	    </div>
        <div data-options="region:'center',title:'Center'">
        	<article>
        	
        	</article>
        </div>
        <div data-options="region: 'south'" style="height: 250px">
        	<article>
        	
        	</article>
        	
        </div>
    </div>
</body>
</html>