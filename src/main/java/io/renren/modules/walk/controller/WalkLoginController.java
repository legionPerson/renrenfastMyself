package io.renren.modules.walk.controller;

import io.renren.modules.walk.vo.WalkPhoneLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkLoginService;
import io.renren.modules.walk.vo.WalkLoginVo;

/**
 * 用户登录控制层
 * @author zln
 *
 */
@RestController
@RequestMapping("/walk-login")
public class WalkLoginController {
	
	@Autowired
	private WalkLoginService walkLoginService;

	/**
	 * 已登录过得用户openid校验登录
	 * @param walkLoginVo
	 * @return
	 */
	@RequestMapping("/loginIn")
	@ResponseBody
	public R loginIn(@RequestBody @Validated WalkLoginVo walkLoginVo) {
		return  walkLoginService.loginIn(walkLoginVo);
	}

	/**
	 * 白名单手机号验证登录
	 * @param walkPhoneLoginVo
	 * @return
	 */
	@RequestMapping("/phoneLoginIn")
	@ResponseBody
	public R phoneLoginIn(@RequestBody @Validated WalkPhoneLoginVo walkPhoneLoginVo) {
		return  walkLoginService.phoneLoginIn(walkPhoneLoginVo);
	}

	/**
	 * 生成手机验证码
	 * @param walkPhoneLoginVo
	 * @return
	 */
	@RequestMapping("/generateIdentifyCode")
	@ResponseBody
	public R generateIdentifyCode(@RequestBody @Validated WalkPhoneLoginVo walkPhoneLoginVo) {
		return  walkLoginService.generateIdentifyCode(walkPhoneLoginVo);
	}

}
