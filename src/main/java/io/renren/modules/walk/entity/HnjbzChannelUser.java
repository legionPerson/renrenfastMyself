package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_CHANNEL_USER")
public class HnjbzChannelUser {

  private String id;

  private String channelId;

  private String openid;

  private String unionid;

  private String nickname;

  private String headimgurl;

  private String bindingStatus;

  private String bindingPhone;

  private String status;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

}
