package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

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
	public int deleteOldMsg() {
		MsgExample example = new MsgExample();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -18);
		example.createCriteria().andMsgDateLessThan(calendar.getTime());
		return msgMapper.deleteByExample(example);
	}

}
