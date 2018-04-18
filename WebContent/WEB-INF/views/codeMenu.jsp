<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div class="code" >
		<div class="head" onclick="codeOpen(this,0)">工作类型</div>
	</div>
	<div class="code" >
		<div class="head" onclick="codeOpen(this,1)">信息类别</div>
	</div>
	<div class="code" >
		<div class="head" onclick="codeOpen(this,2)">信息状态</div>
	</div>
</div>
<script type="text/javascript">
	function codeOpen(target,num) {
		if ($(target).next().attr("id") != null) {
			return false;
		}
		var url;
		switch (num) {
		case 0:
			url="rest/code/service";
			break;
		case 1:
			url="rest/code/type";
			break;
		case 2:
			url="rest/code/status";
			break;
		}
		$.get(url, function(data) {
			$(target).after(data);
		});
	}
	
	$(function() {
		$("#index-page-title").html("代码管理");
		$("#current-page-title").html("代码管理");
	});
</script>