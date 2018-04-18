package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;

public interface AttachService extends GenericService<Attach, String> {
	
	List<Attach> selectByTargetId(String targerId);
	
	List<Attach>[] selectByTargetIds(String[] targerId);

	int deleteByTargetId(String target);
	
	int deleteByTargetIds(String[] target);
}
