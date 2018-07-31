package com.gdin.dzzwsyb.swzzbdbxt.web.model;

public class Analysis {
	private Long roleId;

	private Integer type;

	private Integer onwork;

	private Integer overtime;

	private Integer partialDone;

	private Integer done;

	private Integer stop;

	private Integer year;

	private Integer month;

	public Analysis() {
		super();
	}

	public Analysis(Long roleId, Integer type, Integer onwork, Integer overtime, Integer partialDone, Integer done,
			Integer stop, Integer year, Integer month) {
		super();
		this.roleId = roleId;
		this.type = type;
		this.onwork = onwork;
		this.overtime = overtime;
		this.partialDone = partialDone;
		this.done = done;
		this.stop = stop;
		this.year = year;
		this.month = month;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOnwork() {
		return onwork;
	}

	public void setOnwork(Integer onwork) {
		this.onwork = onwork;
	}

	public Integer getOvertime() {
		return overtime;
	}

	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}

	public Integer getPartialDone() {
		return partialDone;
	}

	public void setPartialDone(Integer partialDone) {
		this.partialDone = partialDone;
	}

	public Integer getDone() {
		return done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public Integer getStop() {
		return stop;
	}

	public void setStop(Integer stop) {
		this.stop = stop;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

}
