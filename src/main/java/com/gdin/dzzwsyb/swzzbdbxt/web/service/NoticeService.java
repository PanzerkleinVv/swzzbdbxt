package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.text.ParseException;
import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeCount;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;

public interface NoticeService extends GenericService<Notice, Long> {

	List<Notice> selectByExample(NoticeExample example);

	List<NoticeCount> countNotice(Long userid);

	/**
	 * 增加提醒
	 * 
	 * @param notice
	 *            对象属性 targetId,targetType,type 不能为空
	 * @return 是否成功
	 * @throws Exception
	 *             出错会回滚
	 */
	boolean addNotice(Notice notice) throws Exception;

	/**
	 * 移除提醒
	 * 
	 * @param notice
	 *            对象属性 targetId,targetType,type,userId 不能为空
	 * @return 是否成功
	 * @throws Exception
	 *             出错会回滚
	 */
	boolean removeNotice(Notice notice) throws Exception;

	/**
	 * 设置已读
	 * 
	 * @param notice
	 *            对象属性 targetId,targetType,type,userId 不能为空
	 * @return 是否成功
	 * @throws Exception
	 *             出错会回滚
	 */
	boolean readNotice(Notice notice) throws Exception;

	void noticeByTargetId(String targetId) throws Exception;

	List<Notice> selectMsg(int type, Long userId, int isRead);

	List<Notice> selectMsg(int type, Long userId);

	int deleteByExample(NoticeExample example);

	/**
	 * 先根据msgid和targetid和targettype删除全部，然后增加
	 */

	void modifyUserId(String msgId, List<Long> roleUserIds, int typ, int targetType) throws Exception;

	void updateIsRead(String msgId, Long userId);

	void updateByMsgId(String msgId, int style);
	
	/**
	 * 先根据msgid删除全部，然后增加
	 */
	void modifySendUserId(String msgId, List<Long> roleUserIds, int type) throws Exception;

	void deleteByTargetIds(List<String> ids);
}
