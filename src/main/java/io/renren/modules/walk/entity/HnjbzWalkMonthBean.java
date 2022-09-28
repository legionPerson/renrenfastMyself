package io.renren.modules.walk.entity;

import java.util.Date;

public class HnjbzWalkMonthBean {

    private String id;

    private String userId;

    private String monthSteps;

    private String stepMonth;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getMonthSteps() {
        return monthSteps;
    }

    public void setMonthSteps(String monthSteps) {
        this.monthSteps = monthSteps == null ? null : monthSteps.trim();
    }

    public String getStepMonth() {
        return stepMonth;
    }

    public void setStepMonth(String stepMonth) {
        this.stepMonth = stepMonth == null ? null : stepMonth.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}