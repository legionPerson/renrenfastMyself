package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_MAP")
public class HnjbzMap {

  private String id;

  private String name;

  private String stepNumber;

  private String status;

  private String createById;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  private String updateById;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

}
