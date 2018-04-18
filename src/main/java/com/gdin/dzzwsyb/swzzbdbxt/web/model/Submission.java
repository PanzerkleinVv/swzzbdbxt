package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Date;

public class Submission {
    private String id;

    private String msgId;

    private Integer type;

    private String situation;

    private String reason;

    private String measure;

    private Long ownerId;

    private Integer superiorVerifyPassed;

    private Long superiorVerifiUserId;

    private Integer status;

    private Date sendTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation == null ? null : situation.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure == null ? null : measure.trim();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getSuperiorVerifyPassed() {
        return superiorVerifyPassed;
    }

    public void setSuperiorVerifyPassed(Integer superiorVerifyPassed) {
        this.superiorVerifyPassed = superiorVerifyPassed;
    }

    public Long getSuperiorVerifiUserId() {
        return superiorVerifiUserId;
    }

    public void setSuperiorVerifiUserId(Long superiorVerifiUserId) {
        this.superiorVerifiUserId = superiorVerifiUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}