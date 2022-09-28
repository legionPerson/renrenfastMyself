/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.walk.service;


import io.renren.common.ref.RetData;
import io.renren.common.utils.R;
import io.renren.modules.walk.entity.HnjbzWalkDayStatistic;

/**
 * 步数统计
 * 
 */
public interface WalkStatisticService  { 
 
    //每天统计
	RetData<Object> selectPersionDayWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	RetData<Object> selectOrganDayWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserOrganDayWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );
	
	RetData<Object> selectCompanyDayWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserCompanyDayWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	
	//每月统计
	RetData<Object> selectPersionMonthWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	RetData<Object> selectOrganMonthWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );
	
	RetData<Object> selectCompanyMonthWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserOrganMonthWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserCompanyMonthWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	
	//每季度统计
	RetData<Object> selectPersionQuarterWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	RetData<Object> selectOrganQuarterWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );
	
	RetData<Object> selectCompanyQuarterWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserOrganQuarterWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserCompanyQuarterWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	
	//总统计
	RetData<Object> selectPersionTotalWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	RetData<Object> selectOrganTatalWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );
	
	RetData<Object> selectCompanyTatalWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserOrganTatalWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

	R selectUserCompanyTatalWalkList(HnjbzWalkDayStatistic hnjbzWalkDayStatistic );

}
