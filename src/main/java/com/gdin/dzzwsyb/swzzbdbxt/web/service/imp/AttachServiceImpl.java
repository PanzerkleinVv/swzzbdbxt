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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.HandleFile;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.AttachMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.AttachExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.properties.SourceURL;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;

@Service
public class AttachServiceImpl extends GenericServiceImpl<Attach, String> implements AttachService {
	@Resource
	private AttachMapper attachMapper;

	@Autowired
	private SourceURL sourceURL;

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
		if (msgs != null && msgs.size() > 0) {
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

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Attach> upload(MultipartFile[] files, String targetId, int targetType) throws Exception {
		List<Attach> attachs = new ArrayList<Attach>();
		for (MultipartFile file : files) {
			Attach attach = new Attach(ApplicationUtils.newUUID(), targetId, targetType, file.getOriginalFilename(),
					ApplicationUtils.getTime());
			insert(attach);
			HandleFile.save(file, sourceURL.FILE_URL + attach.getId());
			attachs.add(attach);
		}
		return attachs;
	}

	@Override
	public void deleteByMsgId(String targetId) {
		attachMapper.deleteByMsgId(targetId);
	}

	@Override

	public void download(String id, Model model, HttpServletRequest request, HttpServletResponse response) {
		Attach attach = attachMapper.selectByPrimaryKey(id);
		String attachFileName = attach.getAttachFileName();
		if (attachFileName != null) {
			File filepath = new File("C://File", attachFileName);
			if (filepath.exists()) {
				response.setContentType("application/force-download;charset=UTF-8");// 设置强制下载不打开
				try {
					response.addHeader("Content-Disposition",
							"attachment;fileName=" + URLEncoder.encode(attachFileName, "UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} // 设置文件名
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
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public List<Attach> selectByTargetId(String targetId, int targetType) {
		final AttachExample example = new AttachExample();
		example.createCriteria().andTargetIdEqualTo(targetId).andTargetTypeEqualTo(targetType);
		return attachMapper.selectByExample(example);

	}

	@Override
	public void deleteByTargetIds(List<String> ids) {
		if (ids != null && ids.size() > 0) {
			AttachExample example = new AttachExample();
			example.createCriteria().andTargetIdIn(ids);
			List<Attach> attachs = attachMapper.selectByExample(example);
			if (attachs != null && attachs.size() > 0) {
				for (Attach attach : attachs) {
					File filepath = new File(sourceURL + attach.getId());
					if (filepath.exists()) {
						filepath.delete();
					}
				}
				attachMapper.deleteByExample(example);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean deleteFile(String id) throws Exception {
		HandleFile.remove(sourceURL + id);
		delete(id);
		return true;
	}

}
