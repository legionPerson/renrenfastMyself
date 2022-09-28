package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzRightInfoMapper;
import io.renren.modules.walk.entity.HnjbzRightInfo;
import io.renren.modules.walk.service.WalkRightInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wp
 * @since 2022-06-07
 */
@Service
public class WalkRightInfoServiceImpl extends ServiceImpl<HnjbzRightInfoMapper, HnjbzRightInfo> implements WalkRightInfoService {

    @Override
    public R getRightInfoList() {
        List<HnjbzRightInfo> hnjbzRightInfos = baseMapper.selectList(null);
        return R.ok().put("data",hnjbzRightInfos);
    }
}
