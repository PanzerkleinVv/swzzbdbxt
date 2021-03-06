package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.List;

public class MsgSponsorExtend extends MsgSponsor {

	private boolean editabled;
	
	private boolean assignable;
	
	private List<User> users;

	private List<SubmissionExtend> submissions;
	
	private List<Attach> attachs;
	
	private List<Log> logs;

	public MsgSponsorExtend() {

	}

	public MsgSponsorExtend(MsgSponsor msgSponsor) {
		super(msgSponsor);
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

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
}
