package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzChannelUser;
import io.renren.modules.walk.entity.dto.UserWhiteDto;
import io.renren.modules.walk.vo.ChannelUserVo;

import java.util.List;

/**
 * <p>
 *  用户渠道信息服务类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
public interface WalkChannelUserService extends IService<HnjbzChannelUser> {

    R getUserDetails(ChannelUserVo channelUserVo);

    R getUserBirthdayList(ChannelUserVo channelUserVo);


    R updateUserDetails(ChannelUserVo channelUserVo);

    List<UserWhiteDto> exportExcelData();

    R getChannelUserInfo(ChannelUserVo channelUserVo);

    R updateChannelUser(ChannelUserVo channelUserVo);
}
