package io.renren.modules.walk.service;

import io.renren.common.utils.R;
import io.renren.modules.walk.vo.WalkMapVo;

/**
 * 
 * @author gxp
 *
 */
public interface WalkMapService {

	/**
	 * 获取地图信息
	 * @param walkMapVo
	 * @return
	 */
	R getMapInfo(WalkMapVo walkMapVo);

	/**
	 * 获取地图列表
	 * @param walkMapVo
	 * @return
	 */
	R getMapList(WalkMapVo walkMapVo);

	/**
	 * 保存地图信息
	 * @param walkMapVo
	 * @return
	 */
	R saveMap(WalkMapVo walkMapVo);

	/**
	 * 更新地图信息
	 * @param walkMapVo
	 * @return
	 */
	R updateMap(WalkMapVo walkMapVo);

	/**
	 * 查询十八个地图机器节点信息
	 * @return
	 */
	R getAllMapList();

}
