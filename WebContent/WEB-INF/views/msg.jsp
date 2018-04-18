<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="height: 20px"></div>
<div id="msgBox">
	<div>
		<span class="msgTitle">标题：</span> <span>${msg.msgTitle}</span> <input id="msgId"
			type="hidden" value="${msg.msgId}" />
	</div>
	<div>
		<span class="msgTitle">工作类型：</span> <span>${msg.msgServiceName}</span>
	</div>
	<div>
		<span class="msgTitle">信息类型：</span> <span>${msg.msgTypeName}</span>
	</div>
	<div>
		<span class="msgTitle">上报单位：</span> <span>${msg.msgUserUnit}</span>
	</div>
	<div>
		<span class="msgTitle">所属地区：</span> <span>${msg.msgAreaName}</span>
	</div>
	<div>
		<span class="msgTitle">联系人姓名：</span> <span>${msg.msgAttn}</span>
	</div>
	<div>
		<span class="msgTitle">联系人电话：</span> <span>${msg.msgPhone}</span>
	</div>
	<c:choose>
		<c:when test="${roleId == '1'}">
			<div id='msgStatus'>
				<span class="msgTitle">信息状态：</span> <span class="" id="msgStatusName"
					onclick="getCode1()">${msg.msgStatusName}</span>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<span class="msgTitle">信息状态：</span> <span>${msg.msgStatusName}</span>
			</div>
		</c:otherwise>
	</c:choose>
	<div>
		<div class="msgTitle">正文：</div>
		<div>${msg.msgContent}</div>
	</div>
</div>
<script type="text/javascript">
	function getCode1() {
		var url;
		var target;
		var method;
		url = "rest/code/status";
		target = $("#msgStatus");
		$.get(url, {
			method : "select1"
		}, function(data) {
			$(target).after(data);
		});
	}

	function codeClick1(target) {
		var thisSpan = $(target);
		var msgStatusName = thisSpan.html();
		if (msgStatusName != $("#msgStatusName").html()) {
			var url = "rest/msg/changeMsgStatus";
			var msgStatus = thisSpan.attr('id');
			$.post(url, {
				msgId : $("#msgId").val(),
				msgStatus : msgStatus
			}, function(data) {
				$('#msgStatusName').html(data);
			});
		}
		$("#msgStatus").next().remove();
	}
</script>