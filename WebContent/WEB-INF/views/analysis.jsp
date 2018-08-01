<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="mainContent">
	<div class="analysis-toolbarBox">
		<form id="analysisForm">
			<div class="analysis-title">统计方式</div>
			<div class="analysis-method-bar">
				<span class="analysis-method analysis-active" id="byRole">按承办处室统计</span>
				<span class="analysis-method" id="byCreateTime">按立项时间统计</span>
			</div>
			<div class="analysis-condition byRole">
				<span>承办方式：</span>
				<span>
					<select id="roleType" name="type" class="form-inline">
						<option value="0" selected="selected">所有</option>
						<option value="1">仅主办</option>
					</select>
				</span>
			</div>
			<div class="analysis-condition byCreateTime hidden-analysis-condition">
				<span>立项年份：</span>
				<span>
					<select id="createYear" name="year" disabled="disabled" class="form-inline">
						<c:forEach var="year" items="${years}">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>
				</span>
				<span>立项月份：</span>
				<span>
					<select id="createMonth" name="month" disabled="disabled" class="form-inline">
						<option id="monthAll" value="0" selected="selected">所有</option>
					</select>
				</span>
			</div>
			<div class="analysis-method-bar">
				<button class="btn green" type="submit">统计</button>
			</div>
		</form>
	</div>
	<div class="analysis-result-bar">
		<div class="analysis-title">统计图表</div>
		<div class="analysis-result" id="analysisChart"></div>
		<div class="analysis-title">统计详表</div>
		<div class="analysis-result" id="analysisTable"></div>
	</div>
	<div id="msgList"></div>
	<script>
		$(function() {
			$("#index-page-title").html("统计信息");
			$("#current-page-title").html("统计信息");
		});
		
		$(".analysis-method").click(
				function() {
					var target = $(this);
					target.toggleClass("analysis-active");
					var method = $(this).attr("id");
					var condition = $("." + method);
					if (target.is(".analysis-active")) {
						condition.removeClass("hidden-analysis-condition");
						condition.find("select").attr("disabled", false);
					} else {
						condition.addClass("hidden-analysis-condition");
						condition.find("select").attr("disabled", true);
					}
					if ($("#byRole").is(".analysis-active")
							&& $("#byCreateTime").is(".analysis-active")) {
						$("#createMonth").attr("disabled", false);
					} else {
						$("#createMonth").attr("disabled", true);
						$("#createMonth").val("0");
					}
				});

		$("#createYear").change(
				function() {
					if (!($("#createMonth").prop("disabled"))) {
						$.ajax({
							type : "GET",
							url : 'rest/analysis/getMonths',
							data : {
								'year' : $("#createYear").val()
							},
							dataType : "json",
							success : function(msg) {
								$('#monthAll').nextAll().remove();
								var tempText = ""
								$.each(msg, function(i, n) {
									tempText += "<option value='" + n + "'>"
											+ n + "月</option>";
								})
								$('#monthAll').after(tempText);
							}
						});
					}
				});
		
		$(function() {
			$.ajax({
				type : "GET",
				url : 'rest/analysis/getMonths',
				data : {
					'year' : $("#createYear").val()
				},
				dataType : "json",
				success : function(msg) {
					$('#monthAll').nextAll().remove();
					var tempText = ""
					$.each(msg, function(i, n) {
						tempText += "<option value='" + n + "'>"
								+ n + "月</option>";
					})
					$('#monthAll').after(tempText);
				}
			});
		});
		
		$("#analysisForm").submit(function() {
			$("#analysisForm button").attr("disabled", true);
			$("#analysisForm button").html("统计中……");
			$(this).ajaxSubmit({
				url : 'rest/analysis/analysis',
				type : "post",
				cache : false,
				dataType : "json",
				success : function(data) {
					alert(data);
					$("#analysisForm button").attr("disabled", false);
					$("#analysisForm button").html("统计");
				},
				error : function(data) {
					alert("统计出错");
					$("#analysisForm button").attr("disabled", false);
					$("#analysisForm button").html("统计");
				}
			});
			return false;
		});
	</script>
</div>