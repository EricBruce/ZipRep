<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/assets/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有宝贝趋势图</title>
<script type="text/javascript">
$(function() {
	$('#dg').datagrid({
		 /* title: "总趋势图",  */
	    url: '${ctx}/product/trend.do',
	    pageSize:10,
	    pagination:true,
	    singleSelect:true,
	    columns:[[
	        {field:'product_id',title:'productID',hidden:true},
	        {field:'product_name',title:'宝贝名称',width:100},
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
			}},
			 {field:'product_price',title:'价格',width:100,formatter: function(value,row,index){
					if (value){
						return value+"元";
					} else {
						return "--";
					}
				}},
				{field:'oper',title:'操作',width:100,formatter: function(value,row,index){
					return "<a href='${ctx}/main/pro_trend.jsp?productID="+row.product_id+"'>点击查看详情</a>";
				}}
	    ]],
	    onLoadSuccess: function(data) {
	    	var sum = 0;
	    	var allA = new Array();
	    	var sellA = new Array();
	    	var proA = new Array();
	    	//总数
	    	//柱状图
	    	$.each(data.rows, function(id,rowData){  
	    		var sellAmount = rowData.sell_amount==null ? 0 : rowData.sell_amount;
	    		var allAmount = rowData.all_amount==null ? 0 : rowData.all_amount;
	    		allA.push(allAmount);
	    		sellA.push(sellAmount);
	    		var pro= rowData.product_name;
	    		proA.push(pro);
	    		sum += allAmount;
	    	});
	    	chartB.series[0].setData(allA);
	    	chartB.series[1].setData(sellA);
	    	chartB.xAxis[0].setCategories(proA);
	    	//饼图
	    	var pd = new Array();
	    	if (sum != 0) {
	    		//比率
		    	$.each(data.rows, function(id,rowData){  
		    		var sellAmount = rowData.sell_amount;
		    		if (sellAmount != null && sellAmount != 0) {
			    		pd.push([rowData.product_name, parseFloat(sellAmount)/sum]);
		    		}
		    	}); 
	    	}
	    	chartA.series[0].setData(pd);
	    }
	});
	
	chartA = new Highcharts.Chart({  
	      chart: {  
	         renderTo: 'containerA',  
	         plotBackgroundColor: null,  
	         plotBorderWidth: null,  
	         plotShadow: false  
	      },  
	      title: {  
	         text: '宝贝销量分配图表',
	         style:{
             	color: '#3E576F',
            	fontSize: '26px'
            }
	      },  
	      tooltip: {  
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	      },  
	      plotOptions: {  
	         pie: {  
	            allowPointSelect: true,  
	            cursor: 'pointer',  
	            dataLabels: {  
	               enabled: true,  
	               color: '#000000',  
	               connectorColor: '#000000',  
	               format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	            }  
	         }  
	      },  
	       series: [{  
	         type: 'pie',  
	         name: '分量',  
	         data: []  
	      }]  
	   }); 
	chartB = new Highcharts.Chart({  
        chart: {
        	 renderTo: 'containerB', 
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
<div style="display: inline-table;width: 100%;">
<div id="containerB" style="width:68%;float: left;"></div>
<div id="containerA" style="width:30%;float: left;"></div>
</div>
<table id="dg" ></table>
<a id="sellSearchA" href="${ctx}/main/pro_sell_trend.jsp" class="easyui-linkbutton">点击查看销量详情</a>

<script type="text/javascript" src="${ctx }/assets/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx }/assets/js/highcharts/modules/exporting.js"></script>
</body>
</html>