package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

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
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample.Criteria;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.PermissionSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgQuery;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;

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

	// 立项号
	@Resource
	private SequenceNumberService sequenceNumberService;

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
		final List<String> msgId = new ArrayList<String>();
		if (roleId < 4L && permissionId < 6L) {
			criteria.andIdIsNotNull();
		} else if (permissionId < 6L) {
			msgId.addAll(msgSponsorService.selectMsgIdByRoleId(roleId));
			msgId.addAll(msgCoSponsorService.selectMsgIdByRoleId(roleId));
			criteria.andIdIn(msgId);
		} else {
			msgId.addAll(msgContractorService.selectMsgIdByUserId(userId));
			criteria.andIdIn(msgId);
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
			if (readable || 1L == roleId || 2L == roleId || 3L == roleId) {
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
				callbackable =	msgSponsorService.callbackable(msg.getId()) ? msgCoSponsorService.callbackable(msg.getId()) : false;
				boolean signable = false;
				signable = permissionId < 6L ? msgSponsorService.signable(msg.getId(), roleId) ? true
						: msgCoSponsorService.signable(msg.getId(), roleId) : false;
				boolean assignable = false;
				assignable = permissionId < 6L ? msgSponsorService.assignable(msg.getId(), roleId) ? true
						: msgCoSponsorService.assignable(msg.getId(), roleId) : false;
				msgExtend.setAttachIds(attachIds);
				msgExtend.setAttachs(attachs);
				model.addAttribute("msg", msgExtend);
				model.addAttribute("callbackable", callbackable);
				model.addAttribute("signable", signable);
				model.addAttribute("assignable", assignable);
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
		List<MsgCoSponsor> msgCoSponsors;
		List<MsgSponsor> msgSponsors;
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
			return "upload";
		} else {
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
			HttpServletRequest request) {
		msgService.delete(msgId);
		attachService.deleteByMsgId(msgId);
		msgCoSponsorService.deleteByMgsId(msgId);
		msgSponsorService.deleteByMgsId(msgId);
		return "upload";
	}

	@RequestMapping(value = "/sign")
	@RequiresPermissions(value = { PermissionSign.ADMIN, PermissionSign.BAN_GONG_SHI_GUAN_LI,
			PermissionSign.BU_LING_DAO, PermissionSign.CHU_SHI_NEI_QIN,
			PermissionSign.CHU_SHI_FU_ZE_REN }, logical = Logical.OR)
	public String sign(Msg msg, RedirectAttributes model, HttpSession session) {
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
	
	@RequestMapping(value="/callback")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String callBack(@RequestParam("id") String msgId,Model model,HttpSession session, HttpServletRequest request,RedirectAttributes reditectModel) {
		MsgExtend msg0 = new MsgExtend();
		msg0.setId(msgId);
		if(msgId != null) {
			boolean callbackable = false;
			callbackable =	msgSponsorService.callbackable(msgId) ? msgCoSponsorService.callbackable(msgId) : false;
			if(callbackable) {
				int count = msgSponsorService.doCallback(msgId);
				count = count + msgCoSponsorService.doCallback(msgId);
				if (count > 0) {
					msg0.setStatus(0);
				}
			}
			else {
				reditectModel.addFlashAttribute("msg1", "撤回失败！");
				reditectModel.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
			}
			reditectModel.addFlashAttribute("msg", msg0);
			return "redirect:/rest/msg/openMsg";
		}
		else {
			return "404";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assign")
	@Transactional(rollbackFor = Exception.class)
	@RequiresPermissions(value = { PermissionSign.ADMIN, PermissionSign.BAN_GONG_SHI_GUAN_LI,
			PermissionSign.BU_LING_DAO, PermissionSign.CHU_SHI_NEI_QIN,
			PermissionSign.CHU_SHI_FU_ZE_REN }, logical = Logical.OR)
	public String assign(String msgId, List<Long> userIds, RedirectAttributes model, HttpSession session) throws Exception {
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
}
