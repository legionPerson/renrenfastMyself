package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkUserMedalService;
import io.renren.modules.walk.vo.UserMedalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  用户奖章前端控制器
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/walk-medal")
public class WalkUserMedalController {
    @Autowired
    private WalkUserMedalService walkUserMedalService;

    /**
     * 获取用户获取的奖章信息
     * @param userMedalVo
     * @return
     */
    @RequestMapping("getUserMedal")
    @ResponseBody
    public R getUserMedal(@RequestBody @Validated UserMedalVo userMedalVo){
        return walkUserMedalService.getUserMedal(userMedalVo);
    }

    /**
     * 新增用户奖章信息
     * @param userMedalVo
     * @return
     */
    @RequestMapping("insertUserMedal")
    @ResponseBody
    public R insertUserMedal(@RequestBody @Validated UserMedalVo userMedalVo){
        return walkUserMedalService.insertUserMedal(userMedalVo);
    }

}

