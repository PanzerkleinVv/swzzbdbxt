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
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.MsgSponsorMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;

@Service
public class MsgSponsorServiceImpl extends GenericServiceImpl<MsgSponsor, String> implements MsgSponsorService {

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
	public int delete(String id) {
		return msgSponsorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public MsgSponsor selectById(String id) {
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
	public GenericDao<MsgSponsor, String> getDao() {
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
	public List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, Map<Long, String> roleMap, Long roleId) {
		List<MsgSponsor> msgSponsors = null;
		for (MsgExtend msg : msgs) {
			Integer status = msg.getStatus();
			final MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andMsgIdEqualTo(msg.getId());
			msgSponsors = msgSponsorMapper.selectByExample(example);
			String sponsorRoleNames = "";
			String contents = "";
			for (MsgSponsor msgSponsor : msgSponsors) {
				sponsorRoleNames = sponsorRoleNames + roleMap.get(msgSponsor.getRoleId()) + "<br/>";
				if (roleId < 4L || (roleId > 3L && roleId == msgSponsor.getRoleId())) {
					contents = contents + "<b>" + roleMap.get(msgSponsor.getRoleId()) + "：</b>" + "<br/>"
							+ ApplicationUtils.replaceNullToEmpty(msgSponsor.getContent()) + "<br/>";
					if (status == null) {
						status = msgSponsor.getStatus();
					} else if (status > 2 && 3 > msgSponsor.getStatus()) {
						status = msgSponsor.getStatus();
					} else if (status == 1 && 2 == msgSponsor.getStatus()) {
						status = msgSponsor.getStatus();
					}
				}
			}
			if (sponsorRoleNames != null && sponsorRoleNames.length() > 0) {
				sponsorRoleNames.substring(0, sponsorRoleNames.lastIndexOf("<br/>"));
				if (contents != null && contents.length() > 0) {
					contents.substring(0, contents.lastIndexOf("<br/>"));
				}
			}
			msg.setStatus(status);
			final String[] msgStatus = SelectArray.getMsgStatus();
			if (status != null) {
				msg.setStatusName(msgStatus[status.intValue()]);
			}
			msg.setSponsorRoleNames(sponsorRoleNames);
			msg.setContents(ApplicationUtils.replaceNullToEmpty(msg.getContents()) + contents);
		}
		return msgs;
	}

	@Override
	public int insertSelective(MsgSponsor record) {
		return msgSponsorMapper.insertSelective(record);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean modifyRoleId(String msgId, List<MsgSponsor> msgSponsors) throws Exception {
		boolean flag = false;
		final MsgSponsorExample example = new MsgSponsorExample();
		example.createCriteria().andMsgIdEqualTo(msgId);
		final Long oldCount = msgSponsorMapper.countByExample(example);
		final Integer deleteCount = msgSponsorMapper.deleteByExample(example);
		if (oldCount != null && deleteCount != null && oldCount.longValue() == deleteCount.longValue()) {
			if (msgSponsors != null && msgSponsors.size() > 0) {
				int insertCount = 0;
				for (MsgSponsor msgSponsor : msgSponsors) {
					insertCount = insertCount + insertSelective(msgSponsor);
				}
				if (insertCount == msgSponsors.size()) {
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

	@Override
	public List<Long> selectRoleIdByMsgId(String msgId) {
		return msgSponsorMapper.selectRoleIdByMsgId(msgId);
	}

	@Override
	public void deleteByMgsId(String msgId) {
		msgSponsorMapper.deleteByMsgId(msgId);
	}

	@Override
	public boolean readable(String msgId, Long roleId) {
		boolean flag = false;
		if (roleId != null && msgId != null) {
			MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andMsgIdEqualTo(msgId);
			long count = msgSponsorMapper.countByExample(example);
			if (count > 0L) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean signable(String msgId, Long roleId) {
		boolean flag = false;
		if (roleId != null && msgId != null) {
			MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andMsgIdEqualTo(msgId).andIsSignedEqualTo(0);
			long count = msgSponsorMapper.countByExample(example);
			if (count > 0L) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean assignable(String msgId, Long roleId) {
		boolean flag = false;
		if (roleId != null && msgId != null) {
			MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andMsgIdEqualTo(msgId).andIsSignedEqualTo(1)
					.andIsAssignedEqualTo(0);
			long count = msgSponsorMapper.countByExample(example);
			if (count > 0L) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean callbackable(String msgId) {
		boolean flag = false;
		if (msgId != null) {
			MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andMsgIdEqualTo(msgId).andIsSignedEqualTo(1);
			long count = msgSponsorMapper.countByExample(example);
			if (count < 1L) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public int doSign(String msgId, Long roleId) {
		if (msgId != null && roleId != null) {
			MsgSponsor msgSponsor = new MsgSponsor();
			msgSponsor.setIsSigned(1);
			MsgSponsorExample example = new MsgSponsorExample();
			example.createCriteria().andMsgIdEqualTo(msgId).andRoleIdEqualTo(roleId);
			return msgSponsorMapper.updateByExampleSelective(msgSponsor, example);
		} else {
			return 0;
		}
	}

	public List<MsgSponsor> selectMsgSponsorsByMsgId(String msgId) {
		return msgSponsorMapper.selectMsgSponsorsByMsgId(msgId);
	}
  
}
