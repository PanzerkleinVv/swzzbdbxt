<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div class="">
		<form action="" method="post">
			<input name="userdesc" id="userdesc" size="25" value="${userdesc}"
				class="input-sm form-inline" type="text"
				autocomplete="off" placeholder="查找用户" />
			<button id="searchBut" type="button" class="btn blue form-inline">查询</button>
			<select id="roleSelect" name="roleId"
				class="input-sm form-inline">
				<option value="0"
					<c:if test="${selectId == 0}">selected="selected"</c:if>>所有</option>
				<c:forEach var="role" items="${sessionScope.roles}"
					varStatus="status">
					<option value="${role.id}"
						<c:if test="${selectId == role.id}">selected="selected"</c:if>>${role.roleName}</option>
				</c:forEach>
			</select>
		</form>
	</div>
	<div class="navigationBar">
		<span style="cursor: pointer;" id="getAll" class="navigationItem">所有</span>
		<c:if test="${navigationBar ne '所有'}">
			<span>>></span>
			<span class="navigationItem">${navigationBar}</span>
		</c:if>
		<span style="color:${msg0};">${msg}</span>
	</div>
	<div class="userTable">
		<div class="userRow userHeader">
			<span class="userItem">用户姓名</span> <span class="userItem">用户名</span>
			<span class="userItem">处室</span> <span class="userItem">权限</span> <span
				class="userItem">状态</span> <span class="userItem">操作</span>
		</div>
		<c:forEach var="user" items="${users}" varStatus="status">
			<div class="userRow">
				<span class="userItem"> <input id="userId${status.index}"
					name="userId" type="hidden" value="${user.id}" />${user.userdesc}
				</span> <span class="userItem">${user.username}</span> <span
					class="userItem">${sessionScope.roleMap[user.roleId]}</span> <span
					class="userItem">${sessionScope.permissionMap[user.permissionId]}</span>
				<span class="userItem">${sessionScope.userState[user.state]}</span> <span class="userItem">
					<button id="${status.index}" type="button" class="btn blue">修改</button>
				</span>
			</div>
		</c:forEach>
	</div>

	<script type="text/javascript">
		$("#getAll").click(function() {
			var url = 'rest/user/admin';
			$.get(url, function(data) {
				showData("#main-content",data);
			});
		});
		$("#roleSelect").change(function() {
			var url = 'rest/user/admin';
			$.get(url, {
				roleId : $("#roleSelect").val()
			}, function(data) {
				showData("#main-content",data);
			});
		});
		$('button').click(function() {
			var butId = $(this).attr('id');
			if (butId == 'searchBut') {
				var url = 'rest/user/admin';
				$.post(url, {
					userdesc : $('#userdesc').val()
				}, function(data) {
					showData("#main-content",data);
				});
			} else {
				var dataId = "userId" + butId;
				var url = 'rest/user/info';
				$.post(url, {
					id : $('#' + dataId).val()
				}, function(data) {
					showData("#main-content",data);
				});
			}
		});
		$('form').keypress(function(e) {
			var keynum;
			if (window.event) // IE
			{
				keynum = e.keyCode;
			} else if (e.which) // Netscape/Firefox/Opera
			{
				keynum = e.which;
			}
			if (keynum == 13) {
				return false;
			}
		});

		$(function() {
			$("#index-page-title").html("用户管理");
			$("#current-page-title").html("用户管理");
		});
	</script>
</div>