package io.renren.modules.walk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RightConInfoVo {
    /**
     * 用户ID
     */
    @NotBlank(message = "获取用户失败")
    private String userId;

    /**
     * 兑换权益类型（1立减金 2洗车卷）
     */
    private String conRightType;
    /**
     * 兑换权益ID
     */
    private String conRightId;

    /**
     * 兑换数量，类似张数，比如2张洗车卷，2张5元代金券
     */
    private String conAmount;

    /**
     * 单笔金额
     */
    private String conSingleMoney;

    /**
     * 使用消费金数量
     */
    private String userGoldAmount;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页记录数
     */
    private int pageSize;
}
