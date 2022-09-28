package io.renren.modules.walk.entity;

import java.util.Date;

public class HnjbzWalkQuarterBean {
	
    private String id;

    private String userId;

    private String quarterSteps;

    private String quarter;

    private String year;

    private Date cteateTime;

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

    public String getQuarterSteps() {
        return quarterSteps;
    }

    public void setQuarterSteps(String quarterSteps) {
        this.quarterSteps = quarterSteps == null ? null : quarterSteps.trim();
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public Date getCteateTime() {
        return cteateTime;
    }

    public void setCteateTime(Date cteateTime) {
        this.cteateTime = cteateTime;
    }
}