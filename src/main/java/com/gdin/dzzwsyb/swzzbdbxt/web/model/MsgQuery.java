package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	private Long assistroleId;

	private Long userId;
	
	private Integer submissionStatus;

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
	
	public Long getAssistroleId() {
		return assistroleId;
	}

	public void setAssistroleId(Long assistroleId) {
		this.assistroleId = assistroleId;
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
		
		String begin="";
		String end="";	
		if(roleId != null) {
				if(assistroleId != null) { 
					if(status != null) {	//主办，协办，状态，时间
						if(limitTimeBegin !=null && limitTimeEnd !=null) { // 检索在2个时间段之间的
							begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
							end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time >=" + begin + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
							criteria.addCriterion(condition);												
						}else if (limitTimeBegin !=null) { // 办结期限开始时间
							begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time >=" + begin + " and status=" + status      + ") ) ";
							criteria.addCriterion(condition);	
							
						}else if(limitTimeEnd !=null) { // 办结期限结束时间
							end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
							criteria.addCriterion(condition);	
						}else if(limitTimeBegin ==null && limitTimeEnd ==null) { // 没有输入办结期限
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and status=" + status      + ") ) ";
							criteria.addCriterion(condition);	
						}
					}else {	//主办，协办，时间
						if(limitTimeBegin !=null && limitTimeEnd !=null ) { // 检索在2个时间段之间的
							begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
							end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time >=" + begin + " and limit_time <=" +end  + " and status >0 "      + ") ) ";
							criteria.addCriterion(condition);												
						}else if(limitTimeBegin !=null) { // 办结期限开始时间
							begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time >=" + begin + " and status >0 "      + ") ) ";
							criteria.addCriterion(condition);	
							
						}else if(limitTimeEnd !=null) { // 办结期限结束时间
							end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time <=" +end  + " and status >0 "      + ") ) ";
							criteria.addCriterion(condition);	
						}else if(limitTimeBegin ==null && limitTimeEnd ==null) { // 没有输入办结期限
							String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " or ( type = 1 and role_id=" + assistroleId + ")" + " and status >0 "      + ") ) ";
							criteria.addCriterion(condition);	
						}
					}
				}
			}
		
		
		// 主办，状态，时间  --扩展
		if(roleId != null) { 
			if(status != null) { //主办，状态，时间 
				if(limitTimeBegin !=null && limitTimeEnd !=null && assistroleId == null) { // 检索在2个时间段之间的
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")"  + " and limit_time >=" + begin + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);												
				}else if(limitTimeBegin !=null && assistroleId == null) { // 办结期限开始时间
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")"  + " and limit_time <=" + begin + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
					
				}else if(limitTimeEnd !=null && assistroleId == null) { // 办结期限结束时间
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
				}else if(limitTimeBegin ==null && limitTimeEnd ==null && assistroleId == null) { // 没有输入办结期限
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")"  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
				}
				
			}else {  // 主办，时间
				if(limitTimeBegin !=null && limitTimeEnd !=null && assistroleId == null) { // 检索在2个时间段之间的
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")"  + " and limit_time >=" + begin + " and limit_time <=" +end  + " and status > 0"     + ") ) ";
					criteria.addCriterion(condition);												
				}else if(limitTimeBegin !=null && assistroleId == null) { // 办结期限开始时间
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")"  + " and limit_time >=" + begin + " and status > 0"       + ") ) ";
					criteria.addCriterion(condition);	
					
				}else if(limitTimeEnd !=null && assistroleId == null) { // 办结期限结束时间
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")" + " and limit_time <=" +end  + " and status > 0"       + ") ) ";
					criteria.addCriterion(condition);	
				}else if(limitTimeBegin ==null && limitTimeEnd ==null && assistroleId == null) { // 没有输入办结期限
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 0 and role_id=" + roleId + ")"  + " and status > 0"       + ") ) ";
					criteria.addCriterion(condition);	
				}
				
			}
		
		}else { // 状态，时间
			if (status != null) {
				if(limitTimeBegin !=null && limitTimeEnd !=null && assistroleId == null && roleId == null && status != null) { // 检索在2个时间段之间的
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where"   + " limit_time >=" + begin + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);												
				}else if(limitTimeBegin !=null && assistroleId == null && roleId == null && status != null) { // 办结期限开始时间
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					String condition = " (id in (select msg_id from sponsor_co_extend where"   + " limit_time >=" + begin + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
					
				}else if(limitTimeEnd !=null && assistroleId == null && roleId == null && status != null) { // 办结期限结束时间
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where"  + " limit_time <=" +end  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
				}else if(limitTimeBegin ==null && limitTimeEnd ==null && assistroleId == null && roleId == null && status != null) { // 没有输入办结期限
					String condition = " (id in (select msg_id from sponsor_co_extend where"  + " status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
				}
			}else {  // 时间
				if(limitTimeBegin !=null && limitTimeEnd !=null && assistroleId == null && roleId == null && status == null) { // 检索在2个时间段之间的
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where"   + " limit_time >=" + begin + " and limit_time <=" +end  + " and status > 0"      + ") ) ";
					criteria.addCriterion(condition);												
				}else if(limitTimeBegin !=null && assistroleId == null && roleId == null && status == null) { // 办结期限开始时间
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					String condition = " (id in (select msg_id from sponsor_co_extend where"   + " limit_time >=" + begin + " and status > 0"      + ") ) ";
					criteria.addCriterion(condition);	
					
				}else if(limitTimeEnd !=null && assistroleId == null && roleId == null && status == null) { // 办结期限结束时间
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where"  + " limit_time <=" +end  + " and status > 0"     + ") ) ";
					criteria.addCriterion(condition);	
				}
				
			}
		
		}
		
		// 协办，状态，时间  --扩展
		if(assistroleId != null) { 
			if(status != null) { //协办，状态，时间 
				if(limitTimeBegin !=null && limitTimeEnd !=null && roleId == null) { // 检索在2个时间段之间的
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")"  + " and limit_time >=" + begin + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);												
				}else if(limitTimeBegin !=null && roleId == null) { // 办结期限开始时间
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")"  + " and limit_time >=" + begin + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
					
				}else if(limitTimeEnd !=null && roleId == null) { // 办结期限结束时间
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time <=" +end  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
				}else if(limitTimeBegin ==null && limitTimeEnd ==null && roleId == null) { // 没有输入办结期限
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")"  + " and status=" + status      + ") ) ";
					criteria.addCriterion(condition);	
				}
				
			}else {  // 协办，时间
				if(limitTimeBegin !=null && limitTimeEnd !=null && roleId == null ) { // 检索在2个时间段之间的
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")"  + " and limit_time >=" + begin + " and limit_time <=" +end  + " and status > 0"     + ") ) ";
					criteria.addCriterion(condition);												
				}else if(limitTimeBegin !=null && roleId == null) { // 办结期限开始时间
					begin = "'" + MsgQuery.formatDate(limitTimeBegin) +"'" ;
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")"  + " and limit_time >=" + begin + " and status > 0"       + ") ) ";
					criteria.addCriterion(condition);	
					
				}else if(limitTimeEnd !=null && roleId == null) { // 办结期限结束时间
					end = "'" + MsgQuery.formatDate(limitTimeEnd) +"'" ; 
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")" + " and limit_time <=" +end  + " and status > 0"       + ") ) ";
					criteria.addCriterion(condition);	
				}
				else if(limitTimeBegin ==null && limitTimeEnd ==null && roleId == null) { // 没有输入办结期限
					String condition = " (id in (select msg_id from sponsor_co_extend where ( type = 1 and role_id=" + assistroleId + ")"  + " and status > 0"       + ") ) ";
					criteria.addCriterion(condition);	
				}
			}
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
	
	
	public static  String formatDate(Date date)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
	
	
}
