package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("HNJBZ_SEND_FLOWERS_INFO")
public class HnjbzSendFlowersInfo {

    /**
     * 赠送人id
     */
    @TableField("send_user_id")
    private String sendUserId;

    /**
     * 接收人id（个人理解则被发送的用户应该是绑定的用户）
     */
    @TableField("ACCEPT_USER_ID")
    private String acceptUserId;

    /**
     * 赠送数量
     */
    @TableField("SEND_COUNT")
    private String sendCount;

    /**
     * 赠送时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date sendDate;

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String name;
    /**
     * 头像
     */
    @TableField(exist = false)
    private String headimgurl;

}
