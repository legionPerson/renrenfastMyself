package io.renren.modules.walk.service;

import io.renren.common.utils.R;
import io.renren.modules.walk.vo.WalkNoticeVo;

/**
 * 
 * @author gxp
 *
 */
public interface WalkNoticeService {

	/**
	 * 获取地图信息
	 * @param walkNoticeVo
	 * @return
	 */
	R getNoticeInfo(WalkNoticeVo walkNoticeVo);

	/**
	 * 获取地图列表
	 * @param walkNoticeVo
	 * @return
	 */
	R getNoticeList(WalkNoticeVo walkNoticeVo);

	/**
	 * 保存地图信息
	 * @param walkNoticeVo
	 * @return
	 */
	R saveNotice(WalkNoticeVo walkNoticeVo);

	/**
	 * 更新地图信息
	 * @param walkNoticeVo
	 * @return
	 */
	R updateNotice(WalkNoticeVo walkNoticeVo);

}
