package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 附件表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class Attach {
	private String id;

	private String targetId;

	private Integer targetType;

	private String attachFileName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
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

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName == null ? null : attachFileName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Attach(String id, String targetId, Integer targetType, String attachFileName, Date createTime) {
		super();
		this.id = id;
		this.targetId = targetId;
		this.targetType = targetType;
		this.attachFileName = attachFileName;
		this.createTime = createTime;
	}
	public Attach() {};
}