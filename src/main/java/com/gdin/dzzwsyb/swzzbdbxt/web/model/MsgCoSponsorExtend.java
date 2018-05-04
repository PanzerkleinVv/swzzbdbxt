package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.List;

public class MsgCoSponsorExtend extends MsgCoSponsor {

	private boolean editabled;

	private List<SubmissionExtend> submissions;

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

	public List<SubmissionExtend> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<SubmissionExtend> submissions) {
		this.submissions = submissions;
	}
}
