package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.NoticeMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeCount;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.NoticeService;

@Service
public class NoticeServiceImpl extends GenericServiceImpl<Notice, Long> implements NoticeService {

	@Resource
	private NoticeMapper noticeMapper;

	@Override
	public int insert(Notice model) {
		return noticeMapper.insertSelective(model);
	}

	@Override
	public int update(Notice model) {
		return noticeMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		return noticeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Notice selectById(Long id) {
		return noticeMapper.selectByPrimaryKey(id);
	}

	@Override
	public Notice selectOne() {
		return null;
	}

	@Override
	public List<Notice> selectList() {
		return null;
	}

	@Override
	public GenericDao<Notice, Long> getDao() {
		return noticeMapper;
	}

	@Override
	public List<Notice> selectByExample(NoticeExample example) {
		return noticeMapper.selectByExample(example);
	}

	@Override
	public List<NoticeCount> countNotice(Long userid) {
		return noticeMapper.countNotice(userid);
	}



}
