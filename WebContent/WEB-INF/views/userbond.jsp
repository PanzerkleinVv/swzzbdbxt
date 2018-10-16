<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.0.3
Version: 1.5.5
Author: KeenThemes
Website: http://www.keenthemes.com/
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title>中共广东省委组织部督察系统</title>
<meta name="renderer" content="webkit">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="assets/css/style-metronic.css" rel="stylesheet" type="text/css" />
<link href="assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="app/css/bond.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="bondBody">
		<div class="bondSearchBox">
			<form action="" method="post">
				<input name="userdesc" id="userdesc" size="25" value="${userBond.name}" class="input-sm form-inline" type="text" autocomplete="off" placeholder="查找用户" />
				<button id="searchBut" type="button" class="btn blue form-inline">查询</button>
			</form>
		</div>
		<div class="bondResultBox">
			<div class="bondResult">找到以下信息：</div>
			<div class="bondTable">
				<div class="userRow userHeader">
					<span class="userItem">用户姓名</span>
					<span class="userItem">操作</span>
				</div>
				<c:forEach var="user" items="${users}" varStatus="status">
					<div class="userRow">
						<span class="userItem">${user.userdesc}</span>
						<span class="userItem">
							<input type="radio" name="choose" value="${user.username}" class="input-sm form-inline" />
						</span>
					</div>
				</c:forEach>
				<div class="userRow userFooter">
					<span class="userItem cancel">取消账号管理</span>
					<span class="userItem">
						<input type="radio" name="choose" value="" class="input-sm form-inline" />
					</span>
				</div>
			</div>
		</div>
		<div class="bondFooter">
			<form id="bondForm" action="${userBond.backIP}:8080/swzzboffice/system/userother_backvalue1.jsp" method="get">
				<input type="hidden" name="flag" value="${userBond.flag}" />
				<input type="hidden" id="choose" name="id" value="" />
				<input type="hidden" name="name" value="${userBond.name}" />
				<input type="hidden" name="backIP" value="${userBond.backIP}" />
				<button id="ok" type="submit" class="btn green form-inline">确认</button>
				<button id="cancel" type="button" class="btn red form-inline">取消</button>
			</form>
		</div>
	</div>
</body>
<script src="assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$('#searchBut').click(function() {
		$.getJSON("rest/user/bondList", {"userdesc": $("#userdesc").val()}, function(data){
			$(".userRow:not(.userHeader):not(.userFooter)").remove();
			$.each(data, function(i, user){
				$(".userFooter").before('<div class="userRow"><span class="userItem">' + user.userdesc + '</span><span class="userItem"><input type="radio" name="choose" value="' + user.username + '" class="input-sm form-inline" /></span></div>');
			});
		});
	});
	$("#bondForm").submit(function() {
		$("#choose").val($('input:radio:checked').val());
	});
	$("#cancel").click(function() {
		window.close();
	});
</script>
</html>