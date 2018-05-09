package com.gdin.dzzwsyb.swzzbdbxt.web.model;

public class SubmissionExtend extends Submission {
	
	private boolean verifiable;
	
	private String ownerDesc;
	
	private String superiorVerifiUserDesc;

	public SubmissionExtend() {

	}
	
	public SubmissionExtend(Submission submission) {
		super(submission);
	}

	public boolean isVerifiable() {
		return verifiable;
	}

	public void setVerifiable(boolean verifiable) {
		this.verifiable = verifiable;
	}

	public String getOwnerDesc() {
		return ownerDesc;
	}

	public void setOwnerDesc(String ownerDesc) {
		this.ownerDesc = ownerDesc;
	}

	public String getSuperiorVerifiUserDesc() {
		return superiorVerifiUserDesc;
	}

	public void setSuperiorVerifiUserDesc(String superiorVerifiUserDesc) {
		this.superiorVerifiUserDesc = superiorVerifiUserDesc;
	}
}
