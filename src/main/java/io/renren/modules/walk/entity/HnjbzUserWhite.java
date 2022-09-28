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
 * @since 2022-05-28
 */
@Data
@TableName("HNJBZ_USER_WHITE")
public class HnjbzUserWhite {

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 生日（YYYYMMDD）
     */
    @TableField("BIRTH_DATE")
    private String birthDate;

    /**
     * 是否单位管理员（0否，1是）
     */
    @TableField("IS_ADMIN")
    private String isAdmin;

    /**
     * 最小级别ID
     */
    @TableField("ORG_ID")
    private String orgId;

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
     * 0正常用户，1无效用户
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建人
     */
    @TableField("CREATE_BY_ID")
    private String createById;

}
