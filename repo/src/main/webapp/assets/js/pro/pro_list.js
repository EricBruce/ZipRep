/**
 * 
 */
$(function(){
	$.ctx = function(ctx) {
		//入库
		//初始化弹出窗
		$('#inSpecTbl').datagrid({
		    url: ctx+'/product/specList.do',
		    columns:[[
		        {field:'product_id',title:'productID',hidden:true},
		        {field:'product_no',title:'productNO',hidden:true},
		        {field:'spec_id',title:'specID',hidden:true},
		        {field:'color_id',title:'colorID',hidden:true},
		        {field:'color_name',title:'颜色',width:120},
		        {field:'rec_amount',title:'数量',width:138, editor: {
                    type: 'numberbox'
                }},
		        {field:'operation_type',title:'操作类型',width:100, editor: {
                    type: 'combobox',
                    options: {
                    	editable:false,
                    	valueField: 'dic_code',
                		textField: 'dic_desc',
                        data: [{
                        	dic_desc: '入库',
                			dic_code: '01'
                		},/*{
                			dic_desc: '出库',
                			dic_code: '02'
                		},*/{
                			dic_desc: '退货',
                			dic_code: '03'
                		}],
//                		url: ctx+'/product/operType.do',
                        required: true
                    }
                },formatter: function(value,row,index){
    				return "入库";
    			}}
		    ]],
		    onDblClickCell: function(index,field,value){
				$(this).datagrid('beginEdit', index);
				var ed = $(this).datagrid('getEditor', {index:index,field:field});
				$(ed.target).focus();
				//默认选择入库
				var typeEd = $(this).datagrid('getEditor', {index:index,field:'operation_type'});
				$(typeEd.target).combobox('setValue','01');
			}
		});
		//往库中添加宝贝
	$("input[name='addPro']").live('click',function(){
		var productID = $(this).attr("productID");
		$('#inSpecTbl').datagrid('load',{productID:productID});
		$("#dd").dialog('open');
	})	;
	//入库操作保存
	$("#inSave").click(function(){
		var rows = $('#inSpecTbl').datagrid('getRows');
        for ( var i = 0; i < rows.length; i++) {
        	$('#inSpecTbl').datagrid('endEdit', i);
        }
		var updated = $('#inSpecTbl').datagrid('getChanges');
		alert(JSON.stringify(updated))
		 $.post(
				 ctx + "/product/proInOper.do", 
				 JSON.stringify(updated), 
				 function(rsp) {
	             if(rsp.status == 1){
	                 $.messager.confirm("提示", "提交成功！",function(r){
	                	window.location = ctx+"/product/list.do";
	                 });
	             }
         }, "JSON").error(function() {
             $.messager.alert("提示", "提交错误了！");
         });
	});
	//关闭
	$("#inClose").click(function(){
		$("#dd").dialog('close');
	});
	
	
	//出库
	//初始化弹出窗
	$('#outSpecTbl').datagrid({
	    url: ctx+'/product/specList.do',
	    columns:[[
	        {field:'product_id',title:'productID',hidden:true},
	        {field:'product_no',title:'productNO',hidden:true},
	        {field:'spec_id',title:'specID',hidden:true},
	        {field:'color_id',title:'colorID',hidden:true},
	        {field:'color_name',title:'颜色',width:120},
	        {field:'rec_amount',title:'数量',width:138, editor: {
                type: 'numberbox'
            }},
	        {field:'operation_type',title:'操作类型',width:100, editor: {
                type: 'combobox',
                options: {
                	editable:false,
                	valueField: 'dic_code',
            		textField: 'dic_desc',
                    data: [{
            			dic_desc: '出库',
            			dic_code: '02'
            		}],
//            		url: ctx+'/product/operType.do',
                    required: true
                }
            },formatter: function(value,row,index){
				return "出库";
			}}
	    ]],
	    onDblClickCell: function(index,field,value){
			$(this).datagrid('beginEdit', index);
			var ed = $(this).datagrid('getEditor', {index:index,field:field});
			$(ed.target).focus();
			//默认选择出库
			var typeEd = $(this).datagrid('getEditor', {index:index,field:'operation_type'});
			$(typeEd.target).combobox('setValue','02');
		}
	});
	//往库中宝贝出库
$("input[name='reducePro']").live('click',function(){
	var productID = $(this).attr("productID");
	$('#outSpecTbl').datagrid('load',{productID:productID});
	$("#ee").dialog('open');
})	;
//入库操作保存
$("#outSave").click(function(){
	var rows = $('#outSpecTbl').datagrid('getRows');
    for ( var i = 0; i < rows.length; i++) {
    	$('#outSpecTbl').datagrid('endEdit', i);
    }
	var updated = $('#outSpecTbl').datagrid('getChanges');
	 $.post(
			 ctx + "/product/proOutOper.do", 
			 JSON.stringify(updated), 
			 function(rsp) {
             if(rsp.status == 1){
                 $.messager.confirm("提示", "提交成功！",function(r){
                	window.location = ctx+"/product/list.do";
                 });
             }
     }, "JSON").error(function() {
         $.messager.alert("提示", "提交错误了！");
     });
});
//关闭
$("#outClose").click(function(){
	$("#ee").dialog('close');
});
	}
});