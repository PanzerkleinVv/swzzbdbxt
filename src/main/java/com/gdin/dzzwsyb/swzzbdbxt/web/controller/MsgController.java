package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.SelectArray;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample.Criteria;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExtend;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgQuery;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AttachService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgCoSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgContractorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgSponsorService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SubmissionService;

@Controller
@RequestMapping(value = "/msg")
public class MsgController {
	
	@Resource
	private MsgService msgService;

	@Resource
	private MsgSponsorService msgSponsorService;

	@Resource
	private MsgCoSponsorService msgCoSponsorService;

	@Resource
	private MsgContractorService msgContractorService;

	@Resource
	private SubmissionService submissionService;

	@Resource
	private AttachService attachService;

	// 立项号
	@Resource
	private SequenceNumberService sequenceNumberService;
	
	@RequestMapping(value = "/query")
	public String query() {
		return "query";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/msgList")
	public String msgList(Model model, HttpSession session, MsgQuery msgQuery) {
		final Long roleId = (Long) session.getAttribute("roleId");
		final Long permissionId = (Long) session.getAttribute("permissionId");
		final Long userId = (Long) session.getAttribute("currentUserId");
		final MsgExample example = new MsgExample();
		Criteria criteria = example.createCriteria();
		final List<String> msgId = new ArrayList<String>();
		if (roleId < 4L && permissionId < 6L) {
			criteria.andIdIsNotNull();
		} else if (permissionId < 6L) {
			msgId.addAll(msgSponsorService.selectMsgIdByRoleId(roleId));
			msgId.addAll(msgCoSponsorService.selectMsgIdByRoleId(roleId));
			criteria.andIdIn(msgId);
		} else {
			msgId.addAll(msgContractorService.selectMsgIdByUserId(userId));
			criteria.andIdIn(msgId);
		}
		msgQuery.setExample(criteria);
		example.setOrderByClause("sequence asc");
		List<Msg> msgs = null;
		Page<Msg> page = null;
		page = msgService.selectByExampleAndPage(example, msgQuery.getPageNo());
		msgs = page.getResult();
		final Map<Long, String> roleMap = (Map<Long, String>) session.getAttribute("roleMap");
		List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
		for (Msg msg : msgs) {
			msgExtends.add(new MsgExtend(msg));
		}
		msgExtends = msgSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
		msgExtends = msgCoSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
		List<List<String>> ids = submissionService.selectIdsByMsgList(msgs, roleId);
		msgExtends = attachService.selectMsgExtendByMsgList(msgExtends, ids);
		model.addAttribute("page", page);
		model.addAttribute("msgs", msgExtends);
		if (msgQuery != null && msgQuery.getStatus() != null && msgQuery.getStatus() == 0) {
			model.addAttribute("titleName", "督查草稿");
		}
		return "msgList";
	}

	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	@RequestMapping(value = "/upload")
	public String upload(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		session.setAttribute("msgBasis", SelectArray.getMsgBasis()); // 立法依据
		if (user != null) {
			model.addAttribute("titleName", "督查上传");
		}
		
		return "upload";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/openMsg")
	public String openMsg(MsgExtend msg, Model model, HttpSession session) {
		final Map<Long, String> roleMap = (Map<Long, String>) session.getAttribute("roleMap");
		final Long roleId = (Long) session.getAttribute("roleId");
		List<Long> msgSponsorSelect = null;//存储下拉框选中的主处室
		List<Long> msgCoSponsorSelect = null;//存储下拉框选中的协助处室
		if (msg != null && msg.getId() != null && !"".equals(msg.getId()) && msg.getStatus() != null
				&& 0 == msg.getStatus().intValue()) {
			Msg msg0 = msgService.selectById(msg.getId());
			model.addAttribute("msg", msg0);
			String basisSelect = null;
			if (msg0 != null) {
				msgSponsorSelect = msgSponsorService.selectRoleIdByMsgId(msg0.getId());
				msgCoSponsorSelect = msgCoSponsorService.selectRoleIdByMsgId(msg0.getId());
				basisSelect = msg0.getBasis();
			}
			model.addAttribute("basisSelect", basisSelect);
			model.addAttribute("msgSponsorSelect", msgSponsorSelect);
			model.addAttribute("msgCoSponsorSelect", msgCoSponsorSelect);
			return "upload";
		} else if (msg != null && msg.getId() != null && !"".equals(msg.getId())) {
			Msg msg0 = msgService.selectById(msg.getId());
			MsgExtend msgExtend = new MsgExtend(msg0);
			List<MsgExtend> msgExtends = new ArrayList<MsgExtend>();
			msgExtends.add(msgExtend);
			msgExtends = msgSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
			msgExtends = msgCoSponsorService.selectMsgExtendByMsgList(msgExtends, roleMap, roleId);
			List<Attach> attachList = attachService.selectByTargetId(msgExtend.getId());
			msgExtend = msgExtends.get(0);
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
			msgExtend.setAttachIds(attachIds);
			msgExtend.setAttachs(attachs);
			model.addAttribute("msg", msgExtend);
			return "msg"; 
		} else {
			return "404";
		}
	}

	@RequestMapping(value = "/insert")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String insert(@RequestParam("msgBasis")String msgBasis,@RequestParam("msgId")String id,@RequestParam("sequenceNumber")Integer sequenceNumbers,@RequestParam("status")int status,@RequestParam("role")String role,@RequestParam("assitrole")String assitrole,@Valid Msg msg,@Valid User user,Model model,HttpServletRequest request) throws Exception  {	
		MsgContractor msgContractor;
		Attach attach;
		MsgCoSponsor msgCoSponsor;
		MsgSponsor msgSponsor;
		String msgContractorId;//督办事项承办人表
		String msgCoSponsorId;//协办处室id
		String msgSponsorId;//主办处室id
		List<Long> msgSponsorSelect = null;//存储下拉框选中的主处室
		List<Long> msgCoSponsorSelect = null;//存储下拉框选中的协助处
		List<MsgCoSponsor> msgCoSponsors;
		List<MsgSponsor> msgSponsors;
		
		String basisSelect =msg.getBasis(); 
		//录入msg类数据库
		//是否有id来判断是保存还是修改
		if(id.isEmpty()) {
			if(msg.getBasis().equals("自定义")) {
				msg.setBasis(msgBasis);
			}
			msgSponsorSelect = new  ArrayList<Long>();
			msgCoSponsorSelect = new ArrayList<Long>();
			String msgId = ApplicationUtils.newUUID();
			Integer sequenceNumber = sequenceNumberService.next();
			msg.setId(msgId);
			msg.setSequence(sequenceNumber);
			msgService.insertSelective(msg);
			List<String> fileNameLists = (List<String>) request.getSession().getAttribute("fileNameLists");
			while(fileNameLists != null) {
				for(String fileName : fileNameLists) {
					String attachId = ApplicationUtils.newUUID();
					attach = new Attach(attachId, msgId, 0, fileName, ApplicationUtils.getTime());
					attachService.insert(attach);
				}
				break;
			}	
			//录入msg_co-sponsor数据库
			String roleIdArr[] = role.split(",");
			for(int i = 0;i<roleIdArr.length;i++) {
				long roleId = Long.parseLong(roleIdArr[i]);
				msgSponsor =new MsgSponsor(ApplicationUtils.newUUID(), msgId, roleId, 0, 0, "", status);
				msgSponsorSelect.add(roleId);
				msgSponsorService.insertSelective(msgSponsor);
			}
			//录入msg_sponsor数据库
			if (assitrole.equals("null")) {
			}
			else {
				String assitroldIdArr[] = assitrole.split(",");
				for(int i = 0;i<assitroldIdArr.length;i++) {
					long assitRoldId = Long.parseLong(assitroldIdArr[i]);
					msgCoSponsor = new MsgCoSponsor(ApplicationUtils.newUUID(), msgId, assitRoldId, 0, 0, "", status);
					msgCoSponsorSelect.add(assitRoldId);
					msgCoSponsorService.insertSelective(msgCoSponsor);
				}
			}
			model.addAttribute("msgBasis",msgBasis);
			model.addAttribute("sequenceNumber",sequenceNumber);
			model.addAttribute("id",msgId);
			model.addAttribute("fileName", fileNameLists);
			request.getSession().removeAttribute("fileNameLists");
			model.addAttribute("basisSelect", basisSelect);
			model.addAttribute("msgSponsorSelect", msgSponsorSelect);
			model.addAttribute("msgCoSponsorSelect", msgCoSponsorSelect);
			return "upload";
		}
		else {
			msg.setId(id);
			msg.setSequence(sequenceNumbers);
			final int msgCount = msgService.update(msg);
			//录入attach数据库
			List<String> fileNameLists = (List<String>) request.getSession().getAttribute("fileNameLists");
			while(fileNameLists != null) {
				for(String fileName : fileNameLists) {
					attachService.deleteByMsgId(id);
					String attachId = ApplicationUtils.newUUID();
					attach = new Attach(attachId, id, status, fileName, ApplicationUtils.getTime());
					attachService.insert(attach);
				}
				break;
			}
			//录入msg_co-sponsor数据库
			msgSponsors = new ArrayList<MsgSponsor>();
			String roleIdArr[] = role.split(",");
			for(int i = 0;i<roleIdArr.length;i++) {
				long roleId = Long.parseLong(roleIdArr[i]);
				msgSponsor = new MsgSponsor(ApplicationUtils.newUUID(), id, roleId, 0, 0, "", status);
				msgSponsors.add(msgSponsor);
			}
			boolean msgSponsorFlag = msgSponsorService.modifyRoleId(id, msgSponsors);
			//录入msg_sponsor数据库
			if (assitrole.equals("null")) {
				msgCoSponsorService.deleteByMgsId(id);
			}
			else {
				String assitroldIdArr[] = assitrole.split(",");
				msgCoSponsors = new ArrayList<MsgCoSponsor>();
				for(int i = 0;i<assitroldIdArr.length;i++) {
					long assitRoldId = Long.parseLong(assitroldIdArr[i]);
					msgCoSponsor = new MsgCoSponsor(ApplicationUtils.newUUID(), id, assitRoldId, 0, 0, "", status);
					msgCoSponsors.add(msgCoSponsor);
				}
				boolean msgCoSponsorFlag = msgCoSponsorService.modifyRoleId(id, msgCoSponsors);
			}
			model.addAttribute("basisSelect", basisSelect);
			return "upload";
		}
		
		
	}

	@RequestMapping(value = "/gett")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String get(@RequestParam("msgId")String id,@RequestParam("sequenceNumber")Integer sequenceNumbers,@RequestParam("role")String role,@Valid Msg msg,@Valid User user,Model model,HttpServletResponse resp,HttpServletRequest request){
		String  basisSelect;
		ArrayList<Long> msgSponsorSelect = new ArrayList<Long>() ;
		List<Role> roles = (List<Role>) request.getSession().getAttribute("roles");
		ArrayList<Long> roleList = null;
		roleList = new ArrayList<Long>();
		if (role.length() > 0) {
			String roleIdArr[] = role.split(",");
			for (int i = 0; i < roleIdArr.length; i++) {
				for (Role role2 : roles) {
					if (Long.parseLong(roleIdArr[i]) == (role2.getId())) {
						roleList.add(role2.getId());
						msgSponsorSelect.add(role2.getId());
						break;
					}
				}
			}
		} else {
			roleList = null;
			msgSponsorSelect = null;
		}
		basisSelect = msg.getBasis();
		model.addAttribute("id",id);
		model.addAttribute("sequenceNumber",sequenceNumbers);
		model.addAttribute("basisSelect",basisSelect);
		model.addAttribute("roleList",roleList);
		model.addAttribute("msgSponsorSelect", msgSponsorSelect);
		return "upload";
	}

	//删除
	@RequestMapping(value = "/detele")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String  detele(@RequestParam("id") String msgId,Model model,HttpServletResponse resp,HttpServletRequest request) {
		msgService.delete(msgId);
		attachService.deleteByMsgId(msgId);
		msgCoSponsorService.deleteByMgsId(msgId);
		msgSponsorService.deleteByMgsId(msgId);
		return "upload";
	}
}
