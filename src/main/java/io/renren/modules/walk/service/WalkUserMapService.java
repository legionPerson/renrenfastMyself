package io.renren.modules.walk.service;

import io.renren.common.utils.R;
import io.renren.modules.walk.vo.WalkUserMapVo;

/**
 * 
 * @author gxp
 *
 */
public interface WalkUserMapService {

    /**
     * 获取用户地图信息
	 * @param walkUserMapVo
     * @return
     */
	R getUserMapInfo(WalkUserMapVo walkUserMapVo);

	/**
	 * 获取用户地图列表
	 * @param walkUserMapVo
	 * @return
	 */
	R getUserMapList(WalkUserMapVo walkUserMapVo);

	/**
	 * 保存用户地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	R saveUserMap(WalkUserMapVo walkUserMapVo);

	/**
	 * 更新用户地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	R updateUserMap(WalkUserMapVo walkUserMapVo);

	/**
	 * 计算用户地图信息
	 * @param walkUserMapVo
	 * @return
	 */
	R calculateUserMap(WalkUserMapVo walkUserMapVo);

}
