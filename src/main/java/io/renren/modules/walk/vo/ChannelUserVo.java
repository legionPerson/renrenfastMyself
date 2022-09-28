package io.renren.modules.walk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ChannelUserVo {
    /**
     * 用户id
     */
    @NotBlank(message = "获取用户失败")
    private String userId;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户头像
     */
    private String headimgurl;
    /**
     * 分行
     */
    private String branch;
    /**
     * 部门
     */
    private String department;
    /**
     * 是否管理员
     */
    private String admin;
    /**
     * 生日
     */
    private String birthDate;
    /**
     * 组织id
     */
    private String orgId;


    /**
     * 渠道用户信息
     */
    private String id;

    private String channelId;

    private String openid;

    private String unionid;

    private String nickname;

    private String bindingStatus;

    private String bindingPhone;

    private String status;

    private Date createTime;

    private Date updateTime;
}
