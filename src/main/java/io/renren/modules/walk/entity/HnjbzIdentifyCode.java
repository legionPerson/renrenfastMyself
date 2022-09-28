package io.renren.modules.walk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("HNJBZ_IDENTIFY_CODE")
public class HnjbzIdentifyCode {

  private String id;
  private String phone;
  private String code;
  private Date createTime;

}
