package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzGoldBudgetInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  工银消费金（金币）收支记录Mapper 接口
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Mapper
public interface HnjbzGoldBudgetInfoMapper extends BaseMapper<HnjbzGoldBudgetInfo> {
    @Select("select sum(budget_amount) from HNJBZ_GOLD_BUDGET_INFO where budget_flag=#{budgetFlag} and USER_ID=#{userId}")
    public Integer getGoldBudgetInCount(String userId,String budgetFlag);
    @Select("select sum(budget_amount) from HNJBZ_GOLD_BUDGET_INFO where budget_flag=#{budgetFlag} and send_user_id=#{userId}")
    public Integer sendGoldBudgetInCount(String userId,String budgetFlag);
}
