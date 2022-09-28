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
@TableName("HNJBZ_ORG")
public class HnjbzOrg {

    /**
     * 机构ID
     */
    @TableId("ID")
    private String id;

    /**
     * 机构名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 级别 1分行 2支行3网点 4单位 5部门
     */
    @TableField("ORG_LEVEL")
    private String orgLevel;

    /**
     * 单位描述
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 是否启用
     */
    @TableField("IS_ENABLE")
    private String isEnable;

    /**
     * 上级单位
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 规则对应
     */
    @TableField("RULE_ID")
    private String ruleId;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 组织内码
     */
    @TableField("INNER_CODE")
    private String innerCode;
    /**
     * 分行
     */
    @TableField(exist = false)
    private String branch;
    /**
     * 单位
     */
    @TableField(exist = false)
    private String company;
    /**
     * 部门
     */
    @TableField(exist = false)
    private String department;
}
