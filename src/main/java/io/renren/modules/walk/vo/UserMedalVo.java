package io.renren.modules.walk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserMedalVo {
    /**
     * 用户id
     */
    @NotBlank(message = "获取用户失败")
    private String userId;
    /**
     * 奖章名称
     */
    private String name;

    /**
     * 奖章描述
     */
    private String describe;

    /**
     * 奖章点亮图标
     */
    private String lightIcon;


    /**
     * 关联地图id
     */
    private String mapId;

    /**
     * 奖章id
     */
    private String id;
}
