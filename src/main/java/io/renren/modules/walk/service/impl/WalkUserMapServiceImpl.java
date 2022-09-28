package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.*;
import io.renren.modules.walk.entity.*;
import io.renren.modules.walk.service.WalkUserMapService;
import io.renren.modules.walk.vo.WalkUserMapVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户地图
 * 
 * @author gxp
 *
 */
@Slf4j
@Service
public class WalkUserMapServiceImpl implements WalkUserMapService {

	@Autowired
	private HnjbzMapMapper hnjbzMapMapper;

	@Autowired
	private HnjbzMedalMapper hnjbzMedalMapper;

	@Autowired
	private HnjbzUserMedalMapper hnjbzUserMedalMapper;

	@Autowired
	private HnjbzUserMapMapper hnjbzUserMapMapper;

	@Autowired
	private HnjbzWalkDayDao hnjbzWalkDayDao;

	@Autowired
	private HnjbzNodeMapper hnjbzNodeMapper;

	@Override
	public R getUserMapInfo(WalkUserMapVo walkUserMapVo) {
		QueryWrapper<HnjbzUserMap> userMapQueryWrapper = new QueryWrapper<HnjbzUserMap>();
		if(!StringUtils.isEmpty(walkUserMapVo.getUuid())){
			userMapQueryWrapper.eq("uuid", walkUserMapVo.getUuid());
		}
		HnjbzUserMap walkUserMap = hnjbzUserMapMapper.selectOne(userMapQueryWrapper);
		return R.ok().put("walkUserMap",walkUserMap);
	}

	@Override
	public R getUserMapList(WalkUserMapVo walkUserMapVo) {
		QueryWrapper<HnjbzUserMap> userMapQueryWrapper = new QueryWrapper<HnjbzUserMap>();
		if(!StringUtils.isEmpty(walkUserMapVo.getUserId())){
			userMapQueryWrapper.eq("user_id", walkUserMapVo.getUserId());
		}
		if(!StringUtils.isEmpty(walkUserMapVo.getMapId())){
			userMapQueryWrapper.eq("map_id", walkUserMapVo.getMapId());
		}
		if(!StringUtils.isEmpty(walkUserMapVo.getStatus())){
			userMapQueryWrapper.eq("status", walkUserMapVo.getStatus());
		}
		List<HnjbzUserMap> mapList = hnjbzUserMapMapper.selectList(userMapQueryWrapper);
		return R.ok().put("mapList",mapList);
	}

	@Override
	public R saveUserMap(WalkUserMapVo walkUserMapVo) {
		//查询当前用户是否有生效地图，存在生效地图，则更改为不生效，最新地图生效
		QueryWrapper<HnjbzUserMap> userMapQueryWrapper = new QueryWrapper<HnjbzUserMap>();
		if(!StringUtils.isEmpty(walkUserMapVo.getUserId())){
			userMapQueryWrapper.eq("user_id", walkUserMapVo.getUserId());
		}
		if(!StringUtils.isEmpty(walkUserMapVo.getStatus())){
			userMapQueryWrapper.eq("status", 1);
		}
		List<HnjbzUserMap> mapList = hnjbzUserMapMapper.selectList(userMapQueryWrapper);
		//更新所有已生效地图为未生效
		for(HnjbzUserMap oldUserMap:mapList){
			//查询地图总步数
			HnjbzMap hnjbzMap = hnjbzMapMapper.selectById(oldUserMap.getMapId());
			if(Integer.valueOf(oldUserMap.getDoneStep()) <Integer.valueOf(hnjbzMap.getStepNumber())){
				//完成步数小于地图步数则地图未完成
				return R.error("存在未完成的地图，不能选择新地图!");
			}
			oldUserMap.setStatus("0");
			hnjbzUserMapMapper.updateById(oldUserMap);
		}
		//新增用户地图
		HnjbzUserMap hnjbzUserMap = new HnjbzUserMap();
		BeanUtils.copyProperties(walkUserMapVo,hnjbzUserMap);
		hnjbzUserMap.setId(StringUtils.get32Guid());
		hnjbzUserMap.setMapStartTime(DateFormatUtils.format(new Date(),"YYYY-MM-dd HH:mm:ss"));
		int count = hnjbzUserMapMapper.insert(hnjbzUserMap);
		return R.ok().put("count",count);
	}

	@Override
	public R updateUserMap(WalkUserMapVo walkUserMapVo) {
		HnjbzUserMap hnjbzUserMap = new HnjbzUserMap();
		BeanUtils.copyProperties(walkUserMapVo,hnjbzUserMap);
		//查询当前用户是否有生效地图，存在生效地图，则更改为不生效，最新地图生效
		QueryWrapper<HnjbzUserMap> userMapQueryWrapper = new QueryWrapper<HnjbzUserMap>();
		if(!StringUtils.isEmpty(walkUserMapVo.getUserId())){
			userMapQueryWrapper.eq("user_id", walkUserMapVo.getUserId());
		}
		if(!StringUtils.isEmpty(walkUserMapVo.getStatus())){
			userMapQueryWrapper.eq("status", 1);
		}
		List<HnjbzUserMap> mapList = hnjbzUserMapMapper.selectList(userMapQueryWrapper);
		//更新所有已生效地图为未生效
		for(HnjbzUserMap oldUserMap:mapList){
			//查询地图总步数
			HnjbzMap hnjbzMap = hnjbzMapMapper.selectById(oldUserMap.getMapId());
			if(Integer.valueOf(oldUserMap.getDoneStep()) <Integer.valueOf(hnjbzMap.getStepNumber())){
				//完成步数小于地图步数则地图未完成
				return R.error("存在未完成的地图，不能选择新地图!");
			}
			oldUserMap.setStatus("0");
			hnjbzUserMapMapper.updateById(oldUserMap);
		}
		int count = hnjbzUserMapMapper.updateById(hnjbzUserMap);
		return R.ok().put("count",count);
	}

	@Override
	public R calculateUserMap(WalkUserMapVo walkUserMapVo) {
		//查询当前用户地图信息，地图状态为生效
		QueryWrapper<HnjbzUserMap> userMapQueryWrapper = new QueryWrapper<HnjbzUserMap>();
		userMapQueryWrapper.eq("user_id", walkUserMapVo.getUserId());
		userMapQueryWrapper.eq("status", 1);//地图生效
		HnjbzUserMap hnjbzUserMap = hnjbzUserMapMapper.selectOne(userMapQueryWrapper);
		List<HnjbzMapNodeBean> mapList = hnjbzMapMapper.getAllMapList();
		if(hnjbzUserMap==null){
			return R.ok().put("mapList",mapList);
		}
		//查询地图开始时间至今的所有步数
		String startTime = hnjbzUserMap.getMapStartTime();
		HnjbzWalkDayBean hnjbzWalkDayBean = new HnjbzWalkDayBean();
		hnjbzWalkDayBean.setStepDate(DateFormatUtils.format(DateUtils.parseDate(startTime,new String[]{"yyyy-MM-dd"}),"yyyy-MM-dd"));//用户地图开始时间
		hnjbzWalkDayBean.setUserId(walkUserMapVo.getUserId());//用户编号
		int sumSteps = hnjbzWalkDayDao.sumSteps(hnjbzWalkDayBean);
		//更新用户已完成步数
		hnjbzUserMap.setDoneStep(String.valueOf(sumSteps));
		hnjbzUserMapMapper.updateById(hnjbzUserMap);

		//查询用户地图节点，判断当前用户所属节点位置
		QueryWrapper<HnjbzNode> nodeQueryWrapper = new QueryWrapper<HnjbzNode>();
		nodeQueryWrapper.eq("map_id", hnjbzUserMap.getMapId());
		nodeQueryWrapper.eq("status", 1);//节点生效
		List<HnjbzNode> nodeList = hnjbzNodeMapper.selectList(nodeQueryWrapper);
		//计算所在当前地图节点数（地图总步数平均分配节点步数，当前所走步数求余数）
		int nodeSize = nodeList.size()>0?nodeList.size():1;
		//查询地图信息
		HnjbzMap hnjbzMap = hnjbzMapMapper.selectById(hnjbzUserMap.getMapId());
		//到达节点数
		int reach = sumSteps/(Integer.valueOf(hnjbzMap.getStepNumber()).intValue() /nodeSize);
		int leftSteps = Integer.valueOf(hnjbzMap.getStepNumber()).intValue()-sumSteps;
		Map result = new HashMap<>();
		result.put("reach",reach>nodeSize?nodeSize:reach);
		result.put("hnjbzNode",nodeList);
		result.put("name",hnjbzMap.getName());
		result.put("id",hnjbzMap.getId());
		result.put("currSteps",sumSteps);
		result.put("mapTotalSteps",hnjbzMap.getStepNumber());
		result.put("leftSteps",leftSteps<0?0:leftSteps);
		//地图到达节点，新增勋章
		if(leftSteps<=0){
			//查询是否存在当前用户地图奖章
			QueryWrapper<HnjbzUserMedal> hnjbzUserMedalQueryWrapper = new QueryWrapper<HnjbzUserMedal>();
			hnjbzUserMedalQueryWrapper.eq("user_id",hnjbzUserMap.getUserId());
			hnjbzUserMedalQueryWrapper.eq("map_id",hnjbzUserMap.getMapId());
			hnjbzUserMedalQueryWrapper.eq("user_map_id",hnjbzUserMap.getId());
			HnjbzUserMedal hnjbzUserMedal = hnjbzUserMedalMapper.selectOne(hnjbzUserMedalQueryWrapper);
			//当前用户地图走完后不存在奖章，则新增奖章信息
			if(hnjbzUserMedal==null){
				//查询用户奖章
				QueryWrapper<HnjbzMedal> hnjbzMedalQueryWrapper = new QueryWrapper<HnjbzMedal>();
				hnjbzMedalQueryWrapper.eq("map_id",hnjbzUserMap.getMapId());
				HnjbzMedal hnjbzMedal = hnjbzMedalMapper.selectOne(hnjbzMedalQueryWrapper);
				hnjbzUserMedal = new HnjbzUserMedal();
				hnjbzUserMedal.setId(StringUtils.get32Guid());
				hnjbzUserMedal.setUserId(hnjbzUserMap.getUserId());
				hnjbzUserMedal.setMapId(hnjbzUserMap.getMapId());
				hnjbzUserMedal.setMedalId(hnjbzMedal.getId());
				hnjbzUserMedal.setUserMapId(hnjbzUserMap.getId());
				int count = hnjbzUserMedalMapper.insert(hnjbzUserMedal);
			}
		}
		return R.ok().put("result",result).put("mapList",mapList);
	}
}
