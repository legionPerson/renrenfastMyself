package io.renren.modules.walk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzSendFlowersInfo;
import io.renren.modules.walk.vo.SendFlowersInfoVo;


/**
 * <p>
 *  鲜花赠送记录服务类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
public interface WalkSendFlowersInfoService extends IService<HnjbzSendFlowersInfo> {

    R sendFlowersInfoList(SendFlowersInfoVo sendFlowersInfoVo);

    R getFlowersInfoList(SendFlowersInfoVo sendFlowersInfoVo);

    R sendFlowers(SendFlowersInfoVo sendFlowersInfoVo);

    R getFlowerCount(SendFlowersInfoVo sendFlowersInfoVo);
}
