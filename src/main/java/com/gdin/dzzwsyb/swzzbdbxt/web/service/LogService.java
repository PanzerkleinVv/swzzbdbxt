package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Log;

public interface LogService extends GenericService<Log, String>{
	
	void log(Log log);
	
	List<Log> getLogsByTargetId(String targetId);
	
	void deleteByTargetIds(List<String> targetIds);

}
