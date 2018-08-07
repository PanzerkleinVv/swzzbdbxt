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
		var roleNames = [];
		$(function() {
			$("#index-page-title").html("统计信息");
			$("#current-page-title").html("统计信息");
			$.ajax({
				type : "GET",
				url : 'rest/analysis/getRoleNames',
				dataType : "json",
				success : function(msg) {
					$.each(msg, function(i, n) {
						roleNames.push(n.roleName);
					});
					roleNames[0] = '';
				}
			});
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
					var analysisChart = $("#analysisChart");
					var analysisTable = $("#analysisTable");
					if (data.results == null) {
						analysisChart.html("无数据");
						analysisTable.html("无数据");
					} else {
						var flagRoleId = data.results[0].roleId;
						var flagYear = data.results[0].year;
						var flagMonth = data.results[0].month;
						var flagType = $("#roleType").val();
						analysisChart.html("<div class='analysisEchart'></div>");
						var analysisEchart = echarts.init($(".analysisEchart")[0]);
						analysisTable.html("<table></table>");
						var table = analysisTable.find("table");
						if (flagRoleId == 0 && flagYear != 0 && flagMonth != 0) {
							var stops = [];
							var onworks = [];
							var overtimes = [];
							var j = 0;
							var xText = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月',flagYear + '年以前'];
							table.append("<tr><th>月份</th><th>在办</th><th>逾期</th><th>阶段性办结</th><th>办结</th><th>中止</th></tr>");
							for (var i = 0; i < 13; i++) {
								table.append("<tr></tr>");
								table.find("tr").last().append("<td>" + xText[i] + "</td>");
								if ((j < data.simpleResults.length && i + 1 == data.simpleResults[j].month) || (j < data.simpleResults.length && data.simpleResults[j].month == 0)) {
				            		stops.push(data.simpleResults[j].stop);
				            		onworks.push(data.simpleResults[j].onwork);
				            		overtimes.push(data.simpleResults[j].overtime);
				            		table.find("tr").last().append("<td>" + data.results[j].onwork + "</td>");
				            		table.find("tr").last().append("<td>" + data.results[j].overtime + "</td>");
				            		table.find("tr").last().append("<td>" + data.results[j].partialDone + "</td>");
				            		table.find("tr").last().append("<td>" + data.results[j].done + "</td>");
				            		table.find("tr").last().append("<td>" + data.results[j].stop + "</td>");
				            		j++;
				            	} else {
				            		stops.push(0);
				            		onworks.push(0);
				            		overtimes.push(0);
				            		table.find("tr").last().append("<td>" + 0 + "</td>");
				            		table.find("tr").last().append("<td>" + 0 + "</td>");
				            		table.find("tr").last().append("<td>" + 0 + "</td>");
				            		table.find("tr").last().append("<td>" + 0 + "</td>");
				            		table.find("tr").last().append("<td>" + 0 + "</td>");
				            	}
							}
							option = {
								title : {
									text: flagYear + '年立项事项统计'
								},
								tooltip : {
									trigger: 'axis',
									axisPointer : {            // 坐标轴指示器，坐标轴触发有效
										type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
									},
									formatter: function (params){
										return params[0].name + '<br/>' + params[0].seriesName + ' : ' + params[0].value + '<br/>' + params[1].seriesName + ' : ' + params[1].value + '<br/>' + params[2].seriesName + ' : ' + params[2].value;
									}
								},
								legend: {
									selectedMode:false,
									data:['在办', '逾期', '不在办']
								},
								xAxis : [
									{
										type : 'category',
										data : xText,
										axisLabel : {
											interval : 0,
											rotate : -30
										}
									}
								],
								yAxis : [
									{
										type : 'value'
									}
								],
								series : [
									{
										name:'不在办',
										type:'bar',
										stack: '总量',
										barCategoryGap: '50%',
										itemStyle: {
											normal: {
								 				color: 'gray',
								 			}
										},
										data: stops
									},
									{
										name:'在办',
										type:'bar',
										stack: '总量',
										itemStyle: {
											normal: {
												color: 'deepskyblue',
											}
										},
										data: onworks
									},
									{
										name:'逾期',
										type:'bar',
										stack: '总量',
										itemStyle: {
											normal: {
												color: 'indianred',
											}
										},
										data: overtimes
									}
								]
							};
							analysisEchart.setOption(option);
						} else if (flagRoleId != 0) {
							flagMonth = $("#createMonth").val();
							var stops = [];
							var onworks = [];
							var overtimes = [];
							var xText = roleNames;
							table.append("<tr>处室<th></th><th>在办</th><th>逾期</th><th>阶段性办结</th><th>办结</th><th>中止</th></tr>");
							for (var i = 1; i < xText.length; i++) {
								table.append("<tr></tr>");
								table.find("tr").last().append("<td>" + xText[i] + "</td>");
								var onwork = 0;
								var overtime = 0;
								var partialDone = 0;
								var done = 0;
								var stop = 0;
								var simpleStop = 0;
								var simpleOnwork = 0;
								var simpleOvertime = 0;
								for (var j = 0; j < data.simpleResults.length; j++) {
									if (i == data.simpleResults[j].roleId) {
										simpleStop += data.simpleResults[j].stop;
										simpleOnwork += data.simpleResults[j].onwork;
										simpleOvertime += data.simpleResults[j].overtime;
										onwork += data.results[j].onwork;
										overtime += data.results[j].overtime;
										partialDone += data.results[j].partialDone;
										done += data.results[j].done;
										stop += data.results[j].stop;
					            	}
								}
								stops.push(simpleStop);
			            		onworks.push(simpleOnwork);
			            		overtimes.push(simpleOvertime);
			            		table.find("tr").last().append("<td>" + onwork + "</td>");
			            		table.find("tr").last().append("<td>" + overtime + "</td>");
			            		table.find("tr").last().append("<td>" + partialDone + "</td>");
			            		table.find("tr").last().append("<td>" + done + "</td>");
			            		table.find("tr").last().append("<td>" + stop + "</td>");
							}
							option = {
									title : {
										text: (flagYear == 0 ? '' : flagYear + '年' + (flagMonth == 0 ? '' : flagMonth + '月')) + (flagType == 0 ? '经办' : '主办') + '事项统计'
									},
									tooltip : {
										trigger: 'axis',
										axisPointer : {            // 坐标轴指示器，坐标轴触发有效
											type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
										},
										formatter: function (params){
											return params[0].name + '<br/>' + params[0].seriesName + ' : ' + params[0].value + '<br/>' + params[1].seriesName + ' : ' + params[1].value + '<br/>' + params[2].seriesName + ' : ' + params[2].value;
										}
									},
									legend: {
										selectedMode:false,
										data:['在办', '逾期', '不在办']
									},
									xAxis : [
										{
											type : 'category',
											data : xText,
											axisLabel : {
												interval : 0,
												rotate : -30
											}
										}
									],
									yAxis : [
										{
											type : 'value'
										}
									],
									series : [
										{
											name:'不在办',
											type:'bar',
											stack: '总量',
											barCategoryGap: '50%',
											itemStyle: {
												normal: {
									 				color: 'gray',
									 			}
											},
											data: stops
										},
										{
											name:'在办',
											type:'bar',
											stack: '总量',
											itemStyle: {
												normal: {
													color: 'deepskyblue',
												}
											},
											data: onworks
										},
										{
											name:'逾期',
											type:'bar',
											stack: '总量',
											itemStyle: {
												normal: {
													color: 'indianred',
												}
											},
											data: overtimes
										}
									]
								};
								analysisEchart.setOption(option);
						}
					}
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