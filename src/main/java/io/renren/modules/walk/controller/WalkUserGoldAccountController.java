package io.renren.modules.walk.controller;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkUserGoldAccountService;
import io.renren.modules.walk.vo.UserGoldAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  用户消费金账户前端控制器
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/walk-gold")
public class WalkUserGoldAccountController {

    @Autowired
    private WalkUserGoldAccountService walkUserGoldAccountService;

    /**
     * 获取用户消费金余额
     * @param userGoldAccountVo
     * @return
     */
    @RequestMapping("getUserGoldAccount")
    @ResponseBody
    public R getUserGoldAccount(@RequestBody @Validated UserGoldAccountVo userGoldAccountVo){
        return walkUserGoldAccountService.getUserGoldAccount(userGoldAccountVo);
    }

    /**
     * 消费金兑换接口
     * @param userGoldAccountVo
     * @return
     */
    @RequestMapping("consumptionGold")
    @ResponseBody
    public R consumptionGold(@RequestBody @Validated UserGoldAccountVo userGoldAccountVo){
        return walkUserGoldAccountService.consumptionGold(userGoldAccountVo);
    }

}
