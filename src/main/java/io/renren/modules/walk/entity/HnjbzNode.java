package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_NODE")
public class HnjbzNode {

  private String id;

  private String name;

  private String mapId;

  private String nodeImg;

  private String nodeType;

  private String nodeDescribe;

  private String nodeOrder;

  private String nodeCoord;

  private String nodeImgUrl;

  private String status;

  private String createById;

  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  private String updateById;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

}
