package io.renren.modules.walk.entity;

public class HnjbzWalkDayBeanReq  extends HnjbzWalkDayBean {
	
	private String code;
	
	private String encryptedData;
	
	private String iv;
    
	private String sessionKey;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
