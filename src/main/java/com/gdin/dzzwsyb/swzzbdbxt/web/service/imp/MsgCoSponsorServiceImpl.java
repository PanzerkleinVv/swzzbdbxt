package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.SelectArray;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.MsgCoSponsorMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;

@Service
public class MsgCoSponsorServiceImpl extends GenericServiceImpl<MsgCoSponsor, String> implements MsgCoSponsorService {

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
	public int delete(String id) {
		return msgCoSponsorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public MsgCoSponsor selectById(String id) {
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
	public GenericDao<MsgCoSponsor, String> getDao() {
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

	@Override
	public List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, Map<Long, String> roleMap, Long roleId) {
		List<MsgCoSponsor> msgCoSponsors = null;
		for (MsgExtend msg : msgs) {
			Integer status = msg.getStatus();
			final MsgCoSponsorExample example = new MsgCoSponsorExample();
			example.createCriteria().andMsgIdEqualTo(msg.getId());
			msgCoSponsors = msgCoSponsorMapper.selectByExample(example);
			String coSponsorRoleNames = "";
			String contents = "";
			for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
				coSponsorRoleNames = coSponsorRoleNames + roleMap.get(msgCoSponsor.getRoleId()) + "<br/>";
				if (roleId < 4L || (roleId > 3L && roleId == msgCoSponsor.getRoleId())) {
					contents = contents + "<b>" + roleMap.get(msgCoSponsor.getRoleId()) + "：</b>" + "<br/>"
							+ ApplicationUtils.replaceNullToEmpty(msgCoSponsor.getContent()) + "<br/>";
					if (status == null) {
						status = msgCoSponsor.getStatus();
					} else if (status > 2 && 3 > msgCoSponsor.getStatus()) {
						status = msgCoSponsor.getStatus();
					} else if (status == 1 && 2 == msgCoSponsor.getStatus()) {
						status = msgCoSponsor.getStatus();
					}
				}
			}
			if (coSponsorRoleNames != null && coSponsorRoleNames.length() > 0) {
				coSponsorRoleNames.substring(0, coSponsorRoleNames.lastIndexOf("<br/>"));
				contents.substring(0, contents.lastIndexOf("<br/>"));
			}
			msg.setStatus(status);
			final String[] msgStatus = SelectArray.getMsgStatus();
			if (status != null) {
				msg.setStatusName(msgStatus[status.intValue()]);
			}
			msg.setCoSponsorRoleNames(coSponsorRoleNames);
			msg.setContents(ApplicationUtils.replaceNullToEmpty(msg.getContents()) + contents);
		}
		return msgs;
	}

	@Override
	public int insertSelective(MsgCoSponsor record) {
		return msgCoSponsorMapper.insertSelective(record);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean modifyRoleId(String msgId, List<MsgCoSponsor> msgCoSponsors) throws Exception {
		boolean flag = false;
		final MsgCoSponsorExample example = new MsgCoSponsorExample();
		example.createCriteria().andMsgIdEqualTo(msgId);
		final Long oldCount = msgCoSponsorMapper.countByExample(example);
		final Integer deleteCount = msgCoSponsorMapper.deleteByExample(example);
		if (oldCount != null && deleteCount != null && oldCount.longValue() == deleteCount.longValue()) {
			if (msgCoSponsors != null && msgCoSponsors.size() > 0) {
				int insertCount = 0;
				for (MsgCoSponsor msgCoSponsor : msgCoSponsors) {
					insertCount = insertCount + insertSelective(msgCoSponsor);
				}
				if (insertCount == msgCoSponsors.size()) {
					flag = true;
				} else {
					throw new Exception("新增出错");
				}
			} else {
				flag = true;
			}
		} else {
			throw new Exception("删除出错");
		}
		return flag;
	}

}
