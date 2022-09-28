/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.ref;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author scc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetData<T> {
	/**
	 * 返回编码
	 */
	@JsonProperty(index = 1)
	private Integer code;
	/**
	 * 返回类型
	 */
	@JsonProperty(index = 2)
	private String msg;
	/**
	 * 返回实体
	 */
	@JsonProperty(index = 3)
	private T data;

	public RetData(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static RetData<Object> ok() {
		return new RetData<Object>(
				BaseResultStatus.SUCCESS.getCode(),
				BaseResultStatus.SUCCESS.getMessage());
	}

	public static RetData<Object> ng(){
		return new RetData<Object>(
				BaseResultStatus.ERROR.getCode(),
				BaseResultStatus.ERROR.getMessage());
	}

}
