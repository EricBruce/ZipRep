<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/assets/include/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建宝贝</title>
</head>
<body style="background-color: rgb(17, 63, 52);">
	<div class="roundcorner" style="background-color: lightslategrey;padding: 10px;padding-left: 10%;">
		<p align="center" style="font-weight: bolder;">宝贝基本信息</p>
		<table style="border-spacing: 20px;">
			<tr>
				<td align="right">
					<span style="font-weight: bolder; color: red;">*</span>宝贝名称:
				</td>
				<td align="left">
					<input name="productID" type="hidden" size="100" value="${product.product_id }"> 
					<input name="productName" type="text" size="100" value="${product.product_name }"> 
					<span>
						还能输入<span id="nmSpan" style="font-weight: bolder; color: lightgreen;">100</span>字
					</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<span style="font-weight: bolder; color: red;">*</span>
					一口价：
				</td>
				<td> 
					<input name="productPrice" type="text" value="${product.product_price }">元
				</td>
			</tr>
			<tr>
				<td align="right" style="vertical-align: top;">
					宝贝规格：
				</td>
				<td align="left">
					<div style=" background-color: lightcyan;" class="roundcorner">
					<div style="width: 500px; background-color: #626962;display:inline-block;-webkit-border-radius:6px;-moz-border-radius:6px; ">
						<table>
						<c:forEach var="c" items="${list }" varStatus="i">
							<c:if test="${i.count mod 3 eq 1 }">
								<tr>
							</c:if>
								<td>
									<div style="padding: 10px;float: left;">
										<input id="colorCk" type="checkbox" value="${c.color_id }">
										<img style="height: 25px; width: 25px; background-color: ${c.RGB};"/>
										<span>${c.color_name }</span>
									</div>
								</td>
								<c:if test="${i.count mod 3  eq 0 }">
									</tr>
								</c:if>
						</c:forEach>
						</table>
					</div>
					<div id="specDiv">
						<table id="selColorTbl" class="mytable" >
							<tr style="background-color: lightblue;">
								<td align="center" style="width: 100px;">颜色分类</td>
								<td align="center" style="width: 100px;">价格（元）<span style="color: red;">*</span></td>
								<td align="center" style="width: 100px;">数量（件）<span style="color: red;">*</span></td>
							</tr>
							<!-- <tr>
								<td><span>咖啡色</span></td>
								<td><input type="text" value="158.00"></td>
								<td><input type="text" value="12"></td>
							</tr> -->
						</table>
					</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right">
					<span style="font-weight: bolder; color: red;">*</span>
					宝贝数量：
				</td>
				<td align="left">
					<input name="amount" type="text" readonly="readonly">件
				</td>
			</tr>
			<tr>
				<td align="right">报警值：</td>
				<td align="left"><input name="productAlarm" type="text" value="${product.product_alarm }"></td>
			</tr>
			<tr>
				<td align="right">商品编码：</td>
				<td align="left"><input name="productNo" type="text" value="${product.product_no }"></td>
			</tr>
			<tr>
				<td align="right" style="vertical-align: top;">宝贝图片:</td>
				<td align="left">
					<div id="upPic" class="roundcorner" align="center" style="width: 100%;background-color: lightcyan;">
						<form id="proImgForm" enctype="multipart/form-data" method="post">
						</form>
							<div style="padding: 10px;">
								选择本地图片： <input id="proImg" name="proImg" type="file" value="文件上传" onchange="checkFileName(this)">
								<!-- <input name="imptSbtBtn" type="submit" value="确定上传"> -->
								<a href="javascript:void(0)" onclick="ajaxFileUpload('${ctx}')" class="easyui-linkbutton"  iconCls="icon-ok">上传</a>
							</div>
						<div style="padding: 10px;">
							图片预览：
							<img name="productImg" src="${ctx }/${product.product_img }" class="roundcorner" height="100px" width="100px" >
							<input name="productImg" type="hidden" value="${product.product_img }">
						</div>
						<div style="font-size: 10px;color: lightslategrey;background-color: lightblue;">
							提示：图片支持的格式jpg/jpeg/gif
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<form id="proForm"></form>
					<input type="button" id="save"  value="保存宝贝">
					<input type="button"  id="back" value="返回">
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="${ctx }/assets/js/pro/pro_edit.js"></script>
	<script type="text/javascript" src="${ctx }/assets/js/util/validator.js"></script>
	<script type="text/javascript" src="${ctx }/assets/js/util/ajaxfileupload.js"></script>
	<script type="text/javascript">
		$(function(){
			//初始化规格
			specs =  eval('(' + '${proSpecs}' + ')');
			var count = 0;
			for (var i = 0; i < specs.length; i++) {
				/* var specID = specs[i].spec_id;
				$("input[id='colorCk'][value='"+ specID +"']").attr("checked", "checked"); */
				$("input[id='colorCk'][value='"+ specs[i].color_id +"']").attr("checked","checked");
				var selCHtm = "<tr id="+ specs[i].color_id +">" +
				"<td><span>"+ specs[i].color_name +"</span></td>" +
				"<td><input type='text' style='width: 100px;' name='price' value="+ specs[i].spec_price + "></td>" +
				"<td><input type='text'' style='width: 100px;' name='num' value="+ specs[i].spec_amount +"></td>" +
				"</tr>" ;
				$("#selColorTbl").append(selCHtm);
				count += specs[i].spec_amount;
			} 
			$("input[name='amount']").val(count)
			$.ctx("${ctx}");
			$("input[name='productName']").blur();
		});
	</script>
</body>
</html>