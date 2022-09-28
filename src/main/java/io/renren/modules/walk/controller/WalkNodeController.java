package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkNodeService;
import io.renren.modules.walk.vo.WalkNodeVo;
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
@RequestMapping("/walk-node")
public class WalkNodeController {
	
	@Autowired
	private WalkNodeService walkNodeService;

    /**
     * 地图节点信息
     * @param walkNodeVo
     * @return
     */
    @RequestMapping("/getNodeInfo")
    @ResponseBody
    public R getNodeInfo(@RequestBody @Validated WalkNodeVo walkNodeVo) {
        return  walkNodeService.getNodeInfo(walkNodeVo);
    }
    /**
     * 地图节点信息列表
     * @param walkNodeVo
     * @return
     */
    @RequestMapping("/getNodeList")
    @ResponseBody
    public R getNodeList(@RequestBody @Validated WalkNodeVo walkNodeVo) {
        return  walkNodeService.getNodeList(walkNodeVo);
    }
	/**
	 * 保存地图节点信息
	 * @param walkNodeVo
	 * @return
	 */
	@RequestMapping("/saveNode")
	@ResponseBody
	public R saveNode(@RequestBody @Validated WalkNodeVo walkNodeVo) {
		return  walkNodeService.saveNode(walkNodeVo);
	}

	/**
	 * 更新地图节点信息
	 * @param walkNodeVo
	 * @return
	 */
	@RequestMapping("/updateNode")
	@ResponseBody
	public R updateNode(@RequestBody @Validated WalkNodeVo walkNodeVo) {
		return  walkNodeService.updateNode(walkNodeVo);
	}


}
