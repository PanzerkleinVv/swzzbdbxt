<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:forEach var="msgSponsor" items="${msgSponsorExtends}">
	<div>&emsp;</div>
	<div id="${msgSponsor.id}">
		<div>
			<span class="msgTitle contentTitle"><strong>${sessionScope.roleMap[msgSponsor.roleId]}：</strong></span>
		</div>
		<div>
			<span class="msgTitle">状&emsp;&emsp;态：</span> <span>${sessionScope.msgStatus[msgSponsor.status]}</span>
		</div>
		<div>
			<span class="msgTitle">承&ensp;办&ensp;人：</span>
			<c:choose>
				<c:when test="${msgSponsor.assignable}">
					<span> <c:set var='i' value="0" /> <c:forEach var="user"
							items="${sessionScope.roleUsers}" varStatus="userStatus">
							<input name="${msgSponsor.id}" id="${msgSponsor.id}${userStatus.index}"
								type="checkbox"
								value="${user.id}"
								<c:if test="${msgSponsor.users[i].id eq user.id}"><c:set var="i" value="${i+1}" /> checked="checked"</c:if>>
							<label for="${msgSponsor.id}${userStatus.index}">${user.userdesc}</label>
						</c:forEach>
					</span>
					<span><button id="assign" type="button" class="btn blue"
							onclick="assign('${msgSponsor.id}')">分派</button></span>
				</c:when>
				<c:otherwise>
					<c:forEach var="user" items="${msgSponsor.users}">
						<span>${user.userdesc}</span>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<div>
			<span class="msgTitle">办理情况：</span>
		</div>
		<div>
			<c:choose>
				<c:when test="${msgSponsor.editabled}">
					<textarea cols="100" rows="15" id="${msgSponsor.id}_editor">${msgSponsor.content}</textarea>
					<span><button id="saveContent" type="button"
							class="btn blue" onclick="saveContent('${msgSponsor.id}','1')">保存修改</button></span>
					<span><button id="addSubmission" type="button"
							class="btn blue" onclick="addSubmission()">新增提请</button></span>
					<script type="text/javascript">
						window.console = window.console || (function(){
							var c = {};c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile
							= c.clear = c.exception = c.trace = c.assert = function(){};
							return c;
						})();
						UE.delEditor('${msgSponsor.id}_editor');
						var ue = UE.getEditor('${msgSponsor.id}_editor');
					</script>
				</c:when>
				<c:otherwise>
					<div>${msgSponsor.content}</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</c:forEach>
<c:forEach var="msgCoSponsor" items="${msgCoSponsorExtends}">
	<div>&emsp;</div>
	<div id="${msgCoSponsor.id}">
		<div>
			<span class="msgTitle contentTitle"><strong>${sessionScope.roleMap[msgCoSponsor.roleId]}：</strong></span>
		</div>
		<div>
			<span class="msgTitle">状&emsp;&emsp;态：</span> <span>${sessionScope.msgStatus[msgCoSponsor.status]}</span>
		</div>
		<div>
			<span class="msgTitle">承&ensp;办&ensp;人：</span>
			<c:choose>
				<c:when test="${msgCoSponsor.assignable}">
					<span> <c:set var='i' value="0" /> <c:forEach var="user"
							items="${sessionScope.roleUsers}" varStatus="userStatus">
							<input id="${msgCoSponsor.id}${userStatus.index}" type="checkbox"
								value="${user.id}" name="${msgCoSponsor.id}"
								<c:if test="${msgCoSponsor.users[i].id eq user.id}"><c:set var="i" value="${i+1}" /> checked="checked"</c:if>>
							<label for="${msgCoSponsor.id}${userStatus.index}">${user.userdesc}</label>
						</c:forEach>
					</span>
					<span><button id="assign" type="button" class="btn blue"
							onclick="assign('${msgCoSponsor.id}')">分派</button></span>
				</c:when>
				<c:otherwise>
					<c:forEach var="user" items="${msgCoSponsor.users}">
						<span>${user.userdesc}</span>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<div>
			<span class="msgTitle">办理情况：</span>
		</div>
		<div>
			<c:choose>
				<c:when test="${msgCoSponsor.editabled}">
					<textarea cols="100" rows="15" id="${msgCoSponsor.id}_editor">${msgCoSponsor.content}</textarea>
					<span><button id="saveContent" type="button"
							class="btn blue" onclick="saveContent('${msgCoSponsor.id}','2')">保存修改</button></span>
					<span><button id="addSubmission" type="button"
							class="btn blue" onclick="addSubmission()">新增提请</button></span>
					<script type="text/javascript">
						window.console = window.console || (function(){
							var c = {};c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile
							= c.clear = c.exception = c.trace = c.assert = function(){};
							return c;
						})();
						UE.delEditor('${msgCoSponsor.id}_editor');
						var ue = UE.getEditor('${msgCoSponsor.id}_editor');
					</script>
				</c:when>
				<c:otherwise>
					<div>${msgCoSponsor.content}</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</c:forEach>