package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.MsgCoSponsorMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;

@Service
public class MsgCoSponsorServiceImpl extends GenericServiceImpl<MsgCoSponsor, Long> implements MsgCoSponsorService {

	@Resource
	private MsgCoSponsorMapper msgCoSponsorMapper;

	@Override
	public int insert(MsgCoSponsor model) {
		return msgCoSponsorMapper.insertSelective(model);
	}

	@Override
	public int update(MsgCoSponsor model) {
		return msgCoSponsorMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		return msgCoSponsorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public MsgCoSponsor selectById(Long id) {
		return msgCoSponsorMapper.selectByPrimaryKey(id);
	}

	@Override
	public MsgCoSponsor selectOne() {
		return null;
	}

	@Override
	public List<MsgCoSponsor> selectList() {
		return null;
	}

	@Override
	public GenericDao<MsgCoSponsor, Long> getDao() {
		return msgCoSponsorMapper;
	}

	@Override
	public List<String> selectMsgIdByRoleId(Long roleId) {
		final MsgCoSponsorExample example = new MsgCoSponsorExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		final List<MsgCoSponsor> msgCoSponsors = msgCoSponsorMapper.selectByExample(example);
		final List<String> msgIds = new ArrayList<String>();
		for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
			msgIds.add(msgCoSponsor.getMsgId());
		}
		return msgIds;
	}

	@Override
	public List<MsgCoSponsor> selectByExample(MsgCoSponsorExample example) {
		return msgCoSponsorMapper.selectByExample(example);
	}

}
