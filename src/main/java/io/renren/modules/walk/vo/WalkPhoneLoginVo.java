package io.renren.modules.walk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WalkPhoneLoginVo {

	private String openid;

	private String code;
	
	@NotBlank(message = "手机号不能为空")
	private String phone;
}
