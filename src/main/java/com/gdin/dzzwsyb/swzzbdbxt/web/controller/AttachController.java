package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.HandleFile;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;

@Controller
@RequestMapping(value = "/attach")
public class AttachController {
	//附件类业务接口
	@Resource
	private AttachService attachService;
	
	@RequestMapping(value = "/download")
	public void download(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
		Attach attach = attachService.selectById(id);
		if (attach != null) {
			response.setContentType("application/force-download;charset=UTF-8");// 设置强制下载不打开
			try {
				response.addHeader("Content-Disposition",
						"attachment;fileName=" + URLEncoder.encode(attach.getAttachFileName(), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} // 设置文件名
			HandleFile.download(attach.getId(), response.getOutputStream());
		}
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

