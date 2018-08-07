package com.gdin.dzzwsyb.swzzbdbxt.web.model;

public class SimpleAnalysis extends Analysis {

	private Integer stop;

	public Integer getStop() {
		return stop;
	}

	public void setStop(Integer stop) {
		this.stop = stop;
	}

	public SimpleAnalysis(Integer stop) {
		super();
		this.stop = stop;
	}

	public SimpleAnalysis() {
		super();
	}

	public SimpleAnalysis(Analysis analysis) {
		super();
		super.setRoleId(analysis.getRoleId());
		super.setType(analysis.getType());
		super.setOnwork(analysis.getOnwork());
		super.setOvertime(analysis.getOvertime());
		super.setPartialDone(analysis.getPartialDone());
		super.setDone(analysis.getDone());
		super.setStop(analysis.getStop());
		super.setYear(analysis.getYear());
		super.setMonth(analysis.getMonth());
		this.stop = super.getDone() + super.getPartialDone() + super.getStop();
	}

}
