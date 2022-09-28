package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Data
@TableName("HNJBZ_WALK_DAY")
public class HnjbzWalkDay {

    /**
     * 32
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 步数
     */
    @TableField("STEPS")
    private Long steps;

    /**
     * 步数日期yyyy-mm-dd
     */
    @TableField("STEP_DATE")
    private String stepDate;

    /**
     * 步数日期yyyy-mm-dd
     */
    @TableField("IS_EXCHANGE")
    private String isExchange;

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

}
