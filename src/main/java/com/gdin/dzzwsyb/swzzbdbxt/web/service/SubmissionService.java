package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionExample;

public interface SubmissionService extends GenericService<Submission, String> {

	List<Submission> selectByMsgExample(SubmissionExample example);

	List<List<String>> selectIdsByMsgList(List<Msg> msgs, Long roleId);
	
	List<Submission> selectByMsgId(String msgId, List<Integer> status);
	
	List<String> selectIdsByMsgIds(List<String> ids);

	void deleteByTargetIds(List<String> ids);
}
