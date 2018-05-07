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

	@Override
	public int deleteNotice(Notice notice) {
		 NoticeExample example = new NoticeExample();
		 example.createCriteria().andTypeEqualTo(notice.getType()).andUserIdEqualTo(notice.getUserId()).andTargetIdEqualTo(notice.getTargetId()).andTargetTypeEqualTo(notice.getTargetType());
		 List<Notice> notices = noticeMapper.selectByExample(example);
		 if(notice != null && notices.size()>0) {
			 return delete(notices.get(0).getId());
		 }else {
			 return 0;
		 }
	}

	@Override
	public boolean addNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean readNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}



}
