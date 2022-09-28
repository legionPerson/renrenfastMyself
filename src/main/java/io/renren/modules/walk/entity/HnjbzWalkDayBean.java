package io.renren.modules.walk.entity;

import java.util.Date;

public class HnjbzWalkDayBean {
	
    private String userId;

    private Long steps;

    private String stepDate;

    private String isExchange;

    private Date createTime;

    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    public String getStepDate() {
        return stepDate;
    }

    public void setStepDate(String stepDate) {
        this.stepDate = stepDate == null ? null : stepDate.trim();
    }

    public String getIsExchange() {
        return isExchange;
    }

    public void setIsExchange(String isExchange) {
        this.isExchange = isExchange;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}