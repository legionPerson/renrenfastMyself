package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.HnjbzMapMapper;
import io.renren.modules.walk.entity.HnjbzMap;
import io.renren.modules.walk.service.WalkMapService;
import io.renren.modules.walk.vo.WalkMapVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地图
 * 
 * @author gxp
 *
 */
@Slf4j
@Service
public class WalkMapServiceImpl implements WalkMapService {

	@Autowired
	private HnjbzMapMapper hnjbzMapMapper;

	@Override
	public R getMapInfo(WalkMapVo walkMapVo) {
		HnjbzMap hnjbzMap = hnjbzMapMapper.selectById(walkMapVo.getId());
		return R.ok().put("hnjbzMap",hnjbzMap);
	}

	@Override
	public R getMapList(WalkMapVo walkMapVo) {
		QueryWrapper<HnjbzMap> mapQueryWrapper = new QueryWrapper<HnjbzMap>();
		if(!StringUtils.isEmpty(walkMapVo.getName())){
			mapQueryWrapper.eq("NAME", walkMapVo.getName());
		}
		if(!StringUtils.isEmpty(walkMapVo.getStatus())){
			mapQueryWrapper.eq("status", walkMapVo.getStatus());
		}
		List<HnjbzMap> mapList = hnjbzMapMapper.selectList(mapQueryWrapper);
		return R.ok().put("mapList",mapList);
	}

	@Override
	public R saveMap(WalkMapVo walkMapVo) {
		HnjbzMap hnjbzMap = new HnjbzMap();
		BeanUtils.copyProperties(walkMapVo,hnjbzMap);
		hnjbzMap.setId(StringUtils.get32Guid());
		int count = hnjbzMapMapper.insert(hnjbzMap);
		return R.ok().put("count",count);
	}

	@Override
	public R updateMap(WalkMapVo walkMapVo) {
		HnjbzMap hnjbzMap = new HnjbzMap();
		BeanUtils.copyProperties(walkMapVo,hnjbzMap);
		int count = hnjbzMapMapper.updateById(hnjbzMap);
		return R.ok().put("count",count);
	}

	@Override
	public R getAllMapList() {
		return R.ok().put("list",hnjbzMapMapper.getAllMapList());
	}
}
