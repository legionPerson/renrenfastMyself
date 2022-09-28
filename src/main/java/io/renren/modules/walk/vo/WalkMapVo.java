package io.renren.modules.walk.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WalkMapVo {

	private String id;

	private String name;

	private String stepNumber;

	private String status;

	private String createById;

	private Date createTime;

	private String updateById;

	private Date updateTime;

}
