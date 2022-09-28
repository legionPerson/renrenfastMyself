package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wp
 * @since 2022-05-28
 */
@KeySequence(value="HNJBZ_GOLD_BUDGET_INFO_SEQUENCES",clazz=String.class)
@Data
@TableName("HNJBZ_GOLD_BUDGET_INFO")
public class HnjbzGoldBudgetInfo  {

    /**
     * Uuid唯一记录id
     */
    @TableId(value = "UUID", type = IdType.INPUT)
    private String uuid;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 收支数量
     */
    @TableField("BUDGET_AMOUNT")
    private String budgetAmount;

    /**
     * 收支方式1、收 2支
     */
    @TableField("BUDGET_FLAG")
    private String budgetFlag;

    /**
     * 收入方式（1、系统每日发放 2、好友赠送 3、任务奖励）
     */
    @TableField("INCOME_TYPE")
    private String incomeType;

    /**
     * 赠送人员ID （非必填，你要赠送给别人时填写自己的ID）
     */
    @TableField("SEND_USER_ID")
    private String sendUserId;

    /**
     * 支出方式（1、权益兑换 2赠送好友）
     */
    @TableField("DISBURSE_TYPE")
    private String disburseType;

    /**
     * 状态 0有效 -1无效
     */
    @TableField("STATUS")
    private String status;

    /**
     * 收支时间
     */
    @TableField(value = "GRANT_DATE",fill = FieldFill.INSERT)
    private Date grantDate;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String name;
    /**
     * 头像
     */
    @TableField(exist = false)
    private String headimgurl;

}
