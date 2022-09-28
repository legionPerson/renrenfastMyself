package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzWalkDay;
import io.renren.modules.walk.vo.WalkDayVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  步数表（日）Mapper 接口
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Mapper
public interface HnjbzWalkDayMapper extends BaseMapper<HnjbzWalkDay> {
    @Select("select USER_ID,SUM(steps) sum_steps,ceil(AVG(steps)) as avg_steps,(select steps from hnjbz_walk_day where step_date=to_char(sysdate,'yyyy-MM-dd') and USER_ID=#{userId}) as steps from hnjbz_walk_day where USER_ID=#{userId} GROUP BY USER_ID")
    public WalkDayVo getWalkCollection(String userId);
}
