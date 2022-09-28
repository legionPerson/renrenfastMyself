package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzUserMedal;
import io.renren.modules.walk.vo.UserMedalVo;

/**
 * <p>
 *  用户奖章服务类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
public interface WalkUserMedalService extends IService<HnjbzUserMedal> {

    R getUserMedal(UserMedalVo userMedalVo);

    R insertUserMedal(UserMedalVo userMedalVo);
}
