<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mainContent">
	<div id="uploadBox">
		<div>
			<div>
				<span class="uploadTitle">立项号：</span>
				<span class="uploadItem withInput">
					<input type='text' id='sequence' value='' class="form-control placeholder-no-fix uploadInput"/>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">督查事项：</span>
				<span class="uploadItem withInput">
					<input type='text' id='name' value='' class="form-control placeholder-no-fix uploadInput"/>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">立项时间：</span>
				<span class="uploadItem withInput">
					<input size="16" type="text" id="create_time" value="" readonly
						class="form_date form-control placeholder-no-fix halfWidth"> 
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">立项依据：</span>
				<span class="uploadItem withInput">
					<input type='text' id='basis' value='' class="form-control placeholder-no-fix uploadInput"/>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">主办处室：</span>
				<span class="uploadItem withInput">
					<select id="role">
						<c:forEach var="role" items="${sessionScope.roles}">
							<option value="${roles.id}">${role.roleName}</option>
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">协办处室：</span>
				<span class="uploadItem withInput">
					<option selected="selected"></option>
					<select id="assitrole">
						<option selected="selected"></option>
						<c:forEach var="role" items="${sessionScope.roles}">
							<option value="${roles.id}">${role.roleName}</option>
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">办结时限：</span>
				<span class="uploadItem withInput">
					<input size="16" type="text" id="limit_time" value="" readonly
						class="form_date form-control placeholder-no-fix halfWidth"> 
				</span>
			</div>
		</div>
		<div>
			<div>
			<form action="" enctype="multipart/form-data" method="post">
		        <table>
		            <tr>
		                <td>请选择文件:</td>
		                <td><input type="file" name="file" multiple/></td>
		            </tr>
		        </table>
    		</form>
		</div>
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

	$(".form_date").datepicker({
		format : 'yyyy-mm-dd',
		language : 'zh-CN'
	});
	function upload(){
		var url = 'rest/attach/upload';
		$.post(url, {
			sequence : $("#sequence").val(),
			name : $("#name").val(),
			role :document.getElementById("role").value,
			assitrole : document.getElementById("assitperrole").value,
			limit_time : $("#limit_time").val(),
		}, function(data) {
			
		});
	}
</script>