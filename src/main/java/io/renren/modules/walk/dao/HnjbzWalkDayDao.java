package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.walk.entity.HnjbzWalkDayBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HnjbzWalkDayDao extends BaseMapper<HnjbzWalkDayBean> {
	
    
    IPage<HnjbzWalkDayBean> selectDataPageList(Page<Map<String, Object>> page,@Param("record")HnjbzWalkDayBean record);
	 
    
    List<HnjbzWalkDayBean> selectDataList (HnjbzWalkDayBean record);
	
    
	HnjbzWalkDayBean selectDto(HnjbzWalkDayBean record);
	

    int insertDto(HnjbzWalkDayBean record);


    int updateDto(HnjbzWalkDayBean record);
    
    
    int deleteDto(HnjbzWalkDayBean record);


    List<Map<String,String>> getChangRatio();

    /**
     * 计算当前用户生效地图步数
     * @param record
     * @return
     */
    int sumSteps(HnjbzWalkDayBean record);
    
}