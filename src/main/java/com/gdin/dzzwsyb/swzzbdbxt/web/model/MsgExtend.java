package com.gdin.dzzwsyb.swzzbdbxt.web.model;

public class MsgExtend extends Msg {
	
	private String sponsorRoleNames;
	
	private String coSponsorRoleNames;
	
	private String contents;
	
	private String attachs[];
	
	private String attachIds[];
	
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

}
