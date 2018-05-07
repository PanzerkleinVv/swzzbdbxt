package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.SubmissionMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;

@Service
public class SubmissionServiceImpl extends GenericServiceImpl<Submission, String> implements SubmissionService {

	@Resource
	private SubmissionMapper submissionMapper;

	@Resource
	private MsgSponsorService msgSponsorService;

	@Resource
	private MsgCoSponsorService msgCoSponsorService;

	@Override
	public int insert(Submission model) {
		return submissionMapper.insertSelective(model);
	}

	@Override
	public int update(Submission model) {
		return submissionMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(String id) {
		return submissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Submission selectById(String id) {
		return submissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public Submission selectOne() {
		return null;
	}

	@Override
	public List<Submission> selectList() {
		return null;
	}

	@Override
	public GenericDao<Submission, String> getDao() {
		return submissionMapper;
	}

	@Override
	public List<Submission> selectByMsgExample(SubmissionExample example) {
		return submissionMapper.selectByExample(example);
	}

	/**
	 * 根据传入的msgList，依次按其msg.id查询对应的submission.id
	 * 返回内层List第一个元素为其msg.id，只需要submission.id请从第二个元素开始遍历！！！ 返回外层List与原msgs元素顺序一致。
	 * 
	 * @param List<Msg>
	 *            msgs
	 * 
	 */
	@Override
	public List<List<String>> selectIdsByMsgList(List<Msg> msgs, Long roleId) {
		List<List<String>> ids = new ArrayList<List<String>>();
		for (Msg msg : msgs) {
			List<String> ids0 = new ArrayList<String>();
			final MsgSponsorExample msgSponsorExample = new MsgSponsorExample();
			final MsgCoSponsorExample msgCoSponsorExample = new MsgCoSponsorExample();
			if (roleId < 4L) {
				msgSponsorExample.createCriteria().andMsgIdEqualTo(msg.getId());
				msgCoSponsorExample.createCriteria().andMsgIdEqualTo(msg.getId());
			} else {
				msgSponsorExample.createCriteria().andMsgIdEqualTo(msg.getId()).andRoleIdEqualTo(roleId);
				msgCoSponsorExample.createCriteria().andMsgIdEqualTo(msg.getId()).andRoleIdEqualTo(roleId);
			}
			List<MsgSponsor> msgSponsors = msgSponsorService.selectByExample(msgSponsorExample);
			List<MsgCoSponsor> msgCoSponsors = msgCoSponsorService.selectByExample(msgCoSponsorExample);
			for (MsgSponsor msgSponsor : msgSponsors) {
				ids0.add(msgSponsor.getId());
			}
			for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
				ids0.add(msgCoSponsor.getId());
			}
			final SubmissionExample example = new SubmissionExample();
			example.createCriteria().andMsgIdIn(ids0);
			List<Submission> submissions = submissionMapper.selectByExample(example);
			ids0.add(0,msg.getId());
			for (Submission submission : submissions) {
				ids0.add(submission.getId());
			}
			ids.add(ids0);
		}
		return ids;
	}

}
