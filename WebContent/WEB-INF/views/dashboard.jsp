<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">	
	<div class="toolbarBox">
		<div>
			<ul>
			    <c:if test="${empty noticeCounts}">暂无提醒信息预览</c:if>
					<c:forEach var="noticeCount" begin="0" items="${noticeCounts}" varStatus="state">
								<li>类型为:<font size="5" >${noticeType[noticeCount.type]}</font>一共有<font size="6" ><a style="cursor:pointer;" onclick="notice(${noticeCount.type},0)"> ${noticeCount.oneType} </a></font>项
								,其中未读：<font size="6" >
								<c:if test="${empty noticeCount.unRead}">0 </c:if>
								<a style="cursor:pointer;" onclick="notice(${noticeCount.type},1)">${noticeCount.unRead} </a></font>项</li>
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
	var url = 'rest/msg/msgListByNotice';
	$.post(url, {
		type:type,
		status:status,
		pageNo : 1
	}, function(data) {
		$('#msgList').html(data);
	});
}



</script>