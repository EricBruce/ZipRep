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
		title: "宝贝趋势图", 
	    url: "${ctx}/product/"+ window.productID +"/trend.do",
	    pageSize:10,
	    pagination:true,
	    singleSelect:true,
	    columns:[[
	        {field:'color_name',title:'宝贝颜色',width:100},
	        {field:'all_amount',title:'库存',width:100,formatter: function(value,row,index){
				if (value){
					return value;
				} else {
					return 0;
				}
			}},
	        {field:'sell_amount',title:'销量',width:100,formatter: function(value,row,index){
				if (value){
					return value;
				} else {
					return 0;
				}
			}}
	    ]],
	    onLoadSuccess: function(data) {
	    	var allA = new Array();
	    	var sellA = new Array();
	    	var colorA = new Array();
	    	$.each(data.rows, function(id,rowData){  
	    		var sellAmount = rowData.sell_amount==null ? 0 : rowData.sell_amount;
	    		var allAmount = rowData.all_amount==null ? 0 : rowData.all_amount;
	    		allA.push(allAmount);
	    		sellA.push(sellAmount);
	    		var color = rowData.color_name;
	    		colorA.push(color);
	    	}); 
	    	chart.series[0].setData(allA);
	    	chart.series[1].setData(sellA);
	    	chart.xAxis[0].setCategories(colorA);
	    }
	});
	
	chart = new Highcharts.Chart({  
		        chart: {
		        	 renderTo: 'container', 
	                type: 'column'
	            },
	            title: {
	                text: '宝贝库存及销量'
	            },
	            subtitle: {
	                text: 'Source: repo.com'
	            },
	            xAxis: {
	                categories: [
	                    '红色',
	                    '白色',
	                    '黑色'
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: '数量(件)'
	                }
	            },
	            tooltip: {
	                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                    '<td style="padding:0"><b>{point.y:.0f} 件</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: '库存',
	                data: [49, 71, 106],
	            	color: 'orange'
	            }, {
	                name: '销量',
	                data: [83, 78, 98]
	            }]
	   }); 
});
</script>
</head>
<body>
<div id="container" style="width:98%;"></div>
<table id="dg"></table>
<%-- <a id="sellSearchA" href="${ctx}/product/<%=request.getParameter("productID")%>/seltrend.do" class="easyui-linkbutton">点击查看宝贝销量详情</a> --%>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/modules/exporting.js"></script>
</body>
</html>