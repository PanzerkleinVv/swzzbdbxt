<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div id="msgBox" class="mainContent">
	<div>
		<span class="msgTitle">立项号&emsp;：</span> <span>${msg.sequence}</span>
		<input id="id" type="hidden" value="${msg.id}" />
	</div>
	<div>
		<span class="msgTitle">督察事项：</span> <span>${msg.name}</span>
	</div>
	<div>
		<span class="msgTitle">立项时间：</span> <span><fmt:formatDate
				value='${msg.createTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
	</div>
	<div>
		<span class="msgTitle">立项依据：</span> <span>${msg.basis}</span>
	</div>
	<div>
		<span class="msgTitle">主办处室：</span> <span>${msg.sponsorRoleNames}</span>
	</div>
	<div>
		<span class="msgTitle">协办处室：</span> <span>${msg.coSponsorRoleNames}</span>
	</div>
	<div>
		<span class="msgTitle">办结时限：</span> <span><fmt:formatDate
				value='${msg.limitTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
	</div>
	<div>
		<span class="msgTitle">附件资料：</span> <span><c:forEach
				var="attachId" items="${msg.attachIds}" varStatus="status0">
				<a href="rest/attach/download?id=${attachId}" target="_blank">${msg.attachs[status0.index]}</a>
			</c:forEach></span>
	</div>
	<div>
		<span class="msgTitle">反馈时间：</span> <span><fmt:formatDate
				value='${msg.endTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
	</div>
	<div class="middleTitle">
		<span class="msgTitle"> <shiro:hasAnyRoles name="admin,1,2">
				<c:if test="${callbackable}">
					<button id="callback" type="button" class="btn blue"
						onclick="callback()">撤回</button>
				</c:if>
			</shiro:hasAnyRoles> <c:if test="${signable}">
				<button id="sign" type="button" class="btn blue" onclick="sign()">签收</button>
			</c:if>
		</span>
	</div>
	<div class="middleTitle">
		<span id='msg0' style='color: ${msg2 ne null ? msg2 : "#000000"}'>${msg1}</span>
	</div>
	<div class="titleEnd">
		<span class="msgTitle">办理情况：</span>
	</div>

</div>
<script type="text/javascript">
	function sign() {
		var url = "rest/msg/sign";
		$.post(url, {
			id : $("#id").val()
		}, function(data) {
			showData("#msg-content", data);
		});
	}

	function callback() {
		var url = "rest/msg/callback";
		$.post(url, {
			"id" : $("#id").val()
		}, function(data) {
			showData("#msg-content", data);
		})
	}

	$(function($) {
		var url = "rest/msg/getContent";
		$.post(url, {
			id : $("#id").val()
		}, function(data) {
			$(".titleEnd").after(data);
		});
	});
	
	function assign(id) {
		var url = "rest/msg/assign";
		var userIds = [];
		$("input[name='" + id + "']:checked").each(function(i) {
			userIds[i] = $(this).val();
		});
		$.post(url, {
			"msgId" : $("#id").val(),
			"userIds[]" : userIds
		}, function(data) {
			showData("#msg-content", data);
		});
	}

	function saveContent(id, type) {
		if (type == 1) {
			var url = "rest/msg/saveMsgSponsor";
		} else  if (type == 2) {
			var url = "rest/msg/saveMsgCoSponsor";
		} else {
			return false;
		}
		$.post(url, {
			"id" : id,
			"content" : UE.getEditor(id + '_editor').getContent()
		}, function(data) {
			showData("#msg-content", data);
		})
	}
	
	function addSubmission(type, targetId) {
		var url = "rest/submission/add";
		$.post(url, {
			"msgId0" : $("#id").val(),
			"msgId" : targetId,
			"type" : type
		}, function(data) {
			showData("#msg-content", data);
		});
	}
	
	function saveSubmission(targetId, status, type) {
		var url = "rest/submission/save";
		if (type == 1 || type == 4) {
			$.post(url, {
				"msgId0" : $("#id").val(),
				"id" : targetId,
				"status" : status,
				"situation" : UE.getEditor(targetId + '_editor1').getContent()
			}, function(data) {
				showData("#msg-content", data);
			});
		} else if (type == 2) {
			$.post(url, {
				"msgId0" : $("#id").val(),
				"id" : targetId,
				"status" : status,
				"situation" : UE.getEditor(targetId + '_editor1').getContent(),
				"reason" : UE.getEditor(targetId + '_editor2').getContent(),
				"measure" : UE.getEditor(targetId + '_editor3').getContent()
			}, function(data) {
				showData("#msg-content", data);
			});
		} else if (type == 3) {
			$.post(url, {
				"msgId0" : $("#id").val(),
				"id" : targetId,
				"status" : status,
				"situation" : UE.getEditor(targetId + '_editor1').getContent(),
				"reason" : UE.getEditor(targetId + '_editor2').getContent()
			}, function(data) {
				showData("#msg-content", data);
			});
		}
	}
	
	function delSubmission(targetId) {
		var url = "rest/submission/del";
		$.post(url, {
			"msgId0" : $("#id").val(),
			"id" : targetId
		}, function(data) {
			showData("#msg-content", data);
		});
	}
	
	function pass(targetId) {
		var url = "rest/submission/verify";
		$.post(url, {
			"msgId0" : $("#id").val(),
			"id" : targetId,
			"superiorVerifyPassed" : 1,
			"status" : 2
		}, function(data) {
			showData("#msg-content", data);
		});
	}
	
	function noPass(targetId) {
		var url = "rest/submission/verify";
		$.post(url, {
			"msgId0" : $("#id").val(),
			"id" : targetId,
			"superiorVerifyPassed" : 0,
			"status" : 2
		}, function(data) {
			showData("#msg-content", data);
		});
	}
	
	function callbackSubmission(targetId) {
		var url = "rest/submission/callback";
		$.post(url, {
			"msgId0" : $("#id").val(),
			"id" : targetId,
			"status" : 0
		}, function(data) {
			showData("#msg-content", data);
		});
	}
</script>