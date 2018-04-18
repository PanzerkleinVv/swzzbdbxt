<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div class="">
		<form action="" method="post">
			<input name="userUnit" id="userUnit" size="25" value="${userunit}"
				class="form-control placeholder-no-fix searchBox" type="text"
				autocomplete="off" placeholder="查找用户" />
			<button id="searchBut" type="button" class="btn blue">查询</button>
		</form>
	</div>
	<div style="color:${msg0};">${msg}</div>
	<div class="userTable">
		<div class="userRow userHeader">
			<span class="userItem1">单位名</span>
			<span class="userItem2">系统名</span>
			<span class="userItem3">操作</span>
		</div>
		<c:forEach var="user" items="${users}" varStatus="status">
			<div class="userRow">
				<span class="userItem1"> <input id="userId${status.index}" name="userId"
						type="hidden" value="${user.userId}" />${user.userUnit}
				</span>
				<span class="userItem2">${user.userName}</span>
				<span class="userItem3"><button id="${status.index}" type="button"
						class="btn blue">修改</button>
				</span>
			</div>
		</c:forEach>
	</div>

	<script type="text/javascript">
		$('button').click(function() {
			var butId = $(this).attr('id');
			if (butId == 'searchBut') {
				var url = 'rest/user/admin';
				$.post(url, {
					userUnit : $('#userUnit').val()
				}, function(data) {
					$('#main-content').html(data);
				});
			} else {
				var dataId = "userId" + butId;
				var url = 'rest/user/info';
				$.post(url, {
					userId : $('#' + dataId).val()
				}, function(data) {
					$('#main-content').html(data);
				});
			}
		});
		$('form').keypress( function(e) {
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