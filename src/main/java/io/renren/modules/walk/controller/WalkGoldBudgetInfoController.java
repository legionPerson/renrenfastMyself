package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkGoldBudgetInService;
import io.renren.modules.walk.vo.GoldBudgetInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/walk-gold")
public class WalkGoldBudgetInfoController {

    @Autowired
    private WalkGoldBudgetInService walkGoldBudgetInService;
    /**
     * 获取用户工行消费金收支记录
     * @param goldBudgetInfoVo
     * @return
     */
    @RequestMapping("getGoldBudgetInfoList")
    @ResponseBody
    public R getGoldBudgetInfoList(@RequestBody @Validated GoldBudgetInfoVo goldBudgetInfoVo){
        return walkGoldBudgetInService.getGoldBudgetInfoList(goldBudgetInfoVo);
    }
    /**
     * 新增工银消费金记录
     * @param goldBudgetInfoVo
     * @return
     */
    @RequestMapping("sendGoldBudgetInfo")
    @ResponseBody
    public R insertGoldBudgetInfo(@RequestBody @Validated GoldBudgetInfoVo goldBudgetInfoVo){
        return walkGoldBudgetInService.sendGoldBudgetInfo(goldBudgetInfoVo);
    }
}
