package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_USER_MAP")
public class HnjbzUserMap {

    private String id;

    private String userId;

    private String mapId;

    private String mapStartTime;

    private String status;
    /**
     * 完成步数
     */
    private String doneStep;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
