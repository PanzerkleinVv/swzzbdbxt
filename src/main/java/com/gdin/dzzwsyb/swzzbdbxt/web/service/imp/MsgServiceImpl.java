package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.MsgMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import org.springframework.stereotype.Service;

/**
 * 督办事项Service实现类
 * 
 * @author PanzerkleinVv
 *
 */
@Service
public class MsgServiceImpl extends GenericServiceImpl<Msg, String> implements MsgService {

	@Resource
	private MsgMapper msgMapper;

	@Override
	public int insert(Msg model) {
		return msgMapper.insertSelective(model);
	}

	@Override
	public int update(Msg model) {
		return msgMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(String id) {
		return msgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Msg selectById(String id) {
		return msgMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Msg> selectList() {
		return null;
	}

	@Override
	public GenericDao<Msg, String> getDao() {
		return msgMapper;
	}

	@Override
	public Page<Msg> selectByExampleAndPage(MsgExample example, int pageNo) {
		Page<Msg> page = new Page<>(pageNo);
		msgMapper.selectByExampleAndPage(example, page);
		return page;
	}

	@Override
	public List<Msg> overMsg() {
		MsgExample example = new MsgExample();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,0);
		example.createCriteria().andLimitTimeLessThan(calendar.getTime());
		return msgMapper.selectByExample(example);
	}

	@Override
	public int insertSelective(Msg record) {
		return msgMapper.insertSelective(record);
	}

	@Override
	public List<String> selectOldDataIds() {
		MsgExample example = new MsgExample();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR,-3);
		example.createCriteria().andCreateTimeLessThan(calendar.getTime());
		List<Msg> msgs = msgMapper.selectByExample(example);
		List<String> ids = null;
		if (msgs != null && msgs.size() > 0) {
			ids = new ArrayList<String>();
			for (Msg msg : msgs) {
				ids.add(msg.getId());
			}
		}
		return ids;
	}

	@Override
	public void deleteByIds(List<String> ids) {
		MsgExample example = new MsgExample();
		example.createCriteria().andIdIn(ids);
		msgMapper.deleteByExample(example);
	}
	

}
