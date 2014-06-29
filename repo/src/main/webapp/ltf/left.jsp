<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/assets/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	$('#tt').tree({
		/* method:'GET',
		url:'${ctx}/assets/include/menu.json', */
		onClick: function(node){
			parent.main.location.replace(node.attributes.url);
		}
	});
});

</script>
</head>
<body>
<ul id="tt" class="easyui-tree">
    <li data-options="iconCls:'icon-add',attributes:{'url':'${ctx}/product/list.do'}">
        <span>库存管理</span>
        <ul>
            <li data-options="iconCls:'icon-remove',attributes:{'url':'xxxxx'}">
                <span>编辑宝贝</span>
            </li>
            <li data-options="iconCls:'icon-remove',attributes:{'url':'${ctx}/product/pronew.do'}">
                <span>新建宝贝</span>
            </li>
            <li data-options="iconCls:'icon-remove',attributes:{'url':'${ctx}/main/pro_list_trend.jsp'}">
                <span>总趋势图</span>
            </li>
            <li data-options="iconCls:'icon-remove',attributes:{'url':'${ctx}/main/pro_sell_record.jsp'}">
                <span>销售记录</span>
            </li>
            <li data-options="iconCls:'icon-remove',attributes:{'url':'${ctx}/main/pro_sell_trend.jsp'}">
                <span>销量分类统计图</span>
            </li>
        </ul>
    </li>
    <li data-options="iconCls:'icon-add',attributes:{'url':'xxxxx'}">
    	<span>用户管理</span>
   	</li>
</ul>
</body>
</html>