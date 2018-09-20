<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">
	<div class="toolbarBox">
		<div>
			<ul class="index-info">
				<li>经办事项<font size="5">共</font><font size="6"><a style="cursor: pointer;color: blue;" onclick="goQuery('0')">${statistics.total}</a></font>件，其中<font size="5">在办</font><font size="6"><a style="cursor: pointer;color: deepskyblue;" onclick="goQuery('1')">${statistics.onwork}</a></font>件，<font size="5">不在办</font><font size="6"><a style="cursor: pointer;color: gray;" onclick="goQuery('2')">${statistics.stop}</a></font>件。</li>
				<li>经办事项将在<font size="5">一月内逾期</font><font size="6"><a style="cursor: pointer;color: goldenrod;" onclick="goQuery('3')">${statistics.month}</a></font>件，<font size="5">一周内逾期</font><font size="6"><a style="cursor: pointer;color: #ff6969;" onclick="goQuery('4')">${statistics.week}</a></font>件，<font size="5">已逾期</font><font size="6"><a style="cursor: pointer;color: #FF0000;" onclick="goQuery('5')">${statistics.overtime}</a></font>件。</li>
				<c:if test="${empty noticeCounts}">暂无提醒信息预览</c:if>
				<c:forEach var="noticeCount" begin="0" items="${noticeCounts}" varStatus="state">
					<li>类型为:<font size="5">${noticeType[noticeCount.type]}</font>一共有<font size="6"><a style="cursor: pointer;" onclick="notice(${noticeCount.type},0)"> ${noticeCount.oneType} </a></font>项 ,其中未读：<font size="6"> <c:if test="${empty noticeCount.unRead}">0 </c:if> <a style="cursor: pointer;" onclick="notice(${noticeCount.type},1)">${noticeCount.unRead} </a></font>项
					</li>
				</c:forEach>
			</ul>
		</div>
		<input type="hidden" id="roleId" value="${sessionScope.roleId}" />
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
		function goQuery(type) {
			var url = 'rest/msg/msgList';
			var data;
			switch (type) {
			case '0':
				data = {
					'roleId': $('#roleId').val(),
					'pageNo': 1
				};
				break;
			case '1':
				data = {
					'roleId': $('#roleId').val(),
					'onwork': true,
					'pageNo': 1
				};
				break;
			case '2':
				data = {
					'roleId': $('#roleId').val(),
					'onwork': false,
					'pageNo': 1
				};
				break;
			case '3':
				var now = new Date();
				now = new Date(now.setMonth(now.getMonth() + 1));
				var dStr = dateTran(now);
				data = {
					'roleId': $('#roleId').val(),
					'status': 1,
					'limitTimeEnd': dStr,
					'pageNo': 1
				};
				break;
			case '4':
				var now = new Date();
				now = new Date(now.setDate(now.getDate() + 7));
				var dStr = dateTran(now);
				data = {
					'roleId': $('#roleId').val(),
					'status': 1,
					'limitTimeEnd': dStr,
					'pageNo': 1
				};
				break;
			case '5':
				data = {
					'roleId': $('#roleId').val(),
					'status': 2,
					'pageNo': 1
				};
				break;
			}
			$.post(url, data, function(data) {
				$('#msgList').html(data);
			});
			return false;
		}
		function dateTran(d) {
			var month = d.getMonth() + 1;
			var day = d.getDate();
			month = (month.toString().length == 1) ? ("0" + month) : month;
			day = (day.toString().length == 1) ? ("0" + day) : day;
			var result = d.getFullYear() + '-' + month + '-' + day;
			return result;
		}
	</script>
</div>