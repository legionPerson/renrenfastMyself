package io.renren.modules.walk.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WalkLoginVo {
	
	@NotBlank(message = "获取code失败")
	private String code;
	
//	@NotBlank(message = "iv不能为空")
	private String iv; 
	
//	@NotBlank(message = "encryptedData不能为空")
	private String encryptedData;
	
	@NotBlank(message = "渠道编码不能为空")
	private String channelId;
}
