package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Calendar;
import java.util.Date;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample.Criteria;
import org.springframework.format.annotation.DateTimeFormat;

public class MsgQuery {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTimeBegin;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTimeEnd;

	private String name;

	private String basis;

	private Integer status;

	private Integer pageNo;

	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public void setExample(Criteria criteria) {
		if (name != null && name.length() > 0) {
			String[] array = name.split("[\\s]+");
			String condition = " (name LIKE '%" + array[0] + "%'";
			for (int i = 1; i < array.length; i++) {
				condition += " OR name LIKE '%" + array[i] + "%'";
			}
			condition += ") ";
			criteria.addCriterion(condition);
		}
		if (basis != null && basis.length() > 0) {
			String[] array = basis.split("[\\s]+");
			String condition = " (basis LIKE '%" + array[0] + "%'";
			for (int i = 1; i < array.length; i++) {
				condition += " OR basis LIKE '%" + array[i] + "%'";
			}
			condition += ") ";
			criteria.addCriterion(condition);
		}
		if (createTimeBegin != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(createTimeBegin);
		}
		if (createTimeEnd != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createTimeEnd);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			criteria.andCreateTimeLessThan(calendar.getTime());
		}
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
	}
}
