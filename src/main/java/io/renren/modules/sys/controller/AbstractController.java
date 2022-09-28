/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserRoleService sysUserRoleService;

	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected List<Long> getUserRole(Long userId){
		return sysUserRoleService.queryRoleIdList(userId);
	}
	
	protected Map<String, String> getRoleOnOff() {
		Map<String, String> map = new HashMap<String, String>();
		SysUserEntity SysUserEntity =  getUser();
  	   if(SysUserEntity == null) {
  		   map.put("code", "-1");
  		   map.put("msg", "查询失败,当前用户信息获取失败，请重新登录后查询");
  		   return  map;
  	   }
  	   List<Long> roleIdList = getUserRole(SysUserEntity.getUserId());
  	   logger.info("SysUserEntity: "+SysUserEntity);
  	   if(roleIdList ==null || roleIdList.size() <= 0) {
  		   map.put("code", "-1");
		   map.put("msg", "查询失败,当前用户角色信息获取失败，请重新登录后查询");
		   return  map;
  	   }
  	   String flag = "1";//项目总监，项目经理
  	   for (Long roleId : roleIdList) {
			 if (roleId == 1 || roleId == 5) { // 优化20220517 业绩考核人员 子管理员 查询全部
				 flag = "0";
				 break;
			 }else if(roleId ==4){
				 flag = "4";
			 }
  	   }
  	   map.put("code", "0");
	   map.put("flag", flag);
	   map.put("userId", SysUserEntity.getUsername());
	   return  map;
	}
}
