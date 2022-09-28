package io.renren.modules.walk.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("HNJBZ_CHANNEL")
public class HnjbzChannel {
	
	private String id;

	private String name;

	private String appid;

	private String appsecret;

	private String status;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
