package io.renren.modules.walk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.renren.modules.walk.entity.HnjbzWalkQuarterBean;

public interface HnjbzWalkQuarterDao {
	
     
    IPage<HnjbzWalkQuarterBean> selectDataPageList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkQuarterBean record);
	
    
    List<HnjbzWalkQuarterBean> selectDataList (HnjbzWalkQuarterBean record);
	
    
	HnjbzWalkQuarterBean selectDto(HnjbzWalkQuarterBean record);
	

    int insertDto(HnjbzWalkQuarterBean record);


    int updateDto(HnjbzWalkQuarterBean record);
    
    
    int deleteDto(HnjbzWalkQuarterBean record);
    
}