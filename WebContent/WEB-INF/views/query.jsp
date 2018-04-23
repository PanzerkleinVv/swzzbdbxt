<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">
	<div class="toolbarBox">
		<div id="queryBox" class="queryBox">
			<div class="queryLine">
				<span class="queryItem queryTitle">立项号：</span>
				<span>
					<input type='text' id='sequence' value=''
						class="input-sm form-inline" />
				</span>
				<span class="queryItem queryTitle">立项依据：</span>
				<span>
					<input type='text' id='basis' value='' class="input-sm form-inline input-long" />
				</span>
				<span class="queryItem queryTitle">督查事项：</span>
				<span>
					<input type='text' id='name' value='' class="input-sm form-inline input-long" />
				</span>
			</div>
			<div class="queryLine">
				<span class="queryItem queryTitle">立项时间：</span>
				<span>
					<input size="16" type="text" id="createTimeBegin" value="" readonly
						class="form_date input-sm form-inline"> <span
						class="middleSpan">至</span> <input size="16" type="text"
						id=createTimeEnd value="" readonly
						class="form_date input-sm form-inline">
				</span>
				<span class="queryItem queryTitle">经办处室：</span>
				<span>
					<select id="roleId" class="input-sm form-inline">
						<option></option>
						<c:forEach var="role" begin="1" items="${sessionScope.roles}">
							<option value="${role.id}">${role.roleName}</option>
						</c:forEach>
					</select>
				</span>
				<span class="queryItem queryTitle">信息状态：</span>
				<span>
					<select id="status" class="input-sm form-inline">
						<option></option>
						<c:forEach var="status" begin="1" items="${sessionScope.msgStatus}" varStatus="state">
							<option value="${state}">${status}</option>
						</c:forEach>
					</select>
				</span>
			</div>
			<div class="queryLine">
			<shiro:lacksPermission name="3">
				<span class="queryItem queryTitle">经办人：</span>
				<span>
					<select id="userId" class="input-sm form-inline">
						<option></option>
						<c:forEach var="user" items="${sessionScope.roleUsers}">
							<option value="${user.id}">${user.userdesc}</option>
						</c:forEach>
					</select>
				</span>
			</shiro:lacksPermission>
				<span class="queryTitle">
					<button id="querySubmit" type="button" class="btn blue"
						onclick="query(1)">检索</button>
				</span>
			</div>
		</div>
	</div>
	<div id="msgList"></div>
	<div id="openMsg"></div>
	<div id="goback">
		<button id="gobackButton" type="button" class="btn blue"
			onclick="goback()">返回</button>
	</div>
</div>
<script type="text/javascript">
	function query(pageNo) {
		var url = 'rest/msg/msgList';
		$.post(url, {
			pageNo : pageNo,
			sequence : $("#sequence").val(),
			basis : $("#basis").val(),
			name : $("#name").val(),
			createTimeBegin : $("#createTimeBegin").val(),
			createTimeEnd : $("#createTimeEnd").val(),
			roleId : $("#roleId").val(),
			status : $("#status").val(),
			userId : $("#userId").val()
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

	$(function() {
		$("#index-page-title").html("信息查询");
		$("#current-page-title").html("信息查询");
	});
</script>