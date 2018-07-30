package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Date;

public class Log {
    private String id;

    private Long userId;

    private String targetId;

    private Date logTime;

    private String content;
    
    public Log() {
    	
    }
    
    public Log(String id, Long userId, String targetId, String content) {
    	this.id = id;
    	this.userId = userId;
    	this.targetId = targetId;
    	this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}