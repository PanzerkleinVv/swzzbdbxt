package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractorExample;

public interface MsgContractorService extends GenericService<MsgContractor, String> {

	List<String> selectMsgIdByUserId(Long userId);

	List<MsgContractor> selectByExample(MsgContractorExample example);

}
