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
 * @since 2022-05-26
 */

@KeySequence(value="HNJBZ_RIGHT_CON_INFO_SEQUENCES",clazz=String.class)
@Data
@TableName("HNJBZ_RIGHT_CON_INFO")
public class HnjbzRightConInfo {

    /**
     * 记录ID
     */
    @TableId(value = "UUID", type = IdType.INPUT)
    private String uuid;

    /**
     * 对用消费金收支记录表ID
     */
    @TableField("GOLD_BUDGET_ID")
    private String goldBudgetId;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 兑换权益类型（1立减金 2洗车卷）
     */
    @TableField("CON_RIGHT_TYPE")
    private String conRightType;

    /**
     * 兑换权益ID
     */
    @TableField("CON_RIGHT_ID")
    private String conRightId;

    /**
     * 兑换数量，类似张数，比如2张洗车卷，2张5元代金券
     */
    @TableField("CON_AMOUNT")
    private String conAmount;

    /**
     * 单笔金额
     */
    @TableField("CON_SINGLE_MONEY")
    private String conSingleMoney;

    /**
     * 兑换价值总金额
     */
    @TableField("CON_GROSS_MONEY")
    private String conGrossMoney;

    /**
     * 使用消费金数量
     */
    @TableField("USER_GOLD_AMOUNT")
    private String userGoldAmount;

    /**
     * 状态 0有效 -1无效
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 权益名称
     */
    @TableField(exist = false)
    private String rightName;
    /**
     * 权益金额
     */
    @TableField(exist = false)
    private String rightAmount;

}
