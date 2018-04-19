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
		<span>权限类型：</span> <span><select id="permissionId" name="permissionId"
			onblur="check(4)" class="form-control placeholder-no-fix">
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
			<c:set var="i" value="0"/>
				<c:forEach var="userState0" items="${sessionScope.userState}"
					varStatus="status0">
					<option value="${i}"
						<c:if test="${user.state == i}"> selected="selected"</c:if>>${userState0}</option>
					<c:set var="i" value="${i+1}"/>
				</c:forEach>
		</select></span><span id='msg3'></span>
	</div>
	</c:if>
	<div class="infoButton">
		<button id="saveBut" type="button" class="btn blue">保存</button>
		<c:if test='${user != null && user.id != null}'>
			<button id="delete" type="button" class="btn blue">删除</button>
		</c:if>
	</div>
	<script type="text/javascript">
		function check(num) {
			var value;
			var msg;
			if (num == 0) {
				value = $('#userUnit').val();
				msg = $('#msg0');
				if (value == null || value.length < 1) {
					msg.html("单位名不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 1) {
				value = $('#userName').val();
				msg = $('#msg1');
				if (value == null || value.length < 1) {
					msg.html("系统名不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else if (value.search('[^\\w\\d]+') > 0) {
					msg.css('color', '#FF0000');
					msg.html('系统名只能是英文或数字');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 2) {
				value = $('#userRoleId').val();
				msg = $('#msg2');
				if (value == null || value.length < 1) {
					msg.html("所属地区不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 3) {
				value = $('#userPhone').val();
				msg = $('#msg3');
				if (value == null || value.length < 1) {
					msg.html("联系电话不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 4) {
				value = $('#userAttn').val();
				msg = $('#msg4');
				if (value == null || value.length < 1) {
					msg.html("联系人不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			}
		}
		$('#userRoleName').click(function() {
			var pDiv = $(this).parent();
			$("#roleSelectSubmit").css("display", "inline");
			if (pDiv.next().attr("id") != "selectRole") {
				var url = 'rest/role/list';
				$.post(url, {
					method : "select"
				}, function(data) {
					pDiv.after(data);
				});
			}
		});
		$('#saveBut').click(function() {
			var url = 'rest/user/modify';
			if (check(0) && check(1) && check(2) && check(3)) {
				$.post(url, {
					userId : $('#userId').val(),
					userUnit : $('#userUnit').val(),
					userName : $('#userName').val(),
					userRoleId : $('#userRoleId').val(),
					userAttn : $('#userAttn').val(),
					userPhone : $('#userPhone').val()
				}, function(data) {
					$('#main-content').html(data);
				});
			}
		});
		$('#pswBut').click(function() {
			var url = 'rest/user/psw0';
			$.post(url, {
				userId : $('#userId').val(),
				userRoleId : $('#userRoleId').val()
			}, function(data) {
				$('#main-content').html(data);
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
			$("#index-page-title").html("编辑用户");
			$("#current-page-title").html("编辑用户");
		});
	</script>
</div>