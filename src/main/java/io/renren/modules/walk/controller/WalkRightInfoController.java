package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkRightInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wp
 * @since 2022-06-07
 */
@Controller
@RequestMapping("/walk-right")
public class WalkRightInfoController {
    @Autowired
    private WalkRightInfoService walkRightInfoService;
    /**
     * 查询送出的鲜花记录
     * @return
     */
    @RequestMapping("getRightInfoList")
    @ResponseBody
    public R getRightInfoList(){
        return walkRightInfoService.getRightInfoList();
    }
}

