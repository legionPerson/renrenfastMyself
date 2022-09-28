package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzOrgMapper;
import io.renren.modules.walk.entity.HnjbzOrg;
import io.renren.modules.walk.service.WalkOrgService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalkOrgServiceImpl extends ServiceImpl<HnjbzOrgMapper, HnjbzOrg> implements WalkOrgService {
    @Override
    public R getOrgList() {
        QueryWrapper<HnjbzOrg> wrapper = new QueryWrapper<>();
        wrapper.select("id","name").eq("parent_id","4");
        List<HnjbzOrg> hnjbzOrgs = baseMapper.selectList(wrapper);
        return R.ok().put("data",hnjbzOrgs);
    }
}
