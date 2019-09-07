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
				String yanxi = request.getParameter("yanxi");
				if (yanxi==null){
					yanxi="囍宴";
				}
			%>
			var yanxi="<%=yanxi%>";
			if (yanxi!=null){
				$.post("taocan.action?yanxi="+yanxi,{op:'findYanXi'},function(data){
					var foods =$.parseJSON(data);
					console.log(foods);
					showFood(foods);
				});
			}
			
		});
	    
	    
	    
	    
	  //显示在页面中的套餐信息
		function showFood(foods){
			var str ="";
			for(var i=0;i<foods.length;i++){
				str+='<dl><dt><img src="'+foods[i].tphoto+'"/></dt><dd class="title">'+foods[i].tname+'</dd>'
				+'<dd class="money">现价:人名币'+foods[i].tprice+'元</dd><dd class="link">'
				+'<a href="javascript:addCart('+foods[i].tid+')"><img src="images/buy_cn.gif"/></a>'
				+'</dd></dl>'
			}
			$('article').eq(-2).append(str);
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
        	<div>
        		<h3>宴席类别</h3>
	        	<div id="class-title" title="宴席">
	        		<div style="padding-left: 20px; padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=囍宴">囍宴</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=百岁宴">百岁宴</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=寿宴">寿宴</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=川菜">婚宴</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=家宴">家宴</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=升学宴">升学宴</a>
	        		</div>
	        		<div style="padding-left: 20px;padding-top:10px; float: left;">
	        			<a href="taocan.jsp?yanxi=谢师宴">谢师宴</a>
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
</body>
</html>