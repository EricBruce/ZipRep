<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>库存管理系统</title>
    <link href="assets/css/default.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" type="text/css" href="assets/js/jquery-easyui-1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="assets/js/jquery-easyui-1.3.4/themes/icon.css" />
    <script type="text/javascript" src="assets/js/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="assets/js/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='assets/js/XiuCai.index.js'></script>

    <script type="text/javascript">
	var _menus = {
	"menus": [{
		"menuid": "1",
		"icon": "icon-sys",
		"menuname": "用户管理",
		"menus": [{
			"menuid": "12",
			"menuname": "角色管理",
			"icon": "icon-add",
			"url": "javascript:void(0)",
		},
		{
			"menuid": "13",
			"menuname": "人员管理",
			"icon": "icon-users",
			"url": "javascript:void(0)",
		},
		{
			"menuid": "14",
			"menuname": "权限管理",
			"icon": "icon-role",
			"url": "javascript:void(0)",
			"child": []
		},
		{
			"menuid": "16",
			"menuname": "菜单管理",
			"icon": "icon-log",
			"url": "javascript:void(0)",
			"child": []
		}]
	},
	{
		"menuid": "8",
		"icon": "icon-sys",
		"menuname": "商铺管理",
		"menus": []
	},
	{
		"menuid": "56",
		"icon": "icon-sys",
		"menuname": "商品管理",
		"menus": [{
			"menuid": "16",
			"menuname": "商品列表",
			"icon": "icon-log",
			"url": "${ctx}/product/list.do",
			"child": []
		},
		{
			"menuid": "16",
			"menuname": "新建商品",
			"icon": "icon-log",
			"url": "${ctx}/product/pronew.do",
			"child": []
		}]
	},
	{
		"menuid": "56",
		"icon": "icon-sys",
		"menuname": "统计分析",
		"menus": [{
			"menuid": "16",
			"menuname": "趋势图",
			"icon": "icon-log",
			"url": "${ctx}/main/pro_list_trend.jsp",
			"child": []
		},
		{
			"menuid": "16",
			"menuname": "销量分类统计图",
			"icon": "icon-log",
			"url": "${ctx}/main/pro_sell_trend.jsp",
			"child": []
		}]
	}]
};

        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }

        

        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })
            
        }

        $(function() {

            openPwd();

            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

			$('#btnCancel').click(function(){closePwd();})

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '/ajax/loginout.ashx';
                    }
                });
            })
        });
		
		

    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>

<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="assets/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>

    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(assets/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎 疯狂秀才 <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; "><img src="assets/images/blocks.gif" width="20" height="20" align="absmiddle" /> 订单库存管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">理想共和</div>
    </div>
    <div region="west" split="true"  title="导航菜单" style="width:180px;" id="west">
			<div id="nav">
		        <!--  导航内容 -->

			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
			</div>
		</div>
    </div>
    
    <!--修改密码窗口-->
    <div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="exit">退出</div>
	</div>


</body>
</html>