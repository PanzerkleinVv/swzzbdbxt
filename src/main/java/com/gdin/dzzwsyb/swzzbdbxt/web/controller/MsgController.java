package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
	
	//立项号
	@Resource
	private SequenceNumberService sequenceNumberService;
	
	MsgContractor msgContractor;
	Attach attach;
	MsgCoSponsor msgCoSponsor;
	MsgSponsor msgSponsor;
	String msgId;//督办事项id
	String msgContractorId;//督办事项承办人表
	String msgCoSponsorId;//协办处室id
	String msgSponsorId;//主办处室id
	ArrayList<Long> msgSponsorSelect;//存储下拉框选中的主处室
	ArrayList<Long> msgCoSponsorSelect;//存储下拉框选中的协助处室
	
	@RequestMapping(value = "/query")
	public String query() {
		return "query";
	}

	@RequestMapping(value = "/msgList")
	public String msgList(Model model, HttpSession session, MsgQuery msgQuery) {
		final Long roleId = (Long)session.getAttribute("roleId");
		final Long permissionId = (Long)session.getAttribute("permissionId");
		final Long userId = (Long)session.getAttribute("currentUserId");
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
		@SuppressWarnings("unchecked")
		final Map<Long, String> roleMap = (Map<Long, String>)session.getAttribute("roleMap");
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
		return "msgList";
	}

	@RequestMapping(value = "/upload")
	public String upload(Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		session.setAttribute("msgBasis", SelectArray.getMsgBasis()); //立法依据
		if (user != null) {

		}
		return "upload";
	}

	@RequestMapping(value = "/uploadMsg")
	public String uploadMsg(Msg msg, Model model, HttpSession session) {
		User user = (User) session.getAttribute("userInfo");
		if (user != null) {

		}
		return "upload";
	}

	@RequestMapping(value = "/deleteMsg")
	public String msgDelete(Msg msg, Model model) {
		msgService.delete(msg.getId());
		return "template";
	}

	@RequestMapping(value = "/openMsg")
	public String openMsg(Msg msg, Model model) {
		Msg msg0 = msgService.selectById(msg.getId());
		model.addAttribute("msg", msg0);
		return "msg";
	}

	@RequestMapping(value = "/changeMsgStatus")
	public String changeMsgStatus(Msg msg, Model model) {
		msgService.update(msg);
		return "msgStatusName";
	}
	
	@RequestMapping(value = "/save")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String save(@RequestParam("status")int status,@RequestParam("role")String role,@RequestParam("assitrole")String assitrole,@Valid Msg msg,@Valid User user,Model model,HttpServletResponse resp,HttpServletRequest request) throws Exception  {
		msgSponsorSelect = new ArrayList() ;
		msgCoSponsorSelect = new ArrayList() ;
		//录入msg类数据库
		//System.out.println("===="+role+"====="+assitrole+"====="+sequenceNumberService.next());
		msgId = ApplicationUtils.newUUID();
		msg.setId(msgId);
		msg.setSequence(sequenceNumberService.next());
		final int msgCount = msgService.insertSelective(msg);
		//录入attach数据库
		List<String> fileNameLists = (List<String>) request.getSession().getAttribute("fileNameLists");
		for(String fileName : fileNameLists) {
			String attachId = ApplicationUtils.newUUID();
			attach = new Attach(attachId, msgId, status, fileName, ApplicationUtils.getTime());
			attachService.insert(attach);
		}
		
		//录入msg_co-sponsor数据库
		msgCoSponsor = new MsgCoSponsor();
		msgSponsor = new MsgSponsor();
		String roleIdArr[] = role.split(",");
		for(int i = 0;i<roleIdArr.length;i++) {
			//System.out.println("+++++++++++"+roleIdArr[i]);
			
			msgCoSponsorId = ApplicationUtils.newUUID();
			msgCoSponsor.setId(msgCoSponsorId);
			msgCoSponsor.setMsgId(msgId);
			long roleId = Long.parseLong(roleIdArr[i]);
			
			msgCoSponsorSelect.add(roleId);
			msgCoSponsor.setRoleId(roleId);
			msgCoSponsor.setIsAssigned(0);
			msgCoSponsor.setIsSigned(0);
			msgCoSponsor.setStatus(status);
			msgCoSponsorService.insertSelective(msgCoSponsor);
		}
		//录入msg_sponsor数据库
		if (assitrole.length()>0) {
			String assitroldIdArr[] = assitrole.split(",");
			for(int i = 0;i<assitroldIdArr.length;i++) {
				msgSponsorId = ApplicationUtils.newUUID();
				msgSponsor.setId(msgSponsorId);
				msgSponsor.setMsgId(msgId);
				long assitRoldId = Long.parseLong(assitroldIdArr[i]);
				msgSponsorSelect.add(assitRoldId);
				msgSponsor.setRoleId(assitRoldId);
				msgSponsor.setIsAssigned(0);
				msgSponsor.setIsSigned(0);
				msgSponsor.setStatus(status);
				msgSponsorService.insertSelective(msgSponsor);
			}
			assitrole = null;
		}
		System.out.println("+++++"+msgSponsorSelect.size());
		model.addAttribute("msgSponsorSelect", msgSponsorSelect);
		model.addAttribute("msgCoSponsorSelect", msgCoSponsorSelect);
		return "upload";
	}
	@RequestMapping(value = "/gett")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String get(@RequestParam("role")String role,@Valid Msg msg,@Valid User user,Model model,HttpServletResponse resp,HttpServletRequest request){
		ArrayList<Long> msgSponsorSelect = new ArrayList() ;
		List<Role> roles = (List<Role>) request.getSession().getAttribute("roles");
		ArrayList<Long> roleList = null;
		roleList = new ArrayList();
		if(role.length()>0) {
			String roleIdArr[] = role.split(",");
			for(int i = 0;i<roleIdArr.length;i++) {
				for(Role role2 : roles) {
					if(Long.parseLong(roleIdArr[i])==(role2.getId())) {
						roleList.add(role2.getId());
						msgSponsorSelect.add(role2.getId());
						break;
					}
					
				}
			}
		}
		else {
			roleList = null;
			msgSponsorSelect =null ;
		}
		model.addAttribute("roleList",roleList);
		model.addAttribute("msgSponsorSelect", msgSponsorSelect);
		return "upload";
	}
	
	
}
