package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkRightConInfoService;
import io.renren.modules.walk.vo.RightConInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/walk-right")
public class WalkRightConInfoController {
    @Autowired
    private WalkRightConInfoService walkRightConInfoService;
    /**
     * 获取微信立减金兑换记录
     * @param rightConInfoVo
     * @return
     */
    @RequestMapping("getGoldExchangeList")
    @ResponseBody
    public R getGoldExchangeList(@RequestBody @Validated RightConInfoVo rightConInfoVo){
        return walkRightConInfoService.getGoldExchangeList(rightConInfoVo);
    }
    /**
     * 新增微信立减金兑换记录
     * @param rightConInfoVo
     * @return
     */
    @RequestMapping("insertExchange")
    @ResponseBody
    public R insertExchange(@RequestBody @Validated RightConInfoVo rightConInfoVo){
        return walkRightConInfoService.insertExchange(rightConInfoVo);
    }
}
