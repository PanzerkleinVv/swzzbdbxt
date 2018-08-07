package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.LogMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Log;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.LogExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.LogService;

@Service
public class LogServiceImpl extends GenericServiceImpl<Log, String> implements LogService {
	
	@Resource
	private LogMapper logMapper;

	@Override
	public int insert(Log model) {
		return logMapper.insertSelective(model);
	}

	@Override
	public int update(Log model) {
		return logMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(String id) {
		return logMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Log selectById(String id) {
		return logMapper.selectByPrimaryKey(id);
	}

	@Override
	public Log selectOne() {
		return null;
	}

	@Override
	public List<Log> selectList() {
		return null;
	}

	@Override
	public void log(Log log) {
		log.setLogTime(new Date());
		logMapper.insert(log);
	}

	@Override
	public List<Log> getLogsByTargetId(String targetId) {
		if (targetId != null && !targetId.isEmpty()) {
			LogExample example = new LogExample();
			example.createCriteria().andTargetIdEqualTo(targetId);
			example.setOrderByClause("log_time desc");
			return logMapper.selectByExample(example);
		} else {
			return null;
		}
	}

	@Override
	public void deleteByTargetIds(List<String> targetIds) {
		if (targetIds != null && targetIds.size() > 0) {
			LogExample example = new LogExample();
			example.createCriteria().andTargetIdIn(targetIds);
			logMapper.deleteByExample(example);
		}
	}

	@Override
	public GenericDao<Log, String> getDao() {
		return logMapper;
	}

}
