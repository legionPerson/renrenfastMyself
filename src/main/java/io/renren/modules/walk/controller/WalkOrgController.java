package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  组织表 前端控制器
 * </p>
 * @author wp
 * @since 2022-05-25
 */

@RestController
@RequestMapping("/walk-org")
public class WalkOrgController {
    @Autowired
    private WalkOrgService walkOrgService;
    /**
     * 获取所有组织
     * @return
     */
    @RequestMapping("getOrgList")
    @ResponseBody
    public R getOrgList(){
        return walkOrgService.getOrgList();
    }
}
