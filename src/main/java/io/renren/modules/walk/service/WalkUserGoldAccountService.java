package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzUserGoldAccount;
import io.renren.modules.walk.vo.GoldBudgetInfoVo;
import io.renren.modules.walk.vo.RightConInfoVo;
import io.renren.modules.walk.vo.UserGoldAccountVo;

/**
 * <p>
 *  用户消费金账户服务类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
public interface WalkUserGoldAccountService extends IService<HnjbzUserGoldAccount> {
    R getUserGoldAccount(UserGoldAccountVo userGoldAccountVo);

    R consumptionGold(UserGoldAccountVo userGoldAccountVo);
}
