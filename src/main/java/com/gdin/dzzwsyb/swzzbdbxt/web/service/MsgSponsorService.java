package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;
import java.util.Map;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExample;

public interface MsgSponsorService extends GenericService<MsgSponsor, String> {

	List<String> selectMsgIdByRoleId(Long roleId);
	
	int insertSelective(MsgSponsor record);

	List<MsgSponsor> selectByExample(MsgSponsorExample example);

	List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgExtends, Map<Long, String> roleMap, Long roleId);

	/**
	 * 更改信息主办处室
	 * @param msgId 修改的msgId
	 * @param msgSponsors 只要删除可以传入null或者长度为0的list
	 * @return 操作是否成功，返回false会回滚
	 * @throws Exception 
	 */
	boolean modifyRoleId(String msgId, List<MsgSponsor> msgSponsors) throws Exception;
	
	List<Long> selectRoleIdByMsgId(String msgId);

	void deleteByMgsId(String msgId);
	
	/**
	 * 查询处室是否可以阅读信息
	 * @param msgId
	 * @param roleId
	 * @return
	 */
	boolean readable(String msgId, Long roleId);
	
	boolean signable(String msgId, Long roleId);
	
	boolean assignable(String msgId, Long roleId);
	
	boolean callbackable(String msgId);

}
