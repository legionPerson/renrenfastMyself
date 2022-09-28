package io.renren.modules.walk.service;

import io.renren.common.utils.R;
import io.renren.modules.walk.vo.WalkNodeVo;

/**
 * 
 * @author gxp
 *
 */
public interface WalkNodeService {

	/**
	 * 获取地图信息
	 * @param walkNodeVo
	 * @return
	 */
	R getNodeInfo(WalkNodeVo walkNodeVo);

	/**
	 * 获取地图列表
	 * @param walkNodeVo
	 * @return
	 */
	R getNodeList(WalkNodeVo walkNodeVo);

	/**
	 * 保存地图信息
	 * @param walkNodeVo
	 * @return
	 */
	R saveNode(WalkNodeVo walkNodeVo);

	/**
	 * 更新地图信息
	 * @param walkNodeVo
	 * @return
	 */
	R updateNode(WalkNodeVo walkNodeVo);


}
