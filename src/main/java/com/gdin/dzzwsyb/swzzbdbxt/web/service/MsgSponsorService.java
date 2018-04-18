package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExample;

public interface MsgSponsorService extends GenericService<MsgSponsor, Long> {

	List<String> selectMsgIdByRoleId(Long roleId);

	List<MsgSponsor> selectByExample(MsgSponsorExample example);

}
