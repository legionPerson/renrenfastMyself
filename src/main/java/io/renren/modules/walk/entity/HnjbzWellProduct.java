package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_WELL_PRODUCT")
public class HnjbzWellProduct {
  @TableId(value = "id", type = IdType.INPUT)
  private String id;
  private String name;
  private String url;
  private String describe;
  private String img;
  private String status;
  private String createById;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  private String updateById;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
}
