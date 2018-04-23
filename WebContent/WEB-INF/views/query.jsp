<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">
	<div class="toolbarBox">
		<div id="queryBox" class="queryBox">
			<div class="queryLine">
				<div class="queryItem queryTitle">立项号：</div>
				<div>
					<input type='text' id='sequence' value=''
						class="form-control placeholder-no-fix" />
				</div>
			</div>
			<div class="queryLine">
				<div class="queryItem queryTitle">立项依据：</div>
				<div>
					<input type='text' id='basis' value=''
						class="form-control placeholder-no-fix" />
				</div>
			</div>
			<div class="queryLine">
				<div class="queryItem queryTitle">督查事项：</div>
				<div>
					<input type='text' id='name' value=''
						class="form-control placeholder-no-fix" />
				</div>
			</div>
			<div class="queryLine">
				<div class="queryItem queryTitle">立项时间：</div>
				<div>
					<input size="16" type="text" id="createTimeBegin" value="" readonly
						class="form_date form-control placeholder-no-fix halfWidth">
					<span class="middleSpan">至</span> <input size="16" type="text"
						id=createTimeEnd value="" readonly
						class="form_date form-control placeholder-no-fix halfWidth">
				</div>
			</div>
			<div class="queryLine">
				<div class="queryItem queryTitle">经办处室：</div>
				<div>
					<select id="roleId">
						<option></option>
						<c:forEach var="role" items="${sessionScope.roles}">
							<option value="${role.id}">${role.roleName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<shiro:lacksPermission name="3">
				<div class="queryLine">
					<div class="queryItem queryTitle">经办人：</div>
					<div>
						<select id="userId">
							<option></option>
							<c:forEach var="user" items="${sessionScope.roleUsers}">
								<option value="${user.id}">${user.userdesc}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</shiro:lacksPermission>
			<div class="queryLine">
				<div class="queryItem queryTitle">信息状态：</div>
				<input type='hidden' id='status' value='' />
			</div>
			<div class="queryLine">
				<div>
					<button id="querySubmit" type="button" class="btn blue"
						onclick="query(1)">检索</button>
				</div>
			</div>
		</div>
		<div id="closeQuery" class="closeQuery" onclick="closeQuery()">︽收起︽</div>
	</div>
	<div id="msgList"></div>
	<div id="openMsg"></div>
	<div id="goback">
		<button id="gobackButton" type="button" class="btn blue"
			onclick="goback()">返回</button>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var num = 0;
		while (num < 3) {
			getCode(num);
			num++;
		}
		$(".toolbarBox").css("width", $("#main-content").css("width"));
	});

	function getCode(num) {
		var url;
		var target;
		switch (num) {
		case 0:
			url = "rest/code/service";
			target = $("#msgServiceIds");
			break;
		case 1:
			url = "rest/code/type";
			target = $("#msgTypeIds");
			break;
		case 2:
			url = "rest/code/status";
			target = $("#msgStatuses");
			break;
		}
		$.get(url, {
			method : "select"
		}, function(data) {
			$(target).after(data);
		});
	}

	$('#msgAreaName').click(function() {
		var pDiv = $(this);
		$("#roleSelectSubmit").css("display", "inline");
		$("#roleSelectAll").css("display", "inline");
		if (pDiv.next().attr("id") != "selectRole") {
			var url = 'rest/role/list';
			$.post(url, {
				method : "selectNoCheck"
			}, function(data) {
				pDiv.after(data);
			});
		}
	});

	$(function() {
		query(1);
	});

	function query(pageNo) {
		getCodeSelected("#msgServiceIds");
		getCodeSelected("#msgTypeIds");
		getCodeSelected("#msgStatuses");
		closeQuery();
		$("#openMsg").hide();
		$("#goback").hide();
		$("#msgList").show();
		var url = 'rest/msg/msgList';
		$.post(url, {
			pageNo : pageNo,
			msgServiceIds : $("#msgServiceIds").val(),
			msgTypeIds : $("#msgTypeIds").val(),
			msgAreaId : $("#msgAreaId").val(),
			msgAreaName : $("#msgAreaName").val(),
			msgKeyword : $("#msgKeyword").val(),
			msgDateBegin : $("#msgDateBegin").val(),
			msgDateEnd : $("#msgDateEnd").val(),
			msgStatuses : $("#msgStatuses").val()
		}, function(data) {
			$('#msgList').html(data);
		});
	}

	function getCodeSelected(target) {
		var array = new Array();
		$(target + " + div .spanSelected").each(function(i) {
			array[i] = $(this).attr("id");
		});
		$(target).val(array);
	}

	$(".form_date").datepicker({
		format : 'yyyy-mm-dd',
		language : 'zh-CN'
	});

	function closeQuery() {
		var queryBox = $("#queryBox");
		var closeQuery = $("#closeQuery");
		if (queryBox.css("display") == "block") {
			queryBox.slideUp("fast");
			closeQuery.html("︾展开︾");
		} else {
			queryBox.slideDown("fast");
			closeQuery.html("︽收起︽");
		}
	}

	function openMsg(msgId) {
		var url = 'rest/msg/openMsg';
		$("#msgList").hide();
		$("#openMsg").show();
		$("#goback").show();
		$.post(url, {
			msgId : msgId
		}, function(data) {
			$('#openMsg').html(data);
		});
	}

	function msgDelete(msgId) {
		var url = 'rest/msg/deleteMsg';
		var pageNo = $("#pageNo").val();
		$.post(url, {
			msgId : msgId
		}, function(data) {
			query(pageNo);
			closeQuery();
		});
	}

	function codeClick(target) {
		var thisSpan = $(target);
		thisSpan.toggleClass("spanSelected");
	}

	$(function() {
		$("#index-page-title").html("信息查询");
		$("#current-page-title").html("信息查询");
	});

	function goback() {
		$("#msgList").show();
		$("#openMsg").hide();
		$("#goback").hide();
	}
</script>