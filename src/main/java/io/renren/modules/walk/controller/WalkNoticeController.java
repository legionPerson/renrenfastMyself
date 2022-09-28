package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkNoticeService;
import io.renren.modules.walk.vo.WalkNoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公告信息控制层
 * @author gxp
 *
 */
@RestController
@RequestMapping("/walk-notice")
public class WalkNoticeController {
	
	@Autowired
	private WalkNoticeService walkNoticeService;

	/**
	 * 获取公告信息
	 * @param walkNoticeVo
	 * @return
	 */
	@RequestMapping("/getNoticeInfo")
	@ResponseBody
	public R getNoticeInfo(@RequestBody @Validated WalkNoticeVo walkNoticeVo) {
		return  walkNoticeService.getNoticeInfo(walkNoticeVo);
	}

	/**
	 * 获取公告信息列表
	 * @param walkNoticeVo
	 * @return
	 */
	@RequestMapping("/getNoticeList")
	@ResponseBody
	public R getNoticeList(@RequestBody @Validated WalkNoticeVo walkNoticeVo) {
		return  walkNoticeService.getNoticeList(walkNoticeVo);
	}

	/**
	 * 保存公告信息
	 * @param walkNoticeVo
	 * @return
	 */
	@RequestMapping("/saveNotice")
	@ResponseBody
	public R saveNotice(@RequestBody @Validated WalkNoticeVo walkNoticeVo) {
		return  walkNoticeService.saveNotice(walkNoticeVo);
	}

	/**
	 * 更新公告信息
	 * @param walkNoticeVo
	 * @return
	 */
	@RequestMapping("/updateNotice")
	@ResponseBody
	public R updateNotice(@RequestBody @Validated WalkNoticeVo walkNoticeVo) {
		return  walkNoticeService.updateNotice(walkNoticeVo);
	}


}
