package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzIdentifyCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 登录验证码 Mapper 接口
 * @author gxp
 *
 */
@Mapper
public interface HnjbzIdentifyCodeMapper extends BaseMapper<HnjbzIdentifyCode>{

    @Select("select * from (select id,code,phone,create_time from HNJBZ_IDENTIFY_CODE where phone = '18736016812' order by create_time desc ) where rownum = 1")
    HnjbzIdentifyCode getMaxTime(String phone);
}
