package com.gdin.dzzwsyb.swzzbdbxt.web.model;

/**
 * 督办事项-协办人关系表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class MsgCoSponsor {
    private String id;

	private String msgId;

	private Long roleId;

	private Integer isSigned;

	private Integer isAssigned;

    private String content;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public MsgCoSponsor(String id, String msgId, Long roleId, Integer isSigned, Integer isAssigned, String content,
			Integer status) {
		super();
		this.id = id;
		this.msgId = msgId;
		this.roleId = roleId;
		this.isSigned = isSigned;
		this.isAssigned = isAssigned;
		this.content = content;
		this.status = status;
	}
    
}