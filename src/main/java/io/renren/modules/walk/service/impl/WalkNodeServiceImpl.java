package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.HnjbzNodeMapper;
import io.renren.modules.walk.entity.HnjbzNode;
import io.renren.modules.walk.service.WalkNodeService;
import io.renren.modules.walk.vo.WalkNodeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地图节点
 * 
 * @author gxp
 *
 */
@Slf4j
@Service
public class WalkNodeServiceImpl implements WalkNodeService {

	@Autowired
	private HnjbzNodeMapper hnjbzNodeMapper;

	@Override
	public R getNodeInfo(WalkNodeVo walkNodeVo) {
		HnjbzNode hnjbzNode = hnjbzNodeMapper.selectById(walkNodeVo.getId());
		return R.ok().put("hnjbzNode",hnjbzNode);
	}

	@Override
	public R getNodeList(WalkNodeVo walkNodeVo) {
		QueryWrapper<HnjbzNode> nodeQueryWrapper = new QueryWrapper<HnjbzNode>();
		if(!StringUtils.isEmpty(walkNodeVo.getMapId())){
			nodeQueryWrapper.eq("map_id", walkNodeVo.getMapId());
		}
		if(!StringUtils.isEmpty(walkNodeVo.getStatus())){
			nodeQueryWrapper.eq("status", walkNodeVo.getStatus());
		}
		List<HnjbzNode> mapList = hnjbzNodeMapper.selectList(nodeQueryWrapper);
		return R.ok().put("mapList",mapList);
	}

	@Override
	public R saveNode(WalkNodeVo walkNodeVo) {
		HnjbzNode hnjbzNode = new HnjbzNode();
		BeanUtils.copyProperties(walkNodeVo,hnjbzNode);
		hnjbzNode.setId(StringUtils.get32Guid());
		int count = hnjbzNodeMapper.insert(hnjbzNode);
		return R.ok().put("count",count);
	}

	@Override
	public R updateNode(WalkNodeVo walkNodeVo) {
		HnjbzNode hnjbzNode = new HnjbzNode();
		BeanUtils.copyProperties(walkNodeVo,hnjbzNode);
		int count = hnjbzNodeMapper.updateById(hnjbzNode);
		return R.ok().put("count",count);
	}

}
