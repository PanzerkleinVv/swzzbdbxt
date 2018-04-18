package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.AttachMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
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
	public List<Attach> selectByTargetId(String targerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attach>[] selectByTargetIds(String[] targerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByTargetId(String target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByTargetIds(String[] target) {
		// TODO Auto-generated method stub
		return 0;
	}

}
