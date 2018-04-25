package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;

public interface AttachService extends GenericService<Attach, String> {
	
	List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, List<List<String>> ids);
	
	void upload(Model model,MultipartFile[] file,HttpServletResponse resp,HttpServletRequest request) ;
		
}
