package io.renren.modules.walk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.walk.entity.HnjbzWalkDayStatistic;

@Mapper
public interface HnjbzWalkStatisticDao {
	
	
	IPage<HnjbzWalkDayStatistic> selectPersionDayWalkList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayStatistic record);
	
	IPage<HnjbzWalkDayStatistic> selectOrganDayWalkList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayStatistic record);

	/**
	 * 当前用户所属部门排行
	 * @param record
	 * @return
	 */
	List<HnjbzWalkDayStatistic> selectOrganDayWalkList(@Param("record")HnjbzWalkDayStatistic record);
	
	IPage<HnjbzWalkDayStatistic> selectCompanyDayWalkList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayStatistic record);

	/**
	 * 当前用户所属单位排行
	 * @param record
	 * @return
	 */
	List<HnjbzWalkDayStatistic> selectCompanyDayWalkList(@Param("record")HnjbzWalkDayStatistic record);
	
	
	
    IPage<HnjbzWalkDayStatistic> selectPersionSummaryWalkList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayStatistic record);
	
	IPage<HnjbzWalkDayStatistic> selectOrganSummaryWalkList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayStatistic record);

	/**
	 * 当前用户所属部门排行
	 * @param record
	 * @return
	 */
	List<HnjbzWalkDayStatistic> selectOrganSummaryWalkList(@Param("record")HnjbzWalkDayStatistic record);
   
	IPage<HnjbzWalkDayStatistic> selectCompanySummaryWalkList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayStatistic record);

	/**
	 * 当前用户所属单位排行
	 * @param record
	 * @return
	 */
	List<HnjbzWalkDayStatistic> selectCompanySummaryWalkList(@Param("record")HnjbzWalkDayStatistic record);


	String selectUserDept( String openid );
    
    String selectUserCompany( String openid );
      
    String selectUserBranch( String openid );
    
    
}