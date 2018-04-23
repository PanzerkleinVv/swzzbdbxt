<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="msgTable">
	<div class="msg msgHeader">
		<span class="msgItem">立项号</span> <span class="msgItem">督查事项</span> <span
			class="msgItem">立项时间</span> <span class="msgItem">立项依据</span> <span
			class="msgItem">主办处室</span><span class="msgItem">协办处室</span><span
			class="msgItem">办结时限</span><span class="msgItem">办理情况</span><span
			class="msgItem">附件资料</span><span class="msgItem">反馈时间</span><span
			class="msgItem">办理状态</span>
	</div>
	<c:if test="${msgs != null}">
		<c:forEach items="${msgs}" var="msg0">
			<div class="msg" id="${msg0.id}" onclick="openMsg('${msg0.id}')">
				<span class="msgItem">${msg0.sequence}</span> <span class="msgItem">${msg0.name}</span>
				<span class="msgItem"> <fmt:formatDate
						value='${msg0.createTime}' type='DATE' pattern='yyyy-MM-dd' />
				</span> <span class="msgItem">${msg0.basis}</span> <span
					class="msgItem sponsor">${msg0.sponsorRoleNames}</span> <span
					class="msgItem co-sponsor">${msg0.coSponsorRoleNames}</span> <span
					class="msgItem"> <fmt:formatDate value='${msg0.limitTime}'
						type='DATE' pattern='yyyy-MM-dd' />
				</span> <span class="msgItem content">${msg0.contents}</span> <span
					class="msgItem attach">${msg0.attachs}</span> <span class="msgItem">
					<fmt:formatDate value='${msg0.endTime}' type='DATE'
						pattern='yyyy-MM-dd' />
				</span> <span class="msgItem">${msg0.status}</span>
			</div>
			<input type="hidden" name="ids" value="${msg0.id}" />
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