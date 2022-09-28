package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzWellProduct;
import io.renren.modules.walk.vo.WellProductVo;


/**
 * <p>
 *  名优产品表服务类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
public interface WalkWellProductService extends IService<HnjbzWellProduct> {

    R getWellProductList(WellProductVo wellProductVo);

    R getWellProductById(String id);

    R insertWellProduct(WellProductVo wellProductVo);

    R updateWellProduct(WellProductVo wellProductVo);
}
