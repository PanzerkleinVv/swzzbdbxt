package com.gdin.dzzwsyb.swzzbdbxt.web.job;

import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;

public class MsgTask {
	
	@Resource
	private MsgService msgService;
	
	public void deleteMsgTask() {
		msgService.deleteOldMsg();
	}
}
