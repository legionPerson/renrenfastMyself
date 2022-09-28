package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.HnjbzMapMapper;
import io.renren.modules.walk.dao.HnjbzMedalMapper;
import io.renren.modules.walk.dao.HnjbzUserMedalMapper;
import io.renren.modules.walk.entity.HnjbzMap;
import io.renren.modules.walk.entity.HnjbzMedal;
import io.renren.modules.walk.entity.HnjbzUserMedal;
import io.renren.modules.walk.service.WalkUserMedalService;
import io.renren.modules.walk.vo.MedalVo;
import io.renren.modules.walk.vo.UserMedalVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  用户奖章服务实现类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Slf4j
@Service
public class WalkUserMedalServiceImpl extends ServiceImpl<HnjbzUserMedalMapper, HnjbzUserMedal> implements WalkUserMedalService {
    @Autowired
    private HnjbzMedalMapper medalMapper;
    @Autowired
    private HnjbzMapMapper mapMapper;

    @Override
    public R insertUserMedal(UserMedalVo userMedalVo) {
        HnjbzUserMedal hnjbzUserMedal = new HnjbzUserMedal();
        hnjbzUserMedal.setMapId(userMedalVo.getMapId());
        hnjbzUserMedal.setMedalId(userMedalVo.getId());
        hnjbzUserMedal.setUserId(userMedalVo.getUserId());
        hnjbzUserMedal.setId(StringUtils.get32Guid());
        baseMapper.insert(hnjbzUserMedal);
        String mapId = hnjbzUserMedal.getMapId();
        String medalId = hnjbzUserMedal.getMedalId();
        HnjbzMap hnjbzMap = mapMapper.selectById(mapId);
        if(!hnjbzMap.getStatus().equals("0")){
            hnjbzMap.setStatus("0");
            mapMapper.updateById(hnjbzMap);
        }
        HnjbzMedal hnjbzMedal = medalMapper.selectById(medalId);
        if(!hnjbzMedal.getStatus().equals("0")){
            hnjbzMedal.setStatus("0");
            medalMapper.updateById(hnjbzMedal);
        }
        return R.ok();
    }

    @Override
    public R getUserMedal(UserMedalVo userMedalVo) {
        List<MedalVo> userMedal = baseMapper.getUserMedal(userMedalVo.getUserId());
        //查询所有奖章列表
        QueryWrapper<HnjbzMedal> medalQueryWrapper = new QueryWrapper<HnjbzMedal>();
        medalQueryWrapper.eq("status", 0);
        List<HnjbzMedal> medalList = medalMapper.selectList(medalQueryWrapper);
        return R.ok().put("data",userMedal).put("medalList",medalList);
    }

}
