package io.renren.modules.walk.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WalkUserMapVo {

	private String uuid;

	private String userId;

	private String mapId;

	private String mapStartTime;

	private String status;

	private String doneStep;

}
