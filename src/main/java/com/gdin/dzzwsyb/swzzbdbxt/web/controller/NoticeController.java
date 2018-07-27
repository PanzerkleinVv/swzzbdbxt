package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.SelectArray;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeCount;
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
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		session.getAttribute("roleId");
		session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("userId");
		List<NoticeCount> noticeCounts = new ArrayList<NoticeCount>();
		noticeCounts = noticeService.countNotice(userId);
		String[] noticeType = SelectArray.getNoticeType();
		//计算提醒信息
		model.addAttribute("noticeCounts",noticeCounts);
		model.addAttribute("noticeType",noticeType);
		return "dashboard";
	}
	
	
	
	
}
