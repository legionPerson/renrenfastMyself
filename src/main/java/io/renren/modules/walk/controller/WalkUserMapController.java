package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkUserMapService;
import io.renren.modules.walk.vo.WalkUserMapVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地图信息控制层
 * @author gxp
 *
 */
@RestController
@RequestMapping("/walk-user-map")
public class WalkUserMapController {
	
	@Autowired
	private WalkUserMapService walkUserMapService;

	/**
	 * 获取地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	@RequestMapping("/getUserMapInfo")
	@ResponseBody
	public R getUserMapInfo(@RequestBody @Validated WalkUserMapVo walkUserMapVo) {
		return  walkUserMapService.getUserMapInfo(walkUserMapVo);
	}

	/**
	 * 获取地图信息列表
	 * @param walkUserMapVo
	 * @return
	 */
	@RequestMapping("/getUserMapList")
	@ResponseBody
	public R getUserMapList(@RequestBody @Validated WalkUserMapVo walkUserMapVo) {
		return  walkUserMapService.getUserMapList(walkUserMapVo);
	}

	/**
	 * 保存地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	@RequestMapping("/saveUserMap")
	@ResponseBody
	public R saveUserMap(@RequestBody @Validated WalkUserMapVo walkUserMapVo) {
		return  walkUserMapService.saveUserMap(walkUserMapVo);
	}

	/**
	 * 更新地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	@RequestMapping("/updateUserMap")
	@ResponseBody
	public R updateUserMap(@RequestBody @Validated WalkUserMapVo walkUserMapVo) {
		return  walkUserMapService.updateUserMap(walkUserMapVo);
	}

	/**
	 * 计算用户地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	@RequestMapping("/calculateUserMap")
	@ResponseBody
	public R calculateUserMap(@RequestBody @Validated WalkUserMapVo walkUserMapVo) {
		return  walkUserMapService.calculateUserMap(walkUserMapVo);
	}

}
