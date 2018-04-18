package com.gdin.dzzwsyb.swzzbdbxt.web.model;

/**
 * 提请事项表大数据字段模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class SubmissionWithBLOBs extends Submission {
	private String situation;

	private String reason;

	private String measure;

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation == null ? null : situation.trim();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure == null ? null : measure.trim();
	}
}