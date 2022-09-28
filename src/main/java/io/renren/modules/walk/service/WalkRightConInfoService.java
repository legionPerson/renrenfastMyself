package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzRightConInfo;
import io.renren.modules.walk.vo.RightConInfoVo;

public interface WalkRightConInfoService extends IService<HnjbzRightConInfo> {
    R getGoldExchangeList(RightConInfoVo rightConInfoVo);

    R insertExchange(RightConInfoVo rightConInfoVo);
}
