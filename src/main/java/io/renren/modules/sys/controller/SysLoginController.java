/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import io.renren.common.ref.RetData;
import io.renren.common.utils.R;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.form.SysLoginForm;
import io.renren.modules.sys.service.SysCaptchaService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public RetData<Object> login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return new RetData<>(-1, "验证码不正确");
		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return new RetData<>(-1, "账号或密码不正确");
		}
		//账号锁定
		if(user.getStatus() == 0){
			return new RetData<>(-1, "账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		RetData<Object> retData = sysUserTokenService.createToken(user.getUserId());
		Map<String, Object> retMap = (Map<String, Object>) retData.getData();

		//用户信息放入session
		ShiroUtils.setSessionAttribute("userInfo",user);
		//用户最高角色层级放入session
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
		String userHighestRole = null;
		if(roleIdList.size()>0){
			roleIdList = roleIdList.stream().sorted(Long::compareTo).collect(Collectors.toList());
			userHighestRole = String.valueOf(roleIdList.get(0));
		}
		ShiroUtils.setSessionAttribute("userHighestRole",userHighestRole);
		//用户信息
		retMap.put("userInfo",user);
		//用户最高角色层级
		retMap.put("userHighestRole",userHighestRole);
		return retData;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}

}
