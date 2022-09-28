package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.HnjbzRightConInfoMapper;
import io.renren.modules.walk.dao.HnjbzRightInfoMapper;
import io.renren.modules.walk.entity.HnjbzRightConInfo;
import io.renren.modules.walk.entity.HnjbzRightInfo;
import io.renren.modules.walk.service.WalkRightConInfoService;
import io.renren.modules.walk.vo.RightConInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalkRightConInfoServiceImpl extends ServiceImpl<HnjbzRightConInfoMapper, HnjbzRightConInfo> implements WalkRightConInfoService {
    @Autowired
    private HnjbzRightInfoMapper rightInfoMapper;
    @Override
    public R getGoldExchangeList(RightConInfoVo rightConInfoVo) {
        Page<HnjbzRightConInfo> page = new Page<>(rightConInfoVo.getPage(),rightConInfoVo.getPageSize());
        QueryWrapper<HnjbzRightConInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",rightConInfoVo.getUserId())
                .eq("con_right_type",rightConInfoVo.getConRightType())
                .orderByDesc("update_time");
        Page<HnjbzRightConInfo> infoPage = baseMapper.selectPage(page, wrapper);
        infoPage.getRecords().forEach(e->{
            QueryWrapper<HnjbzRightInfo> queryWrapper = new QueryWrapper<>();
            String rightId = e.getConRightId();
            queryWrapper.eq("right_id",rightId);
            HnjbzRightInfo hnjbzRightInfo = rightInfoMapper.selectOne(queryWrapper);
            String rightName = hnjbzRightInfo.getRightName();
            String rightAmount = hnjbzRightInfo.getRightAmount();
            e.setRightName(rightName);
            e.setRightAmount(rightAmount);
        });
        return R.ok().put("data",infoPage);
    }

    @Override
    public R insertExchange(RightConInfoVo rightConInfoVo) {
        HnjbzRightConInfo hnjbzRightConInfo = new HnjbzRightConInfo();
        hnjbzRightConInfo.setUuid(StringUtils.get32Guid());
        hnjbzRightConInfo.setUserId(rightConInfoVo.getUserId());
        hnjbzRightConInfo.setConRightType(rightConInfoVo.getConRightType());
        hnjbzRightConInfo.setConRightId(rightConInfoVo.getConRightId());
        hnjbzRightConInfo.setConAmount(rightConInfoVo.getConAmount());
        hnjbzRightConInfo.setUserGoldAmount(rightConInfoVo.getUserGoldAmount());
        int count = baseMapper.insert(hnjbzRightConInfo);
        return R.ok().put("data",count);
    }
}
