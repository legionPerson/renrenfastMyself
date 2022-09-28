package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("HNJBZ_NODE")
public class HnjbzMapNodeBean {

  private String id;

  private String name;

  private String stepNumber;

  private String status;

  private String createById;

  private Date createTime;

  private String updateById;

  private Date updateTime;

  private List<HnjbzNode> hnjbzNode;

}
