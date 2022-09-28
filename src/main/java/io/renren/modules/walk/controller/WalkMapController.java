package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkMapService;
import io.renren.modules.walk.vo.WalkMapVo;
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
@RequestMapping("/walk-map")
public class WalkMapController {
	
	@Autowired
	private WalkMapService walkMapService;

	/**
	 * 获取地图信息
	 * @param walkMapVo
	 * @return
	 */
	@RequestMapping("/getMapInfo")
	@ResponseBody
	public R getMapInfo(@RequestBody @Validated WalkMapVo walkMapVo) {
		return  walkMapService.getMapInfo(walkMapVo);
	}

	/**
	 * 获取地图信息列表
	 * @param walkMapVo
	 * @return
	 */
	@RequestMapping("/getMapList")
	@ResponseBody
	public R getMapList(@RequestBody @Validated WalkMapVo walkMapVo) {
		return  walkMapService.getMapList(walkMapVo);
	}

	/**
	 * 保存地图信息
	 * @param walkMapVo
	 * @return
	 */
	@RequestMapping("/saveMap")
	@ResponseBody
	public R saveMap(@RequestBody @Validated WalkMapVo walkMapVo) {
		return  walkMapService.saveMap(walkMapVo);
	}

	/**
	 * 更新地图信息
	 * @param walkMapVo
	 * @return
	 */
	@RequestMapping("/updateMap")
	@ResponseBody
	public R updateMap(@RequestBody @Validated WalkMapVo walkMapVo) {
		return  walkMapService.updateMap(walkMapVo);
	}

	/**
	 * 查询所有地图及节点信息
	 * @param walkMapVo
	 * @return
	 */
	@RequestMapping("/getAllMapList")
	@ResponseBody
	public R getAllMapList(@RequestBody @Validated WalkMapVo walkMapVo) {
		return  walkMapService.getAllMapList();
	}

}
