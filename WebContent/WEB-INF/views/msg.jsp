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
		<span class="msgTitle">附件资料：</span> <span><c:forEach
				var="attachId" items="${msg.attachIds}" varStatus="status0">
				<a href="rest/attach/download?id=${attachId}" target="_blank"
					style="display: block; margin-left: 20px;">${msg.attachs[status0.index]}</a>
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
			$("#limitTime").datepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN'
			});
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

	function saveContent1(target) {
		var form = $(target).parents("form");
		var type = form.find("#contentType").val();
		if (type == 1) {
			var url = "rest/msg/saveMsgSponsor";
		} else if (type == 2) {
			var url = "rest/msg/saveMsgCoSponsor";
		} else {
			return false;
		}
		form.find('input[type="file"]').each(function(i, n) {
			if (!checkFile(n)) {
				$(n).parent().remove();
			}
		});
		form.ajaxSubmit({
			'url' : url,
			'type' : "post",
			'cache' : false,
			'success' : function(data) {
				showData("#msg-content", data);
			}
		});
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
			if (status == 0 || (status == 1 && $("#limitTime").val().length > 0)) {
				$.post(url, {
					"msgId0" : $("#id").val(),
					"id" : targetId,
					"status" : status,
					"situation" : UE.getEditor(targetId + '_editor1').getContent(),
					"reason" : UE.getEditor(targetId + '_editor2').getContent(),
					"measure" : UE.getEditor(targetId + '_editor3').getContent(),
					"limitTime" : $("#limitTime").val()
				}, function(data) {
					showData("#msg-content", data);
				});
			} else {
				$("#limitTime").parent().next().remove();
				$("#limitTime").parent().after("<span style='color: #FF0000;'>延期期限不能为空</span>");
			}
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

	function deleteFile(id) {
		$.ajax({
			type : "POST",
			url : 'rest/attach/delete',
			data : {
				'id' : id
			},
			dataType : "text",
			async : false,
			success : function(msg) {
				if ("true" == msg.match("true")) {
					$('#attach_' + id).remove();
				} else {
					$('#attach_' + id).append('<i class="red">&emsp;删除失败</i>');
				}
			}
		});
		return false;
	}

	var fileBand = 0;

	function addAttach(target) {
		$(target)
				.before(
						"<div><a class='red' onclick='removeFile(this)'>[删除]</a><label for='file_" + fileBand + "'>请选择文件</label><input type='file' name='files' id='file_"
								+ fileBand
								+ "' style='display: none' onchange='fileChange(this)' /></div>");
		$(target).parent().attr('for', 'file_' + fileBand);
		fileBand++;
	}

	function fileChange(target) {
		if (checkFile(target)) {
			fileBand++;
		} else {
			removeFile($(target));
		}
	}

	function checkFile(target) {
		var msg = $("#msg5");
		if (target.files.length == 0) {
			msg.html("请选择文件");
			msg.css('color', '#FF0000');
			return false;
		} else {
			var fileSize = target.files[0].size;
			var maxSize = 3 * 1024 * 1024;
			if (fileSize > maxSize) {
				msg.html("单个文件不能大于3Mb");
				msg.css('color', '#FF0000');
				return false;
			} else {
				msg.html("");
				$(target).prevAll('label').html(target.files[0].name);
				return true;
			}
		}
	}

	function removeFile(target) {
		$(target).parent().remove();
	}
</script>