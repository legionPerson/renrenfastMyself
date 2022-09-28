package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2022-05-25
 */
@Data
@TableName("HNJBZ_USER_MEDAL")
public class HnjbzUserMedal {

    /**
     * uuid
     */
    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 关联地图id
     */
    @TableField("MAP_ID")
    private String mapId;

    /**
     * 奖章id
     */
    @TableField("MEDAL_ID")
    private String medalId;

    /**
     * 用户地图id
     */
    @TableField("USER_MAP_ID")
    private String userMapId;

    /**
     * 领取时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
