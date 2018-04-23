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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.RoleService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;
import com.mysql.fabric.xmlrpc.base.Data;

import sun.launcher.resources.launcher_de;

@Controller
@RequestMapping(value = "/attach")
public class AttachController {
	@Resource
	private AttachService attachService;
	@Resource
	private RoleService roleService;
	@Resource
	private MsgService msgService;
	@Resource
	private SequenceNumberService sequenceNumberService;
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@RequestMapping(value = "/upload")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String upload(@Valid Msg msg,@Valid Role role,Model model,@RequestParam("file")MultipartFile[] multipartFiles,HttpServletRequest request) throws IllegalStateException, IOException, ParseException  {
		//储存文件名
		List<String> fileNameLists = new ArrayList<String>() ;
		//多文件
		for(MultipartFile multipartFile : multipartFiles) {
			if(multipartFile.getSize()>0) {
				String fileName = multipartFile.getOriginalFilename();
				fileNameLists.add(fileName);
				System.out.println(multipartFile.getOriginalFilename());
				//保存的位置临时文件
				//String path = request.getSession().getServletContext().getRealPath("files/");
				File filepath = new File("C:\\Users\\Administrator\\git\\swzzbdbxt\\WebContent",fileName);
				 if (!filepath.getParentFile().exists()) { 
					 filepath.getParentFile().mkdirs();
		            } 	
				multipartFile.transferTo(filepath);
				
			}
			//没有选中文件，返回错误页面
			else {
				System.out.println(multipartFile.getOriginalFilename());
				return "/500";
			}
		}
		//录入msg类数据库
		System.out.println("===="+msg.getName()+"====="+status+"====="+msg.getCreateTime());
		String msgIg = ApplicationUtils.newUUID();
		msg.setId(msgIg);
		msg.setSequence(sequenceNumberService.next());
		final int count = msgService.insertSelective(msg);
		//录入attach数据库
		
		//录入msg_contractor数据库
		//录入msg_co-sponsor数据库
		//录入msg_sponsor数据库
		return "success";
		
		
	}
	
	@RequestMapping(value = "/download")
	public String download(Model model, Attach attach,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String filename = attach.getAttachFileName(); 
		 if (filename != null) {
			 File filepath = new File("C://Users//Administrator//git//swzzbdbxt//WebContent//files",filename);
			 if (filepath.exists()) {
				 response.setContentType("application/force-download");// 设置强制下载不打开
			     response.addHeader("Content-Disposition",
			                         "attachment;fileName=" + filename);// 设置文件名
			     byte[] buffer = new byte[1024];
			     FileInputStream fis = null;
			     BufferedInputStream bis = null;
			     try {
			    	 fis = new FileInputStream(filepath);
			    	 bis = new BufferedInputStream(fis);
			    	 OutputStream os = response.getOutputStream();
			    	 int i = bis.read(buffer);
			    	 while (i != -1) {
			    		 os.write(buffer, 0, i);
			    		 i = bis.read(buffer);
			                   }
			      } catch (Exception e) {
			                      // TODO: handle exception
			    	  e.printStackTrace();
			      } finally {
			    	  		if (bis != null) {
			    	  			try {
			                             bis.close();
			                         } catch (IOException e) {
			                             // TODO Auto-generated catch block
			                            e.printStackTrace();
			                         }
			                     }
			    	  		if (fis != null) {
			    	  			try {
			                             fis.close();
			                         } catch (IOException e) {
			                             // TODO Auto-generated catch block
			                             e.printStackTrace();
			                          }
			                     }
			                  }
			              }
			         }
		return "/500";  
	}
	
	@RequestMapping(value = "/delete")
	public String delete(Model model, Attach attach) {
		return null;
	}
}

