<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${roles != null}">
	<div class="role ${level} append" id="_${roleId}">
		<c:forEach items="${roles}" var="role0">
			<span id="${role0.roleId}"
				<c:choose>
					<c:when test="${level != 3}">onclick="roleClick(this)"</c:when>
					<c:otherwise>onclick="roleSelect(this)"</c:otherwise>
				</c:choose>>${role0.roleName}</span>
		</c:forEach>
		<c:if test='${level == 3 && method != "selectNoCheck"}'>
			<span id="add" onclick="roleAdd(this)">新增</span>
		</c:if>
	</div>
</c:if>