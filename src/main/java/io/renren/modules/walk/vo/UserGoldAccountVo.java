package io.renren.modules.walk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UserGoldAccountVo {
    /**
     * 用户id
     */
    @NotBlank(message = "获取用户失败")
    private String userId;
    /**
     * 消耗工银消费金数量
     */
    private String userGoldAmount;
    /**
     * 数量
     */
    private int conAmount;

    /**
     * 权益id
     */
    private String rightId;
    /**
     * 权益类型 1.微信立减金 2.洗车卡
     */
    private String rightType;
    /**
     * 兑换权益金额
     */
    private String rightAmount;

    private String hnAppid;
    private String orderId;
    private String stock_id;
    private String openId;
}
