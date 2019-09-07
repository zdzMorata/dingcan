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
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="back/easyui/easyui.css"/>
<link type="text/css" rel="stylesheet" href="back/easyui/icon.css"/>
<style type="text/css">
	li{
	list-style: none;
	}
	body{
	margin: 0 auto;
	width: 800px;
	}
</style>
<script type="text/javascript" src="back/easyui/jquery.min.js"></script>
<script type="text/javascript" src="back/easyui/jquery.easyui.min.js"></script>
<!-- 支持中文 -->
<script type="text/javascript" src="back/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
<table id="resfood_show"></table>

<div id="dlg" class="easyui-dialog" style="width: 500px; height: 300px; padding: 10px 20px;" closed="true" buttons="#dlg-buttons"> 
	<form id="fm" method="post" enctype="multipart/form-data">
		<div class="fitem"> 
            <label for="caixi">菜系</label>
            <select id="caixi" >
                <option>徽菜</option>
                <option>苏菜</option>
                <option>粤菜</option>
                <option>川菜</option>
                <option>浙菜</option>
                <option>鲁菜</option>
                <option>湘菜</option>
                <option>闽菜</option>
            </select>
		</div> 
		<div class="fitem"> 
			菜名<input id="cname" name="cname" class="easyui-validatebox"/> 
		</div>  
		
		<div class="fitem"> 
			<label for="leixi">类系</label>
            <select id="leixi" >
                <option>其他类</option>
                <option>肉类</option>
                <option>海鲜类</option>
                <option>鱼类</option>
            </select>
		</div>
		
		<div class="fitem"> 
			本金<input id="lirun" name="lirun" class="easyui-validatebox"/> 
		</div> 
		
		<div class="fitem"> 
			价格<input id="cprice" name="cprice"  class="easyui-validatebox"/> 
			<button onclick="javascript:doDingJia()" type="button">智能定价</button>
		</div>
		<div class="fitem">
			预测销量<input id="xiaoliang" name="xiaoliang" disabled="disabled"  class="easyui-validatebox"/> 
			<button onclick="javascript:doXiaoLiang()" type="button">预测销量</button>
		</div>
		
		<div class="fitem"> 
			照片<input id="cphoto" type="file",name="cphoto" class="easyui-vlidatebox"/> 
		</div>
	</form>
</div>

	<div id="dlg-buttons"> 
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" iconcls="icon-save">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"
			iconcls="icon-cancel">取消</a> 
	</div>

<div id="dllg" class="easyui-dialog" style="width: 500px; height: 300px; padding: 10px 20px;" closed="true" buttons="#dlg-buttons"> 
	<form id="fmm" method="post" enctype="multipart/form-data">
		<div class="fitem"> 
			菜系<input id="caixi" name="caixi" class="easyui-validatebox"/> 
		</div> 
		<div class="fitem"> 
			菜名<input id="cname" name="cname" class="easyui-validatebox"/> 
		</div> 
		<div class="fitem"> 
			价格<input id="cprice" name="cprice"  class="easyui-validatebox"/> 
		</div> 
		<div class="fitem"> 
			照片<input id="cphoto" type="file",name="cphoto" class="easyui-vlidatebox"/> 
		</div> 
		
	</form>
</div>

	<div id="dlg-buttons"> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveuser()" iconcls="icon-save">保存</a> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dllg').dialog('close')"
	            iconcls="icon-cancel">取消</a> 
	</div>

<script type="text/javascript">
	$("#resfood_show").datagrid({
		url:'resfood.action',
		queryParams:{op:'find'},
		toolbar:[{
			iconCls:'icon-add',
			handler:function(){
				addFood();
			}
		},
		{
			iconCls:'icon-edit',
			handler:function(){
				update();
			}
		},
		{
			iconCls:'icon-remove',
			handler:function(){
				remove();
			}
		},
		{
			iconCls:'icon-reload',
			handler:function(){
				$("#resfood_show").datagrid('reload');  //重新加载当前表格中的数据  获取最新数据
			}
		}],
		pagination:true, //分页工具栏
		pageList:[1,3,5,],  //每个显示的条数   可以动态改变的
		pageNumber:1,  //初始化显示的页码数
		pageSize:5,   //默认每页显示的条数
		columns:[[    
		          {field:'cid',title:'编号',width:100},    
		          {field:'caixi',title:'菜系',width:100}, 
		          {field:'cname',title:'菜名',width:100},
		          {field:'didan',title:'订单',width:100},
		          {field:'cprice',title:'价格',width:100,align:'right'},
		          {field:'cphoto',title:'图片',width:150,align:'center',formatter:function(value,row,index){
		        	  if(''==value || null==value){
		        		  return '未上传图片';
		        	  }else{
		        		  return '<img src="'+value+'" width="150" />';
		        	  }
		          }}
		      ]]   
	});
	
	function addFood(){
		$("#dlg").dialog("open").dialog('setTitle','新增菜品');
		$("#fm").form("clear");
	};
	
	function update(){
    	var row = $('#resfood_show').datagrid('getSelected');
    	if(row){
    		$("#dllg").dialog("open").dialog('setTitle', '更新菜品');
    		$("#fmm").form("clear");
    		$("#fmm").form("load", row);
    		url = "resfood.action?op=update&cid=" + row.cid;
    		console.log(row.cid);
    		localStorage.setItem("cid",row.cid);
    	}
    	
    }
	
	function save() {
        $("#fm").form("submit", {
            url: "resfood.action?op=add",
            
            success: function (result) {
                if (result) {
                    $.messager.alert("提示信息", "操作成功");
                    $("#dlg").dialog("close");
                    $("#dg").datagrid("load");
                    $("#resfood_show").datagrid('reload');
                }else {
                    $.messager.alert("提示信息", "操作失败");
                }
            }
            
        });
    }

	function saveuser() {
		var cid = localStorage.getItem("cid");
		console.log("-----"+cid);
   		$("#fmm").form("submit", {
            url: "resfood.action?op=update&cid="+cid,
            success: function (result) {
                if (result) {
                    $.messager.alert("提示信息", "修改成功");
                    $("#dllg").dialog("close");
                    $("#dg").datagrid("load");
                    $("#resfood_show").datagrid('reload');
                }else {
                    $.messager.alert("提示信息", "操作失败");
                }
            }
        });
   }
	
	function remove() {
        var row = $('#resfood_show').datagrid('getSelected');
        if (row) {
            $.messager.confirm('Confirm', '您确认删除吗?', function (r) {
                if (r) {
                    $.post('resfood.action?op=del'+"&cid="+row.cid, function (json) {
                    		 $.messager.alert("提示信息", "删除成功");
                    		 $("#dlg").dialog("close");
                    		 $("#dg").datagrid("load");
                    		 $("#resfood_show").datagrid('reload');
                    });
                }
            });
        }else{
       	 $.messager.alert("提示信息", "请选择要删除的数据");
        }
    } 
	
	
	function doDingJia(){
		var caixi=$("#caixi").val();
		var leixi=$("#leixi").val();
		var lirun=$("#lirun").val();
		console.log(caixi+"==="+leixi+"==="+lirun);
		$.post('price.action',{op:'doPrice',caixi:caixi,leixi:leixi,lirun:lirun},function(data){
			$("#cprice").val(data);
		});
	}
	
	function doXiaoLiang(){
		var caixi=$("#caixi").val();
		var leixi=$("#leixi").val();
		var cname=$("#cname").val();
		var price = $("#cprice").val();
		$.post('xiaoliang.action',{op:'doXiaoLiang',caixi:caixi,leixi:leixi,cname:cname,price:price},function(data){
			$("#xiaoliang").val(data);
		});
	}

</script>

</body>
</html>