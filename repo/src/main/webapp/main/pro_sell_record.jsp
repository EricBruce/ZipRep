<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/assets/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宝贝趋势图</title>
<script type="text/javascript">
$(function() {
	window.productID = "<%=request.getParameter("productID")%>";
	$('#dg').datagrid({
		title: "销售记录", 
	    url: "${ctx}/product/selrec.do",
	    pageSize:10,
	    pagination:true,
	    singleSelect:true,
	    columns:[[
	        {field:'operation_at',title:'时间',width:100},
	        {field:'product_img',title:'图片',width:100,formatter: function(value,row,index){
				return "<img src='${ctx}/"+ value +"' height='100' width='100'>";
			}},
			{field:'product_name',title:'名称',width:100},
			{field:'color_name',title:'颜色',width:100},
	        {field:'rec_amount',title:'数量',width:100}
	    ]]
	});
});
</script>
</head>
<body>
<div id="container" style="width: 98%; "></div>
<table id="dg"></table>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/modules/exporting.js"></script>
</body>
</html>