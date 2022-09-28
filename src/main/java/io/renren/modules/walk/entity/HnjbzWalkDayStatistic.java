package io.renren.modules.walk.entity;

public class HnjbzWalkDayStatistic  extends HnjbzWalkDayBean {
	
	private String openid;
	
	private String userName;
	
	private String orgId;
	
	private String orgName;
	
	private String companyId;
	
	private String companyName;
	
	private String branchId;
	
	private String stepDateBegin;
	
	private String stepDateEnd;

	private String headimgurl;
	
    private int page;
	
	private int limit;

	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getStepDateBegin() {
		return stepDateBegin;
	}

	public void setStepDateBegin(String stepDateBegin) {
		this.stepDateBegin = stepDateBegin;
	}

	public String getStepDateEnd() {
		return stepDateEnd;
	}

	public void setStepDateEnd(String stepDateEnd) {
		this.stepDateEnd = stepDateEnd;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
}
