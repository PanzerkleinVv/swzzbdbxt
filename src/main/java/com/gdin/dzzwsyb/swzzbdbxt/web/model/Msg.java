package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 督办事项表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class Msg {
	private String id;

	private Integer sequence;



	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	private String name;

	private String basis;



	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date limitTime;


	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	private Integer status;

	private String content;
	
	public Msg() {
		
	}
	
	public Msg(Msg msg) {
		basis = msg.getBasis();
		content = msg.getContent();
		createTime = msg.getCreateTime();
		endTime = msg.getEndTime();
		id = msg.getId();
		limitTime = msg.getLimitTime();
		name = msg.getName();
		sequence = msg.getSequence();
		status = msg.getStatus();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis == null ? null : basis.trim();
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}