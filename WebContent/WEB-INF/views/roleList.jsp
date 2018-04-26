<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var='i' value="0"></c:set>
							<c:forEach var="role" items="${roleList}" begin="1">
		      					<option value="${role.id}" <c:if test="${msgCoSponsorSelect[i] eq role.id}"><c:set var="i" value="${i+1}" />selected="selected"</c:if>>${role.roleName}</option>
		      				</c:forEach> 