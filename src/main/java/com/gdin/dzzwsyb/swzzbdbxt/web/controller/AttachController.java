package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;

@Controller
@RequestMapping(value = "/role")
public class AttachController {

	public String upload(Model model, HttpServletRequest request) {
		return null;
	}
	
	public String download(Model model, Attach attach) {
		return null;
	}

}
