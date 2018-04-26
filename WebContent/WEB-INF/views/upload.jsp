<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- <form id="from" method="post"> -->
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
					<input size="16" type="text" id="createTime" value="<fmt:formatDate value='${msg.createTime}'
						type='DATE' pattern='yyyy-MM-dd' />" readonly
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
					<select id="role" multiple="multiple" onblur="check(3)" onchange="getData()">
						<c:set var='i' value="0"></c:set>
							<c:forEach var="role" items="${sessionScope.roles}" begin="1">
		      					<option value="${role.id}" <c:if test="${msgSponsorSelect[i] eq role.id}"><c:set var="i" value="${i+1}" />selected="selected"</c:if>>${role.roleName}</option>
		      				</c:forEach> 
					</select>
				</span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">协办处室：</span>
				<span class="uploadItem withInput">
					<select id="assitrole" multiple="multiple" ">
						<c:set var='i' value="0"></c:set>
							<c:forEach var="role" items="${roleList}" begin="1">
		      					<option value="${role.id}" <c:if test="${msgCoSponsorSelect[i] eq role.id}"><c:set var="i" value="${i+1}" />selected="selected"</c:if>>${role.roleName}</option>
		      				</c:forEach> 
					</select>
				</span>
				<span id='msg3'></span>
			</div>
		</div>
		<div>
			<div>
				<span class="uploadTitle">办结时限：</span>
				<span class="uploadItem withInput">
					<input size="16" type="text" id="limitTime"  value="<fmt:formatDate value='${msg.limitTime}'
						type='DATE' pattern='yyyy-MM-dd' />" 	 readonly
						class="form_date form-control placeholder-no-fix halfWidth" onblur="check(4)"/> 
				</span>
				
				<span id='msg4'></span>
			</div>
		</div>
	<!-- 	</form> -->
		<div>
			<div>
			<form id="uploadForm"  enctype="multipart/form-data" method="post">
		        <table>
		            <tr>
		                <td>请选择文件:</td>
		                <td><input id="excelFile" type="file" name="file" multiple/></td>
		                <td><button type="button"  onclick="doUpload()" >上传</button></td>
		            </tr>
		            <tr>
		            	<span id='successMsg'></span>
		            </tr>
		        </table>
    		</form>
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

<script type="text/javascript">
	$(".form_date").datepicker({
		format : 'yyyy-mm-dd',
		language : 'zh-CN'
	});
	function getData(){
			var form = new FormData(document.getElementById("form"));  
			form.append("role",$("#role").val());
			console.log($("#role").val());
			$.ajax({
				type: "POST",
				url: 'rest/msg/gett', 
				async: false,
				data: form,
				contentType: false, 
				processData:false,
				success: function(response) {
					
				},
			});
	
		}
	function doUpload() {  
		 //var File = $('#excelFile').get(0).files[0];  
	     var data = new FormData($( "#uploadForm" )[0]); 
	     var successMsg = $('#successMsg');
	     console.log(data); 
	     $.ajax ({ 
	          url: 'rest/attach/upload' ,  
	          type: 'POST',  
	          data:data,
	          fileElementId: 'excelFile', //文件上传域的ID(<input type="file">的id)    
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (returndata) {
	        	  console.log(returndata);
	        	  successMsg.html("上传附件成功");
	        	  successMsg.css('color', '#00FF00'); 
	          },
	    
	     });  
	} 
	function upload(){
		if (check(0) && check(1) && check(2)&& check(3) && check(4)) {
			 var roleId = "<%=session.getAttribute("roleId")%>";
			 console.log($("#role").val()); 
			 var form = new FormData(document.getElementById("form"));  
			 form.append("status",0);
			 form.append("name",$("#name").val());
			 form.append("basis",$("#basis").val());
			 form.append("role",$("#role").val());
			 form.append("assitrole",$("#assitrole").val());
			 form.append("limitTime",$("#limitTime").val());
			 form.append("createTime",$("#createTime").val());
			 form.append("id",roleId);
			 
		     $.ajax({  
		     	url:'rest/msg/save',  
		      	type:"post",  
			    data:form, 
			    /* fileElementId: 'file', */
			    cache: false,  
			    processData: false,  
			    contentType: false,  
			    success:function(data){  
			         alert("保存成功！");
			         showData("#main-content",data);
			      },  
			     
		      });  
		}
		else{
			 alert("必需字段不能为空"); 
		}
	};
	//发布按钮
	function send(){
		if (check(0) && check(1) && check(2)&& check(3) && check(4)) {
			 var roleId = "<%=session.getAttribute("roleId")%>";
			 console.log($("#role").val()); 
			 var form = new FormData(document.getElementById("form"));  
			 form.append("status",1);
			 form.append("name",$("#name").val());
			 form.append("basis",$("#basis").val());
			 form.append("role",$("#role").val());
			 form.append("assitrole",$("#assitrole").val());
			 form.append("limitTime",$("#limitTime").val());
			 form.append("createTime",$("#createTime").val());
			 form.append("id",roleId);
			 
		     $.ajax({  
		     	url:'rest/msg/save',  
		      	type:"post",  
			    data:form, 
			    /* fileElementId: 'file', */
			    cache: false,  
			    processData: false,  
			    contentType: false,  
			    success:function(data){  
			         alert("发送成功！");
			         showData("#main-content",data);
			      },  
			     
		      });  
		}
		else{
			 alert("必需字段不能为空"); 
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
				}  else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				}
			}   else if (num == 1) {
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
			} else if (num == 3) {
				value =$("#role").val();
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
			}  else if (num == 4) {
				value = $("#limitTime").val();
				msg = $('#msg4');
				if (value == null || value.length < 1) {
					msg.html("办结期限不能为空");
					msg.css('color', '#FF0000');
					return false;
				} else {
					msg.html("OK");
					msg.css('color', '#00FF00');
					return true;
				} 
			}
		}
	


		$(function() {
			$("#index-page-title").html("督查上传");
			$("#current-page-title").html("督查上传");
		});
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

  