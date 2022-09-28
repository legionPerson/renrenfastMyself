package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wp
 * @since 2022-06-07
 */
@Data
@TableName("HNJBZ_RIGHT_INFO")
public class HnjbzRightInfo  {

    /**
     * 权益ID
     */
    @TableId("RIGHT_ID")
    private String rightId;

    /**
     * 权益名称
     */
    @TableField("RIGHT_NAME")
    private String rightName;

    /**
     * 1.微信立减金 2.洗车卡
     */
    @TableField("RIGHT_TYPE")
    private String rightType;

    /**
     * 权益金额（分）
     */
    @TableField("RIGHT_AMOUNT")
    private String rightAmount;

    /**
     * 权益描述
     */
    @TableField("RIGHT_DESC")
    private String rightDesc;

    /**
     * 应用ID
     */
    @TableField("APP_ID")
    private String appId;

    /**
     * 状态 0有效 -1无效
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 权益特有参数(json格式)
     */
    @TableField("APP_PAR_AM")
    private String appParAm;

    /**
     * 消耗工银消费金数量
     */
    @TableField("USE_GOLD_AMOUNT")
    private String userGoldAmount;

}
