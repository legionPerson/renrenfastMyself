package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzWalkDay;
import io.renren.modules.walk.vo.WalkDayVo;

public interface HnjbzWalkDayService extends IService<HnjbzWalkDay> {
    R getWalkDayList(WalkDayVo walkDayVo);

    R getWalkCollection(WalkDayVo walkDayVo);
}
