package io.renren.modules.walk.controller;


import io.renren.common.utils.R;
import io.renren.modules.walk.service.WalkWellProductService;
import io.renren.modules.walk.vo.WellProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  名优产品前端控制器
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/walk-product")
public class WalkWellProductController {
    @Autowired
    private WalkWellProductService walkWellProductService;

    /**
     * 获取所有名优商品
     * @return
     */
    @RequestMapping("getWellProductList")
    @ResponseBody
    public R getWellProductList(@RequestBody @Validated WellProductVo wellProductVo){
        return walkWellProductService.getWellProductList(wellProductVo);
    }

    /**
     * 根据id获取名优商品详情
     * @param id
     * @return
     */
    @RequestMapping("getWellProductById")
    @ResponseBody
    public R getWellProductById(@RequestParam String id){
        return walkWellProductService.getWellProductById(id);
    }

    /**
     * 新增名优产品
     * @param wellProductVo
     * @return
     */
    @RequestMapping("insertWellProduct")
    @ResponseBody
    public R insertWellProduct(@RequestBody @Validated WellProductVo wellProductVo){
        return walkWellProductService.insertWellProduct(wellProductVo);
    }
    /**
     * 修改名优产品
     * @param wellProductVo
     * @return
     */
    @RequestMapping("updateWellProduct")
    @ResponseBody
    public R updateWellProduct(@RequestBody @Validated WellProductVo wellProductVo){
        return walkWellProductService.updateWellProduct(wellProductVo);
    }
}

