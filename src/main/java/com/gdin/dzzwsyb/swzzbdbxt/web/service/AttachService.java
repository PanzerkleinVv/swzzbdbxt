package com.gdin.dzzwsyb.swzzbdbxt.web.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;

public interface AttachService extends GenericService<Attach, String> {
	
	List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, List<List<String>> ids);
	
	List<Attach> upload(MultipartFile[] files, String targetId, int targetType) throws Exception;
		
	void deleteByMsgId(String targetId);
	
	List<Attach> selectByTargetId(String targetId, int targetType);
	
	void deleteByTargetIds(List<String> ids) throws Exception;
	
	Boolean deleteFile(String id) throws Exception;
}
