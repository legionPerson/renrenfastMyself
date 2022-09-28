package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzSendFlowersInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  鲜花赠送记录Mapper 接口
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Mapper
public interface HnjbzSendFlowersInfoMapper extends BaseMapper<HnjbzSendFlowersInfo> {
    @Select("SELECT (send_count1 - send_count2) count FROM (SELECT sum( send_count) send_count1, max(accept_user_id) as accept_user_id FROM HNJBZ_SEND_FLOWERS_INFO WHERE HNJBZ_SEND_FLOWERS_INFO.accept_user_id = #{userId} ) a LEFT JOIN ( SELECT sum( send_count ) send_count2, max(SEND_USER_ID) as SEND_USER_ID FROM HNJBZ_SEND_FLOWERS_INFO WHERE HNJBZ_SEND_FLOWERS_INFO.SEND_USER_ID = #{userId} ) b ON a.accept_user_id = b.SEND_USER_ID")
    public Integer getFlowerCount(String userId);
    @Select("select sum(SEND_COUNT) from HNJBZ_SEND_FLOWERS_INFO where ACCEPT_USER_ID=#{userId}")
    public Integer getFlowerInfoCount(String userId);
    @Select("select sum(SEND_COUNT) from HNJBZ_SEND_FLOWERS_INFO where SEND_USER_ID=#{userId}")
    public Integer sendFlowerInfoCount(String userId);
}
