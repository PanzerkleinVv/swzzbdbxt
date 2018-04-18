<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div id="uploadBox">
		<div>
			<div>
				<span class="uploadTitle">标题：</span>
				<span class="uploadItem withInput">
					<input type='text' id='msgTitle' value='' class="form-control placeholder-no-fix uploadInput"/>
				</span>
				<span id='msg0' class="uploadItem"></span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">工作类型：</span>
				<span id="msgServiceName" onclick="getCode(0)" class="uploadItem"></span>
				<span id='msg1' class="uploadItem"></span>
			</div>
			<input type='hidden' id='msgServiceId' value='' />
		</div>
		<div>
			<div>
				<span class="uploadTitle">信息类型：</span>
				<span id="msgTypeName" onclick="getCode(1)" class="uploadItem"></span>
				<span id='msg2' class="uploadItem"></span>
			</div>
			<input type='hidden' id='msgTypeId' value='' />
		</div>
		<div>
			<span class="uploadTitle">上报单位：</span>
			<span id="msgUserUnit" class="uploadItem">${msgUserUnit}</span>
		</div>
		<div>
			<div>
				<span class="uploadTitle">所属地区：</span>
				<span id="msgAreaName" onclick="getCode(2)" class="uploadItem"></span>
				<span id='msg3' class="uploadItem"></span>
			</div>
			<div>
				<input name="msgAreaId" id="msgAreaId"
					type="hidden" value="" /> 
				<span class="uploadItem">
					<button id="roleSelectSubmit" type="button" style="display: none;"
							class="btn blue" onclick="roleClose()">确定</button>
				</span>
			</div>
		</div>
		<div>
			<span class="uploadTitle">联系人姓名：</span>
			<span class="uploadItem withInput"><input type="text" id="msgAttn" value='${msgAttn}' class="form-control placeholder-no-fix uploadInput"/></span>
			<span id='msg4' class="uploadItem"></span>
		</div>
		<div>
			<span class="uploadTitle">联系人电话：</span>
			<span class="uploadItem withInput"><input type="text" id="msgPhone" value='${msgPhone}' class="form-control placeholder-no-fix uploadInput"/></span>
			<span id='msg5' class="uploadItem"></span>
		</div>
		<div>
			<div><span class="uploadTitle">正文：</span><span id='msg6' class="uploadItem"></span></div>
			<div id="summernote" class="uploadItem"></div>
		</div>
		<div class="uploadButton">
			<span>
				<button id="uploadSubmit" type="button" class="btn blue"
					onclick="upload()">上传</button>
			</span>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var num = 0;
		while (num < 4) {
			getCode(num);
			num++;
		}
	});

	function getCode(num) {
		var url;
		var target;
		var method;
		switch (num) {
		case 0:
			url = "rest/code/service";
			target = $("#msgServiceId");
			method = "select";
			break;
		case 1:
			url = "rest/code/type";
			target = $("#msgTypeId");
			method = "select";
			break;
		case 2:
			target = $("#msgAreaId");
			$("#roleSelectSubmit").css("display", "inline");
			if (target.next().attr("id") != "selectRole") {
				url = "rest/role/list";
				method = "selectNoCheck";
			}
			$("#roleSelectSubmit").show();
			break;
		}
		var nextDiv = $(target).next();
		if(nextDiv.attr("id") != "selectRole" && nextDiv.attr("id") != "type" && nextDiv.attr("id") != "service") {
			$.get(url, {
				method : method
			}, function(data) {
				$(target).after(data);
				$(target).next().addClass("uploadItem");
			});
		}
	}
	
	function check(num) {
		var value;
		var msg = $('#msg' + num);
		switch (num) {
		case 0:
			value = $("#msgTitle").val();
			break;
		case 1:
			value = $("#msgServiceId").val();
			break;
		case 2:
			value = $("#msgTypeId").val();
			break;
		case 3:
			value = $("#msgAreaId").val();
			break;
		case 4:
			value = $("#msgAttn").val();
			break;
		case 5:
			value = $("#msgPhone").val();
			break;
		case 6:
			value = $("#summernote").summernote('code');
			break;
		}
		if (value == null || value.length < 1) {
			msg.html("不能为空");
			msg.css('color', '#FF0000');
			return false;
		} else {
			msg.html("OK");
			msg.css('color', '#00FF00');
			return true;
		}
	}
	
	function checkUpload() {
		var num = 0;
		var result = true;
		while (num < 7) {
			if (check(num) == false) {
				result = false;
			}
			num++;
		}
		return result;
	}

	function upload() {
		if (checkUpload() == true) {
			var url = 'rest/msg/uploadMsg';
			$.post(url, {
				msgServiceId : $("#msgServiceId").val(),
				msgTypeId : $("#msgTypeId").val(),
				msgAreaId : $("#msgAreaId").val(),
				msgTitle : $("#msgTitle").val(),
				msgAttn : $("#msgAttn").val(),
				msgPhone : $("#msgPhone").val(),
				msgContent : $("#summernote").summernote('code')
			}, function(data) {
				$('.mainContent').html(data);
			});
		}
	}

	$(document).ready(function() {
		$('#summernote').summernote({
			height : 500,
			minHeight : null,
			maxHeight : null,
			lang: 'zh-CN',
			toolbar: [
				['style', ['bold', 'italic', 'underline', 'clear']],
				['fontsize', ['fontname', 'fontsize']],
				['color', ['color']],
				[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
				[ 'Insert', [ 'table' ] ] ],
			fontNames: ['宋体', '黑体', '楷体', '微软雅黑'],
			disableDragAndDrop: true
		});
	});
	
	function codeClick(target) {
		var thisSpan = $(target);
		var codeItems = thisSpan.parents(".items");
		var idInput;
		var nameSpan;
		var codeType = codeItems.attr("id");
		if (codeType == 'service') {
			idInput = $(msgServiceId);
			nameSpan = $(msgServiceName);
		} else if (codeType == 'type') {
			idInput = $(msgTypeId);
			nameSpan = $(msgTypeName);
		}
		idInput.val(thisSpan.attr('id'));
		nameSpan.html(thisSpan.html());
		codeItems.remove();
	}
	
	$(function() {
		$("#index-page-title").html("信息上传");
		$("#current-page-title").html("信息上传");
	});
</script>