package com.gdin.dzzwsyb.swzzbdbxt.web.job;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.UserService;

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

	@Resource
	private UserService userService;
	
	public void deleteMsgTask() throws Exception {
		final int type =  1 ;
		final int isRead = 1;
		List<MsgSponsor> msgSponsors = msgSponsorService.overLimitTime();
		List<MsgCoSponsor> msgCoSponsors = msgCoSponsorService.overCoLimitTime();
		//把主处室和副处室表的status改为逾期-status=2
		if(msgSponsors != null && msgSponsors.size()>0) {
			msgSponsorService.updateStatus(msgSponsors, 2);
			for(MsgSponsor msgSponsor : msgSponsors) {
				String msgId = msgSponsor.getMsgId();
				List<User> roleUsers = userService.selectByRoleId(msgSponsor.getRoleId());
				for(User user : roleUsers) {
					NoticeExample example = new NoticeExample();
					example.createCriteria().andUserIdEqualTo(user.getId()).andTargetIdEqualTo(msgId);
					//删除notice
					noticeService.deleteByExample(example);
					Notice notice = new Notice(user.getId(), type, msgId, 0, ApplicationUtils.getTime(), isRead);
					noticeService.addNotice(notice);
					
				}
			}
		}
		if(msgCoSponsors != null && msgCoSponsors.size()>0) {
			msgCoSponsorService.updateStatus(msgCoSponsors, 2);
			for(MsgCoSponsor msgCoSponsor : msgCoSponsors) {
				String msgId = msgCoSponsor.getMsgId();
				List<User> roleUsers = userService.selectByRoleId(msgCoSponsor.getRoleId());
				for(User user : roleUsers) {
					NoticeExample example = new NoticeExample();
					example.createCriteria().andUserIdEqualTo(user.getId()).andTargetIdEqualTo(msgId);
					//删除notice
					noticeService.deleteByExample(example);
					Notice notice = new Notice(user.getId(), type, msgId, 0, ApplicationUtils.getTime(), isRead);
					noticeService.addNotice(notice);
				}
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
			try {
				attachService.deleteByTargetIds(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
			submissionService.deleteByTargetIds(ids);
			msgContractorService.deleteByTargetIds(msgIds);
			msgCoSponsorService.deleteByTargetIds(msgIds);
			msgSponsorService.deleteByTargetIds(msgIds);
			msgService.deleteByIds(msgIds);
		}
	}
}

