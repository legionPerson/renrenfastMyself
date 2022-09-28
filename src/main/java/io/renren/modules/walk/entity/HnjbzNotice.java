package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_NOTICE")
public class HnjbzNotice {

  private String id;

  private String title;

  private String content;

  private String userId;

  private String orgId;

  private Date publishDate;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

}
