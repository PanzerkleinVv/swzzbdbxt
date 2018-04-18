package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgQuery;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;

@Controller
@RequestMapping(value = "/msg")
public class MsgController {

	@Resource
	private MsgService msgService;

	@RequestMapping(value = "/query")
	public String query() {
		return "query";
	}

	@RequestMapping(value = "/msgList")
	public String msgList(MsgQuery msgQuery, Model model, HttpSession session) {
		if (msgQuery.getMsgAreaId() == null || msgQuery.getMsgAreaId() < 1) {
			msgQuery.setMsgAreaId((Integer) session.getAttribute("roleId"));
			msgQuery.setMsgAreaName("所有");
		}
		MsgExample example = new MsgExample();
		List<Msg> msgs = null;
		Page<Msg> page = null;
		msgQuery.setExample(example);
		page = msgService.selectByExampleAndPage(example, msgQuery.getPageNo());
		msgs = page.getResult();
		model.addAttribute("msgQuery", msgQuery);
		model.addAttribute("page", page);
		model.addAttribute("msgs", msgs);
		return "msgList";
	}

	@RequestMapping(value = "/upload")
	public String upload(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {
			model.addAttribute("msgUserUnit", user.getUserUnit());
			model.addAttribute("msgAttn", user.getUserAttn());
			model.addAttribute("msgPhone", user.getUserPhone());
		}
		return "upload";
	}

	@RequestMapping(value = "/uploadMsg")
	public String uploadMsg(Msg msg, Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {
			String msgId = DigestUtils.sha1Hex(UUID.randomUUID().toString());
			msg.setMsgId(msgId);
			msg.setMsgUserId(user.getUserId());
			msg.setMsgStatus(1);
			msg.setMsgDate(new Date(System.currentTimeMillis()));
			int flag = msgService.insert(msg);
			if (flag > 0) {
				Msg msg0 = msgService.selectById(msgId);
				model.addAttribute("msg", msg0);
				return "msg";
			}
			model.addAttribute("msgUserUnit", user.getUserUnit());
			model.addAttribute("msgAttn", user.getUserAttn());
			model.addAttribute("msgPhone", user.getUserPhone());
		}
		return "upload";
	}

	@RequestMapping(value = "/deleteMsg")
	public String msgDelete(Msg msg, Model model) {
		msgService.delete(msg.getMsgId());
		return "template";
	}

	@RequestMapping(value = "/openMsg")
	public String openMsg(Msg msg, Model model) {
		Msg msg0 = msgService.selectById(msg.getMsgId());
		model.addAttribute("msg", msg0);
		return "msg";
	}

	@RequestMapping(value = "/changeMsgStatus")
	@RequiresRoles(value = RoleSign.GD)
	public String changeMsgStatus(Msg msg, Model model) {
		msgService.update(msg);
		Msg msg0 = msgService.selectById(msg.getMsgId());
		model.addAttribute("msgStatusName", msg0.getMsgStatusName());
		return "msgStatusName";
	}
}
