<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">	<div class="toolbarBox">

		<div>
			<ul>
				<c:forEach var="noticeCount" begin="0" items="${sessionScope.noticeCounts}" varStatus="state">
							<li>类型为：${noticeCount.type}</li>一共有<a href=""  onclick="notice(${noticeCount.type},0)"><span> ${noticeCount.oneType}</span></a> 项
							,其中未读：<a href=""  onclick="notice(${noticeCount.type},0)"><span>${noticeCount.unRead}</span></a> 项
							,其中已读：<a href=""  onclick="notice(${noticeCount.type},0)" ><span>${noticeCount.oneType-noticeCount.unRead}</span></a> 项
				</c:forEach>
			</ul>
		</div>
	<div id="msgList"></div>
</div>
<script>
$(function() {
	$("#index-page-title").html("提醒预览");
	$("#current-page-title").html("提醒预览");
});
function notice(type,status) {
	var url = 'rest/notice/msgList';
	$.post(url, {
		type:type,
		status:type
	}, function(data) {
		$('#msgList').html(data);
	});
}



</script>