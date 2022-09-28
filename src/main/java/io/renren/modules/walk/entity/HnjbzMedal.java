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
 * @since 2022-05-25
 */
@Data
@TableName("HNJBZ_MEDAL")
public class HnjbzMedal {

    /**
     * 奖章id
     */
    @TableId("ID")
    private String id;

    /**
     * 关联地图id
     */
    @TableField("MAP_ID")
    private String mapId;

    /**
     * 奖章名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 奖章描述
     */
    @TableField("DESCRIBE")
    private String describe;

    /**
     * 奖章点亮图标
     */
    @TableField("LIGHT_ICON")
    private String lightIcon;

    /**
     * 奖章未点亮图标
     */
    @TableField("DARK_ICON")
    private String darkIcon;

    /**
     * 0有效，-1无效
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建人id
     */
    @TableField("CREATE_BY_ID")
    private Long createById;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改人id
     */
    @TableField("UPDATE_BY_ID")
    private String updateById;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}
