package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;

@Controller
@RequestMapping(value = "/attach")
public class AttachController {
	//附件类业务接口
	@Resource
	private AttachService attachService;
	
	@RequestMapping(value = "/download")
	public void download(@RequestParam("id") String id,Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
		attachService.download(id, model, request, response);
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Boolean delete(Model model, Attach attach) {
		try {
			return attachService.deleteFile(attach.getId());
		} catch (Exception e) {
			return false;
		}
	}
}

