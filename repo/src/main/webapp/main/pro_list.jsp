<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/assets/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宝贝列表</title>
<script type="text/javascript" src="${ctx }/assets/js/pro/pro_list.js"></script>
<script type="text/javascript">
	$(function() {
		$.ctx("${ctx}");
		$('#productID').searchbox({
			searcher : function(value, name) {
				alert(value + "," + name)
			},
			menu : '#mm',
			prompt : "请输入商家编号"
		});
		$('#productName').searchbox({
			searcher : function(value, name) {
				alert(value + "," + name)
			},
			menu : '#ss',
			prompt : "请输入宝贝名称"
		});

		$('#dg').datagrid({
			toolbar : '#tb'
		});

		//设置分页控件  
		var p = $('#list_data').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,//每页显示的记录条数，默认为10  
			pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表  
			beforePageText : '第',//页数文本框前显示的汉字  
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		/*onBeforeRefresh:function(){ 
		    $(this).pagination('loading'); 
		    alert('before refresh'); 
		    $(this).pagination('loaded'); 
		}*/
		});
	});
</script>
</head>
<body>
	<hr>
	<!-- 搜索框 -->
	<div id="searchDiv">
		<input id="productID" style="width: 350px;"></input>
		<div id="mm" style="width: 120px">
			<div data-options="name:'productID',iconCls:'icon-ok'">商家编号</div>
		</div>
		<input id="productName" style="width: 350px;"></input>
		<div id="ss" style="width: 120px">
			<div data-options="name:'productName',iconCls:'icon-ok'">宝贝名称</div>
		</div>
	</div>
	<hr>
	<div id="listDiv">
		<div id="toolbar" style="padding: 5px;">
			<!-- <input type="checkbox">全选 -->
			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		</div>
		<c:forEach var="pro" items="${list }">
		<div style="padding-bottom: 10px;">
			<table style="width: 98%; border-spacing: 0px;">
				<tr style="background-color: lightblue;">
					<td colspan="6" align="left" style="padding: 5px;">
						<div style="float: left;">
							<input type="checkbox" pid="${pro.product_id }"> 
							商家编号：
							<span style="font-weight: bolder;">${pro.product_no }</span>
						</div>
						<div style="float: right; padding-right: 20px;">
							<a href="${ctx}/product/proedit.do?productID=${pro.product_id}">编辑宝贝</a>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap">
						<table style="width: 100%; background-color: white;">
							<tr>
								<td style="width: 20%; padding-left: 20px;">
									<div style="float: left;">
										<img src="${ctx }/${pro.product_img}" height="100px" width="100px"
											style="-webkit-border-radius: 6px; -moz-border-radius: 6px; cursor: pointer;">
									</div>
								</td>
								<td style="width: 30%">
									<div style="float: left;">
										<div style="font-weight: bold; color: blue; padding-bottom: 10px; cursor: pointer;">
											${pro.product_name }
										</div>
										<div style="padding: 5px;">
											<img alt="" src="" height="15px" width="15px"> 
											<span style="font-size: 12px; color: grey;">${pro.colorname }</span>
										</div>
									</div>
								</td>
								<td style="width: 12%"><span style="font-size: large;color: chocolate;">${pro.sell_amount eq null ? 0 : pro.sell_amount }</span></td>
								<td style="width: 12%"><span style="font-size: larger;color: orangered;">${pro.all_amount eq null ? 0 : pro.all_amount }</span></td>
								<td style="width: 12%"><span style="color: green;">${pro.product_price }</span>&nbsp;元</td>
								<td style="width: 12%">
									<div align="center" style="padding: 10px">
										<input type="button" class="icon-add" name="addPro" productID="${pro.product_id}" style="width: 25px; height: 25px;"></input>
									</div>
									<div align="center">
										<input type="button" class="icon-remove" name="reducePro" productID="${pro.product_id}" style="width: 25px; height: 25px;"></input>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</c:forEach>
	</div>
	<div id="dd" class="easyui-dialog" title="入库操作" style="width:400px;height:200px;"
	        data-options="iconCls:'icon-save',resizable:true,modal:true,buttons:'#bb',closed: true">
	   	<table id="inSpecTbl">
		</table>
	</div>
	<div id="bb">
		<a id="inSave" href="#" class="easyui-linkbutton">Save</a>
		<a id="inClose" href="#" class="easyui-linkbutton">Close</a>
	</div>
	<div id="ee" class="easyui-dialog" title="出库操作" style="width:400px;height:200px;"
	        data-options="iconCls:'icon-save',resizable:true,modal:true,buttons:'#cc',closed: true">
	   	<table id="outSpecTbl">
		</table>
	</div>
	<div id="cc">
		<a id="outSave" href="#" class="easyui-linkbutton">Save</a>
		<a id="outClose" href="#" class="easyui-linkbutton">Close</a>
	</div>
</body>
</html>