<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="from"  enctype="multipart/form-data" method="post">
<div class="mainContent">
	<div id="uploadBox">
		<div>
			<div>
				<span class="uploadTitle">督查事项：</span>
				<span class="uploadItem withInput">
					<input type='text' id='name' value="${msg.name}"
					class="form-control placeholder-no-fix uploadInput" onblur="check(0)"/>
				</span>
				<span id='msg0'></span>
			</div>
		</div>
		 <div>
			<div>
				<span class="uploadTitle">立项时间：</span>
				<span class="uploadItem withInput">
					<input size="16" type="text" id="createTime" value="${msg.createTime}" readonly
						class="form_date form-control placeholder-no-fix halfWidth" onblur="check(1)"> 
				</span>
				<span id='msg1'></span>
			</div>
		</div>	 
		<div>
			<div>
				<span class="uploadTitle">立项依据：</span>
				<span class="uploadItem withInput">
					<input type='text' id='basis' value="${msg.basis}" class="form-control placeholder-no-fix uploadInput" onblur="check(2)"/>
				</span>
				<span id='msg2'></span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">主办处室：</span>
				<span class="uploadItem withInput">
					<select id="role" multiple="multiple" >
						<c:forEach var="role" items="${sessionScope.roles}" begin="1">
      							<option value="${role.roleName}">${role.roleName}</option>
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">协办处室：</span>
				<span class="uploadItem withInput">
					<select id="assitrole" multiple="multiple">
						<c:forEach var="role" items="${sessionScope.roles}" begin="1">
							<option value="${role.roleName}">${role.roleName}</option>
						</c:forEach>
					</select>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">办结时限：</span>
				<span class="uploadItem withInput">
					<input size="16" type="text" id="limitTime"  value="${msg.limitTime}" 	 readonly
						class="form_date form-control placeholder-no-fix halfWidth" onblur="check(4)"/> 
				</span>
				<span id='msg4'></span>
			</div>
		</div>
		<div>
			<div>
			
		        <table>
		            <tr>
		                <td>请选择文件:</td>
		                <td><input type="file" name="file" multiple/></td>
		            </tr>
		        </table>
    		
		</div>
		</div>
		<div class="uploadButton">
			<span>
				<button id="uploadSubmit" type="button" class="btn blue"
					onclick="upload()">保存</button>
			</span>
			<span>
				<button id="uploadSubmit" type="button" class="btn blue"
					onclick="send()">发布</button>
			</span>
		</div>
	</div>
	
</div>
</form>
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
		 var form = new FormData(document.getElementById("form"));  
		 form.append("status",0);
		 form.append("name",$("#name").val());
		 form.append("basis",$("#basis").val());
		 form.append("role",$("#role").val());
		 form.append("assitrole",$("#assitrole").val());
		 form.append("limitTime",$("#limitTime").val());
		 form.append("createTime",$("#createTime").val());
	      $.ajax({  
	      url:'rest/attach/upload',  
	      type:"post",  
	      data:form,  
	      cache: false,  
	      processData: false,  
	      contentType: false,  
	      success:function(data){  
	          alert("保存成功！"); 
	          window.location.href = '/index.jsp'
	      },  
	      error:function(e){  
	          alert("保存失败！重新输入"); 
	          
	       }  
	      });          
	};
	//发布按钮
	function send(){
		var url = 'rest/attach/upload';
		$.post(url, {
			status : 1,
			name : $("#name").val(),
			basis : $("#basis").val(),
			role : $("#role").val(),
			assitrole : $("#assitrole").val(),
			limit_time : $("#limit_time").val(),
		}, function(data) {
			
		});
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
				}  else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			}  else if (num == 1) {
				value = $("#createTime").val();
				msg = $('#msg1');
				if (value == null || value.length < 1) {
					msg.html("立项时间不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			}  else if (num == 2) {
				value =$("#basis").val();
				msg = $('#msg2');
				if (value == null || value.length < 1) {
					msg.html("立项依据不能为空");
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
					msg.html("办结时限不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			}
		}
	</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#role').multiselect({
        	enableFiltering: true,
        	nonSelectedText:'请选择主办处室',
        	filterPlaceholder:'搜索',
        	nSelectedText:'项被选中',
        	includeSelectAllOption:true,
        	selectAllText:'全选/取消全选',
        	allSelectedText:'已选中所有主办处室',
        	maxHeight:300
        });
        $('#assitrole').multiselect({
        	enableFiltering: true,
        	nonSelectedText:'请选择协办处室',
        	filterPlaceholder:'搜索',
        	nSelectedText:'项被选中',
        	includeSelectAllOption:true,
        	selectAllText:'全选/取消全选',
        	allSelectedText:'已选中所有协办处室',
        	maxHeight:300
        });
    });

</script>

  