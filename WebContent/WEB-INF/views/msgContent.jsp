<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:forEach var="msgSponsor" items="${msgSponsorExtends}">
	<div>&emsp;</div>
	<div id="${msgSponsor.id}" class="msgSponsorContent">
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
							<input name="${msgSponsor.id}"
								id="${msgSponsor.id}${userStatus.index}" type="checkbox"
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
		<c:choose>
			<c:when test="${msgSponsor.editabled}">
				<div>
					<textarea cols="100" rows="15" id="${msgSponsor.id}_editor">${msgSponsor.content}</textarea>
				</div>
				<div class="middleTitle">
					<button id="saveContent" type="button" class="btn blue"
						onclick="saveContent('${msgSponsor.id}','1')">保存修改</button>
					<script type="text/javascript">
						window.console = window.console
								|| (function() {
									var c = {};
									c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
									};
									return c;
								})();
						UE.delEditor('${msgSponsor.id}_editor');
						var ue = UE.getEditor('${msgSponsor.id}_editor');
					</script>
				</div>
			</c:when>
			<c:otherwise>
				<div>${msgSponsor.content}</div>
			</c:otherwise>
		</c:choose>
		<div>
			<span class="msgTitle">处室提请：</span>
		</div>
		<c:if test="${msgSponsor.editabled && msgSponsor.status < 3 && sessionScope.roleId == msgSponsor.roleId}">
			<div class="middleTitle">
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('1','${msgSponsor.id}')">提请办结</button>
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('2','${msgSponsor.id}')">提请延期</button>
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('3','${msgSponsor.id}')">提请中止</button>
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('4','${msgSponsor.id}')">提请阶段性办结</button>
			</div>
		</c:if>
		<c:if test="${msgSponsor.submissions != null}">
			<div>
				<c:forEach var="submission" items="${msgSponsor.submissions}">
					<c:choose>
						<c:when test="${submission.status == 0}">
							<div>
								<div class="submissionTitle">
									<span class="msgTitle">提请${sessionScope.submissionType[submission.type]}（${sessionScope.submissionStatus[submission.status]}）</span>
								</div>
								<div>
									<c:choose>
										<c:when test="${submission.type == 1}">
											<span class="msgTitle">办理情况：&emsp;</span>
										</c:when>
										<c:otherwise>
											<span class="msgTitle">进展情况：&emsp;</span>
										</c:otherwise>
									</c:choose>
								</div>
								<div>
									<textarea cols="100" rows="15" id="${submission.id}_editor1">${submission.situation}</textarea>
									<script type="text/javascript">
										window.console = window.console
												|| (function() {
													var c = {};
													c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
													};
													return c;
												})();
										UE.delEditor('${submission.id}_editor1');
										var ue = UE.getEditor('${submission.id}_editor1');
									</script>
								</div>
								<c:if test="${submission.type == 2 || submission.type == 3}">
									<div>
										<span class="msgTitle">${sessionScope.submissionType[submission.type]}理由：&emsp;</span>
									</div>
									<div>
										<textarea cols="100" rows="15" id="${submission.id}_editor2">${submission.reason}</textarea>
										<script type="text/javascript">
											window.console = window.console
													|| (function() {
														var c = {};
														c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
														};
														return c;
													})();
											UE.delEditor('${submission.id}_editor2');
											var ue = UE.getEditor('${submission.id}_editor2');
										</script>
									</div>
								</c:if>
								<c:if test="${submission.type == 2}">
									<div>
										<span class="msgTitle">拟采取措施：</span>
									</div>
									<div>
										<textarea cols="100" rows="15" id="${submission.id}_editor3">${submission.measure}</textarea>
										<script type="text/javascript">
											window.console = window.console
													|| (function() {
														var c = {};
														c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
														};
														return c;
													})();
											UE.delEditor('${submission.id}_editor3');
											var ue = UE.getEditor('${submission.id}_editor3');
										</script>
									</div>
								</c:if>
								<div class="middleTitle">
									<button id="saveSubmission" type="button" class="btn blue"
										onclick="saveSubmission('${submission.id}','0','${submission.type}')">保存</button>
									<button id="saveSubmission" type="button" class="btn blue"
										onclick="saveSubmission('${submission.id}','1','${submission.type}')">发布</button>
									<button id="delSubmission" type="button" class="btn red"
										onclick="delSubmission('${submission.id}')">删除</button>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<div class="submissionTitle">
									<span class="msgTitle">提请${sessionScope.submissionType[submission.type]}（${sessionScope.submissionStatus[submission.status]}）</span>
								</div>
								<div>
									<span class="msgTitle">提请时间：&emsp;</span>
									<span><fmt:formatDate
											value='${submission.sendTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
								</div>
								<div>
									<c:choose>
										<c:when test="${submission.type == 1}">
											<span class="msgTitle">办理情况：&emsp;</span>
										</c:when>
										<c:otherwise>
											<span class="msgTitle">进展情况：&emsp;</span>
										</c:otherwise>
									</c:choose>
								</div>
								<div>${submission.situation}</div>
								<c:if test="${submission.type == 2 || submission.type == 3}">
									<div>
										<span class="msgTitle">${sessionScope.submissionType[submission.type]}理由：&emsp;</span>
									</div>
									<div>${submission.reason}</div>
								</c:if>
								<c:if test="${submission.type == 2}">
									<div>
										<span class="msgTitle">拟采取措施：</span>
									</div>
									<div>${submission.measure}</div>
								</c:if>
								<div>
									<span class="msgTitle">提请人：&emsp;&emsp;</span>
									<span>${submission.ownerDesc}</span>
								</div>
								<c:choose>
									<c:when test="${submission.status == 1}">
										<div class="middleTitle">
										<c:choose>
												<c:when test="${submission.verifiable}">
													<button id="pass" type="button" class="btn green"
															onclick="pass('${submission.id}')">审核通过</button>
													<button id="noPass" type="button" class="btn red"
															onclick="noPass('${submission.id}')">审核不通过</button>
												</c:when>
												<c:otherwise>
													<button id="callbackSubmission" type="button" class="btn blue"
															onclick="callbackSubmission('${submission.id}')">撤回</button>
												</c:otherwise>
											</c:choose>
										</div>
									</c:when>
									<c:when test="${submission.status == 2}">
										<div>
											<span class="msgTitle">审核人：&emsp;&emsp;</span>
											<span>${submission.superiorVerifiUserDesc}</span>
										</div>
										<div>
											<span class="msgTitle">审核结果：&emsp;</span>
											<c:choose>
												<c:when test="${submission.superiorVerifyPassed == 1}">
													<span><b style="color: blue;">通过</b></span>
												</c:when>
												<c:when test="${submission.superiorVerifyPassed == 0}">
													<span><b style="color: blue;">不通过</b></span>
												</c:when>
											</c:choose>
										</div>
									</c:when>
								</c:choose>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</c:if>
	</div>
</c:forEach>
<c:forEach var="msgCoSponsor" items="${msgCoSponsorExtends}">
	<div>&emsp;</div>
	<div id="${msgCoSponsor.id}" class="msgSponsorContent">
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
		<c:choose>
			<c:when test="${msgCoSponsor.editabled}">
				<div>
					<textarea cols="100" rows="15" id="${msgCoSponsor.id}_editor">${msgCoSponsor.content}</textarea>
				</div>
				<div class="middleTitle">
					<button id="saveContent" type="button" class="btn blue"
						onclick="saveContent('${msgCoSponsor.id}','2')">保存修改</button>
					<script type="text/javascript">
						window.console = window.console
								|| (function() {
									var c = {};
									c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
									};
									return c;
								})();
						UE.delEditor('${msgCoSponsor.id}_editor');
						var ue = UE.getEditor('${msgCoSponsor.id}_editor');
					</script>
				</div>
			</c:when>
			<c:otherwise>
				<div>${msgCoSponsor.content}</div>
			</c:otherwise>
		</c:choose>
		<div>
			<span class="msgTitle">处室提请：</span>
		</div>
		<c:if test="${msgCoSponsor.editabled && msgCoSponsor.status < 3 && sessionScope.roleId == msgCoSponsor.roleId}">
			<div class="middleTitle">
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('1','${msgCoSponsor.id}')">提请办结</button>
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('2','${msgCoSponsor.id}')">提请延期</button>
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('3','${msgCoSponsor.id}')">提请中止</button>
				<button id="addSubmission" type="button" class="btn blue"
					onclick="addSubmission('4','${msgCoSponsor.id}')">提请阶段性办结</button>
			</div>
		</c:if>
		<c:if test="${msgCoSponsor.submissions != null}">
			<div>
				<c:forEach var="submission" items="${msgCoSponsor.submissions}">
					<c:choose>
						<c:when test="${submission.status == 0}">
							<div>
								<div class="submissionTitle">
									<span class="msgTitle">提请${sessionScope.submissionType[submission.type]}（${sessionScope.submissionStatus[submission.status]}）</span>
								</div>
								<div>
									<c:choose>
										<c:when test="${submission.type == 1}">
											<span class="msgTitle">办理情况：&emsp;</span>
										</c:when>
										<c:otherwise>
											<span class="msgTitle">进展情况：&emsp;</span>
										</c:otherwise>
									</c:choose>
								</div>
								<div>
									<textarea cols="100" rows="15" id="${submission.id}_editor1">${submission.situation}</textarea>
									<script type="text/javascript">
										window.console = window.console
												|| (function() {
													var c = {};
													c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
													};
													return c;
												})();
										UE.delEditor('${submission.id}_editor1');
										var ue = UE.getEditor('${submission.id}_editor1');
									</script>
								</div>
								<c:if test="${submission.type == 2 || submission.type == 3}">
									<div>
										<span class="msgTitle">${sessionScope.submissionType[submission.type]}理由：&emsp;</span>
									</div>
									<div>
										<textarea cols="100" rows="15" id="${submission.id}_editor2">${submission.reason}</textarea>
										<script type="text/javascript">
											window.console = window.console
													|| (function() {
														var c = {};
														c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
														};
														return c;
													})();
											UE.delEditor('${submission.id}_editor2');
											var ue = UE.getEditor('${submission.id}_editor2');
										</script>
									</div>
								</c:if>
								<c:if test="${submission.type == 2}">
									<div>
										<span class="msgTitle">拟采取措施：</span>
									</div>
									<div>
										<textarea cols="100" rows="15" id="${submission.id}_editor3">${submission.measure}</textarea>
										<script type="text/javascript">
											window.console = window.console
													|| (function() {
														var c = {};
														c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.prfile = c.clear = c.exception = c.trace = c.assert = function() {
														};
														return c;
													})();
											UE.delEditor('${submission.id}_editor3');
											var ue = UE.getEditor('${submission.id}_editor3');
										</script>
									</div>
								</c:if>
								<div class="middleTitle">
									<button id="saveSubmission" type="button" class="btn blue"
										onclick="saveSubmission('${submission.id}','0','${submission.type}')">保存</button>
									<button id="saveSubmission" type="button" class="btn blue"
										onclick="saveSubmission('${submission.id}','1','${submission.type}')">发布</button>
									<button id="delSubmission" type="button" class="btn red"
										onclick="delSubmission('${submission.id}')">删除</button>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<div class="submissionTitle">
									<span class="msgTitle">提请${sessionScope.submissionType[submission.type]}（${sessionScope.submissionStatus[submission.status]}）</span>
								</div>
								<div>
									<span class="msgTitle">提请时间：&emsp;</span>
									<span><fmt:formatDate
											value='${submission.sendTime}' type='DATE' pattern='yyyy-MM-dd' /></span>
								</div>
								<div>
									<c:choose>
										<c:when test="${submission.type == 1}">
											<span class="msgTitle">办理情况：&emsp;</span>
										</c:when>
										<c:otherwise>
											<span class="msgTitle">进展情况：&emsp;</span>
										</c:otherwise>
									</c:choose>
								</div>
								<div>${submission.situation}</div>
								<c:if test="${submission.type == 2 || submission.type == 3}">
									<div>
										<span class="msgTitle">${sessionScope.submissionType[submission.type]}理由：&emsp;</span>
									</div>
									<div>${submission.reason}</div>
								</c:if>
								<c:if test="${submission.type == 2}">
									<div>
										<span class="msgTitle">拟采取措施：</span>
									</div>
									<div>${submission.measure}</div>
								</c:if>
								<div>
									<span class="msgTitle">提请人：&emsp;&emsp;</span>
									<span>${submission.ownerDesc}</span>
								</div>
								<c:choose>
									<c:when test="${submission.status == 1}">
										<div class="middleTitle">
										<c:choose>
												<c:when test="${submission.verifiable}">
													<button id="pass" type="button" class="btn green"
															onclick="pass('${submission.id}')">审核通过</button>
													<button id="noPass" type="button" class="btn red"
															onclick="noPass('${submission.id}')">审核不通过</button>
												</c:when>
												<c:otherwise>
													<button id="callbackSubmission" type="button" class="btn blue"
															onclick="callbackSubmission('${submission.id}')">撤回</button>
												</c:otherwise>
											</c:choose>
										</div>
									</c:when>
									<c:when test="${submission.status == 2}">
										<div>
											<span class="msgTitle">审核人：&emsp;&emsp;</span>
											<span>${submission.superiorVerifiUserDesc}</span>
										</div>
										<div>
											<span class="msgTitle">审核结果：&emsp;</span>
											<c:choose>
												<c:when test="${submission.superiorVerifyPassed == 1}">
													<span><b style="color: blue;">通过</b></span>
												</c:when>
												<c:when test="${submission.superiorVerifyPassed == 0}">
													<span><b style="color: blue;">不通过</b></span>
												</c:when>
											</c:choose>
										</div>
									</c:when>
								</c:choose>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</c:if>
	</div>
</c:forEach>