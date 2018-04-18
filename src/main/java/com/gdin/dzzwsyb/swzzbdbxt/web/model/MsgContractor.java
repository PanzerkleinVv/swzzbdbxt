package com.gdin.dzzwsyb.swzzbdbxt.web.model;

/**
 * 督办事项-承办人关系表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class MsgContractor {
	private Long id;

	private String msgId;

	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId == null ? null : msgId.trim();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}