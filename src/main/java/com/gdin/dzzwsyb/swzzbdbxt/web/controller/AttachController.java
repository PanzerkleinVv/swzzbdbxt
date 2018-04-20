package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;

@Controller
@RequestMapping(value = "/attach")
public class AttachController {

	@RequestMapping(value = "/upload")
	public String upload(Model model,   @RequestParam("file")MultipartFile multipartFile,HttpServletRequest request) throws IllegalStateException, IOException  {
		
			if(multipartFile.getSize()>0) {
				String fileName = multipartFile.getOriginalFilename();
				System.out.println(multipartFile.getOriginalFilename());
				//保存的位置
				//String path = request.getSession().getServletContext().getRealPath("files/");
				File filepath = new File("C://Users//Administrator//git//swzzbdbxt//WebContent//files",fileName);
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
		
		return "/401";
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

