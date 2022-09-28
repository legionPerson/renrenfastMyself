package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzUserMedal;
import io.renren.modules.walk.vo.MedalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */

@Mapper
public interface HnjbzUserMedalMapper extends BaseMapper<HnjbzUserMedal> {

    @Select("select a.STATUS,a.LIGHT_ICON,a.DARK_ICON,a.MAP_ID,a.MAP_NAME,a.MEDAL_ID,a.MEDAL_NAME,b.count from (select distinct hum.MAP_ID,hum.MEDAL_ID,hmd.name as medal_name,hm.name as map_name,hmd.STATUS,hmd.LIGHT_ICON,hmd.DARK_ICON from HNJBZ_USER_MEDAL hum left join HNJBZ_MEDAL hmd on hum.medal_id=hmd.id left join HNJBZ_MAP hm on hum.map_id=hm.id where hum.USER_ID=#{userId}) a left join (select count(MEDAL_ID) as count,MEDAL_ID from HNJBZ_USER_MEDAL where USER_ID=#{userId} group by MEDAL_ID) b on a.MEDAL_ID=b.MEDAL_ID")
    public List<MedalVo> getUserMedal(String userId);

}
