<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/assets/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
</head>
<body>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:70px;background-color: rgb(169, 250, 205);">
    	<iframe id="top" src="${ctx}/ltf/top.jsp" style="width: 100%;height:100%;border: 0;"></iframe>
    </div>
    <!-- <div data-options="region:'east',title:'East'" style="width:100px;"></div> -->
    <div data-options="region:'west',title:'SiteMap'" style="width:200px;">
    	<iframe id="left" src="${ctx}/ltf/left.jsp" style="width: 100%;height:100%;border: 0;"></iframe>
    </div>
    <div data-options="region:'center'" style="padding:5px;background:#eee;">
    	<iframe id="main" style="width: 100%;height:100%;border: 0;"></iframe>
    </div>
    <div data-options="region:'south',border:false" style="height:50px;background-color: rgb(169, 250, 205);">
    	<iframe id="footer" src="${ctx}/ltf/footer.jsp" style="width: 100%;height:100%;border: 0;"></iframe>
    </div>
</body>
</html>