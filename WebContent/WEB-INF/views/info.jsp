<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div class="infoMethod">${method}用户
		<input name="userId" id="userId" type="hidden"
			value="<c:if test='${user != null}'>${user.userId}</c:if>" />
	</div>
	<div style="color:${msg0};">${msg}</div>
	<div>
		<span>单位名：</span> <span><input name="userUnit" id="userUnit"
			type="text" onblur="check(0)"
			value="<c:if test='${user != null}'>${user.userUnit}</c:if>"
			class="form-control placeholder-no-fix" /></span><span id='msg0'></span>
	</div>
	<div>
		<span>系统名：</span> <span><input name="userName" id="userName"
			type="text" onblur="check(1)"
			value="<c:if test='${user != null}'>${user.userName}</c:if>"
			class="form-control placeholder-no-fix" /></span><span id='msg1'></span>
	</div>
	<div>
		<span>所属地区：</span> <span><input name="userRoleName"
			id="userRoleName" type="text" readonly="readonly" onblur="check(2)"
			value="<c:if test='${userRole != null}'>${userRole.roleName}</c:if>"
			style="cursor: default;" class="form-control placeholder-no-fix" /></span><span
			id='msg2'></span><input name="userRoleId" id="userRoleId"
			type="hidden"
			value="<c:if test='${user != null}'>${user.userRoleId}</c:if>" /> <span>
			<span><button id="roleSelectSubmit" type="button"
					style="display: none;" class="btn blue" onclick="roleClose()">确定</button></span>
		</span>
	</div>
	<div>
		<span>联系人：</span> <span><input name="userAttn" id="userAttn"
			type="text" onblur="check(4)"
			value="<c:if test='${user != null}'>${user.userAttn}</c:if>"
			class="form-control placeholder-no-fix" /></span><span id='msg4'></span>
	</div>
	<div>
		<span>联系电话：</span> <span><input name="userPhone" id="userPhone"
			type="text" onblur="check(3)"
			value="<c:if test='${user != null}'>${user.userPhone}</c:if>"
			class="form-control placeholder-no-fix" /></span><span id='msg3'></span>
	</div>
	<div class="infoButton">
		<button id="saveBut" type="button" class="btn blue">保存</button>
	<c:if test='${user != null && user.userId != null}'>
		<button id="pswBut" type="button" class="btn blue">还原密码</button>
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