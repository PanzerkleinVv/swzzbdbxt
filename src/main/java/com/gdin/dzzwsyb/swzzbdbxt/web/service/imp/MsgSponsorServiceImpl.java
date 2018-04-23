package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.MsgSponsorMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;

@Service
public class MsgSponsorServiceImpl extends GenericServiceImpl<MsgSponsor, Long> implements MsgSponsorService {

	@Resource
	private MsgSponsorMapper msgSponsorMapper;

	@Override
	public int insert(MsgSponsor model) {
		return msgSponsorMapper.insertSelective(model);
	}

	@Override
	public int update(MsgSponsor model) {
		return msgSponsorMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		return msgSponsorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public MsgSponsor selectById(Long id) {
		return msgSponsorMapper.selectByPrimaryKey(id);
	}

	@Override
	public MsgSponsor selectOne() {
		return null;
	}

	@Override
	public List<MsgSponsor> selectList() {
		return null;
	}

	@Override
	public GenericDao<MsgSponsor, Long> getDao() {
		return msgSponsorMapper;
	}

	@Override
	public List<String> selectMsgIdByRoleId(Long roleId) {
		final MsgSponsorExample example = new MsgSponsorExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		final List<MsgSponsor> msgSponsors = msgSponsorMapper.selectByExample(example);
		final List<String> msgIds = new ArrayList<String>();
		for (MsgSponsor msgSponsor : msgSponsors) {
			msgIds.add(msgSponsor.getMsgId());
		}
		return msgIds;
	}

	@Override
	public List<MsgSponsor> selectByExample(MsgSponsorExample example) {
		return msgSponsorMapper.selectByExample(example);
	}

	@Override
	public List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, Map<Long, String> roleMap) {
		List<MsgSponsor> msgSponsors = null;
		for (MsgExtend msg : msgs) {
			final MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andMsgIdEqualTo(msg.getId());
			msgSponsors = msgSponsorMapper.selectByExample(example);
			String sponsorRoleNames = "";
			String contents = "";
			for (MsgSponsor msgSponsor : msgSponsors) {
				sponsorRoleNames = sponsorRoleNames + roleMap.get(msgSponsor.getRoleId()) + "<br/>";
				contents = contents + "<b>" + roleMap.get(msgSponsor.getRoleId()) + "：</b>" + "<br/>"
						+ ApplicationUtils.replaceNullToEmpty(msgSponsor.getContent()) + "<br/>";
			}
			if (sponsorRoleNames != null && sponsorRoleNames.length() > 0) {
				sponsorRoleNames.substring(0, sponsorRoleNames.lastIndexOf("<br/>"));
				contents.substring(0, contents.lastIndexOf("<br/>"));
			}
			msg.setCoSponsorRoleNames(sponsorRoleNames);
			msg.setContents(ApplicationUtils.replaceNullToEmpty(msg.getContents()) + contents);
		}
		return msgs;
	}

}
