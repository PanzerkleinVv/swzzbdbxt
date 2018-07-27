package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.List;

public class MsgCoSponsorExtend extends MsgCoSponsor {

	private boolean editabled;
	
	private boolean assignable;
	
	private List<User> users;

	private List<SubmissionExtend> submissions;
	
	private List<Attach> attachs;

	public MsgCoSponsorExtend() {

	}

	public MsgCoSponsorExtend(MsgCoSponsor msgCoSponsor) {
		super(msgCoSponsor);
	}

	public boolean isEditabled() {
		return editabled;
	}

	public void setEditabled(boolean editabled) {
		this.editabled = editabled;
	}

	public boolean isAssignable() {
		return assignable;
	}

	public void setAssignable(boolean assignable) {
		this.assignable = assignable;
	}

	public List<SubmissionExtend> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<SubmissionExtend> submissions) {
		this.submissions = submissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}
}
