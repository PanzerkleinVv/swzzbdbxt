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
}
