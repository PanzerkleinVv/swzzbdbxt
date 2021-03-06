package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.enums.MessageColor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Log;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsorExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample.Criteria;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.PermissionSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgQuery;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.LogService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.UserService;

@Controller
@RequestMapping(value = "/msg")
public class MsgController {

	@Resource
	private MsgService msgService;

	@Resource
	private MsgSponsorService msgSponsorService;

	@Resource
	private MsgCoSponsorService msgCoSponsorService;

	@Resource
	private MsgContractorService msgContractorService;

	@Resource
	private SubmissionService submissionService;

	@Resource
	private AttachService attachService;

	@Resource
	private UserService userService;
	
	@Resource
	private LogService logService;

	// 立项号
	@Resource
	private SequenceNumberService sequenceNumberService;

	@Resource
	private NoticeService noticeService;

	@RequestMapping(value = "/query")
	public String query() {
		return "query";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/msgList")
	public String msgList(Model model, HttpSession session, MsgQuery msgQuery) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		final MsgExample example = new MsgExample();
		Criteria criteria = example.createCriteria();
		if (msgQuery != null && msgQuery.getSubmissionStatus() != null) {
			if (msgQuery.getSubmissionStatus() == 1 && roleId < 4L && permissionId < 6L) {
				msgQuery.setExample(criteria);
				model.addAttribute("titleName", "待审核");
			} else if (msgQuery.getSubmissionStatus() == 0 && permissionId < 6L) {
				msgQuery.setRoleId(roleId);
				msgQuery.setExample(criteria);
				model.addAttribute("titleName", "提请草稿");
			} else if (msgQuery.getSubmissionStatus() == 0 && permissionId == 6L) {
				msgQuery.setUserId(userId);
				msgQuery.setExample(criteria);
				model.addAttribute("titleName", "提请草稿");
			}
		} else {
			if (roleId < 4L && permissionId < 6L) {
				criteria.andIdIsNotNull();
			} else if (permissionId < 6L) {
				msgQuery.setRoleId(roleId);
			} else {
				msgQuery.setUserId(userId);
			}
			msgQuery.setExample(criteria);
		}
		example.setOrderByClause("sequence asc");
		List<Msg> msgs = null;
		Page<Msg> page = null;
		page = msgService.selectByExampleAndPage(example, msgQuery.getPageNo());
		msgs = page.getResult();
		final Map<Long, String> roleMap = (Map<Long, String>) session.getAttribute("roleMap");
		List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
		for (Msg msg : msgs) {
			msgExtends.add(new MsgExtend(msg));
		}
		msgExtends = msgSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
		msgExtends = msgCoSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
		List<List<String>> ids = submissionService.selectIdsByMsgList(msgs, roleId);
		msgExtends = attachService.selectMsgExtendByMsgList(msgExtends, ids);
		// 去掉后缀
		for (MsgExtend msgExtend : msgExtends) {
			String attachs[] = msgExtend.getAttachs();
			if (attachs != null && attachs.length > 0) {
				for (int i = 0; i < attachs.length; i++) {
					String fileNameArray[] = attachs[i].replace(".", ",").split(",");
					String fileName = fileNameArray[0];
					attachs[i] = fileName;
				}
				msgExtend.setAttachs(attachs);
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("msgs", msgExtends);
		if (msgQuery != null && msgQuery.getStatus() != null && msgQuery.getStatus() == 0) {
			model.addAttribute("titleName", "督查草稿");
		}
		return "msgList";
	}

	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	@RequestMapping(value = "/upload")
	public String upload(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {
			model.addAttribute("titleName", "督查上传");
		}

		return "upload";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/openMsg")
	public String openMsg(@ModelAttribute("msg") MsgExtend msg, Model model, HttpSession session,
			@ModelAttribute("msg1") String msg1, @ModelAttribute("msg2") String msg2, @ModelAttribute("error") String error) {
		final Map<Long, String> roleMap = (Map<Long, String>) session.getAttribute("roleMap");
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		List<Long> msgSponsorSelect = null;// 存储下拉框选中的主处室
		List<Long> msgCoSponsorSelect = null;// 存储下拉框选中的协助处室
		List<Attach> attachList = null;
		// 标志notice为已读
		noticeService.updateIsRead(msg.getId(), userId);

		if (msg != null && msg.getId() != null && !"".equals(msg.getId()) && msg.getStatus() != null
				&& 0 == msg.getStatus().intValue()) {
			Msg msg0 = msgService.selectById(msg.getId());
			String msgBasis = null;
			msgBasis = msg0.getBasis();
			if (msg0 != null) {
				msgSponsorSelect = msgSponsorService.selectRoleIdByMsgId(msg0.getId());
				msgCoSponsorSelect = msgCoSponsorService.selectRoleIdByMsgId(msg0.getId());
				attachList = attachService.selectByTargetId(msg0.getId(), 0);
			}
			Date limitTime = (msgSponsorService.selectMsgSponsorsByMsgId(msg.getId())).get(0).getLimitTime();
			msg0.setLimitTime(limitTime);
			model.addAttribute("error", error);
			model.addAttribute("msg", msg0);
			model.addAttribute("id", msg.getId());
			model.addAttribute("msgBasis", msgBasis);
			model.addAttribute("basisSelect", msgBasis);
			model.addAttribute("attachs", attachList);
			model.addAttribute("msgSponsorSelect", msgSponsorSelect);
			model.addAttribute("msgCoSponsorSelect", msgCoSponsorSelect);
			return "upload";
		} else if (msg != null && msg.getId() != null && !"".equals(msg.getId())) {
			boolean readable = false;
			readable = msgSponsorService.readable(msg.getId(), roleId) && permissionId < 6L ? true
					: msgCoSponsorService.readable(msg.getId(), roleId) && permissionId < 6L ? true
							: msgContractorService.readable(msg.getId(), userId);
			if (readable || ((1L == roleId || 2L == roleId || 3L == roleId) && permissionId < 6L)) {
				Msg msg0 = msgService.selectById(msg.getId());
				MsgExtend msgExtend = new MsgExtend(msg0);
				List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
				msgExtends.add(msgExtend);
				msgExtends = msgSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
				msgExtends = msgCoSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
				attachList = attachService.selectByTargetId(msgExtend.getId(), 0);
				msgExtend = msgExtends.get(0);
				if (!msgExtend.getSponsorRoleNames().isEmpty()) {
					msgExtend.setSponsorRoleNames(msgExtend.getSponsorRoleNames()
							.substring(0, msgExtend.getSponsorRoleNames().lastIndexOf("<br/>")).replace("<br/>", "、"));
				}
				if (!msgExtend.getCoSponsorRoleNames().isEmpty()) {
					msgExtend.setCoSponsorRoleNames(msgExtend.getCoSponsorRoleNames()
							.substring(0, msgExtend.getCoSponsorRoleNames().lastIndexOf("<br/>"))
							.replace("<br/>", "、"));
				}
				String[] attachs = null;
				String[] attachIds = null;
				if (attachList != null && attachList.size() > 0) {
					attachs = new String[attachList.size()];
					attachIds = new String[attachList.size()];
					for (int j = 0; j < attachList.size(); j++) {
						attachIds[j] = attachList.get(j).getId();
						attachs[j] = attachList.get(j).getAttachFileName();
					}
				}
				boolean callbackable = false;
				callbackable = msgSponsorService.callbackable(msg.getId())
						? msgCoSponsorService.callbackable(msg.getId())
						: false;
				boolean signable = false;
				signable = permissionId < 6L ? msgSponsorService.signable(msg.getId(), roleId) ? true
						: msgCoSponsorService.signable(msg.getId(), roleId) : false;
				msgExtend.setAttachIds(attachIds);
				msgExtend.setAttachs(attachs);
				model.addAttribute("msg", msgExtend);
				model.addAttribute("callbackable", callbackable);
				model.addAttribute("signable", signable);
				model.addAttribute("msg1", msg1);
				model.addAttribute("msg2", msg2);
				return "msg";
			} else {
				return "404";
			}
		} else {
			return "404";
		}
	}

	@Transactional
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces="text/plain")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String insert(MsgExtend msg, @RequestParam(value = "files", required = false) MultipartFile[] files,
			RedirectAttributes reditectModel) {
		MsgCoSponsor msgCoSponsor;
		MsgSponsor msgSponsor;
		Date limitTime = null;
		List<Long> msgSponsorSelect = null;// 存储下拉框选中的主处室
		List<Long> msgCoSponsorSelect = null;// 存储下拉框选中的协助处室
		List<MsgCoSponsor> msgCoSponsors = new ArrayList<MsgCoSponsor>();
		List<MsgSponsor> msgSponsors = new ArrayList<MsgSponsor>();
		String msgId = null;
		// 录入msg类数据库
		// 是否有id来判断是保存还是修改
		if (msg.getBasis().equals("自定义")) {
			msg.setBasis(msg.getMsgBasis());
		}
		try {
			if (msg.getId().isEmpty()) {
				Integer sequenceNumber = sequenceNumberService.next();
				msgSponsorSelect = new ArrayList<Long>();
				msgCoSponsorSelect = new ArrayList<Long>();
				msgId = ApplicationUtils.newUUID();
				msg.setId(msgId);
				msg.setSequence(sequenceNumber);
				limitTime = msg.getLimitTime();
				msg.setLimitTime(null);
				msgService.insertSelective(msg);
				// 录入attach数据库
				if (files.length > 0) {
					attachService.upload(files, msgId, 0);
				}
				// 录入msg_sponsor数据库
				for (int i = 0; i < msg.getRole().size(); i++) {
					long roleId = msg.getRole().get(i);
					msgSponsor = new MsgSponsor(ApplicationUtils.newUUID(), msgId, roleId, 0, 0, "", msg.getStatus(),
							limitTime);
					msgSponsorSelect.add(roleId);
					msgSponsorService.insertSelective(msgSponsor);
					msgSponsors.add(msgSponsor);
				}
				// 录入msg_co-sponsor数据库
				if (msg.getAssitrole() != null && msg.getAssitrole().size() != 0) {
					for (int i = 0; i < msg.getAssitrole().size(); i++) {
						long assitRoldId = msg.getAssitrole().get(i);
						msgCoSponsor = new MsgCoSponsor(ApplicationUtils.newUUID(), msgId, assitRoldId, 0, 0, "",
								msg.getStatus(), limitTime);
						msgCoSponsorSelect.add(assitRoldId);
						msgCoSponsorService.insertSelective(msgCoSponsor);
						msgCoSponsors.add(msgCoSponsor);
					}
				}
			} else {
				final int msgCount = msgService.update(msg);
				limitTime = msg.getLimitTime();
				if (msgCount > 0) {
					msgSponsorSelect = new ArrayList<Long>();
					msgCoSponsorSelect = new ArrayList<Long>();
					// 录入attach数据库
					if (files.length > 0) {
						attachService.upload(files, msg.getId(), 0);
					}
					// 录入msg_sponsor数据库
					for (int i = 0; i < msg.getRole().size(); i++) {
						long roleId = msg.getRole().get(i);
						msgSponsorSelect.add(roleId);
						msgSponsor = new MsgSponsor(ApplicationUtils.newUUID(), msg.getId(), roleId, 0, 0, "",
								msg.getStatus(), limitTime);
						msgSponsors.add(msgSponsor);
					}
					boolean msgSponsorFlag = msgSponsorService.modifyRoleId(msg.getId(), msgSponsors);
					if (!msgSponsorFlag) {
						throw new Exception("修改主办处室出错，操作回滚");
					}
					// 录入msg_co-sponsor数据库
					if (msg.getAssitrole() == null || msg.getAssitrole().size() == 0) {
						msgCoSponsorService.deleteByMgsId(msg.getId());
					} else {
						for (int i = 0; i < msg.getAssitrole().size(); i++) {
							long assitRoldId = msg.getAssitrole().get(i);
							msgCoSponsorSelect.add(assitRoldId);
							msgCoSponsor = new MsgCoSponsor(ApplicationUtils.newUUID(), msg.getId(), assitRoldId, 0, 0, "",
									msg.getStatus(), limitTime);
							msgCoSponsors.add(msgCoSponsor);
						}
						boolean msgCoSponsorFlag = msgCoSponsorService.modifyRoleId(msg.getId(), msgCoSponsors);
						if (!msgCoSponsorFlag) {
							throw new Exception("修改主办处室出错，操作回滚");
						}
					}
				}
			}
			if (msg.getStatus() == 0) {
				// 录入提醒表
				int type = 5;// 草稿箱
				List<Long> roleIdList = new ArrayList<Long>();
				roleIdList.add(1L);
				roleIdList.add(2L);
				roleIdList.add(3L);
				List<Long> roleUserIds = new ArrayList<Long>();
				List<User> roleUsers = userService.selectByRoleIdList(roleIdList);
				for (User user2 : roleUsers) {
					roleUserIds.add(user2.getId());
				}
				noticeService.modifyUserId(msg.getId(), roleUserIds, type, 0);
			} else {
				// 发布之后变为待签收状态
				int type = 3;// 待签收
				List<Long> roleIdList = new ArrayList<Long>();
				for (MsgSponsor msgSponsor2 : msgSponsors) {
					roleIdList.add(msgSponsor2.getRoleId());
				}
				while (msgCoSponsors.size() > 0) {
					for (MsgCoSponsor msgCoSponsor2 : msgCoSponsors) {
						roleIdList.add(msgCoSponsor2.getRoleId());
					}
					break;
				}
				List<Long> roleUserIds = new ArrayList<Long>();
				List<User> roleUsers = userService.selectByRoleIdList(roleIdList);
				for (User user2 : roleUsers) {
					if (user2.getPermissionId() < 6L) {
						roleUserIds.add(user2.getId());
					}
				}
				noticeService.modifySendUserId(msg.getId(), roleUserIds, type);
			}
			reditectModel.addFlashAttribute("msg", msg);
			return "redirect:/rest/msg/openMsg";
		} catch (Exception e) {
			reditectModel.addFlashAttribute("error", e.getMessage());
			reditectModel.addFlashAttribute("msg", msg);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "redirect:/rest/msg/openMsg";
		}
	}

	// 删除
	@RequestMapping(value = "/detele")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String detele(@RequestParam("id") String msgId, Model model, HttpServletResponse resp,
			HttpServletRequest request) throws Exception {
		msgService.delete(msgId);
		attachService.deleteByMsgId(msgId);
		msgCoSponsorService.deleteByMgsId(msgId);
		msgSponsorService.deleteByMgsId(msgId);
		noticeService.noticeByTargetId(msgId);
		return "upload";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sign")
	@RequiresPermissions(value = { PermissionSign.ADMIN, PermissionSign.BAN_GONG_SHI_GUAN_LI,
			PermissionSign.BU_LING_DAO, PermissionSign.CHU_SHI_NEI_QIN,
			PermissionSign.CHU_SHI_FU_ZE_REN }, logical = Logical.OR)
	public String sign(Msg msg, RedirectAttributes model, HttpSession session) throws Exception {
		final Long roleId = (Long) session.getAttribute("roleId");
		if (msg != null && msg.getId() != null) {
			boolean signable = false;
			signable = msgSponsorService.signable(msg.getId(), roleId) ? true
					: msgCoSponsorService.signable(msg.getId(), roleId);
			if (signable) {
				int count = 0;
				count = msgSponsorService.doSign(msg.getId(), roleId);
				count = count + msgCoSponsorService.doSign(msg.getId(), roleId);
				if (count == 1) {
					// 签收成功，notice变为待指派
					int type = 4;// 待指派
					final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
					List<Long> roleUserIds = new ArrayList<Long>();
					for (User user : roleUsers) {
						if (user.getPermissionId() < 6L) {
							roleUserIds.add(user.getId());
						}
					}
					noticeService.modifyUserId(msg.getId(), roleUserIds, type, 0);
					model.addFlashAttribute("msg1", "签收成功！");
					model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
					model.addFlashAttribute("msg", new MsgExtend(msg));
					return "redirect:/rest/msg/openMsg";
				}
			}
		}
		model.addFlashAttribute("msg1", "签收失败！");
		model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		model.addFlashAttribute("msg", new MsgExtend(msg));
		return "redirect:/rest/msg/openMsg";
	}

	@RequestMapping(value = "/callback")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String callBack(@RequestParam("id") String msgId, Model model, HttpSession session,
			HttpServletRequest request, RedirectAttributes reditectModel) throws Exception {
		MsgExtend msg0 = new MsgExtend();
		msg0.setId(msgId);
		if (msgId != null) {
			boolean callbackable = false;
			callbackable = msgSponsorService.callbackable(msgId) ? msgCoSponsorService.callbackable(msgId) : false;
			if (callbackable) {
				int count = msgSponsorService.doCallback(msgId);
				count = count + msgCoSponsorService.doCallback(msgId);
				if (count > 0) {
					msg0.setStatus(0);
				}
				// 撤回重新录入notice提醒表
				int type = 5;// 草稿
				noticeService.noticeByTargetId(msgId);
				List<Long> roleIdList = new ArrayList<Long>();
				roleIdList.add(1L);
				roleIdList.add(2L);
				roleIdList.add(3L);
				List<Long> roleUserIds = new ArrayList<Long>();
				List<User> roleUsers = userService.selectByRoleIdList(roleIdList);
				for (User user2 : roleUsers) {
					if (user2.getPermissionId() < 6L) {
						roleUserIds.add(user2.getId());
					}
				}
				noticeService.modifyUserId(msgId, roleUserIds, type, 0);
			} else {
				reditectModel.addFlashAttribute("msg1", "撤回失败！");
				reditectModel.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
			}

			reditectModel.addFlashAttribute("msg", msg0);
			return "redirect:/rest/msg/openMsg";
		} else {
			return "404";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assign")
	@Transactional(rollbackFor = Exception.class)
	@RequiresPermissions(value = { PermissionSign.ADMIN, PermissionSign.BAN_GONG_SHI_GUAN_LI,
			PermissionSign.BU_LING_DAO, PermissionSign.CHU_SHI_NEI_QIN,
			PermissionSign.CHU_SHI_FU_ZE_REN }, logical = Logical.OR)
	public String assign(String msgId, @RequestParam(value = "userIds[]", required = false) long[] userIds,
			RedirectAttributes model, HttpSession session) throws Exception {
		final Long roleId = (Long) session.getAttribute("roleId");
		final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
		final MsgExtend msgExtend = new MsgExtend();
		msgExtend.setId(msgId);
		if (msgId != null && !"".equals(msgId)) {
			boolean assignable = false;
			assignable = msgSponsorService.assignable(msgId, roleId) ? true
					: msgCoSponsorService.assignable(msgId, roleId);
			if (assignable) {
				final List<Long> roleUserIds = new ArrayList<Long>();
				for (User roleUser : roleUsers) {
					roleUserIds.add(roleUser.getId());
				}
				msgContractorService.modifyUserId(msgId, userIds, roleUserIds);
				// 成功删除改督查事项待分派提醒
				NoticeExample example = new NoticeExample();
				example.createCriteria().andUserIdIn(roleUserIds).andTargetIdEqualTo(msgId).andTargetTypeEqualTo(0);
				noticeService.deleteByExample(example);
				model.addFlashAttribute("msg1", "分派成功！");
				model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
				model.addFlashAttribute("msg", msgExtend);
				return "redirect:/rest/msg/openMsg";
			}
		}
		model.addFlashAttribute("msg1", "分派失败！");
		model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		model.addFlashAttribute("msg", msgExtend);
		return "redirect:/rest/msg/openMsg";
	}

	@RequestMapping(value = "/getContent")
	public String getContent(Msg msg, Model model, HttpSession session) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		if (msg != null && msg.getId() != null) {
			boolean readable = false;
			readable = msgSponsorService.readable(msg.getId(), roleId) && permissionId < 6L ? true
					: msgCoSponsorService.readable(msg.getId(), roleId) && permissionId < 6L ? true
							: msgContractorService.readable(msg.getId(), userId);
			List<MsgSponsorExtend> msgSponsorExtends = null;
			List<MsgCoSponsorExtend> msgCoSponsorExtends = null;
			if ((1L == roleId || 2L == roleId || 3L == roleId) && permissionId < 6L) {
				List<MsgSponsor> msgSponsors = msgSponsorService.selectSignedMsgSponsorsByMsgId(msg.getId());
				List<MsgCoSponsor> msgCoSponsors = msgCoSponsorService.selectSignedMsgCoSponsorsByMsgId(msg.getId());
				List<Integer> status0 = new ArrayList<Integer>();
				status0.add(1);
				status0.add(2);
				List<Integer> status1 = new ArrayList<Integer>(status0);
				status1.add(0);
				if (msgSponsors != null && msgSponsors.size() > 0) {
					msgSponsorExtends = new ArrayList<MsgSponsorExtend>();
					MsgSponsorExtend msgSponsorExtend = null;
					for (MsgSponsor msgSponsor : msgSponsors) {
						msgSponsorExtend = new MsgSponsorExtend(msgSponsor);
						msgSponsorExtend.setAttachs(attachService.selectByTargetId(msgSponsorExtend.getId(), 1));
						msgSponsorExtend.setLogs(logService.getLogsByTargetId(msgSponsorExtend.getId()));
						if (msgSponsorExtend.getLogs() != null && msgSponsorExtend.getLogs().size() > 0) {
							msgSponsorExtend.getLogs().get(0).setContent(msgSponsorExtend.getLogs().get(0).getContent() + "（最新）");
						}
						if (msgSponsorExtend.getStatus() > 2
								|| (msgSponsorExtend.getRoleId() == roleId && msgSponsorExtend.getStatus() < 3)) {
							msgSponsorExtend.setEditabled(true);
							if (msgSponsorExtend.getRoleId() == roleId && msgSponsorExtend.getStatus() < 3) {
								msgSponsorExtend.setAssignable(true);
							} else {
								msgSponsorExtend.setAssignable(false);
							}
						} else {
							msgSponsorExtend.setEditabled(false);
							msgSponsorExtend.setAssignable(false);
						}
						final List<User> roleUsers = userService.selectByRoleId(msgSponsorExtend.getRoleId());
						final List<Long> userIds = msgContractorService.selectByMsgIdAndRoleUsers(msg.getId(),
								roleUsers);
						msgSponsorExtend.setUsers(userService.selectByUserIds(userIds));
						List<Submission> submissions = null;
						if (msgSponsorExtend.getRoleId() == roleId) {
							submissions = submissionService.selectByMsgId(msgSponsorExtend.getId(), status1);
						} else {
							submissions = submissionService.selectByMsgId(msgSponsorExtend.getId(), status0);
						}
						if (submissions != null && submissions.size() > 0) {
							List<SubmissionExtend> submissionExtends = new ArrayList<SubmissionExtend>();
							for (Submission submission : submissions) {
								SubmissionExtend submissionExtend = new SubmissionExtend(submission);
								if (submissionExtend.getOwnerId() != null) {
									submissionExtend.setOwnerDesc(
											(userService.selectById(submissionExtend.getOwnerId()).getUserdesc()));
								}
								if (submissionExtend.getSuperiorVerifiUserId() != null) {
									submissionExtend.setSuperiorVerifiUserDesc((userService
											.selectById(submissionExtend.getSuperiorVerifiUserId()).getUserdesc()));
								}
								submissionExtend.setVerifiable(true);
								submissionExtends.add(submissionExtend);
							}
							msgSponsorExtend.setSubmissions(submissionExtends);
						}
						msgSponsorExtends.add(msgSponsorExtend);
					}
				}
				if (msgCoSponsors != null && msgCoSponsors.size() > 0) {
					msgCoSponsorExtends = new ArrayList<MsgCoSponsorExtend>();
					MsgCoSponsorExtend msgCoSponsorExtend = null;
					for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
						msgCoSponsorExtend = new MsgCoSponsorExtend(msgCoSponsor);
						msgCoSponsorExtend.setAttachs(attachService.selectByTargetId(msgCoSponsorExtend.getId(), 1));
						msgCoSponsorExtend.setLogs(logService.getLogsByTargetId(msgCoSponsorExtend.getId()));
						if (msgCoSponsorExtend.getLogs() != null && msgCoSponsorExtend.getLogs().size() > 0) {
							msgCoSponsorExtend.getLogs().get(0).setContent(msgCoSponsorExtend.getLogs().get(0).getContent() + "（最新）");
						}
						if (msgCoSponsorExtend.getStatus() > 2
								|| (msgCoSponsorExtend.getRoleId() == roleId && msgCoSponsorExtend.getStatus() < 3)) {
							msgCoSponsorExtend.setEditabled(true);
							if (msgCoSponsorExtend.getRoleId() == roleId && msgCoSponsorExtend.getStatus() < 3) {
								msgCoSponsorExtend.setAssignable(true);
							} else {
								msgCoSponsorExtend.setAssignable(false);
							}
						} else {
							msgCoSponsorExtend.setEditabled(false);
							msgCoSponsorExtend.setAssignable(false);
						}
						final List<User> roleUsers = userService.selectByRoleId(msgCoSponsorExtend.getRoleId());
						final List<Long> userIds = msgContractorService.selectByMsgIdAndRoleUsers(msg.getId(),
								roleUsers);
						msgCoSponsorExtend.setUsers(userService.selectByUserIds(userIds));
						List<Submission> submissions = null;
						if (msgCoSponsorExtend.getRoleId() == roleId) {
							submissions = submissionService.selectByMsgId(msgCoSponsorExtend.getId(), status1);
						} else {
							submissions = submissionService.selectByMsgId(msgCoSponsorExtend.getId(), status0);
						}
						if (submissions != null && submissions.size() > 0) {
							List<SubmissionExtend> submissionExtends = new ArrayList<SubmissionExtend>();
							for (Submission submission : submissions) {
								SubmissionExtend submissionExtend = new SubmissionExtend(submission);
								if (submissionExtend.getOwnerId() != null) {
									submissionExtend.setOwnerDesc(
											(userService.selectById(submissionExtend.getOwnerId()).getUserdesc()));
								}
								if (submissionExtend.getSuperiorVerifiUserId() != null) {
									submissionExtend.setSuperiorVerifiUserDesc((userService
											.selectById(submissionExtend.getSuperiorVerifiUserId()).getUserdesc()));
								}
								submissionExtend.setVerifiable(true);
								submissionExtends.add(submissionExtend);
							}
							msgCoSponsorExtend.setSubmissions(submissionExtends);
						}
						msgCoSponsorExtends.add(msgCoSponsorExtend);
					}
				}
				model.addAttribute("msgSponsorExtends", msgSponsorExtends);
				model.addAttribute("msgCoSponsorExtends", msgCoSponsorExtends);
				return "msgContent";
			} else if (readable) {
				List<MsgSponsor> msgSponsors = msgSponsorService.selectMsgSponsorsByMsgIdRoleId(msg.getId(), roleId);
				List<MsgCoSponsor> msgCoSponsors = msgCoSponsorService.selectMsgCoSponsorsByMsgIdRoleId(msg.getId(),
						roleId);
				List<Integer> status0 = new ArrayList<Integer>();
				status0.add(0);
				status0.add(1);
				status0.add(2);
				if (msgSponsors != null && msgSponsors.size() > 0) {
					msgSponsorExtends = new ArrayList<MsgSponsorExtend>();
					MsgSponsorExtend msgSponsorExtend = null;
					for (MsgSponsor msgSponsor : msgSponsors) {
						msgSponsorExtend = new MsgSponsorExtend(msgSponsor);
						msgSponsorExtend.setAttachs(attachService.selectByTargetId(msgSponsorExtend.getId(), 1));
						msgSponsorExtend.setLogs(logService.getLogsByTargetId(msgSponsorExtend.getId()));
						if (msgSponsorExtend.getLogs() != null && msgSponsorExtend.getLogs().size() > 0) {
							msgSponsorExtend.getLogs().get(0).setContent(msgSponsorExtend.getLogs().get(0).getContent() + "（最新）");
						}
						if (msgSponsorExtend.getStatus() < 3) {
							msgSponsorExtend.setEditabled(true);
							if (permissionId < 6L) {
								msgSponsorExtend.setAssignable(true);
							} else {
								msgSponsorExtend.setAssignable(false);
							}
						} else {
							msgSponsorExtend.setEditabled(false);
							msgSponsorExtend.setAssignable(false);
						}
						final List<User> roleUsers = userService.selectByRoleId(roleId);
						final List<Long> userIds = msgContractorService.selectByMsgIdAndRoleUsers(msg.getId(),
								roleUsers);
						msgSponsorExtend.setUsers(userService.selectByUserIds(userIds));
						List<Submission> submissions = null;
						submissions = submissionService.selectByMsgId(msgSponsorExtend.getId(), status0);
						if (submissions != null && submissions.size() > 0) {
							List<SubmissionExtend> submissionExtends = new ArrayList<SubmissionExtend>();
							for (Submission submission : submissions) {
								SubmissionExtend submissionExtend = new SubmissionExtend(submission);
								if (submissionExtend.getOwnerId() != null) {
									submissionExtend.setOwnerDesc(
											(userService.selectById(submissionExtend.getOwnerId()).getUserdesc()));
								}
								if (submissionExtend.getSuperiorVerifiUserId() != null) {
									submissionExtend.setSuperiorVerifiUserDesc((userService
											.selectById(submissionExtend.getSuperiorVerifiUserId()).getUserdesc()));
								}
								submissionExtend.setVerifiable(false);
								submissionExtends.add(submissionExtend);
							}
							msgSponsorExtend.setSubmissions(submissionExtends);
						}
						msgSponsorExtends.add(msgSponsorExtend);
					}
				}
				if (msgCoSponsors != null && msgCoSponsors.size() > 0) {
					msgCoSponsorExtends = new ArrayList<MsgCoSponsorExtend>();
					MsgCoSponsorExtend msgCoSponsorExtend = null;
					for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
						msgCoSponsorExtend = new MsgCoSponsorExtend(msgCoSponsor);
						msgCoSponsorExtend.setAttachs(attachService.selectByTargetId(msgCoSponsorExtend.getId(), 1));
						msgCoSponsorExtend.setLogs(logService.getLogsByTargetId(msgCoSponsorExtend.getId()));
						if (msgCoSponsorExtend.getLogs() != null && msgCoSponsorExtend.getLogs().size() > 0) {
							msgCoSponsorExtend.getLogs().get(0).setContent(msgCoSponsorExtend.getLogs().get(0).getContent() + "（最新）");
						}
						if (msgCoSponsorExtend.getStatus() < 3) {
							msgCoSponsorExtend.setEditabled(true);
							if (permissionId < 6L) {
								msgCoSponsorExtend.setAssignable(true);
							} else {
								msgCoSponsorExtend.setAssignable(false);
							}
						} else {
							msgCoSponsorExtend.setEditabled(false);
							msgCoSponsorExtend.setAssignable(false);
						}
						final List<User> roleUsers = userService.selectByRoleId(roleId);
						final List<Long> userIds = msgContractorService.selectByMsgIdAndRoleUsers(msg.getId(),
								roleUsers);
						msgCoSponsorExtend.setUsers(userService.selectByUserIds(userIds));
						List<Submission> submissions = null;
						submissions = submissionService.selectByMsgId(msgCoSponsorExtend.getId(), status0);
						if (submissions != null && submissions.size() > 0) {
							List<SubmissionExtend> submissionExtends = new ArrayList<SubmissionExtend>();
							for (Submission submission : submissions) {
								SubmissionExtend submissionExtend = new SubmissionExtend(submission);
								if (submissionExtend.getOwnerId() != null) {
									submissionExtend.setOwnerDesc(
											(userService.selectById(submissionExtend.getOwnerId()).getUserdesc()));
								}
								if (submissionExtend.getSuperiorVerifiUserId() != null) {
									submissionExtend.setSuperiorVerifiUserDesc((userService
											.selectById(submissionExtend.getSuperiorVerifiUserId()).getUserdesc()));
								}
								submissionExtend.setVerifiable(false);
								submissionExtends.add(submissionExtend);
							}
							msgCoSponsorExtend.setSubmissions(submissionExtends);
						}
						msgCoSponsorExtends.add(msgCoSponsorExtend);
					}
				}
				model.addAttribute("msgSponsorExtends", msgSponsorExtends);
				model.addAttribute("msgCoSponsorExtends", msgCoSponsorExtends);
				return "msgContent";
			}
		}
		return "404";
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveMsgSponsor", method = RequestMethod.POST, produces="text/plain")
	public String saveMsgSponsor(MsgSponsor msgSponsor, @RequestParam(value = "files", required = false) MultipartFile[] files, RedirectAttributes model, HttpSession session)
			throws Exception {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		final User userInfo = (User) session.getAttribute("userInfo");
		final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
		if (msgSponsor != null && msgSponsor.getId() != null) {
			final MsgSponsor msgSponsor0 = msgSponsorService.selectById(msgSponsor.getId());
			if (msgSponsor0 != null) {
				final List<Long> userIds = msgContractorService.selectByMsgIdAndRoleUsers(msgSponsor0.getMsgId(),
						roleUsers);
				boolean editabled = false;
				if (roleId < 4L && permissionId < 6L && msgSponsor0.getStatus() > 2) {
					editabled = true;
				} else if (msgSponsor0.getStatus() < 3 && msgSponsor0.getRoleId() == roleId && permissionId < 6L) {
					editabled = true;
				} else if (userIds != null && userIds.contains(userId)) {
					editabled = true;
				}
				if (editabled) {
					try {
						final int count = msgSponsorService.update(msgSponsor);
						if (count > 0) {
							if (files.length > 0) {
								attachService.upload(files, msgSponsor.getId(), 1);
							}
							logService.log(new Log(ApplicationUtils.newUUID(), userId, msgSponsor.getId(), userInfo.getUserdesc()));
							// 动态更新==0
							int type = 0;
							List<User> users = userService.selectByRoleId(msgSponsor0.getRoleId());
							for (User user : users) {
								NoticeExample example = new NoticeExample();
								example.createCriteria().andUserIdEqualTo(user.getId())
										.andTargetIdEqualTo(msgSponsor0.getMsgId());
								noticeService.deleteByExample(example);
								Notice notice = new Notice(user.getId(), type, msgSponsor0.getMsgId(), 1,
										ApplicationUtils.getTime(), 1);
								noticeService.addNotice(notice);
							}
	
							final MsgExtend msgExtend = new MsgExtend();
							msgExtend.setId(msgSponsor0.getMsgId());
							model.addFlashAttribute("msg1", "保存办理情况成功！");
							model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
							model.addFlashAttribute("msg", msgExtend);
							return "redirect:/rest/msg/openMsg";
						}
					} catch (Exception e) {
						final MsgExtend msgExtend = new MsgExtend();
						msgExtend.setId(msgSponsor0.getMsgId());
						model.addFlashAttribute("msg1", e.getMessage());
						model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
						model.addFlashAttribute("msg", msgExtend);
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return "redirect:/rest/msg/openMsg";
					}
				}
				final MsgExtend msgExtend = new MsgExtend();
				msgExtend.setId(msgSponsor0.getMsgId());
				model.addFlashAttribute("msg1", "保存办理情况失败！ --无修改权限");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
				model.addFlashAttribute("msg", msgExtend);
				return "redirect:/rest/msg/openMsg";
			}
		}
		return "404";
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveMsgCoSponsor", method = RequestMethod.POST, produces="text/plain")
	public String saveMsgCoSponsor(MsgCoSponsor msgCoSponsor, @RequestParam(value = "files", required = false) MultipartFile[] files, RedirectAttributes model, HttpSession session)
			throws Exception {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		final User userInfo = (User) session.getAttribute("userInfo");
		final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
		if (msgCoSponsor != null && msgCoSponsor.getId() != null) {
			final MsgCoSponsor msgCoSponsor0 = msgCoSponsorService.selectById(msgCoSponsor.getId());
			if (msgCoSponsor0 != null) {
				final List<Long> userIds = msgContractorService.selectByMsgIdAndRoleUsers(msgCoSponsor0.getMsgId(),
						roleUsers);
				boolean editabled = false;
				if (roleId < 4L && permissionId < 6L && msgCoSponsor0.getStatus() > 2) {
					editabled = true;
				} else if (msgCoSponsor0.getStatus() < 3 && msgCoSponsor0.getRoleId() == roleId && permissionId < 6L) {
					editabled = true;
				} else if (userIds != null && userIds.contains(userId)) {
					editabled = true;
				}
				if (editabled) {
					try {
						final int count = msgCoSponsorService.update(msgCoSponsor);
						if (count > 0) {
							if (files.length > 0) {
								attachService.upload(files, msgCoSponsor.getId(), 1);
							}
							logService.log(new Log(ApplicationUtils.newUUID(), userId, msgCoSponsor.getId(), userInfo.getUserdesc()));
							// 动态更新==0
							int type = 0;
							List<User> users = userService.selectByRoleId(msgCoSponsor0.getRoleId());
							for (User user : users) {
								NoticeExample example = new NoticeExample();
								example.createCriteria().andUserIdEqualTo(user.getId())
										.andTargetIdEqualTo(msgCoSponsor0.getMsgId());
								noticeService.deleteByExample(example);
								Notice notice = new Notice(user.getId(), type, msgCoSponsor0.getMsgId(), 1,
										ApplicationUtils.getTime(), 1);
								noticeService.addNotice(notice);
							}
							final MsgExtend msgExtend = new MsgExtend();
							msgExtend.setId(msgCoSponsor0.getMsgId());
							model.addFlashAttribute("msg1", "保存办理情况成功！");
							model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
							model.addFlashAttribute("msg", msgExtend);
							return "redirect:/rest/msg/openMsg";
						}
					} catch (Exception e) {
						final MsgExtend msgExtend = new MsgExtend();
						msgExtend.setId(msgCoSponsor0.getMsgId());
						model.addFlashAttribute("msg1", e.getMessage());
						model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
						model.addFlashAttribute("msg", msgExtend);
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return "redirect:/rest/msg/openMsg";
					}
				}
				final MsgExtend msgExtend = new MsgExtend();
				msgExtend.setId(msgCoSponsor0.getMsgId());
				model.addFlashAttribute("msg1", "保存办理情况失败！ --无修改权限");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
				model.addFlashAttribute("msg", msgExtend);
				return "redirect:/rest/msg/openMsg";
			}
		}
		return "404";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/msgListByNotice")
	public String noticeList(@RequestParam("pageNo") int pageNo, @RequestParam("status") Integer status,
			@RequestParam("type") Integer type, Model model, HttpSession session) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long userId = (Long) session.getAttribute("userId");
		final MsgExample example = new MsgExample();
		List<Notice> notices = null;
		List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
		List<Msg> msgs = new ArrayList<Msg>();
		List<String> msgIds = new ArrayList<String>();
		Page<Msg> page = null;
		example.setOrderByClause("sequence asc");
		Criteria criteria = example.createCriteria();
		if (status != null && status == 0) {
			notices = noticeService.selectMsg(type, userId);
		} else {
			notices = noticeService.selectMsg(type, userId, 1);
		}
		for (Notice notice : notices) {
			Msg msg = msgService.selectById(notice.getTargetId());
			msgExtends.add(new MsgExtend(msg));
			msgs.add(msg);
			msgIds.add(msg.getId());
		}
		criteria.andIdIn(msgIds);
		page = msgService.selectByExampleAndPage(example, pageNo);
		final Map<Long, String> roleMap = (Map<Long, String>) session.getAttribute("roleMap");
		msgExtends = msgSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
		msgExtends = msgCoSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
		List<List<String>> ids = submissionService.selectIdsByMsgList(msgs, roleId);
		msgExtends = attachService.selectMsgExtendByMsgList(msgExtends, ids);
		model.addAttribute("msgs", msgExtends);
		model.addAttribute("page", page);
		model.addAttribute("titleName", "提醒预览");
		model.addAttribute("status", status);
		model.addAttribute("type", type);
		return "msgList";
	}
}
