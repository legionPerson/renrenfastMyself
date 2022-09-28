package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.service.HnjbzWalkDayService;
import io.renren.modules.walk.vo.WalkDayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  步数表（日）前端控制器
 * </p>
 * 步数表（日）
 * @author wp
 * @since 2022-05-25
 */

@Controller
@RequestMapping("/walk-user")
public class HnjbzWalkDayController {
    @Autowired
    private HnjbzWalkDayService hnjbzWalkDayService;

    /**
     * 获取用户每日步数记录
     * @param walkDayVo
     * @return
     */
    @RequestMapping("getWalkDayList")
    @ResponseBody
    public R getWalkDayList(@RequestBody @Validated WalkDayVo walkDayVo){
        return hnjbzWalkDayService.getWalkDayList(walkDayVo);
    }
    /**
     * 获取用户步数集合
     * @param walkDayVo
     * @return
     */
    @RequestMapping("getWalkCollection")
    @ResponseBody
    public R getWalkCollection(@RequestBody @Validated WalkDayVo walkDayVo){
        return hnjbzWalkDayService.getWalkCollection(walkDayVo);
    }

}

