package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.RoleService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;
import com.mysql.fabric.xmlrpc.base.Data;

import sun.launcher.resources.launcher_de;

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
	@RequiresRoles(value = RoleSign.ADMIN)
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

