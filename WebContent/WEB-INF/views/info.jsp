<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div class="navigationBar">
		<span style="cursor: pointer;" id="getAll" class="navigationItem">所有</span>
		<span>>></span> <span class="navigationItem">${navigationBar}</span> <span
			style="color:${msg0};">${msg}</span>
	</div>
	<div>
		<span>大组工网ID：</span> <span><input name="id" id="id"
			type="hidden" value="<c:if test='${user != null}'>${user.id}</c:if>" /><input
			name="username" id="username" type="text" onblur="check(0)"
			value="<c:if test='${user != null}'>${user.username}</c:if>"
			class="form-control placeholder-no-fix" /></span><span id='msg0'></span>
	</div>
	<div>
		<span>用户姓名：</span> <span><input name="userdesc" id="userdesc"
			type="text" onblur="check(1)"
			value="<c:if test='${user != null}'>${user.userdesc}</c:if>"
			class="form-control placeholder-no-fix" /></span><span id='msg1'></span>
	</div>
	<div>
		<span>所属处室：</span> <span><select id="roleId" name="roleId"
			onblur="check(2)" class="form-control placeholder-no-fix">
				<option></option>
				<c:forEach var="role" items="${sessionScope.roles}"
					varStatus="status">
					<option value="${role.id}"
						<c:if test="${user != null && user.roleId == role.id}"> selected="selected"</c:if>>${role.roleName}</option>
				</c:forEach>
		</select></span><span id='msg2'></span>
	</div>
	<div>
		<span>权限类型：</span> <span><select id="permissionId"
			name="permissionId" onblur="check(4)"
			class="form-control placeholder-no-fix">
				<option></option>
				<c:forEach var="permission" items="${sessionScope.permissions}"
					varStatus="status">
					<option value="${permission.id}"
						<c:if test="${user != null && user.permissionId == permission.id}"> selected="selected"</c:if>>${permission.permissionName}</option>
				</c:forEach>
		</select></span><span id='msg4'></span>
	</div>
	<c:if test="${method == '修改'}">
		<div>
			<span>帐号状态：</span> <span><select id=state name="state"
				onblur="check(3)" class="form-control placeholder-no-fix">
					<c:set var="i" value="0" />
					<c:forEach var="userState0" items="${sessionScope.userState}"
						varStatus="status0">
						<option value="${i}"
							<c:if test="${user.state == i}"> selected="selected"</c:if>>${userState0}</option>
						<c:set var="i" value="${i+1}" />
					</c:forEach>
			</select></span><span id='msg3'></span>
		</div>
	</c:if>
	<c:if test="${method == '新增'}">
		<div>
			<input id="state" name="state" type="hidden" value="1" />
		</div>
	</c:if>
	<div class="infoButton">
		<button id="saveBut" type="button" class="btn blue">保存</button>
		<c:if test='${user != null && user.id != null}'>
			<button id="delete" type="button" class="btn blue">删除</button>
		</c:if>
	</div>
	<script type="text/javascript">
		$("#getAll").click(function() {
			var url = 'rest/user/admin';
			$.get(url, function(data) {
				showData("#main-content", data);
			});
		});
		function check(num) {
			var value;
			var msg;
			if (num == 0) {
				value = $('#username').val();
				msg = $('#msg0');
				if (value == null || value.length < 1) {
					msg.html("大组工网ID不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else if (value.search('[^\\w\\d]+') > 0) {
					msg.css('color', '#FF0000');
					msg.html('系统名只能是英文或数字');
					return false;
				} else {
					var url = "rest/user/checkUsername";
					var flag;
					$.getJSON(url, {
						'username' : value,
						'id' : $("#id").val()
					}, function(data) {
						msg.html(data.msg);
						msg.css('color', data.msg0);
					});
					if (msg.html() != 'OK') {
						return false;
					} else {
						return true
					}
				}
			} else if (num == 1) {
				value = $('#userdesc').val();
				msg = $('#msg1');
				if (value == null || value.length < 1) {
					msg.html("用户姓名不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 2) {
				value = $('#roleId').val();
				msg = $('#msg2');
				if (value == null || value.length < 1) {
					msg.html("所属处室不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 3) {
				value = $('#state').val();
				msg = $('#msg3');
				if (value == null || value.length < 1) {
					msg.html("帐号状态不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 4) {
				value = $('#permissionId').val();
				msg = $('#msg4');
				if (value == null || value.length < 1) {
					msg.html("权限类型不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			}
		}
		$('#saveBut').click(function() {
			var url = 'rest/user/modify';
			if (check(0) && check(1) && check(2) && check(3)) {
				$.post(url, {
					id : $('#id').val(),
					username : $('#username').val(),
					userdesc : $('#userdesc').val(),
					roleId : $('#roleId').val(),
					permissionId : $('#permissionId').val(),
					state : $('#state').val()
				}, function(data) {
					showData("#main-content", data);
				});
			}
		});
		$('#delete').click(function() {
			var url = 'rest/user/delete';
			$.post(url, {
				id : $('#id').val()
			}, function(data) {
				showData("#main-content", data);
			});
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
			$("#index-page-title").html("${method}用户");
			$("#current-page-title").html("${method}用户");
		});
	</script>
</div>