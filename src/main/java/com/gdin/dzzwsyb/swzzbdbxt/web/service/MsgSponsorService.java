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

	List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgExtends, Map<Long, String> roleMap);

}
