package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;
import java.util.Map;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsorExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;

public interface MsgCoSponsorService extends GenericService<MsgCoSponsor, String> {

	List<String> selectMsgIdByRoleId(Long roleId);

	int insertSelective(MsgCoSponsor record);

	List<MsgCoSponsor> selectByExample(MsgCoSponsorExample example);

	List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgExtend, Map<Long, String> roleMap, Long roleId);

	boolean modifyRoleId(String msgId, List<MsgCoSponsor> msgCoSponsors) throws Exception;

	List<Long> selectRoleIdByMsgId(String msgId);

	void deleteByMgsId(String msgId);

	/**
	 * 查询处室是否可以阅读信息
	 * 
	 * @param msgId
	 * @param roleId
	 * @return
	 */
	boolean readable(String msgId, Long roleId);

	boolean signable(String msgId, Long roleId);

	boolean assignable(String msgId, Long roleId);
	
	boolean callbackable(String msgId);
	
	List<MsgCoSponsor> selectMsgCoSponsorsByMsgId(String msgId);
	
	Long selectByMgsId(String msgId);

	int doSign(String msgId, Long roleId);
	
	int doCallback(String msgId);
	
	int doAssign(String msgId, Long roleId);
	
	List<MsgCoSponsor> selectMsgCoSponsorsByMsgIdRoleId(String msgId, Long roleId);
}
