package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.SubmissionMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;

@Service
public class SubmissionServiceImpl extends GenericServiceImpl<Submission, String> implements SubmissionService {

	@Resource
	private SubmissionMapper submissionMapper;

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
	 * 返回内层List第一个元素为其msg.id，只需要submission.id请从第二个元素开始遍历！！！
	 * 返回外层List与原msgs元素顺序一直。
	 * @param List<Msg> msgs
	 * 
	 */
	@Override
	public List<List<String>> selectIdsByMsgList(List<Msg> msgs) {
		List<List<String>> ids = new ArrayList<List<String>>();
		for (Msg msg : msgs) {
			SubmissionExample example = new SubmissionExample();
			example.createCriteria().andMsgIdEqualTo(msg.getId());
			List<Submission> submissions = submissionMapper.selectByExample(example);
			List<String> ids0 = new ArrayList<String>();
			ids0.add(msg.getId());
			for (Submission submission : submissions) {
				ids0.add(submission.getId());
			}
			ids.add(ids0);
		}
		return ids;
	}

}
