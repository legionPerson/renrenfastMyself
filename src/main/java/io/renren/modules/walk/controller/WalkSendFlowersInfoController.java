package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkSendFlowersInfoService;
import io.renren.modules.walk.vo.SendFlowersInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  鲜花赠送记录前端控制器
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/walk-flower")
public class WalkSendFlowersInfoController {

    @Autowired
    private WalkSendFlowersInfoService walkSendFlowersInfoService;

    /**
     * 查询送出的鲜花记录
     * @param sendFlowersInfoVo
     * @return
     */
    @RequestMapping("sendFlowersInfoList")
    @ResponseBody
    public R sendFlowersInfoList(@RequestBody @Validated SendFlowersInfoVo sendFlowersInfoVo){
        return walkSendFlowersInfoService.sendFlowersInfoList(sendFlowersInfoVo);
    }

    /**
     * 查询收到的鲜花记录
     * @param sendFlowersInfoVo
     * @return
     */
    @RequestMapping("getFlowersInfoList")
    @ResponseBody
    public R getFlowersInfoList(@RequestBody @Validated SendFlowersInfoVo sendFlowersInfoVo){
        return walkSendFlowersInfoService.getFlowersInfoList(sendFlowersInfoVo);
    }

    /**
     * 查询当前用户拥有的鲜花数量
     * @param sendFlowersInfoVo
     * @return
     */
    @RequestMapping("getUserFlowersCount")
    @ResponseBody
    public R getUserFlowersCount(@RequestBody @Validated SendFlowersInfoVo sendFlowersInfoVo){
        return walkSendFlowersInfoService.getFlowerCount(sendFlowersInfoVo);
    }

    /**
     * 赠送鲜花
     * @param sendFlowersInfoVo
     * @return
     */
    @RequestMapping("sendFlowers")
    @ResponseBody
    public R sendFlowers(@RequestBody @Validated SendFlowersInfoVo sendFlowersInfoVo){
        return walkSendFlowersInfoService.sendFlowers(sendFlowersInfoVo);
    }

}

