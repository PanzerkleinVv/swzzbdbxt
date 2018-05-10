<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">
	<div class="toolbarBox">
		<div id="queryBox" class="queryBox">
			<div class="queryLine">
				<span class="queryItem queryTitle">立项号：</span>
				<span>
					<input type='text' id='sequenceQuery' value=''
						class="input-sm form-inline" />
				</span>
				<span class="queryItem queryTitle">立项依据：</span>
				<span>
					<input type='text' id='basisQuery' value='' class="input-sm form-inline input-long" />
				</span>
				<span class="queryItem queryTitle">督查事项：</span>
				<span>
					<input type='text' id='nameQuery' value='' class="input-sm form-inline input-long" />
				</span>
			</div>
			<div class="queryLine">
				<span class="queryItem queryTitle">立项时间：</span>
				<span>
					<input size="16" type="text" id="createTimeBeginQuery" value="" readonly
						class="form_date input-sm form-inline"> <span
						class="middleSpan">至</span> <input size="16" type="text"
						id="createTimeEndQuery" value="" readonly
						class="form_date input-sm form-inline">
				</span>
				<shiro:hasAnyRoles name="admin,1,2">
				<span class="queryItem queryTitle">经办处室：</span>
				<span>
					<select id="roleIdQuery" class="input-sm form-inline">
						<option></option>
						<c:forEach var="role" begin="1" items="${sessionScope.roles}">
							<option value="${role.id}">${role.roleName}</option>
						</c:forEach>
					</select>
				</span>
				</shiro:hasAnyRoles>
				<span class="queryItem queryTitle">信息状态：</span>
				<span>
					<select id="statusQuery" class="input-sm form-inline">
						<option></option>
						<c:forEach var="status" begin="1" items="${sessionScope.msgStatus}" varStatus="state">
							<option value="${state.index}">${status}</option>
						</c:forEach>
					</select>
				</span>
			</div>
			<div class="queryLine">
			<shiro:lacksPermission name="3">
				<span class="queryItem queryTitle">经办人：</span>
				<span>
					<select id="userIdQuery" class="input-sm form-inline">
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
					<button id="resetSubmit" type="button" class="btn blue"
						onclick="reset()">重置</button>
				</span>
			</div>
		</div>
	</div>
	<div id="msgList"></div>
</div>
<script type="text/javascript">
	function query(pageNo) {
		var url = 'rest/msg/msgList';
		$.post(url, {
			pageNo : pageNo,
			sequence : $("#sequenceQuery").val(),
			basis : $("#basisQuery").val(),
			name : $("#nameQuery").val(),
			createTimeBegin : $("#createTimeBeginQuery").val(),
			createTimeEnd : $("#createTimeEndQuery").val(),
			roleId : $("#roleIdQuery").val(),
			status : $("#statusQuery").val(),
			userId : $("#userIdQuery").val()
		}, function(data) {
			$('#msgList').html(data);
		});
	}
	
	function reset() {
		$("#sequenceQuery").val("");
		$("#basisQuery").val("");
		$("#nameQuery").val("");
		$("#createTimeBeginQuery").val("");
		$("#createTimeEndQuery").val("");
		$("#roleIdQuery").val("");
		$("#statusQuery").val("");
		$("#userIdQuery").val("");
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

	$(function() {
		$("#index-page-title").html("信息查询");
		$("#current-page-title").html("信息查询");
	});
</script>