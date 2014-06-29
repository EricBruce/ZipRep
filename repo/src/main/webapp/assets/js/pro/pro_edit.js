/**
 * 
 */
$(function(){
	$.ctx = function(ctx){
	//颜色选择
	$("input[id='colorCk']").click(function(){
		var c_id = $(this).val();
//		alert($(this).prop("checked"))
		if ($(this).is(':checked')) {
			var selCHtm = "<tr id="+ c_id +">" +
			"<td><span>"+ $(this).parent().find("span").text() +"</span></td>" +
			"<td><input type='text' style='width: 100px;' name='price'></td>" +
			"<td><input type='text'' style='width: 100px;' name='num'></td>" +
			"</tr>" ;
			$("#selColorTbl").append(selCHtm);
		} else {
			$("#selColorTbl").find("tr[id='"+c_id+"']").remove();
		}
	});
	
	//正则校验
	//数量
	$("input[name='num']").live('blur', function(){
		var num = $(this).val();
		if (!Validator.isNumber(num, true)) {
			alert("数量不能为空且必须为整数");
			$(this).focus();
			return false;
		}
		//总数
		var amount = 0;
		$("input[name='num']").each(function(){
			if (Validator.isNumber($(this).val(), true)) {
				amount += parseInt($(this).val());
			}
		});
		$("input[name='amount']").val(amount);
	});
	//价格校验
	$("input[name='price']").live('blur', function(){
		var price = $(this).val();
		if (!Validator.isDouble(price, true)) {
			alert("价格不能为空且必须为浮点数");
			$(this).focus();
			return false;
		}
	});
	
	//宝贝名称
	$("input[name='productName']").keyup(function() {
		var maxLen = 100;
		var len = $(this).val().length;
		$("#nmSpan").text(maxLen - len);
	});
	$("input[name='productName']").blur(function() {
		var maxLen = 100;
		var len = $(this).val().length;
		$("#nmSpan").text(maxLen - len);
	});
	
	//一口价
	$("input[name='productPrice']").blur(function() {
		var price = $(this).val();
		if (!Validator.isDouble(price, true)) {
			alert("一口价不能为空且必须为浮点数");
			$(this).focus();
			return false;
		}
	});
	
	//报警值
	$("input[name='productAlarm']").blur(function(){
		var num = $(this).val();
		if (!Validator.isNumber(num, false)) {
			alert("报警值必须为整数");
			$(this).focus();
			return false;
		}
	});
	
	//保持宝贝
	$("#save").click(function() {
		var productID = $("input[name='productID']").val();
		var productName = $("input[name='productName']").val();
		var productPrice = $("input[name='productPrice']").val();
		var spec = "";
		$("#selColorTbl").find("tr:gt(0)").each(function() {
			spec += $(this).attr("id") + "_";
			spec += $(this).find("span").text()+"_";
			spec += $(this).find("input[name='price']").val()+"_";
			spec += $(this).find("input[name='num']").val()+"|";
		});
		if (spec != "") {
			spec = spec.substring(0, spec.length-1);
		}
		var productAlarm = $("input[name='productAlarm']").val();
		var productNo = $("input[name='productNo']").val();
		var productImg = $("input[name='productImg']").val();
		//提交表单
		$("#proForm").attr("action", ctx +"/product/edit.do");
		$("#proForm").attr("method", "post");
		$("#proForm").append("<input name='product_id' type='hidden' value='"+ productID +"'>");
		$("#proForm").append("<input name='product_name' type='hidden' value='"+ productName +"'>");
		$("#proForm").append("<input name='product_price' type='hidden' value='"+ productPrice +"'>");
		$("#proForm").append("<input name='product_alarm' type='hidden' value='"+ productAlarm +"'>");
		$("#proForm").append("<input name='product_no' type='hidden' value='"+ productNo +"'>");
		if (productImg != "") {
			$("#proForm").append("<input name='product_img' type='hidden' value='"+ productImg +"'>");
		}
		$("#proForm").append("<input name='spec' type='hidden' value='"+ spec +"'>");
		$("#proForm").submit();
	});
	
	//返回
	$("#back").click(function() {
		history.go(-1);
	});
	
	//图片上传
	
	}
});

//图片上传
function ajaxFileUpload(ctx) {
	$.ajaxFileUpload({
         	url:  ctx + '/product/uploadImg.do', //需要链接到服务器地址
         	secureuri: false,
         	fileElementId: 'proImg', //文件选择框的id属性
         	dataType: 'json',  //服务器返回的格式类型
         	 //成功
         	success: function (data, status) {
         		var code = data.code;
	             if(code==1){
	            	 $.messager.alert("提示","上传成功！","info");
	            	 var fileName = data.filename;
	            	 $("input[name='productImg']").val(fileName);
	            	 $("img[name='productImg']").attr("src", ctx + "/" + fileName);
	             }else{
	            	 $.messager.alert("提示","上传失败！","warning");
	             }
         },
       //异常
         error: function (data, status, e) {
        	 $.messager.alert("提示","出错了，请重新上传！","error");
         }
     });
	return false;
}


function checkFileName(id) {
	var okText = /jpg|jpeg|gif|png/;// 这里是允许的扩展名
	var filename = id.value;// 得到当前file文件域的值
	var newFileName = filename.split('.'); // 这是将文件名以点分开,因为后缀肯定是以点什么结尾的.
	newFileName = newFileName[newFileName.length - 1];// 这个是得到文件后缀,因为split后是一个数组所以最后的那个数组就是文件的后缀名.newFileName.length为当前的长度，-1则为后缀的位置，因为是从0开始的
	// 开始检查后缀
	if (newFileName.search(okText) == -1) {// search如果没有刚返回-1.这个如果newFileName在okText里没有,则为不允许上传的类型.
		var err = okText.toString();// 将正则转为字符
		var errText = err.replace(/^\/|\/+(.*)/g, "");// 用正则替换 / 开头和 /i结束
		var errText = errText.replace(/\|/g, ",");// 用正则把 | 替换成 ,
		$("input[name='imptSbtBtn']").attr("disabled","disabled");
		alert("请上传" + errText + "图片格式的文件");// 提示
		return false;
	} else {
		$("input[name='imptSbtBtn']").removeAttr("disabled");
	}
}