package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzChannelUserMapper;
import io.renren.modules.walk.dao.HnjbzSendFlowersInfoMapper;
import io.renren.modules.walk.entity.HnjbzSendFlowersInfo;
import io.renren.modules.walk.service.WalkSendFlowersInfoService;
import io.renren.modules.walk.vo.ChannelUserVo;
import io.renren.modules.walk.vo.SendFlowersInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 *  鲜花赠送记录服务实现类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Slf4j
@Service
public class WalkSendFlowersInfoServiceImpl extends ServiceImpl<HnjbzSendFlowersInfoMapper, HnjbzSendFlowersInfo> implements WalkSendFlowersInfoService {

    @Autowired
    private HnjbzChannelUserMapper hnjbzChannelUserMapper;

    @Override
    public R sendFlowersInfoList(SendFlowersInfoVo sendFlowersInfoVo) {
        Page<HnjbzSendFlowersInfo> page = new Page<>(sendFlowersInfoVo.getPage(),sendFlowersInfoVo.getPageSize());
        QueryWrapper<HnjbzSendFlowersInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("send_user_id",sendFlowersInfoVo.getUserId()).orderByDesc("send_date");
        Page<HnjbzSendFlowersInfo> infoPage = baseMapper.selectPage(page, wrapper);
        Integer count = baseMapper.sendFlowerInfoCount(sendFlowersInfoVo.getUserId());
        for (HnjbzSendFlowersInfo record : infoPage.getRecords()) {
            ChannelUserVo userVo = hnjbzChannelUserMapper.getUserName(record.getAcceptUserId());
            record.setName(userVo.getName());
            record.setHeadimgurl(userVo.getHeadimgurl());
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("records",infoPage.getRecords());
        map.put("current",infoPage.getCurrent());
        map.put("size",infoPage.getSize());
        map.put("total",infoPage.getTotal());
        map.put("count",count==null?"0":count);
        return R.ok().put("data",map);
    }

    @Override
    public R getFlowersInfoList(SendFlowersInfoVo sendFlowersInfoVo) {
        Page<HnjbzSendFlowersInfo> page = new Page<>(sendFlowersInfoVo.getPage(),sendFlowersInfoVo.getPageSize());
        QueryWrapper<HnjbzSendFlowersInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("accept_user_id",sendFlowersInfoVo.getUserId()).orderByDesc("send_date");
        Page<HnjbzSendFlowersInfo> infoPage = baseMapper.selectPage(page, wrapper);
        Integer count = baseMapper.getFlowerInfoCount(sendFlowersInfoVo.getUserId());
        for (HnjbzSendFlowersInfo record : infoPage.getRecords()) {
            ChannelUserVo userVo = hnjbzChannelUserMapper.getUserName(record.getSendUserId());
            record.setName(userVo.getName());
            record.setHeadimgurl(userVo.getHeadimgurl());
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("records",infoPage.getRecords());
        map.put("current",infoPage.getCurrent());
        map.put("size",infoPage.getSize());
        map.put("total",infoPage.getTotal());
        map.put("count",count==null?"0":count);
        return R.ok().put("data",map);
    }

    @Override
    public R getFlowerCount(SendFlowersInfoVo sendFlowersInfoVo){
            Integer count = baseMapper.getFlowerInfoCount(sendFlowersInfoVo.getUserId());
            return R.ok().put("data",count==null?"0":count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R sendFlowers(SendFlowersInfoVo sendFlowersInfoVo) {
        HnjbzSendFlowersInfo hnjbzSendFlowersInfo = new HnjbzSendFlowersInfo();
        hnjbzSendFlowersInfo.setAcceptUserId(sendFlowersInfoVo.getAcceptUserId());
        hnjbzSendFlowersInfo.setSendCount(String.valueOf(sendFlowersInfoVo.getSendCount()));
        hnjbzSendFlowersInfo.setSendUserId(sendFlowersInfoVo.getUserId());
        int count = baseMapper.insert(hnjbzSendFlowersInfo);
        return R.ok().put("data",count);
    }

}
