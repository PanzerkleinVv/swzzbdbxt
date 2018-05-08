package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgQuery;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample.Criteria;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;

/**
* @author huangp:630519137@qq.com
* @version 创建时间：2018年5月7日 下午3:50:24
* 类说明
* 提示类控制器
*/
@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
	@Resource
	NoticeService noticeService;
	
	@RequestMapping("/notices")
	public String notices(Model model, HttpSession session) {
		final Long roldId = (Long) session.getAttribute("roldId");
		final Long userId = (Long) session.getAttribute("userId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		List<Notice> noticeList = null;
		NoticeExample noticeExample  = new NoticeExample();
		if(roldId == 1) {
			noticeList = noticeService.selectList();
		}
		else {
			Criteria criteria = noticeExample.createCriteria();
			criteria.andUserIdEqualTo(roldId);
			noticeList = noticeService.selectByExample(noticeExample);
		}
		model.addAttribute("noticeList", noticeList);
		return "noticeList";
	}
	
	@RequestMapping("/msgList")
	public String msgList(@RequestParam("status") Integer status,@RequestParam("Type") Integer type,Model model, HttpSession session) {
		
		
		return null;
		
	}
}
