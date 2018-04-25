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
	//立项号
	@Resource
	private SequenceNumberService sequenceNumberService;
	//督查项+立项人
	@Resource
	private MsgContractorService msgContractorService;
	//主处室
	@Resource
	private MsgCoSponsorService msgCoSponsorService;
	//协助处室
	@Resource
	private MsgSponsorService msgSponsorService;
	private int status;
	/*private String role;//主办处室下拉框
	private String assitrole;//协助处室下拉框
	public void setRole(String role) {
		this.role = role;
	}
	public String getAssitrole() {
		return assitrole;
	}
	public void setAssitrole(String assitrole) {
		this.assitrole = assitrole;
	}*/
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@RequestMapping(value = "/upload")
	@RequiresRoles(value = RoleSign.ADMIN)
	public void upload(Model model,@RequestParam("file")MultipartFile[] file,HttpServletResponse resp,HttpServletRequest request) throws Exception  {
		 System.out.println("--------------------"+file.length);
		//储存文件名
		List<String> fileNameLists = new ArrayList<String>() ;
		//多文件
		for(MultipartFile multipartFile : file) {
			if(multipartFile.getSize()>0) {
				String fileName = multipartFile.getOriginalFilename();
				fileNameLists.add(fileName);
				System.out.println(multipartFile.getOriginalFilename());
				//保存的位置临时文件
				/*String path = request.getSession().getServletContext().getRealPath("files/");
				File filepath=new File(path);*/
				 File filepath = new File("C://Users//Administrator//git//swzzbdbxt//WebContent//files",fileName);
				 if (!filepath.getParentFile().exists()) { 
					 filepath.getParentFile().mkdirs();
		            } 	
				multipartFile.transferTo(filepath);
				
			}
			//没有选中文件，返回错误页面
			else {
				
			}
		}
		//保存文件名
		request.getSession().setAttribute("fileNameLists", fileNameLists);
		
	}
	@RequestMapping(value = "/save")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String save(@RequestParam("role")String role,@RequestParam("assitrole")String assitrole,@Valid Msg msg,@Valid User user,Model model,HttpServletResponse resp,HttpServletRequest request) throws Exception  {
		MsgContractor msgContractor;
		Attach attach;
		MsgCoSponsor msgCoSponsor;
		MsgSponsor msgSponsor;
		//设置attach的目标类型 0--msg 1--submission,是否签收 0--否 1--是,是否分派 0--否 1--是
		int statu = 0;
		//录入msg类数据库
		System.out.println("===="+role+"====="+status+"====="+sequenceNumberService.next());
		String msgId = ApplicationUtils.newUUID();
		msg.setId(msgId);
		msg.setSequence(sequenceNumberService.next());
		final int msgCount = msgService.insertSelective(msg);
		//录入attach数据库
		List<String> fileNameLists = (List<String>) request.getSession().getAttribute("fileNameLists");
		for(String fileName : fileNameLists) {
			String attachId = ApplicationUtils.newUUID();
			attach = new Attach(attachId, msgId, statu, fileName, ApplicationUtils.getTime());
			attachService.insert(attach);
		}
		
		//录入msg_contractor数据库
		System.out.println("===="+user.getId());
		msgContractor = new MsgContractor();
		msgContractor.setMsgId(msgId);
		msgContractor.setUserId(user.getId());
		final int msgContractorCount = msgContractorService.insert(msgContractor);
		//录入msg_co-sponsor数据库
		msgCoSponsor = new MsgCoSponsor();
		msgSponsor = new MsgSponsor();
		String roleId[] = role.split(",");
		for(int i = 0;i<roleId.length-1;i++) {
			//String msgCoSponsorId = ApplicationUtils.newUUID();
			msgCoSponsor.setMsgId(msgId);
			msgCoSponsor.setRoleId(Long.parseLong(roleId[i]));
			msgCoSponsor.setIsAssigned(statu);
			msgCoSponsor.setIsSigned(statu);
			msgCoSponsorService.insert(msgCoSponsor);
		}
		
		//录入msg_sponsor数据库
		while (!assitrole.isEmpty()||assitrole.equals(null)) {
			String assitroldId[] = role.split(",");
			for(int i = 0;i<assitroldId.length-1;i++) {
				//String msgSponsorId = ApplicationUtils.newUUID();
				msgSponsor.setMsgId(msgId);
				msgCoSponsor.setRoleId(Long.parseLong(roleId[i]));
				msgCoSponsor.setIsAssigned(statu);
				msgCoSponsor.setIsSigned(statu);
				msgSponsorService.insert(msgSponsor);
			}
		}
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

