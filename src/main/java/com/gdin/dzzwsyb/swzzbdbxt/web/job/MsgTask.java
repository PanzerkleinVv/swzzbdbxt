package com.gdin.dzzwsyb.swzzbdbxt.web.job;

import java.util.List;
import javax.annotation.Resource;


import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;

public class MsgTask {
	
	@Resource
	private MsgService msgService;
	
	@Resource
	private MsgSponsorService msgSponsorService;
	
	@Resource
	private MsgCoSponsorService msgCoSponsorService;
	
	@Resource
	private NoticeService noticeService;
	
	@Resource
	private MsgContractorService msgContractorService;
	
	public void deleteMsgTask() throws Exception {
		final int type =  1 ;
		final int targetType = 0;
		final int isRead = 1;
		List<Msg> msgList = msgService.overMsg();//全部逾期的msg
		//删除前一天逾期的notice
		NoticeExample noticeExample = new NoticeExample();
		noticeExample.createCriteria().andTypeEqualTo(type);
		int count = noticeService.deleteByExample(noticeExample);
		if(msgList.size()>0) {
			for(Msg msg : msgList) {
				List<MsgSponsor> msgSponsors = msgSponsorService.selectMsgSponsorsByMsgId(msg.getId());
				for(MsgSponsor msgSponsor : msgSponsors) {
					Notice notice =new Notice(1L, type, msg.getId(), targetType, ApplicationUtils.getTime(), isRead);
					noticeService.addNotice(notice);
				}
			}
		}
	}
}
