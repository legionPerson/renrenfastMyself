package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

//@KeySequence(value="HNJBZ_USER_GOLD_ACCOUNT_SEQUENCES",clazz=String.class)
@Data
@TableName("HNJBZ_USER_GOLD_ACCOUNT")
public class HnjbzUserGoldAccount {
  @TableId(value = "user_id", type = IdType.INPUT)
  private String userId;
  private String frontGoldAmount;
  private String laterGoldAmount;
  private String status;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
}
