package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzGoldBudgetInfo;
import io.renren.modules.walk.vo.GoldBudgetInfoVo;

public interface WalkGoldBudgetInService  extends IService<HnjbzGoldBudgetInfo> {
    R getGoldBudgetInfoList(GoldBudgetInfoVo goldBudgetInfoVo);

    R sendGoldBudgetInfo(GoldBudgetInfoVo goldBudgetInfoVo);
}
