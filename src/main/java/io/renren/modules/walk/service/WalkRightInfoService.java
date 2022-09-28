package io.renren.modules.walk.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzRightInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wp
 * @since 2022-06-07
 */
public interface WalkRightInfoService extends IService<HnjbzRightInfo> {

    R getRightInfoList();
}
