package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.RoleService;

@Controller
@RequestMapping(value = "/attach")
public class AttachController {
	//附件类业务接口
	@Resource
	private AttachService attachService;
	//处室
	@Resource
	private RoleService roleService;
	//督查项
	@Resource
	private MsgService msgService;
	//督查项+立项人
	@Resource
	private MsgContractorService msgContractorService;
	//协助处室
	@Resource
	private MsgCoSponsorService msgCoSponsorService;
	//主处室
	@Resource
	private MsgSponsorService msgSponsorService;
	
	@RequestMapping(value = "/upload")
	public void upload(Model model,@RequestParam("file")MultipartFile[] file,HttpServletResponse resp,HttpServletRequest request) throws Exception  {
		attachService.upload(model, file, resp, request);
	}
	@RequestMapping(value = "/download")
	public void download(@RequestParam("id") String id,Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
		attachService.download(id, model, request, response);
	}
	
	@RequestMapping(value = "/delete")
	public String delete(Model model, Attach attach) {
		return null;
	}
}

