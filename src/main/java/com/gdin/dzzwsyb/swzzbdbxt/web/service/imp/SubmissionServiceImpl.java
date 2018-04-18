package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.SubmissionMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
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

}
