package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample.Criteria;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgQuery;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
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

	@RequestMapping(value = "/query")
	public String query() {
		return "query";
	}

	@RequestMapping(value = "/msgList")
	public String msgList(Model model, HttpSession session, MsgQuery msgQuery) {
		final Long roleId = (Long)session.getAttribute("roleId");
		final Long permissionId = (Long)session.getAttribute("permissionId");
		final Long userId = (Long)session.getAttribute("currentUserId");
		final
		MsgExample example = new MsgExample();
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
		@SuppressWarnings("unchecked")
		final Map<Long, String> roleMap = (Map<Long, String>)session.getAttribute("roleMap");
		List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
		for (Msg msg : msgs) {
			msgExtends.add(new MsgExtend(msg));
		}
		msgExtends = msgSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap);
		msgExtends = msgCoSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap);
		List<List<String>> ids = submissionService.selectIdsByMsgList(msgs);
		msgExtends = attachService.selectMsgExtendByMsgList(msgExtends, ids);
		model.addAttribute("page", page);
		model.addAttribute("msgs", msgExtends);
		return "msgList";
	}

	@RequestMapping(value = "/upload")
	public String upload(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {

		}
		return "upload";
	}

	@RequestMapping(value = "/uploadMsg")
	public String uploadMsg(Msg msg, Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {

		}
		return "upload";
	}

	@RequestMapping(value = "/deleteMsg")
	public String msgDelete(Msg msg, Model model) {
		msgService.delete(msg.getId());
		return "template";
	}

	@RequestMapping(value = "/openMsg")
	public String openMsg(Msg msg, Model model) {
		Msg msg0 = msgService.selectById(msg.getId());
		model.addAttribute("msg", msg0);
		return "msg";
	}

	@RequestMapping(value = "/changeMsgStatus")
	public String changeMsgStatus(Msg msg, Model model) {
		msgService.update(msg);
		return "msgStatusName";
	}
}
