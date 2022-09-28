package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.HnjbzChannelUserMapper;
import io.renren.modules.walk.dao.HnjbzNoticeMapper;
import io.renren.modules.walk.dao.HnjbzOrgMapper;
import io.renren.modules.walk.dao.HnjbzUserWhiteMapper;
import io.renren.modules.walk.entity.HnjbzChannelUser;
import io.renren.modules.walk.entity.HnjbzNotice;
import io.renren.modules.walk.entity.HnjbzOrg;
import io.renren.modules.walk.entity.HnjbzUserWhite;
import io.renren.modules.walk.service.WalkNoticeService;
import io.renren.modules.walk.vo.WalkNoticeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 公告
 * 
 * @author gxp
 *
 */
@Slf4j
@Service
public class WalkNoticeServiceImpl implements WalkNoticeService {

	@Autowired
	private HnjbzNoticeMapper hnjbzNoticeMapper;

	@Autowired
	private HnjbzChannelUserMapper hnjbzChannelUserMapper;

	@Autowired
	private HnjbzUserWhiteMapper hnjbzUserWhiteMapper;

	@Autowired
	private HnjbzOrgMapper hnjbzOrgMapper;

	@Override
	public R getNoticeInfo(WalkNoticeVo walkNoticeVo) {
		HnjbzNotice hnjbzNotice = hnjbzNoticeMapper.selectById(walkNoticeVo.getId());
		return R.ok().put("hnjbzNotice",hnjbzNotice);
	}

	@Override
	public R getNoticeList(WalkNoticeVo walkNoticeVo) {
		//根据传递的userId查询用户所属分行及总行
		HnjbzChannelUser hnjbzChannelUser = hnjbzChannelUserMapper.selectById(walkNoticeVo.getUserId());
		if(hnjbzChannelUser==null){
			return R.error("用户不存在!");
		}
		//手机号查询白名单信息
		QueryWrapper<HnjbzUserWhite> userWhiteQueryWrapper = new QueryWrapper<HnjbzUserWhite>();
		userWhiteQueryWrapper.eq("phone", hnjbzChannelUser.getBindingPhone());
		HnjbzUserWhite hnjbzUserWhite = hnjbzUserWhiteMapper.selectOne(userWhiteQueryWrapper);
		if(hnjbzUserWhite==null){
			return R.error("白名单信息不存在，公告获取失败！");
		}
		//获取用户所属组织列表
		List<String> orgList = getOrgList(hnjbzUserWhite.getOrgId());
		//查询用户所属组织公告信息
		QueryWrapper<HnjbzNotice> noticeQueryWrapper = new QueryWrapper<HnjbzNotice>();
		noticeQueryWrapper.in("org_id", orgList);
		List<HnjbzNotice> mapList = hnjbzNoticeMapper.selectList(noticeQueryWrapper);
		return R.ok().put("mapList",mapList);
	}

	@Override
	public R saveNotice(WalkNoticeVo walkNoticeVo) {
		HnjbzNotice hnjbzNotice = new HnjbzNotice();
		BeanUtils.copyProperties(walkNoticeVo,hnjbzNotice);
		int count = hnjbzNoticeMapper.insert(hnjbzNotice);
		return R.ok().put("count",count);
	}

	@Override
	public R updateNotice(WalkNoticeVo walkNoticeVo) {
		HnjbzNotice hnjbzNotice = new HnjbzNotice();
		BeanUtils.copyProperties(walkNoticeVo,hnjbzNotice);
		int count = hnjbzNoticeMapper.updateById(hnjbzNotice);
		return R.ok().put("count",count);
	}


	public List<String> getOrgList(String orgId){
		HnjbzOrg hnjbzOrg = hnjbzOrgMapper.selectById(orgId);
		List<String> orgList = Arrays.asList(hnjbzOrg.getInnerCode().split(","));
		return orgList;
	}
}
