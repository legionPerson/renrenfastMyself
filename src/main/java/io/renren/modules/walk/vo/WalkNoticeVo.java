package io.renren.modules.walk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WalkNoticeVo {

	private String id;

	private String title;

	private String content;

	private String userId;

	private String orgId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publishDate;

	private Date createTime;

}
