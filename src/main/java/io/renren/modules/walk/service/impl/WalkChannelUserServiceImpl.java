package io.renren.modules.walk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzChannelUserMapper;
import io.renren.modules.walk.dao.HnjbzOrgMapper;
import io.renren.modules.walk.dao.HnjbzUserWhiteMapper;
import io.renren.modules.walk.entity.HnjbzChannelUser;
import io.renren.modules.walk.entity.HnjbzOrg;
import io.renren.modules.walk.entity.HnjbzUserWhite;
import io.renren.modules.walk.entity.dto.UserWhiteDto;
import io.renren.modules.walk.service.WalkChannelUserService;
import io.renren.modules.walk.vo.ChannelUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  用户渠道信息服务实现类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Slf4j
@Service
public class WalkChannelUserServiceImpl extends ServiceImpl<HnjbzChannelUserMapper, HnjbzChannelUser> implements WalkChannelUserService {
    @Autowired
    private HnjbzUserWhiteMapper hnjbzUserWhiteMapper;
    @Autowired
    private HnjbzOrgMapper hnjbzOrgMapper;

    @Override
    public R getUserDetails(ChannelUserVo channelUserVo) {
            QueryWrapper<HnjbzChannelUser> wrapper = new QueryWrapper<>();
            wrapper.eq("id", channelUserVo.getUserId());
            HnjbzChannelUser channelUser = baseMapper.selectOne(wrapper);
            if (channelUser == null) {
                return R.error("该用户不存在");
            }
            channelUserVo.setHeadimgurl(channelUser.getHeadimgurl());
            QueryWrapper<HnjbzUserWhite> userWhiteQueryWrapper = new QueryWrapper<>();
            userWhiteQueryWrapper.eq("phone", channelUser.getBindingPhone());
            HnjbzUserWhite hnjbzUserWhite = hnjbzUserWhiteMapper.selectOne(userWhiteQueryWrapper);
            if (hnjbzUserWhite != null) {
                String orgId = hnjbzUserWhite.getOrgId();
                channelUserVo.setName(hnjbzUserWhite.getName());
                channelUserVo.setAdmin(hnjbzUserWhite.getIsAdmin());
                channelUserVo.setBirthDate(hnjbzUserWhite.getBirthDate());
                if (orgId != null) {
                    channelUserVo.setOrgId(orgId);
                    HnjbzOrg hnjbzOrg = hnjbzOrgMapper.getBranchAndDepartment(orgId);
                    channelUserVo.setBranch(hnjbzOrg.getBranch());
                    channelUserVo.setDepartment(hnjbzOrg.getDepartment());
                }
            }
            return R.ok().put("data", channelUserVo);
    }

    @Override
    public R getUserBirthdayList(ChannelUserVo channelUserVo) {
            List<ChannelUserVo> userBirthdy = baseMapper.getUserBirthdy(channelUserVo.getUserId());
            List<ChannelUserVo> userBirthdys = new ArrayList<>();
            userBirthdy.forEach(e->{
                if (!e.getUserId().equals(channelUserVo.getUserId())){
                    userBirthdys.add(e);
                }
            });
            return R.ok().put("data", userBirthdys);
    }

    @Override
    public List<UserWhiteDto> exportExcelData() {
            List<HnjbzUserWhite> hnjbzUserWhites = hnjbzUserWhiteMapper.selectList(null);
            ArrayList<UserWhiteDto> userWhiteDtos = new ArrayList<>(hnjbzUserWhites.size());
            hnjbzUserWhites.forEach(e->{
                UserWhiteDto userWhiteDto = new UserWhiteDto();
                BeanUtils.copyProperties(e,userWhiteDto);
                userWhiteDtos.add(userWhiteDto);
            });
            return userWhiteDtos;
    }

    @Override
    public R getChannelUserInfo(ChannelUserVo channelUserVo) {
        return R.ok().put("channelUser",baseMapper.selectById(channelUserVo.getId()));
    }

    @Override
    public R updateChannelUser(ChannelUserVo channelUserVo) {
        HnjbzChannelUser hnjbzChannelUser = new HnjbzChannelUser();
        BeanUtils.copyProperties(channelUserVo,hnjbzChannelUser);
        hnjbzChannelUser.setId(channelUserVo.getUserId());
        int count = baseMapper.updateById(hnjbzChannelUser);
        return R.ok().put("count",count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateUserDetails(ChannelUserVo channelUserVo) {
        QueryWrapper<HnjbzChannelUser> wrapper = new QueryWrapper<>();
        HnjbzChannelUser channelUser = baseMapper.selectById(channelUserVo.getUserId());
        if (channelUserVo.getHeadimgurl()!=null){
            channelUser.setHeadimgurl(channelUser.getHeadimgurl());
        }
        baseMapper.updateById(channelUser);
        String BindingPhone=channelUser.getBindingPhone();
        QueryWrapper<HnjbzUserWhite> whiteQueryWrapper = new QueryWrapper<>();
        whiteQueryWrapper.eq("phone",BindingPhone);
        HnjbzUserWhite userWhite = hnjbzUserWhiteMapper.selectOne(whiteQueryWrapper);
        if(StringUtils.isNotEmpty(channelUserVo.getBirthDate())){
            userWhite.setBirthDate(channelUserVo.getBirthDate());
        }
        if (StringUtils.isNotEmpty(channelUserVo.getName())){
            userWhite.setName(channelUserVo.getName());
        }
        if (StringUtils.isNotEmpty(channelUserVo.getOrgId())){
            userWhite.setOrgId(channelUserVo.getOrgId());
        }
        hnjbzUserWhiteMapper.update(userWhite,whiteQueryWrapper);
        return R.ok();
    }

}