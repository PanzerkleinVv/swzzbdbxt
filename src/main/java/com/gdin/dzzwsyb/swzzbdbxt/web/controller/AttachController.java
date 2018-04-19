package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;

@Controller
@RequestMapping(value = "/attach")
public class AttachController {

	@RequestMapping(value = "/upload")
	public String upload(Model model, HttpServletRequest request) {
		return null;
	}
	
	@RequestMapping(value = "/download")
	public String download(Model model, Attach attach) {
		return null;
	}
	
	@RequestMapping(value = "/delete")
	public String delete(Model model, Attach attach) {
		return null;
	}

	@RequestMapping(value = "/test")
	public String test(Model model, Attach attach) {
		return null;
	}
	
	@RequestMapping(value = "/test2")
	public String test2(Model model, Attach attach) {
		return null;
	}
}
