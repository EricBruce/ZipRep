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
	    url: "${ctx}/product/seltrend.do",
	    pageSize:10,
	    singleSelect:true,
	    columns:[[
	        {field:'opt_time',title:'时间',width:100},
	        {field:'sell_amount',title:'销量',width:100}
	    ]],
	    onLoadSuccess:function(data) {
	    	var optTime = new Array();
	    	var sellAmount = new Array();
	    	//折线图
	    	$.each(data.rows, function(id,rowData){
	    			optTime.push(rowData.opt_time);
	    			sellAmount.push(rowData.sell_amount);
	    	});
	    	chart.xAxis[0].setCategories(optTime);
	    	chart.series[0].setData(sellAmount);
	    }
	});
	
	chart = new Highcharts.Chart({  
			  chart: {  
			          renderTo: 'container',  
			          defaultSeriesType: 'line',  
			          marginRight: 130,  
			          marginBottom: 50
			        },
			title: {
	          text: '销量统计图表',
	          x: -20 //center
	      },
	      subtitle: {
	          text: 'Source: repo.com',
	          x: -20
	      },
	      xAxis: {
	          categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	      },
	      yAxis: {
	          title: {
	              text: '销量(件)'
	          },
	          plotLines: [{
	              value: 0,
	              width: 1,
	              color: '#808080'
	          }]
	      },
	      tooltip: {
	          valueSuffix: '°C'
	      },
	      legend: {
	          layout: 'vertical',
	          align: 'right',
	          verticalAlign: 'middle',
	          borderWidth: 0
	      },
	      series: [{
	          name: '销量',
	          data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
	      }]  
	   }); 
	
	$("a[id='catType']").mouseover(function() {
		//重新加载表格
		$('#dg').datagrid('load',{
			catType: $(this).attr("catType")
		});
		//重新加载图表
		
	});
});
</script>
</head>
<body>
<a id="catType" catType="1" href="javascript:void(0)" class="easyui-linkbutton" >查看最近一星期销量图表</a>
<a id="catType" catType="2" href="javascript:void(0)" class="easyui-linkbutton" >查看最近一月销量图表</a>
<a id="catType" catType="3"  href="javascript:void(0)" class="easyui-linkbutton" >查看最近一年销量图表</a>
<div id="container" style="width: 98%; "></div>
<table id="dg"></table>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/modules/exporting.js"></script>
</body>
</html>