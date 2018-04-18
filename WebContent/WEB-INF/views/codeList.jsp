<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="items" id="${codeType}">
	<c:if test="${codes != null && codes.size() != 0}">
		<c:if test='${method != "select1"}'>
			<c:forEach items="${codes}" var="code">
				<div class="item">
					<span id="${code.id}" onclick="codeClick(this)">${code.name}</span>
				</div>
			</c:forEach>
		</c:if>
		<c:if test='${method == "select1"}'>
			<c:forEach items="${codes}" var="code">
				<div class="item">
					<span id="${code.id}" onclick="codeClick1(this)">${code.name}</span>
				</div>
			</c:forEach>
		</c:if>
	</c:if>
	<c:if test='${method != "select" && method != "select1"}'>
		<div class="item add">
			<span id="add"><input type="text" id="addCode"
				onblur="checkCode(this)" class="form-control placeholder-no-fix"/></span> <span id="addMsg"></span>
		</div>
	</c:if>
</div>
<c:if test='${method != "select" && method != "select1"}'>
	<script type="text/javascript">
		function checkCode(target) {
			var value = $(target).val();
			var msg = $(target).parent().next();
			if (value == null || value.length == 0) {
				msg.html("不能为空");
				msg.css("color", "#FF0000");
				return false;
			} else {
				msg.html("OK");
				msg.css('color', '#00FF00');
				if (msg.next().length <= 0) {
					msg
							.after("<button type='button' onclick='codeSubmit(this)' class='btn blue pull-right'>保存</button>");
				}
				return true;
			}
		}
		function codeClick(target) {
			var thisSpan = $(target);
			if (thisSpan.find("input#old").attr("id") != "old") {
				var old = thisSpan.html();
				thisSpan
						.html("<input type='hidden' id='old' value='" + old + "'/>");
				thisSpan.find("input#old").after(
						"<input type='text' id='new' onblur='checkNewCode(this)' value='"
								+ old + "' class='form-control placeholder-no-fix'/>");
			}
		}
		function checkNewCode(target) {
			var newCode = $(target);
			var oldCode = $(target).prev();
			if (newCode.val() != null && newCode.val().length != 0) {
				if (newCode.val() != oldCode.val()) {
					var url = "rest/code/save";
					$.post(url, {
						id : newCode.parent().attr("id"),
						name : newCode.val(),
						method : "update",
						type : newCode.parent().parent().parent().attr("id")
					}, function(data) {
						var head = newCode.parent().parent().parent().prev();
						head.nextAll().remove();
						head.after(data);
					});
					return true;
				}
			}
			newCode.parent().html(oldCode.val());
			return false;
		}
		function codeSubmit(target) {
			var code = $(target).prev().prev().find("#addCode");
			if (checkCode(code)) {
				var url = "rest/code/save";
				$.post(url, {
					name : code.val(),
					method : "insert",
					type : code.parent().parent().parent().attr("id")
				}, function(data) {
					var head = code.parent().parent().parent().prev();
					head.nextAll().remove();
					head.after(data);
				});
			}
		}
	</script>
</c:if>
<c:if test='${method == "select"}'>
	<style>
		div.item {
			display: inline;
		}
		div.item span{
			display: inline;
			color: #000000;
			border: 1px solid #4d90fe;
			background-color: #FFFFFF;
			cursor: default;
			line-height: 24px;
			word-break: keep-all;
			padding: 0 2px 0 2px;
			margin: 0 0 0 5px;
			font-size: 13px;
		}
		.spanSelected {
			display: inline !important;
			color: #FFFFFF !important;
			background-color: #4d90fe !important;
		}
	</style>
</c:if>