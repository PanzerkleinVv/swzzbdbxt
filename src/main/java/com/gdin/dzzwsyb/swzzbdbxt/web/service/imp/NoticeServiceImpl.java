package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.NoticeMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeCount;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Statistics;
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
	public boolean addNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(notice.getTargetId() != null&&notice.getTargetType() != null&&notice.getType() != null) {
			noticeMapper.insert(notice);
			flag = true;
		}
		else {
			throw new Exception("新增出错");
		}
		return flag;
	}

	@Override
	public boolean removeNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(notice.getTargetId() != null&&notice.getTargetType() != null&&notice.getType() != null) {
			noticeMapper.deleteByPrimaryKey(notice.getId());
			flag = true;
		}
		else {
			throw new Exception("删除出错");
		}
		return flag;
	}

	@Override
	public boolean readNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(notice.getTargetId() != null&&notice.getTargetType() != null&&notice.getType() != null) {
			notice.setIsRead(1);
			noticeMapper.updateByPrimaryKey(notice);
			flag = true;
		}
		else {
			throw new Exception("标志已读出错");
		}
		return flag;
	
	}

	@Override
	public void noticeByTargetId(String targetId) throws Exception {
		// TODO Auto-generated method stub
		NoticeExample example = new NoticeExample();
		example.createCriteria().andTargetIdEqualTo(targetId);
		List<Notice> notices = selectByExample(example);
		if(notices.size() > 0 ) {
			for(Notice notice : notices) {
				removeNotice(notice);
			}
		}
	}

	@Override
	public List<Notice> selectMsg(int type,Long userId, int isRead) {
		// TODO Auto-generated method stub
		NoticeExample example = new NoticeExample();
		example.createCriteria().andTypeEqualTo(type).andIsReadEqualTo(isRead).andUserIdEqualTo(userId);
		return noticeMapper.selectByExample(example);
	}

	@Override
	public List<Notice> selectMsg(int type,Long userId) {
		// TODO Auto-generated method stub
		NoticeExample example = new NoticeExample();
		example.createCriteria().andTypeEqualTo(type).andUserIdEqualTo(userId);
		return noticeMapper.selectByExample(example);
	}

	@Override
	public int deleteByExample(NoticeExample example) {
		// TODO Auto-generated method stub
		return noticeMapper.deleteByExample(example);
	}

	@Override
	public void modifyUserId(String msgId, List<Long> roleUserIds,int type,int targetType) throws Exception {
		// TODO Auto-generated method stub
		final int isRead = 1;//提醒表-未读
		for(Long userId :roleUserIds) {
			NoticeExample example = new NoticeExample();
			example.createCriteria().andUserIdEqualTo(userId).andTargetIdEqualTo(msgId).andTargetTypeEqualTo(targetType);
			deleteByExample(example);
			Notice notice = new Notice(userId, type, msgId, targetType, ApplicationUtils.getTime(), isRead);
			noticeMapper.insert(notice);
		}
	}
	@Override
	public void modifySendUserId(String msgId, List<Long> roleUserIds,int type) throws Exception {
		// TODO Auto-generated method stub
		final int isRead = 1;//提醒表-未读
		final int targetType = 0;//提醒表-msg
		noticeByTargetId(msgId);
		for(Long userId :roleUserIds) {
			Notice notice = new Notice(userId, type, msgId, targetType, ApplicationUtils.getTime(), isRead);
			addNotice(notice);
		}
	}

	@Override
	public void updateIsRead(String msgId, Long userId) {
		// TODO Auto-generated method stub
		final int isRead = 0;//提醒表-已读
		NoticeExample example = new NoticeExample();
		example.createCriteria().andUserIdEqualTo(userId).andTargetIdEqualTo(msgId);
		List<Notice> notices = noticeMapper.selectByExample(example);
		for(Notice notice : notices) {
			notice.setIsRead(isRead);
			noticeMapper.updateByExample(notice, example);
		}
	}

	@Override
	public void updateByMsgId(String msgId,int style) {
		// TODO Auto-generated method stub
		NoticeExample example = new NoticeExample();
		example.createCriteria().andTargetIdEqualTo(msgId);
		List<Notice> notices = noticeMapper.selectByExample(example);
		for(Notice notice : notices) {
			notice.setType(style);
			noticeMapper.updateByExample(notice, example);
		}
	}

	@Override
	public void deleteByTargetIds(List<String> ids) {
		NoticeExample example = new NoticeExample();
		example.createCriteria().andTargetIdIn(ids);
		noticeMapper.deleteByExample(example);
	}

	@Override
	public Statistics statistics(Long roleId, Long userId) {
		return noticeMapper.msgStatistics(roleId, userId);
	}

}
