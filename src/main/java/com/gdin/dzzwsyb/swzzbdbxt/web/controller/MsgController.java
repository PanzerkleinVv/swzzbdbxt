package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.enums.MessageColor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractorExample;
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
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
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
		if (roleId < 4L && permissionId < 6L) {
			criteria.andIdIsNotNull();
		} else if (permissionId < 6L) {
			msgQuery.setRoleId(roleId);
		} else {
			msgQuery.setUserId(userId);
		}
		msgQuery.setExample(criteria);
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
			@ModelAttribute("msg1") String msg1, @ModelAttribute("msg2") String msg2) {
		final Map<Long, String> roleMap = (Map<Long, String>) session.getAttribute("roleMap");
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		List<Long> msgSponsorSelect = null;// 存储下拉框选中的主处室
		List<Long> msgCoSponsorSelect = null;// 存储下拉框选中的协助处室
		if (msg != null && msg.getId() != null && !"".equals(msg.getId()) && msg.getStatus() != null
				&& 0 == msg.getStatus().intValue()) {
			Msg msg0 = msgService.selectById(msg.getId());
			model.addAttribute("msg", msg0);
			String msgBasis = null;
			msgBasis = msg0.getBasis();
			if (msg0 != null) {
				msgSponsorSelect = msgSponsorService.selectRoleIdByMsgId(msg0.getId());
				msgCoSponsorSelect = msgCoSponsorService.selectRoleIdByMsgId(msg0.getId());

			}
			model.addAttribute("id", msg.getId());
			model.addAttribute("msgBasis", msgBasis);
			model.addAttribute("basisSelect", msgBasis);
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
				List<Attach> attachList = attachService.selectByTargetId(msgExtend.getId());
				msgExtend = msgExtends.get(0);
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

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String insert(@RequestParam("msgBasis") String msgBasis, @RequestParam("msgId") String id,
			@RequestParam("sequenceNumber") Integer sequenceNumbers, @RequestParam("status") int status,
			@RequestParam("role") String role, @RequestParam("assitrole") String assitrole, @Valid Msg msg,
			@Valid User user, Model model, HttpServletRequest request, RedirectAttributes reditectModel)
			throws Exception {
		Attach attach;
		MsgCoSponsor msgCoSponsor;
		MsgSponsor msgSponsor;
		List<Long> msgSponsorSelect = null;// 存储下拉框选中的主处室
		List<Long> msgCoSponsorSelect = null;// 存储下拉框选中的协助处室
		List<MsgCoSponsor> msgCoSponsors = null;
		List<MsgSponsor> msgSponsors = null;
		String basisSelect = msg.getBasis();
		Integer sequenceNumber = sequenceNumberService.next();
		List<String> fileNameLists = (List<String>) request.getSession().getAttribute("fileNameLists");
		String msgId = null;
		// 录入msg类数据库
		// 是否有id来判断是保存还是修改
		if (msg.getBasis().equals("自定义")) {
			msg.setBasis(msgBasis);
		}
		if (id.isEmpty()) {
			msgId = ApplicationUtils.newUUID();
			msgSponsorSelect = new ArrayList<Long>();
			msgCoSponsorSelect = new ArrayList<Long>();
			msgId = ApplicationUtils.newUUID();
			msg.setId(msgId);
			msg.setSequence(sequenceNumber);
			msgService.insertSelective(msg);
			while (fileNameLists != null) {
				for (String fileName : fileNameLists) {
					String attachId = ApplicationUtils.newUUID();
					attach = new Attach(attachId, msgId, 0, fileName, ApplicationUtils.getTime());
					attachService.insert(attach);
				}
				break;
			}
			// 录入msg_co-sponsor数据库
			String roleIdArr[] = role.split(",");
			for (int i = 0; i < roleIdArr.length; i++) {
				long roleId = Long.parseLong(roleIdArr[i]);
				msgSponsor = new MsgSponsor(ApplicationUtils.newUUID(), msgId, roleId, 0, 0, "", status);
				msgSponsorSelect.add(roleId);
				msgSponsorService.insertSelective(msgSponsor);
			}
			// 录入msg_sponsor数据库
			if (assitrole.equals("null")) {
			} else {
				String assitroldIdArr[] = assitrole.split(",");
				for (int i = 0; i < assitroldIdArr.length; i++) {
					long assitRoldId = Long.parseLong(assitroldIdArr[i]);
					msgCoSponsor = new MsgCoSponsor(ApplicationUtils.newUUID(), msgId, assitRoldId, 0, 0, "", status);
					msgCoSponsorSelect.add(assitRoldId);
					msgCoSponsorService.insertSelective(msgCoSponsor);
				}
			}
		} else {
			msgId = id;
			msg.setId(id);
			msg.setSequence(sequenceNumbers);
			final int msgCount = msgService.update(msg);
			if (msgCount > 0) {
				msgSponsorSelect = new ArrayList<Long>();
				msgCoSponsorSelect = new ArrayList<Long>();
				// 录入attach数据库
				while (fileNameLists != null) {
					for (String fileName : fileNameLists) {
						attachService.deleteByMsgId(id);
						String attachId = ApplicationUtils.newUUID();
						attach = new Attach(attachId, id, status, fileName, ApplicationUtils.getTime());
						attachService.insert(attach);
					}
					break;
				}
				// 录入msg_co-sponsor数据库
				msgSponsors = new ArrayList<MsgSponsor>();
				String roleIdArr[] = role.split(",");
				for (int i = 0; i < roleIdArr.length; i++) {
					long roleId = Long.parseLong(roleIdArr[i]);
					msgSponsorSelect.add(roleId);
					msgSponsor = new MsgSponsor(ApplicationUtils.newUUID(), id, roleId, 0, 0, "", status);
					msgSponsors.add(msgSponsor);
				}
				boolean msgSponsorFlag = msgSponsorService.modifyRoleId(id, msgSponsors);
				if (!msgSponsorFlag) {
					throw new Exception("修改主办处室出错，操作回滚");
				}
				// 录入msg_sponsor数据库
				if (assitrole.equals("null")) {
					msgCoSponsorService.deleteByMgsId(id);
				} else {
					String assitroldIdArr[] = assitrole.split(",");
					msgCoSponsors = new ArrayList<MsgCoSponsor>();
					for (int i = 0; i < assitroldIdArr.length; i++) {
						long assitRoldId = Long.parseLong(assitroldIdArr[i]);
						msgCoSponsorSelect.add(assitRoldId);
						msgCoSponsor = new MsgCoSponsor(ApplicationUtils.newUUID(), id, assitRoldId, 0, 0, "", status);
						msgCoSponsors.add(msgCoSponsor);
					}
					boolean msgCoSponsorFlag = msgCoSponsorService.modifyRoleId(id, msgCoSponsors);
					if (!msgCoSponsorFlag) {
						throw new Exception("修改主办处室出错，操作回滚");
					}
				}
			}
		}
		model.addAttribute("id", msgId);
		model.addAttribute("basisSelect", basisSelect);
		model.addAttribute("msgSponsorSelect", msgSponsorSelect);
		model.addAttribute("msgCoSponsorSelect", msgCoSponsorSelect);
		model.addAttribute("msgBasis", msgBasis);
		model.addAttribute("sequenceNumber", sequenceNumber);
		model.addAttribute("fileName", fileNameLists);
		request.getSession().removeAttribute("fileNameLists");
		model.addAttribute("id", msgId);
		if (status == 0) {
			//录入提醒表
			int type = 5;//草稿箱
			
			noticeService.noticeByTargetId(msgId);
			List<Long> roleIdList = new ArrayList<Long>();
			roleIdList.add(1L);
			roleIdList.add(2L);
			roleIdList.add(3L);
			List<Long> roleUserIds = new ArrayList<Long>();
			List<User> roleUsers = userService.selectByRoleIdList(roleIdList);
			for(User user2 : roleUsers) {
				roleUserIds.add(user2.getId());
			}
			noticeService.modifyUserId(msgId, roleUserIds,type);
			return "upload";
		} else {
			//发布之后变为待签收状态
			int type = 3 ;//待签收
			noticeService.noticeByTargetId(msgId);//根据msgid删除notice
			List<Long> roleIdList = new ArrayList<Long>();
			for(MsgSponsor msgSponsor2 : msgSponsors) {
				roleIdList.add(msgSponsor2.getRoleId());
			}
			while(msgCoSponsors.size()>0) {
				for(MsgCoSponsor msgCoSponsor2 : msgCoSponsors) {
					roleIdList.add(msgCoSponsor2.getRoleId());
				}
				break;
			}
			List<Long> roleUserIds = new ArrayList<Long>();
			List<User> roleUsers = userService.selectByRoleIdList(roleIdList);
			for(User user2 : roleUsers) {
				roleUserIds.add(user2.getId());
			}
			noticeService.modifyUserId(msgId, roleUserIds,type);
			return "redirect:/rest/msg/openMsg?id=" + msgId;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gett")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String get(@RequestParam("msgBasis") String msgBasis, @RequestParam("msgId") String id,
			@RequestParam("sequenceNumber") Integer sequenceNumbers, @RequestParam("role") String role, @Valid Msg msg,
			@Valid User user, Model model, HttpServletResponse resp, HttpServletRequest request) {

		String basisSelect;
		ArrayList<Long> msgSponsorSelect = new ArrayList<Long>();
		List<Role> roles = (List<Role>) request.getSession().getAttribute("roles");
		ArrayList<Long> roleList = null;
		roleList = new ArrayList<Long>();
		if (role.length() > 0) {
			String roleIdArr[] = role.split(",");
			for (int i = 0; i < roleIdArr.length; i++) {
				for (Role role2 : roles) {
					if (Long.parseLong(roleIdArr[i]) == (role2.getId())) {
						roleList.add(role2.getId());
						msgSponsorSelect.add(role2.getId());
						break;
					}
				}
			}
		} else {
			roleList = null;
			msgSponsorSelect = null;
		}
		basisSelect = msg.getBasis();
		model.addAttribute("id", id);
		model.addAttribute("msgBasis", msgBasis);
		model.addAttribute("sequenceNumber", sequenceNumbers);
		model.addAttribute("basisSelect", basisSelect);
		model.addAttribute("roleList", roleList);
		model.addAttribute("msgSponsorSelect", msgSponsorSelect);
		return "upload";
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
					//签收成功，notice变为待指派
					int type = 4;//待指派
					final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
					List<Long> roleUserIds = new ArrayList<Long>();
					for(User user : roleUsers) {
						if(user.getPermissionId() < 6L) {
							roleUserIds.add(user.getId());
						}
					}
					noticeService.modifyUserId(msg.getId(), roleUserIds, type);
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
	public String callBack(@RequestParam("id") String msgId,Model model,HttpSession session, HttpServletRequest request,RedirectAttributes reditectModel) throws Exception {
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
				//撤回重新录入notice提醒表
				int type = 5;//草稿
				noticeService.noticeByTargetId(msgId);
				List<Long> roleIdList = new ArrayList<Long>();
				roleIdList.add(1L);
				roleIdList.add(2L);
				roleIdList.add(3L);
				List<Long> roleUserIds = new ArrayList<Long>();
				List<User> roleUsers = userService.selectByRoleIdList(roleIdList);
				for(User user2 : roleUsers) {
					if(user2.getPermissionId() < 6L) {
						roleUserIds.add(user2.getId());
					}
				}
				noticeService.modifyUserId(msgId, roleUserIds,type);
			}
			else {
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
	public String assign(String msgId, @RequestParam(value = "userIds[]") long[] userIds, RedirectAttributes model,
			HttpSession session) throws Exception {
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
				//成功删除改督查事项待分派提醒
				noticeService.noticeByTargetId(msgId);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveMsgSponsor")
	public String saveMsgSponsor(MsgSponsor msgSponsor, RedirectAttributes model, HttpSession session) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
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
					final int count = msgSponsorService.update(msgSponsor);
					if (count > 0) {
						final MsgExtend msgExtend = new MsgExtend();
						msgExtend.setId(msgSponsor0.getMsgId());
						model.addFlashAttribute("msg1", "保存办理情况成功！");
						model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
						model.addFlashAttribute("msg", msgExtend);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveMsgCoSponsor")
	public String saveMsgCoSponsor(MsgCoSponsor msgCoSponsor, RedirectAttributes model, HttpSession session) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
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
					final int count = msgCoSponsorService.update(msgCoSponsor);
					if (count > 0) {
						final MsgExtend msgExtend = new MsgExtend();
						msgExtend.setId(msgCoSponsor0.getMsgId());
						model.addFlashAttribute("msg1", "保存办理情况成功！");
						model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
						model.addFlashAttribute("msg", msgExtend);
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
	
	@RequestMapping("/msgListByNotice")
	public String noticeList(@RequestParam("pageNo") int pageNo,@RequestParam("status") Integer status,@RequestParam("type") Integer type,Model model, HttpSession session) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		final MsgExample example = new MsgExample();
		List<Notice> notices = null;	
		List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
		List<Msg> msgs = new ArrayList<Msg>();
		List<String> msgIds = new ArrayList<String>();
		Page<Msg> page = null;
		example.setOrderByClause("sequence asc");
		Criteria criteria = example.createCriteria();
		if(status != null && status == 0) {
			notices = noticeService.selectMsg(type,userId);
		}
		else {
			notices = noticeService.selectMsg(type, userId,1);
		}
		for(Notice notice : notices) {
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
		model.addAttribute("status",status);
		model.addAttribute("type",type);
		return "msgList";
	}
}
