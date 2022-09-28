package io.renren.modules.walk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.walk.entity.HnjbzMap;
import io.renren.modules.walk.entity.HnjbzMapNodeBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地图信息表 Mapper 接口
 * @author gxp
 *
 */
@Mapper
public interface HnjbzMapMapper extends BaseMapper<HnjbzMap>{

    List<HnjbzMapNodeBean> getAllMapList();
}
