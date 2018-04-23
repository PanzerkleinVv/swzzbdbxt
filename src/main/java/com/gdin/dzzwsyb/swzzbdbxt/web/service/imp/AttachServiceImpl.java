package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.AttachMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.AttachExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;

@Service
public class AttachServiceImpl extends GenericServiceImpl<Attach, String> implements AttachService {

	@Resource
	private AttachMapper attachMapper;

	@Override
	public int insert(Attach model) {
		return attachMapper.insertSelective(model);
	}

	@Override
	public int update(Attach model) {
		return attachMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(String id) {
		return attachMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Attach selectById(String id) {
		return attachMapper.selectByPrimaryKey(id);
	}

	@Override
	public Attach selectOne() {
		return null;
	}

	@Override
	public List<Attach> selectList() {
		return null;
	}

	@Override
	public GenericDao<Attach, String> getDao() {
		return attachMapper;
	}

	@Override
	public List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, List<List<String>> ids) {
		if(msgs != null && msgs.size() > 0) {
			MsgExtend msg = null;
			List<Attach> attachList = null;
			for (int i = 0; i < msgs.size(); i++) {
				msg = msgs.get(i);
				AttachExample example = new AttachExample();
				example.createCriteria().andTargetIdIn(ids.get(i));
				attachList = attachMapper.selectByExample(example);
				String[] attachs = null;
				String[] attachIds = null;
				if (attachList != null && attachList.size() > 0) {
					attachs = new String[attachList.size()];
					attachIds = new String[attachList.size()];
					for (int j = 0; j < attachList.size(); j++) {
						attachIds[j] = attachList.get(j).getId();
						attachs[j] = attachList.get(j).getAttachFileName();
					}
				}
				msg.setAttachIds(attachIds);
				msg.setAttachs(attachs);
			}
		}
		return msgs;
	}

}
