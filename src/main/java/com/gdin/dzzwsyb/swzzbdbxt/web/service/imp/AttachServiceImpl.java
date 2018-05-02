package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.AttachMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.AttachExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;

@Service
public class AttachServiceImpl extends GenericServiceImpl<Attach, String> implements AttachService {

	@Resource
	private AttachMapper attachMapper;

	@Override
	public int insert(Attach model) {
		return attachMapper.insertSelective(model);
	}

	@Override
	public int update(Attach model) {
		return attachMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(String id) {
		return attachMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Attach selectById(String id) {
		return attachMapper.selectByPrimaryKey(id);
	}

	@Override
	public Attach selectOne() {
		return null;
	}

	@Override
	public List<Attach> selectList() {
		return null;
	}

	@Override
	public GenericDao<Attach, String> getDao() {
		return attachMapper;
	}

	@Override
	public List<MsgExtend> selectMsgExtendByMsgList(List<MsgExtend> msgs, List<List<String>> ids) {
		if(msgs != null && msgs.size() > 0) {
			MsgExtend msg = null;
			List<Attach> attachList = null;
			for (int i = 0; i < msgs.size(); i++) {
				msg = msgs.get(i);
				AttachExample example = new AttachExample();
				example.createCriteria().andTargetIdIn(ids.get(i));
				attachList = attachMapper.selectByExample(example);
				String[] attachs = null;
				String[] attachIds = null;
				if (attachList != null && attachList.size() > 0) {
					attachs = new String[attachList.size()];
					attachIds = new String[attachList.size()];
					for (int j = 0; j < attachList.size(); j++) {
						attachIds[j] = attachList.get(j).getId();
						attachs[j] = attachList.get(j).getAttachFileName();
					}
				}
				msg.setAttachIds(attachIds);
				msg.setAttachs(attachs);
			}
		}
		return msgs;
	}

	@Override
	public void upload(Model model, MultipartFile[] file, HttpServletResponse resp, HttpServletRequest request){
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
						try {
							multipartFile.transferTo(filepath);
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					//没有选中文件，返回错误页面
					else {
						fileNameLists.add(null);
					}
				}
				//保存文件名
				request.getSession().setAttribute("fileNameLists", fileNameLists);
				
	}

	@Override
	public void deleteByMsgId(String targetId) {
		attachMapper.deleteByMsgId(targetId);
	}

	@Override

	public void download(String id, Model model, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Attach attach = attachMapper.selectByPrimaryKey(id);
		System.out.println("----------"+attach.getAttachFileName());
		String attachFileName = attach.getAttachFileName();
		 if (attachFileName != null) {
			 File filepath = new File("C://Users//Administrator//git//swzzbdbxt//WebContent//files",attachFileName);
			 if (filepath.exists()) {
				 /*response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			     response.setHeader("Content-Disposition", "attachment;filename="+attachFileName);
				 */response.setContentType("application/force-download;charset=UTF-8");// 设置强制下载不打开
			     try {
					response.addHeader("Content-Disposition",
					                     "attachment;fileName=" + URLEncoder.encode(attachFileName, "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// 设置文件名
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
			 else{
				 
			 }
 }

	public List<Attach> selectByTargetId(String targetId) {
		final AttachExample example = new AttachExample();
		example.createCriteria().andTargetIdEqualTo(targetId);
		return attachMapper.selectByExample(example);

	}

}
