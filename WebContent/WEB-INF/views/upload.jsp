<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="mainContent">
	<form id="uploadForm" enctype="multipart/form-data" method="post"
		autocomplete="off">
		<div id="uploadBox">
			<input type='text' id='msgId' name='id' value="${id}"
				style="display: none"
				class="form-control placeholder-no-fix uploadInput" /> <input
				type='text' id='sequenceNumber' name='sequence'
				value="${sequenceNumber}" style="display: none"
				class="form-control placeholder-no-fix uploadInput" />
			<div>
				<div>
					<span class="uploadTitle">督察事项：</span> <span
						class="uploadItem withInput"> <input type='text' id='name'
						name='name' value="${msg.name}"
						class="form-control placeholder-no-fix uploadInput"
						onblur="check(0)" />
					</span> <span id='msg0'></span>
				</div>
			</div>
			<div>
				<div>
					<span class="uploadTitle">立项时间：</span> <span
						class="uploadItem withInput"> <input size="16" type="text"
						id="createTime" name='createTime'
						value="<fmt:formatDate value='${msg.createTime}'
						type='DATE' pattern='yyyy-MM-dd' />"
						readonly
						class="form_date form-control placeholder-no-fix halfWidth"
						onblur="check(1)">
					</span> <span id='msg1'></span>
				</div>
			</div>
			<div>
				<div>
					<span class="uploadTitle">主办处室：</span> <span
						class="uploadItem withInput"> <select id="role" name="role"
						multiple="multiple" onblur="check(3)">
							<c:set var='i' value="0"></c:set>
							<c:forEach var="role" items="${sessionScope.roles}" begin="1">
								<option id="role_${role.id}" value="${role.id}"
									<c:if test="${msgSponsorSelect[i] eq role.id}"><c:set var="i" value="${i+1}" />selected="selected"</c:if>>${role.roleName}</option>
							</c:forEach>
					</select>
					</span> <span id='msg3'></span>
				</div>
			</div>
			<div>
				<div>
					<span class="uploadTitle">协办处室：</span> <span
						class="uploadItem withInput"> <select id="assitrole"
						name="assitrole" multiple="multiple">
							<c:set var='i' value="0"></c:set>
							<c:set var='j' value="0"></c:set>
							<c:forEach var="role" items="${sessionScope.roles}" begin="1">
								<option id="assitrole_${role.id}" value="${role.id}"
									<c:if test="${msgCoSponsorSelect[i] eq role.id}"><c:set var="i" value="${i+1}" />selected="selected"</c:if>>${role.roleName}</option>
							</c:forEach>
					</select>
					</span>
				</div>
			</div>
			<div>
				<div>
					<span class="uploadTitle">立项依据：</span> <span
						class="uploadItem withInput"> <select id="basis"
						name="basis" class="input-sm form-inline" onblur="check(2)"
						onchange="basisChange()">
							<option selected="selected"></option>
							<c:forEach var="basis" items="${sessionScope.Basis}"
								varStatus="state">
								<option value="${basis}"
									<c:if test="${basis eq basisSelect}"><c:set var="i" value="${i+1}" />selected="selected"</c:if>>${basis}</option>
							</c:forEach>
					</select> <input type='text' style="display: none;" id='msgBasis'
						name='msgBasis' value="${msgBasis}"
						class="input-sm form-inline input-longPlus" onblur="check(2)" />
					</span> <span id='msg2'></span>
				</div>
			</div>
			<div>
				<div>
					<span class="uploadTitle">办结时限：</span> <span
						class="uploadItem withInput"> <input size="16" type="text"
						id="limitTime" name="limitTime"
						value="<fmt:formatDate value='${msg.limitTime}'
						type='DATE' pattern='yyyy-MM-dd' />"
						readonly
						class="form_date form-control placeholder-no-fix halfWidth"
						onblur="check(4)" />
					</span> <span id='msg4'></span>
				</div>
			</div>
			<div>
				<div>
					<span class="uploadTitle">附&emsp;&emsp;件：</span> <span
						class="uploadItem withInput"> <c:forEach var="attach"
							items="${attachs}">
							<div id='attach_${attach.id}'>
								<a class="red" onclick="deleteFile('${attach.id}')">[删除]</a>${attach.attachFileName}</div>
						</c:forEach> <label><button id="addFile" type="button"
								class="btn green" onclick="addAttach(this)">增加</button></label>
					</span> <span id='msg5'></span>
				</div>
			</div>
			<input type="hidden" id="msgStatus" name="status" value='0' />
			<div class="uploadButton">
				<span>
					<button id="insert" type="button" class="btn blue"
						onclick="insertMsg(this)">保存</button>
				</span> <span>
					<button id="delete" type="button" class="btn red" onclick="dd()">删除</button>
				</span> <span>
					<button id="send" type="button" class="btn blue"
						onclick="sendMsg(this)">发布</button>
				</span>
			</div>
		</div>
	</form>
	<div style="display: none;" id="error">${error}</div>
	
	<script type="text/javascript">
		var parentTarget = "#" + $(".mainContent").parent().attr("id");
		function basisChange() {
			var basis = $("#basis").val();
			if (basis == "自定义") {
				$("#msgBasis").show();
			} else {
				$("#msgBasis").hide();
			}
		}

		function deleteFile(id) {
			$.ajax({
				type : "POST",
				url : 'rest/attach/delete',
				data : {
					'id' : id
				},
				dataType : "text",
				async : false,
				success : function(msg) {
					if ("true" == msg.match("true")) {
						$('#attach_' + id).remove();
					} else {
						$('#attach_' + id).append(
								'<i class="red">&emsp;删除失败</i>');
					}
				}
			});
			return false;
		}

		var fileBand = 0;

		function addAttach(target) {
			$(target)
					.before(
							"<div><a class='red' onclick='removeFile(this)'>[删除]</a><label for='file_" + fileBand + "'>请选择文件</label><input type='file' name='files' id='file_"
									+ fileBand
									+ "' style='filter:alpha(opacity=0);opacity:0;height:0;' onchange='fileChange(this)' /></div>");
			$(target).parent().attr('for', 'file_' + fileBand);
			fileBand++;
		}

		function fileChange(target) {
			if (checkFile(target)) {
				fileBand++;
			} else {
				removeFile($(target));
			}
		}

		function checkFile(target) {
			var msg = $("#msg5");
			if (target.value == "") {
				msg.html("请选择文件");
				msg.css('color', '#FF0000');
				return false;
			} else {
				var fileSize;
				if (window.navigator.userAgent.indexOf("MSIE")>=1) {
					msg.html("注意：单个文件不能大于3Mb");
					msg.css('color', '#000000');
					var filepath = target.value;
					var pos = filepath.lastIndexOf("\\");
					var filename = filepath.substring(pos + 1);
					$(target).prevAll('label').html(filename);
					return true;
				} else {
					fileSize = target.files[0].size;
				}
				var maxSize = 3 * 1024 * 1024;
				if (fileSize > maxSize) {
					msg.html("单个文件不能大于3Mb");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("");
					var filepath = target.value;
					var pos = filepath.lastIndexOf("\\");
					var filename = filepath.substring(pos + 1);
					$(target).prevAll('label').html(filename);
					return true;
				}
			}
		}

		function removeFile(target) {
			$(target).parent().remove();
		}

		function insertMsg(target) {
			debugger;
			var form = $("#uploadForm");
			disabledAll();
			$("#msgStatus").val("0");
			if (check(0) && check(1) && check(2) && check(3) && check(4)) {
				$('input[type="file"]').each(function(i, n) {
					if (!checkFile(n)) {
						$(n).parent().remove();
					}
				});
				form.ajaxSubmit({
					url : 'rest/msg/insert',
					type : "post",
					cache : false,
					success : function(data) {
						showData(parentTarget, data);
						if ($("#error").html().length > 0) {
							alert("保存失败：" + $("#error").html());
						} else {
							alert("保存成功！");
						}
						$(document).ready(function() {
							var basis = $("#basis").val();
							if (basis == "自定义") {
								$("#msgBasis").show();
							} else {
								$("#msgBasis").hide();
							}
						})
					},
					error : function(data) {
						alert("请检查附件大小");
						enabledAll();
					}
				});
			} else {
				alert("必需字段不能为空");
				enabledAll();
			}
		};

		//删除按钮
		function dd() {
			disabledAll();
			if ($("#msgId").val() == "") {
				var obj = document.getElementById('fileID');
				obj.outerHTML = obj.outerHTML;
				$("#name").val("");
				$("#basis").val("");
				$("#msgBasis").val("");
				$("#createTime").val("");
				$("#limitTime").val("");
				enabledAll();
			} else {
				var url = 'rest/msg/detele';
				$.post(url, {
					id : $("#msgId").val()
				}, function(data) {
					alert("删除成功，重新上传");
					showData(parentTarget, data);
				});
			}
		}
		//发布按钮

		function sendMsg(target) {
			var form = $(target).parents("form");
			disabledAll();
			$("#msgStatus").val("1");
			if (check(0) && check(1) && check(2) && check(3) && check(4)) {
				$('input[type="file"]').each(function(i, n) {
					if (!checkFile(n)) {
						$(n).parent().remove();
					}
				});
				form.ajaxSubmit({
					url : 'rest/msg/insert',
					type : "post",
					cache : false,
					success : function(data) {
						showData(parentTarget, data);
						if ($("#error").html().length > 0) {
							alert("保存失败：" + $("#error").html());
						} else {
							alert("发布成功");
						}
					},
					error : function(data) {
						alert("请检查附件大小");
						enabledAll();
					}
				});
			} else {
				alert("必需字段不能为空");
				enabledAll();
			}
		};

		//非空校验
		function check(num) {
			var value;
			var msg;
			if (num == 0) {
				value = $("#name").val();
				msg = $('#msg0');
				if (value == null || value.length < 1) {
					msg.html("督查事项不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 1) {
				value = $("#createTime").val();
				msg = $('#msg1');
				if (value == null || value.length < 1) {
					return false;
				} else {
					return true;
				}
			} else if (num == 2) {
				value = $("#basis").val();
				msg = $('#msg2');
				if (value == null || value.length < 1) {
					msg.html("立项依据不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else if (value == "自定义" && $("#msgBasis").val().length < 1) {
					msg.html("立项依据不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 3) {
				value = $("#role").val();
				msg = $('#msg3');
				if (value == null || value.length < 1) {
					msg.html("主办处室不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			} else if (num == 4) {
				value = $("#limitTime").val();
				msg = $('#msg4');
				if (value == null || value.length < 1) {
					return false;
				} else {
					return true;
				}
			}
		}

		function disabledAll() {
			$("#insert").attr("disabled", true);
			$("#delete").attr("disabled", true);
			$("#send").attr("disabled", true);
		}

		function enabledAll() {
			$("#insert").removeAttr("disabled");
			$("#delete").removeAttr("disabled");
			$("#send").removeAttr("disabled");
		}

		$(function() {
			$("#index-page-title").html("督察立项");
			$("#current-page-title").html("督察立项");
		});
		
		$(document).ready(function() {
			$(".form_date").datepicker({
				format : 'yyyy-mm-dd',
				language : 'zh-CN',
				autoclose : true
			});
		});
		$(document).ready(function() {
			if ($('#msgBasis').val() != null && $('#msgBasis').val() != "") {
				var array = new Array();
				$("#basis option").each(function() {
					array.push($(this).val());
				});
				if ($.inArray($('#msgBasis').val(), array) < 0) {
					$("#basis").val("自定义");
					basisChange();
				}
			}
		});

		$(document).ready(
				function() {
					$('#role option').each(
							function(i, n) {
								if ($(n).attr("selected") == 'selected') {
									$("#assitrole_" + $(n).val()).attr(
											'disabled', true);
								} else {
									$("#assitrole_" + $(n).val()).attr(
											'disabled', false);
								}
							});
					$('#assitrole option').each(function(i, n) {
						if ($(n).attr("selected") == 'selected') {
							$("#role_" + $(n).val()).attr('disabled', true);
						} else {
							$("#role_" + $(n).val()).attr('disabled', false);
						}
					});
					$('#role').multiselect(
							{
								enableFiltering : true,
								nonSelectedText : '请选择主办处室',
								filterPlaceholder : '搜索',
								nSelectedText : '项被选中',
								includeSelectAllOption : true,
								selectAllText : '全选/取消全选',
								allSelectedText : '已选中所有主办处室',
								maxHeight : 300,
								onChange : function(option, checked) {
									if (checked) {
										$("#assitrole_" + $(option).val())
												.attr('disabled', true);
									} else {
										$("#assitrole_" + $(option).val())
												.attr('disabled', false);
									}
									$("#assitrole").multiselect('refresh');
								}
							});
					$('#assitrole').multiselect(
							{
								enableFiltering : true,
								nonSelectedText : '请选择协办处室',
								filterPlaceholder : '搜索',
								nSelectedText : '项被选中',
								includeSelectAllOption : true,
								selectAllText : '全选/取消全选',
								allSelectedText : '已选中所有协办处室',
								maxHeight : 300,
								onChange : function(option, checked) {
									if (checked) {
										$("#role_" + $(option).val()).attr(
												'disabled', true);
									} else {
										$("#role_" + $(option).val()).attr(
												'disabled', false);
									}
									$("#role").multiselect('refresh');
								}
							});
				});
	</script>

</div>
