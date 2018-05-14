package com.gdin.dzzwsyb.swzzbdbxt.web.job;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;

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
	
	@Resource
	private SubmissionService submissionService;
	
	@Resource
	private AttachService attachService;

	public void deleteMsgTask() throws Exception {
		final int type = 1;
		final int targetType = 0;
		final int isRead = 1;
		List<Msg> msgList = msgService.overMsg();// 全部逾期的msg
		// 把逾期的信息关联的处室的status改为逾期-2
		for (Msg msg : msgList) {
			List<MsgSponsor> msgSponsors = msgSponsorService.selectMsgSponsorsByMsgId(msg.getId());
			for (MsgSponsor msgSponsor : msgSponsors) {
				msgSponsor.setStatus(2);
				msgSponsorService.update(msgSponsor);
			}
			List<MsgCoSponsor> msgCoSponsors = msgCoSponsorService.selectMsgCoSponsorsByMsgId(msg.getId());
			if (msgCoSponsors != null && msgCoSponsors.size() > 0) {
				for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
					msgCoSponsor.setStatus(2);
					msgCoSponsorService.update(msgCoSponsor);
				}
			}
		}
		if (msgList.size() > 0) {
			for (Msg msg : msgList) {
				noticeService.updateByMsgId(msg.getId());
			}
		}
	}

	public void deleteOldData() {
		List<String> ids = new ArrayList<String>();
		List<String> msgIds = null;
		msgIds = msgService.selectOldDataIds();
		if (ids != null) {
			ids.addAll(msgIds);
			ids.addAll(msgSponsorService.selectIdsByMsgIds(msgIds));
			ids.addAll(msgCoSponsorService.selectIdsByMsgIds(msgIds));
			ids.addAll(msgContractorService.selectIdsByMsgIds(msgIds));
			ids.addAll(submissionService.selectIdsByMsgIds(ids));
			noticeService.deleteByTargetIds(ids);
			attachService.deleteByTargetIds(ids);
			submissionService.deleteByTargetIds(ids);
			msgContractorService.deleteByTargetIds(msgIds);
			msgCoSponsorService.deleteByTargetIds(msgIds);
			msgSponsorService.deleteByTargetIds(msgIds);
			msgService.deleteByIds(msgIds);
		}
	}
}
