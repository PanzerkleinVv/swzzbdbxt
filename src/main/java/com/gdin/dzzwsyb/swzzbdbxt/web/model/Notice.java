package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 通知提醒表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class Notice {
	private Long id;

	private Long userId;

	private Integer type;

	private String targetId;

	private Integer targetType;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	private Integer isRead;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId == null ? null : targetId.trim();
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Notice(Long userId, Integer type, String targetId, Integer targetType, Date createTime, Integer isRead) {
		super();
		this.userId = userId;
		this.type = type;
		this.targetId = targetId;
		this.targetType = targetType;
		this.createTime = createTime;
		this.isRead = isRead;
	}
	
	public Notice() {};
}