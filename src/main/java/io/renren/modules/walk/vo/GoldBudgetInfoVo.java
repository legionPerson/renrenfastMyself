package io.renren.modules.walk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldBudgetInfoVo {
    /**
     * 用户id
     */
    @NotBlank(message = "获取用户失败")
    private String userId;
    /**
     * 收支数量
     */
    private String budgetAmount;
    /**
     * 收支状态 1收 2支
     */
    private String budgetFlag;
    /**
     * 收入方式
     */
    private String incomeType;
    /**
     * 接收人id
     */
    private String sendUserId;
    /**
     * 支出方式
     */
    private String disburseType;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页记录数
     */
    private int pageSize;

}
