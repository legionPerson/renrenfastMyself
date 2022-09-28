package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzChannelUser;
import io.renren.modules.walk.vo.ChannelUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 渠道用户信息表 Mapper 接口
 * @author zln
 *
 */
@Mapper
public interface HnjbzChannelUserMapper extends BaseMapper<HnjbzChannelUser>{
    @Select("select huw.name,hcu.HEADIMGURL from HNJBZ_CHANNEL_USER hcu left join HNJBZ_USER_WHITE huw on hcu.BINDING_PHONE=huw.PHONE where hcu.id=#{userId}")
    public ChannelUserVo getUserName(String userId);
    @Select("select hcu.id as user_id,hcu.HEADIMGURL,a.name,a.BIRTH_DATE from HNJBZ_CHANNEL_USER hcu inner JOIN  (select name,phone,BIRTH_DATE from HNJBZ_USER_WHITE where org_id in(select id from HNJBZ_ORG where PARENT_ID=(select PARENT_ID from HNJBZ_ORG where id=(select ORG_ID from HNJBZ_USER_WHITE where phone=(select BINDING_PHONE from HNJBZ_CHANNEL_USER where id=#{userId})))) and HNJBZ_USER_WHITE.BIRTH_DATE=to_char(sysdate,'yyyy-MM-dd'))a on hcu.BINDING_PHONE=a.PHONE")
    public List<ChannelUserVo> getUserBirthdy(String userId);
}
