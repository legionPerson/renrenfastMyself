package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzWalkDayMapper;
import io.renren.modules.walk.entity.HnjbzWalkDay;
import io.renren.modules.walk.service.HnjbzWalkDayService;
import io.renren.modules.walk.vo.WalkDayVo;
import org.springframework.stereotype.Service;

@Service
public class HnjbzWalkDayServiceImpl extends ServiceImpl<HnjbzWalkDayMapper, HnjbzWalkDay> implements HnjbzWalkDayService {
    @Override
    public R getWalkDayList(WalkDayVo walkDayVo) {
        Page<HnjbzWalkDay> page = new Page<>(walkDayVo.getPage(),walkDayVo.getPageSize());
        QueryWrapper<HnjbzWalkDay> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",walkDayVo.getUserId()).orderByDesc("step_date");
        Page<HnjbzWalkDay> dayPage = baseMapper.selectPage(page, wrapper);
        return R.ok().put("data",dayPage);
    }

    @Override
    public R getWalkCollection(WalkDayVo walkDayVo) {
        WalkDayVo walkCollection = baseMapper.getWalkCollection(walkDayVo.getUserId());
        if (walkCollection==null){
            return R.ok().put("data",new WalkDayVo(0L,0L,0L));
        }
        walkCollection.setSteps(walkCollection.getSteps()==null?0L:walkCollection.getSteps());
        walkCollection.setAvgSteps(walkCollection.getAvgSteps()==null?0L:walkCollection.getAvgSteps());
        walkCollection.setSumSteps(walkCollection.getSumSteps()==null?0L:walkCollection.getSumSteps());
        return R.ok().put("data",walkCollection);
    }
}
