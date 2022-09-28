package io.renren.modules.walk.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WalkNodeVo {

	private String id;

	private String mapId;

	private String nodeImg;

	private String nodeType;

	private String nodeDescribe;

	private String nodeOrder;

	private String nodeCoord;

	private String nodeImgUrl;

	private String status;

	private String createById;

	private Date createTime;

	private String updateById;

	private Date updateTime;

}
