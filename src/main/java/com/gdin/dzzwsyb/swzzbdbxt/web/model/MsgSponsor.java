package com.gdin.dzzwsyb.swzzbdbxt.web.model;

/**
 * 督办事项-承办人关系表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class MsgSponsor {
	private Long id;

	private String msgId;

	private Long roleId;

	private Integer isSigned;

	private Integer isAssigned;

    private String content;

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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(Integer isSigned) {
		this.isSigned = isSigned;
	}

	public Integer getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Integer isAssigned) {
		this.isAssigned = isAssigned;
	}
}