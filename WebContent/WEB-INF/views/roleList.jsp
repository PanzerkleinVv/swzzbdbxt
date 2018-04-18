<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div
	<c:choose> <c:when test='${method == "select" || method == "selectNoCheck"}'>id="selectRole"</c:when><c:otherwise>class="mainContent"</c:otherwise></c:choose>>
	<c:if test="${role != null}">
		<div class="role ${level}">
			<span id="${role.roleId}" onclick="roleClick(this)">${role.roleName}</span>
		</div>
	</c:if>
</div>
<c:choose>
	<c:when test='${method == "selectNoCheck"}'>
		<script type="text/javascript">
			function roleSelect(target) {
				$("#msgAreaId").val($(target).attr("id"));
				if ($("#msgAreaName").prop('tagName') == 'INPUT') {
					$("#msgAreaName").val($(target).html());
				} else {
					$("#msgAreaName").html($(target).html());
				}
			}
			function roleClose() {
				$("#roleSelectSubmit").hide();
				$("#roleSelectAll").hide();
				$("#selectRole").remove();
			}
			function selectAll() {
				var msgAreaNameValue = $('#msgAreaName').val();
				if (msgAreaNameValue.indexOf('所有') == -1) {
					$('#msgAreaName').val($('#msgAreaName').val() + ' 所有');
				}
				roleClose();
			}
			function roleClick(target) {
				$(target).parent().nextAll().remove();
				if ($(target).parent().parent().attr("id") == "selectRole") {
					roleSelect($(target));
				}
				var url = "rest/role/openList";
				$.post(url, {
					method : 'selectNoCheck',
					roleId : $(target).attr("id")
				}, function(data) {
					$(target).parent().after(data);
				});
			}
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function roleSelect(target) {
				$("#userRoleId").val($(target).attr("id"));
				$("#userRoleName").val($(target).html());
				check(2);
			}
			function roleClose() {
				$("#selectRole").remove();
				$("#roleSelectSubmit").hide();
			}
			function roleClick(target) {
				$(target).parent().nextAll().remove();
				if ($(target).parent().parent().attr("id") == "selectRole") {
					roleSelect($(target));
				}
				var url = "rest/role/openList";
				$.post(url, {
					roleId : $(target).attr("id")
				}, function(data) {
					$(target).parent().after(data);
				});
			}
			function roleAdd(target) {
				$(target).parent().nextAll().remove();
				var pIdValue = $(target).parent().attr('id').substr(1);
				var url = "rest/role/addRole";
				$.post(url, {
					pId : pIdValue
				}, function(data) {
					$(target).parent().after(data);
				});
			}
			function checkRole(target) {
				var targetValue = $(target).val();
				var targetId = $(target).attr("id");
				var msg = $(target).next("span");
				if (targetValue == null || targetValue.length == 0) {
					msg.html("不能为空");
					msg.css("color", "#FF0000");
					return false;
				} else if (targetId == "roleSign"
						&& targetValue.search("[^a-z]+") != -1) {
					msg.html("地区拼音只能为小写英文");
					msg.css("color", "#FF0000");
					return false;
				} else {
					msg.html("OK");
					msg.css("color", "#00FF00");
					return true;
				}
			}
			function roleSubmit(target) {
				checkRole($(".role #roleName"));
				checkRole($(".role #roleSign"));
				checkRole($(".role #roleDesc"));
				var url = "rest/role/addRoleSubmit";
				var roleName = $(".role #roleName").val();
				var roleSign = $(".role #roleSign").val();
				var roleDesc = $(".role #roleDesc").val();
				var pId = $("input#pId").val();
				var pDiv = $(".role span#" + pId).parent();
				if (pId != null && pId.length > 0) {
					$.post(url, {
						pId : pId,
						roleName : roleName,
						roleSign : roleSign,
						roleDesc : roleDesc
					}, function(data) {
						pDiv.nextAll().remove();
						pDiv.after(data);
					});
				}
			}
		</script>
	</c:otherwise>
</c:choose>
<c:if test='${method != "select" && method != "selectNoCheck"}'>
	<script type="text/javascript">
		$(function() {
			$("#index-page-title").html("地区管理");
			$("#current-page-title").html("地区管理");
		});
	</script>
</c:if>