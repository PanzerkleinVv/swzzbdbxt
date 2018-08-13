package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample.Criteria;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import org.springframework.format.annotation.DateTimeFormat;

public class MsgQuery {

	private Integer sequence;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTimeBegin;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTimeEnd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date limitTimeBegin;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date limitTimeEnd;

	private String name;

	private String basis;

	private Integer status;

	private Integer pageNo;

	private Long roleId;

	private Integer type;

	private Long userId;

	private Integer submissionStatus;
	
	private Boolean onwork;

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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(Integer submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	public Date getLimitTimeBegin() {
		return limitTimeBegin;
	}

	public void setLimitTimeBegin(Date limitTimeBegin) {
		this.limitTimeBegin = limitTimeBegin;
	}

	public Date getLimitTimeEnd() {
		return limitTimeEnd;
	}

	public void setLimitTimeEnd(Date limitTimeEnd) {
		this.limitTimeEnd = limitTimeEnd;
	}

	public void setExample(Criteria criteria) {
		if (userId != null && userId != 0L) {
			String condition = " (id in (select msg_id from msg_contractor where user_id=" + userId + ")) ";
			criteria.addCriterion(condition);
		}

		if (roleId != null || type != null || status != null || limitTimeBegin != null || limitTimeEnd != null || onwork != null) {
			String condition = " (id in (select msg_id from msg_role where $$$)) ";
			List<String> subConditions = new ArrayList<String>();
			if (roleId != null) {
				subConditions.add(" role_id = " + roleId + " ");
			}
			if (type != null && type != 0) {
				subConditions.add(" type = " + type + " ");
			}
			if (status != null) {
				subConditions.add(" status = " + status + " ");
			} else {
				subConditions.add(" status <> 0 ");
			}
			if (onwork != null) {
				if (onwork) {
					subConditions.add(" status in (1, 2) ");
				} else {
					subConditions.add(" status in (3, 4, 5) ");
				}
			}
			if (limitTimeBegin != null) {
				subConditions.add(" limit_time >= str_to_date('" + dateToString(limitTimeBegin) + "', '%Y-%m-%d') ");
			}
			if (limitTimeEnd != null) {
				subConditions.add(" limit_time <= str_to_date('" + dateToString(limitTimeEnd) + "', '%Y-%m-%d') ");
			}
			if (!subConditions.isEmpty()) {
				String subCondition = subConditions.get(0);
				for (int i = 1; i < subConditions.size(); i++) {
					subCondition += " and " + subConditions.get(i);
				}
				condition = condition.replace("$$$", subCondition);
			}
			criteria.addCriterion(condition);
		}

		if (sequence != null && sequence != 0) {
			criteria.andSequenceEqualTo(sequence);
		}

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
		if (submissionStatus != null && submissionStatus == 0) {
			String condition = "(id in (select msg_id from msg_sponsor where id in (select msg_id from submission where status = 0)) OR id in (select msg_id from `msg_co-sponsor` where id in (select msg_id from submission where status = 0)))";
			criteria.addCriterion(condition);
		} else if (submissionStatus != null && submissionStatus == 1) {
			String condition = "(id in (select msg_id from msg_sponsor where id in (select msg_id from submission where status = 1)) OR id in (select msg_id from `msg_co-sponsor` where id in (select msg_id from submission where status = 1)))";
			criteria.addCriterion(condition);
		}

	}
	
	private String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String formatDate(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public Boolean getOnwork() {
		return onwork;
	}

	public void setOnwork(Boolean onwork) {
		this.onwork = onwork;
	}

}
