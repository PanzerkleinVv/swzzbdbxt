package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.MsgContractorMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;

@Service
public class MsgContractorServiceImpl extends GenericServiceImpl<MsgContractor, Long> implements MsgContractorService {

	@Resource
	private MsgContractorMapper msgContractorMapper;

	@Override
	public int insert(MsgContractor model) {
		return msgContractorMapper.insertSelective(model);
	}

	@Override
	public int update(MsgContractor model) {
		return msgContractorMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		return msgContractorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public MsgContractor selectById(Long id) {
		return msgContractorMapper.selectByPrimaryKey(id);
	}

	@Override
	public MsgContractor selectOne() {
		return null;
	}

	@Override
	public List<MsgContractor> selectList() {
		return null;
	}

	@Override
	public GenericDao<MsgContractor, Long> getDao() {
		return msgContractorMapper;
	}

	@Override
	public List<String> selectMsgIdByUserId(Long userId) {
		final MsgContractorExample example = new MsgContractorExample();
		example.createCriteria().andUserIdEqualTo(userId);
		final List<MsgContractor> msgContractors = msgContractorMapper.selectByExample(example);
		final List<String> msgIds = new ArrayList<String>();
		for (MsgContractor msgContractor : msgContractors) {
			msgIds.add(msgContractor.getMsgId());
		}
		return msgIds;
	}

	@Override
	public List<MsgContractor> selectByExample(MsgContractorExample example) {
		return msgContractorMapper.selectByExample(example);
	}

}
