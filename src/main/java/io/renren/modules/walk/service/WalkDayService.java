/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.walk.service;


import io.renren.common.ref.RetData;
import io.renren.modules.walk.entity.HnjbzWalkDayBeanReq;

/**
 * 步行
 *
 * 
 */
public interface WalkDayService  {


	RetData<Object> getSessionKey(HnjbzWalkDayBeanReq hnjbzWalkDayBeanReq) throws Exception;

	RetData<Object> getRunData(HnjbzWalkDayBeanReq hnjbzWalkDayBeanReq) throws Exception;

	void exchangeGoldBudget( );
	
	
	
}
