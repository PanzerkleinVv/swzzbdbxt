package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.enums.MessageColor;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.UserService;

@Controller
@RequestMapping(value = "/submission")
public class SubmissionController {
	
	@Resource
	private MsgService msgService;
	
	@Resource
	private MsgSponsorService msgSponsorService;

	@Resource
	private MsgCoSponsorService msgCoSponsorService;

	@Resource
	private SubmissionService submissionService;
	
	@Resource
	private NoticeService noticeService;
	
	@Resource
	private UserService userService;


	@RequestMapping(value = "/add")
	public String add(Submission submission, String msgId0, RedirectAttributes model) {
		if (submission != null && submission.getMsgId() != null && submission.getType() != null) {
			submission.setId(ApplicationUtils.newUUID());
			submission.setStatus(0);
			final int count = submissionService.insert(submission);
			if (count == 1) {
				model.addFlashAttribute("msg1", "新增提请成功！");
				model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
			} else {
				model.addFlashAttribute("msg1", "新增提请失败！");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
			}
		} else {
			model.addFlashAttribute("msg1", "新增提请失败！");
			model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		}
		MsgExtend msg = new MsgExtend();
		msg.setId(msgId0);
		model.addFlashAttribute("msg", msg);
		return "redirect:/rest/msg/openMsg";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/del")
	public String del(Submission submission, String msgId0, RedirectAttributes model,HttpSession session) {
		if (submission != null && submission.getId() != null) {
			final int count = submissionService.delete(submission.getId());
			if (count == 1) {
				//撤回提请删除notice相关
				int type = 6;//类型为提请草稿-6
				final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
				for(User user : roleUsers) {
					NoticeExample example = new NoticeExample();
					example.createCriteria().andUserIdEqualTo(user.getId()).andTargetIdEqualTo(msgId0).andTypeEqualTo(type);
					noticeService.deleteByExample(example);
				}
				model.addFlashAttribute("msg1", "删除提请成功！");
				model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
			} else {
				model.addFlashAttribute("msg1", "删除提请失败！");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
			}
		} else {
			model.addFlashAttribute("msg1", "删除提请失败！");
			model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		}
		MsgExtend msg = new MsgExtend();
		msg.setId(msgId0);
		model.addFlashAttribute("msg", msg);
		return "redirect:/rest/msg/openMsg";
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/save")
	public String save(Submission submission, String msgId0, RedirectAttributes model, HttpSession session) throws Exception {
		String msgFlag = "保存";
		if (submission != null && submission.getId() != null) {
			if (submission.getStatus() == 1) {
				submission.setOwnerId((Long) session.getAttribute("userId"));
				submission.setSendTime(ApplicationUtils.getTime());
				Msg msg = new Msg();
				msg.setId(msgId0);
				msg.setEndTime(submission.getSendTime());
				msgService.update(msg);
				//审核提请求，提示admin，部领导，办公室
				int type = 6;//类型为提请草稿-6
				final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
				NoticeExample example = new NoticeExample();
				example.createCriteria().andTargetIdEqualTo(msgId0).andTypeEqualTo(type).andTargetTypeEqualTo(1);
				noticeService.deleteByExample(example);
				int type2 = 2;//类型为待审核-2
				List<Long> roleIdList = new ArrayList<Long>();
				roleIdList.add(1L);
				roleIdList.add(2L);
				roleIdList.add(3L);
				List<Long> roleUserIds = new ArrayList<Long>();
				List<User> roleUsers2 = userService.selectByRoleIdList(roleIdList);
				for(User user : roleUsers2) {
					if(user.getPermissionId() < 6L) {
						roleUserIds.add(user.getId());
					}
				}
				noticeService.modifyUserId(msgId0, roleUserIds, type2, 1);	
				msgFlag = "发布";
			} 
			else {
				//保存提请成功就要提醒该处室的所有人
				int type = 6;//类型为提请草稿-6
				final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
				List<Long> roleUserIds = new ArrayList<Long>();
				for(User user : roleUsers) {
					roleUserIds.add(user.getId());
				}
				noticeService.modifyUserId(msgId0, roleUserIds, type, 1);
			}
			final int count = submissionService.update(submission);
			if (count == 1) {
				model.addFlashAttribute("msg1", msgFlag + "提请成功！");
				model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
			} else {
				model.addFlashAttribute("msg1", msgFlag + "提请失败！");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
				throw new Exception("保存失败");
			}
		} else {
			model.addFlashAttribute("msg1", msgFlag + "提请失败！");
			model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		}
		MsgExtend msg = new MsgExtend();
		msg.setId(msgId0);
		model.addFlashAttribute("msg", msg);
		return "redirect:/rest/msg/openMsg";
	}
	
	@RequestMapping(value = "/callback")
	public String callback(Submission submission, String msgId0, RedirectAttributes model,HttpSession session) throws Exception {
		if (submission != null && submission.getId() != null) {
			Submission submission0 = submissionService.selectById(submission.getId());
			if (submission0 != null && submission0.getStatus() == 1) {
				final int count = submissionService.update(submission);
				if (count == 1) {
					//撤回提请删除notice相关
					int type = 6;//类型为提请草稿-6
					final List<User> roleUsers = (List<User>) session.getAttribute("roleUsers");
					for(User user : roleUsers) {
						NoticeExample example = new NoticeExample();
						example.createCriteria().andUserIdEqualTo(user.getId()).andTargetIdEqualTo(msgId0).andTypeEqualTo(type);
						noticeService.deleteByExample(example);
					}
					model.addFlashAttribute("msg1", "撤回提请成功！");
					model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
				} else {
					model.addFlashAttribute("msg1", "撤回提请失败！");
					model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
				}
			} else {
				model.addFlashAttribute("msg1", "撤回提请失败！");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
			}
		} else {
			model.addFlashAttribute("msg1", "撤回提请失败！");
			model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		}
		MsgExtend msg = new MsgExtend();
		msg.setId(msgId0);
		model.addFlashAttribute("msg", msg);
		return "redirect:/rest/msg/openMsg";
	}
	
	@Transactional(rollbackFor = Exception.class)
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	@RequestMapping(value = "/verify")
	public String verify(Submission submission, String msgId0, HttpSession session, RedirectAttributes model) throws Exception {
		if (submission != null && submission.getId() != null) {
			Submission submission0 = submissionService.selectById(submission.getId());
			if (submission0 != null && submission0.getStatus() == 1) {
				submission.setSuperiorVerifiUserId((Long) session.getAttribute("userId"));
				final int count = submissionService.update(submission);
				if (count == 1) {
					if (submission.getSuperiorVerifyPassed() == 1) {
						Integer msgStutus = null;
						if (submission0.getType() == 1) {
							msgStutus = 4;
						} else if (submission0.getType() == 2) {
							msgStutus = 1;
						} else if (submission0.getType() == 3) {
							msgStutus = 5;
						} else if (submission0.getType() == 4) {
							msgStutus = 3;
						}
						MsgSponsor msgSponsor = new MsgSponsor();
						msgSponsor.setId(submission0.getMsgId());
						msgSponsor.setStatus(msgStutus);
						int count0 = msgSponsorService.update(msgSponsor);
						MsgCoSponsor msgCoSponsor = new MsgCoSponsor();
						msgCoSponsor.setId(submission0.getMsgId());
						msgCoSponsor.setStatus(msgStutus);
						count0 = count0 + msgCoSponsorService.update(msgCoSponsor);
						if (count0 != 1) {
							throw new Exception("审核出错");
						}
					}
					//撤回提请删除notice相关
					int type = 2;//类型为提请草稿-6
					NoticeExample example = new NoticeExample();
					example.createCriteria().andTargetIdEqualTo(msgId0).andTypeEqualTo(type).andTargetTypeEqualTo(1);;
					noticeService.deleteByExample(example);
					model.addFlashAttribute("msg1", "审核提请完成！");
					model.addFlashAttribute("msg2", MessageColor.SUCCESS.getColor());
				} else {
					model.addFlashAttribute("msg1", "审核提请失败！");
					model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
				}
			} else {
				model.addFlashAttribute("msg1", "审核提请失败！");
				model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
			}
		} else {
			model.addFlashAttribute("msg1", "审核提请失败！");
			model.addFlashAttribute("msg2", MessageColor.FAILURE.getColor());
		}
		MsgExtend msg = new MsgExtend();
		msg.setId(msgId0);
		model.addFlashAttribute("msg", msg);
		return "redirect:/rest/msg/openMsg";
	}
}
