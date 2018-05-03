<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div style="height: 20px"></div>
<div id="msgBox">
	<div>
		<span class="msgTitle">立项号&emsp;：</span> <span>${msg.sequence}</span>
		<input id="id" type="hidden" value="${msg.id}" />
	</div>
	<div>
		<span class="msgTitle">督察事项：</span> <span>${msg.name}</span>
	</div>
	<div>
		<span class="msgTitle">立项时间：</span> <span><fmt:formatDate
				value='${msg.createTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
	</div>
	<div>
		<span class="msgTitle">立项依据：</span> <span>${msg.basis}</span>
	</div>
	<div>
		<span class="msgTitle">主办处室：</span> <span>${msg.sponsorRoleNames}</span>
	</div>
	<div>
		<span class="msgTitle">协办处室：</span> <span>${msg.coSponsorRoleNames}</span>
	</div>
	<div>
		<span class="msgTitle">办结时限：</span> <span><fmt:formatDate
				value='${msg.limitTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
	</div>
	<div>
		<span class="msgTitle">附件资料：</span> <span><c:forEach
				var="attachId" items="${msg.attachIds}" varStatus="status0">
				<a href="rest/attach/download?id=${attachId}" target="_blank">${msg.attachs[status0.index]}</a>
			</c:forEach></span>
	</div>
	<div>
		<span class="msgTitle">反馈时间：</span> <span><fmt:formatDate
				value='${msg.endTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
	</div>
	<div>
		<span class="msgTitle"> <shiro:hasAnyRoles name="admin,1,2">
				<c:if test="${callbackable}">
					<button id="callback" type="button" class="btn blue"
						onclick="callback()">撤回</button>
				</c:if>
			</shiro:hasAnyRoles> <c:if test="${signable}">
				<button id="insert" type="button" class="btn blue" onclick="sign()">签收</button>
			</c:if> <c:if test="${assignable}">
				<button id="insert" type="button" class="btn blue"
					onclick="assign()">指派</button>
			</c:if>
		</span>
	</div>
	<div class="titleEnd">
		<span class="msgTitle">办理情况：</span>
	</div>
</div>
<script type="text/javascript">
	function callback(){
		var url = "rest/msg/callback";
		$.post(url,{"id": $("#id").val()},function(data){
			$("#msgBox").html(data);
		})
		
	}
</script>