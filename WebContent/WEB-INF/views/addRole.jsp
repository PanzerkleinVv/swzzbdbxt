<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${pId != null}">
	<div class="role add append" id="addRole">
		<input type="hidden" name="pId" id="pId" value="${pId}" />
		<div><span>地区简称</span>
			<input type="text" name="roleName" id="roleName" value=""
				onblur="checkRole(this)" /><span></span>
		</div>
		<div><span>地区拼音</span>
			<input type="text" name="roleSign" id="roleSign" value=""
				onblur="checkRole(this)" /><span></span>
		</div>
		<div><span>地区全称</span>
			<input type="text" name="roleDesc" id="roleDesc" value=""
				onblur="checkRole(this)" /><span></span>
		</div>
		<div>
			<button id="roleAddSubmit" type="button"
				class="btn blue" onclick="roleSubmit(this)">保存</button>
		</div>
	</div>
</c:if>