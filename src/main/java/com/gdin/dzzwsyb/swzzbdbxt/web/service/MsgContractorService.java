package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;

public interface MsgContractorService extends GenericService<MsgContractor, String> {

	List<String> selectMsgIdByUserId(Long userId);

	List<MsgContractor> selectByExample(MsgContractorExample example);
	
	void modifyUserId(String msgId, long[] userIds, List<Long> roleUserIds);
	
	/**
	 * 判断用户是否可读信息
	 * @param msgId
	 * @param userId
	 * @return
	 */
	boolean readable(String msgId, Long userId);
	
	List<Long> selectByMsgIdAndRoleUsers(String msgId, List<User> roleUsers);

}
