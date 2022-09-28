package io.renren.modules.walk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.walk.entity.HnjbzWalkMonthBean;

public interface HnjbzWalkMonthDao {
	  
    
    IPage<HnjbzWalkMonthBean> selectDataPageList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkMonthBean record);
	
    
    List<HnjbzWalkMonthBean> selectDataList (HnjbzWalkMonthBean record);
	
    
	HnjbzWalkMonthBean selectDto(HnjbzWalkMonthBean record);
	

    int insertDto(HnjbzWalkMonthBean record);


    int updateDto(HnjbzWalkMonthBean record);
    
    
    int deleteDto(HnjbzWalkMonthBean record);
    
}