<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div style="height: 20px"></div>
<div class="msgTable">
	<div class="msg msgHeader">
		<span class="msgItem1">标 题</span> <span class="msgItem2">单 位</span> <span
			class="msgItem3">时 间</span> <span class="msgItem4">状态</span> <span
			class="msgItem5">操作</span>
	</div>
	<c:if test="${msgs != null}">
		<c:forEach items="${msgs}" var="msg0">
			<div class="msg" id="${msg0.msgId}">
				<span class="msgItem1" onclick="openMsg('${msg0.msgId}')">${msg0.msgTitle}</span>
				<span class="msgItem2" onclick="openMsg('${msg0.msgId}')">${msg0.msgUserUnit}</span>
				<span class="msgItem3" onclick="openMsg('${msg0.msgId}')"> <fmt:formatDate
						value='${msg0.msgDate}' type='DATE' pattern='yyyy-MM-dd' />
				</span> <span class="msgItem4" onclick="openMsg('${msg0.msgId}')">${msg0.msgStatusName}</span>
				<span class="msgItem5"><button id="deleteButton"
						type="button" class="btn blue" onclick="msgDelete('${msg0.msgId}')">撤回</button></span>
			</div>
		</c:forEach>
	</c:if>
</div>
<div class="pageBox">
	<input type="hidden" id="pageNo" value="${page.pageNo}" />
	<c:if test="${page.pageNo eq 1}">
		<span class="page firstP">首页</span>
		<span class="page beforeP">上一页</span>
	</c:if>
	<c:if test="${page.pageNo > 1}">
		<span class="page firstP" onclick="query(1)">首页</span>
		<span class="page beforeP" onclick="query(${page.pageNo-1})">上一页</span>
	</c:if>
	<c:if test="${page.totalPages<5}">
		<c:forEach begin="1" end="${page.totalPages}" varStatus="index">
			<span class="page numP" id="${index.index}"
				onclick="query(${index.index})">${index.index}</span>
		</c:forEach>
	</c:if>
	<c:if test="${page.totalPages>5}">
		<c:if test="${page.pageNo<=3}">
			<c:forEach begin="1" end="5" varStatus="index">
				<span class="page numP" id="${index.index}"
					onclick="query(${index.index})">${index.index}</span>
			</c:forEach>
		</c:if>
	</c:if>
	<span class="page countP">第${page.pageNo}/${page.totalPages}页（共${page.totalCount}条结果）</span>
	<c:if test="${page.totalPages>5}">
		<c:if test="${page.pageNo>=4&&page.pageNo<=page.totalPages-2}">
			<c:forEach begin="${page.pageNo-2}" end="${page.pageNo+2}"
				varStatus="index">
				<span class="page numP" id="${index.index}"
					onclick="query(${index.index})">${index.index}</span>
			</c:forEach>
		</c:if>
	</c:if>
	<c:if test="${page.totalPages>5}">
		<c:if test="${page.pageNo>(page.totalPages-2)}">
			<c:forEach begin="${page.totalPages-4}" end="${page.totalPages}"
				varStatus="index">
				<span class="page numP" id="${index.index}"
					onclick="query(${index.index})">${index.index}</span>
			</c:forEach>
		</c:if>
	</c:if>
	<c:if test="${page.pageNo eq page.totalPages}">
		<span class="page afterP">下一页</span>
		<span class="page lastP">尾页</span>
	</c:if>
	<c:if test="${page.pageNo < page.totalPages}">
		<span class="page afterP" onclick="query(${page.pageNo+1})">下一页</span>
		<span class="page lastP" onclick="query(${page.totalPages})">尾页</span>
	</c:if>
</div>