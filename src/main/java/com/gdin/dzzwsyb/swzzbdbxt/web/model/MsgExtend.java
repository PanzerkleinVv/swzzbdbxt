package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.List;

public class MsgExtend extends Msg {
	
	private String sponsorRoleNames;
	
	private String coSponsorRoleNames;
	
	private List<Long> role;
	
	private List<Long> assitrole;
	
	private String contents;
	
	private String attachs[];
	
	private String attachIds[];
	
	private Integer status;
	
	private String statusName;
	
	private String msgBasis;
	
	public MsgExtend() {
		
	}
	
	public MsgExtend(Msg msg) {
		super(msg);
	}
	
	public MsgExtend(MsgExtend msgExtend) {
		this((Msg)msgExtend);
		sponsorRoleNames = msgExtend.getSponsorRoleNames();
		coSponsorRoleNames = msgExtend.getCoSponsorRoleNames();
		contents = msgExtend.getContents();
		attachs = msgExtend.getAttachs();
		attachIds = msgExtend.getAttachIds();
		role = msgExtend.getRole();
		assitrole = msgExtend.getAssitrole();
	}

	public String getSponsorRoleNames() {
		return sponsorRoleNames;
	}

	public void setSponsorRoleNames(String sponsorRoleNames) {
		this.sponsorRoleNames = sponsorRoleNames;
	}

	public String getCoSponsorRoleNames() {
		return coSponsorRoleNames;
	}

	public void setCoSponsorRoleNames(String coSponsorRoleNames) {
		this.coSponsorRoleNames = coSponsorRoleNames;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String[] getAttachs() {
		return attachs;
	}

	public void setAttachs(String[] attachs) {
		this.attachs = attachs;
	}

	public String[] getAttachIds() {
		return attachIds;
	}

	public void setAttachIds(String[] attachIds) {
		this.attachIds = attachIds;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getMsgBasis() {
		return msgBasis;
	}

	public void setMsgBasis(String msgBasis) {
		this.msgBasis = msgBasis;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<Long> getRole() {
		return role;
	}

	public void setRole(List<Long> role) {
		this.role = role;
	}

	public List<Long> getAssitrole() {
		return assitrole;
	}

	public void setAssitrole(List<Long> assitrole) {
		this.assitrole = assitrole;
	}

}
