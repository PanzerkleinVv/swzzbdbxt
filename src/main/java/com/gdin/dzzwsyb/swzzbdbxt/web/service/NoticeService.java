package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeCount;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;

public interface NoticeService extends GenericService<Notice, Long> {

	List<Notice> selectByExample(NoticeExample example);

	List<NoticeCount> countNotice(Long userid);
	
	/**
	 * 增加提醒
	 * @param notice 对象属性 targetId,targetType,type 不能为空
	 * @return 是否成功
	 * @throws Exception 出错会回滚
	 */
	boolean addNotice(Notice notice) throws Exception;
	
	/**
	 * 移除提醒
	 * @param notice 对象属性 targetId,targetType,type,userId 不能为空
	 * @return 是否成功
	 * @throws Exception 出错会回滚
	 */
	boolean removeNotice(Notice notice) throws Exception;
	
	/**
	 * 设置已读
	 * @param notice 对象属性 targetId,targetType,type,userId 不能为空
	 * @return 是否成功
	 * @throws Exception 出错会回滚
	 */
	boolean readNotice(Notice notice) throws Exception;

}
