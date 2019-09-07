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
			菜系<input id="caixi" name="caixi" class="easyui-validatebox"/> 
		</div> 
		<div class="fitem"> 
			套餐名<input id="tname" name="tname" class="easyui-validatebox"/> 
		</div> 
		<div class="fitem"> 
			菜名<input id="cname" name="cname"  class="easyui-validatebox"/> 
		</div> 
		
		<div class="fitem"> 
			宴席<input id="yanxi" name="yanxi" class="easyui-validatebox"/> 
		</div>
		
		<div class="fitem"> 
			照片<input id="tphoto" type="file",name="tphoto" class="easyui-vlidatebox"/> 
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
			套餐名<input id="tname" name="tname" class="easyui-validatebox"/> 
		</div> 
		<div class="fitem"> 
			菜名<input id="cname" name="cname"  class="easyui-validatebox"/> 
		</div> 
		
		<div class="fitem"> 
			宴席<input id="yanxi" name="yanxi" class="easyui-validatebox"/> 
		</div>
		
		<div class="fitem"> 
			照片<input id="tphoto" type="file",name="tphoto" class="easyui-vlidatebox"/> 
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
		url:'TaoCan.action',
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
		          {field:'tid',title:'编号',width:100},    
		          {field:'tname',title:'套餐名',width:100}, 
		          {field:'cname',title:'菜名',width:100},
		          {field:'tprice',title:'价格',width:100},
		          {field:'caixi',title:'菜系',width:100,align:'right'},
		          {field:'yanxi',title:'宴席',width:100,align:'right'},
		          {field:'tphoto',title:'图片',width:150,align:'center',formatter:function(value,row,index){
		        	  if(''==value || null==value){
		        		  return '未上传图片';
		        	  }else{
		        		  return '<img src="'+value+'" width="150" />';
		        	  }
		          }}
		      ]]   
	});
	
	function addFood(){
		$("#dlg").dialog("open").dialog('setTitle','新增套餐');
		$("#fm").form("clear");
	};
	
	function update(){
    	var row = $('#resfood_show').datagrid('getSelected');
    	if(row){
    		$("#dllg").dialog("open").dialog('setTitle', '更新套餐');
    		$("#fmm").form("clear");
    		$("#fmm").form("load", row);
    		url = "TaoCan.action?op=update&tid=" + row.tid;
    		console.log(row.tid);
    		localStorage.setItem("tid",row.tid);
    	}
    	
    }
	
	function save() {
        $("#fm").form("submit", {
            url: "TaoCan.action?op=add",
            
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
		var tid = localStorage.getItem("tid");
		console.log("-----"+tid);
   		$("#fmm").form("submit", {
            url: "TaoCan.action?op=update&tid="+tid,
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
                    $.post('TaoCan.action?op=del'+"&tid="+row.tid, function (json) {
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

</script>

</body>
</html>