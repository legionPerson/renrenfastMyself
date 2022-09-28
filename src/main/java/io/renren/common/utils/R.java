/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;

	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		return params;
	}

	public Object getParam(String key){
		return params.get(key);
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		if (params == null) {
			params = new HashMap<>();
		}
		params.put(key,value);
		return this;
	}

	@Override
	public String toString() {
		try{
			JSONObject json = new JSONObject();
			json.put("code", code);
			json.put("message", msg);

			if (params == null) {
				return json.toString();
			}

			Set<String> keys = params.keySet();

			for (String key : keys) {
				json.put(key, params.get(key));
			}

			return json.toString();
		}catch(Exception e){
			return "";
		}

	}
}
