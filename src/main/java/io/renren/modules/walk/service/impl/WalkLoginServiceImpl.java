package io.renren.modules.walk.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.walk.dao.*;
import io.renren.modules.walk.entity.*;
import io.renren.modules.walk.service.WalkLoginService;
import io.renren.modules.walk.utils.HttpClientUtil;
import io.renren.modules.walk.vo.WalkLoginVo;
import io.renren.modules.walk.vo.WalkPhoneLoginVo;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 登录实现
 * 
 * @author zln
 *
 */
@Slf4j
@Service
public class WalkLoginServiceImpl implements WalkLoginService {

	@Autowired
	private HnjbzChannelMapper hnjbzChannelMapper;

	@Autowired
	private HnjbzChannelUserMapper hnjbzChannelUserMapper;

	@Autowired
	private HnjbzUserWhiteMapper hnjbzUserWhiteMapper;

	@Autowired
	private HnjbzOrgMapper hnjbzOrgMapper;

	@Autowired
	private HnjbzIdentifyCodeMapper hnjbzIdentifyCodeMapper;


	@Value("${wechat.openid.url}")
	private String openidUrl;
	
	/**
	 * 用户登录
	 */
	@Override
	public R loginIn(WalkLoginVo walkLoginVo) {

		log.info("用户登录信息："+ walkLoginVo.toString());
		
		//根据渠道号获取渠道信息
		QueryWrapper<HnjbzChannel> channelQueryWrapper = new QueryWrapper<HnjbzChannel>();
		channelQueryWrapper.eq("ID", walkLoginVo.getChannelId());
		HnjbzChannel channel = hnjbzChannelMapper.selectOne(channelQueryWrapper);
		
		if(channel == null) {
			return R.error("获取渠道信息异常");
		}
		
		String result = "";
		// 请求微信服务器，用code换取openid。（后期行内需要走代理服务器地址）
		try {

			result = HttpClientUtil.get(String.format(openidUrl, channel.getAppid(), channel.getAppsecret(), walkLoginVo.getCode()), null,
						null, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return  R.error("登录凭证校验失败");
		}
		

		if (StringUtils.isEmpty(result)) {
			return  R.error("登录凭证校验失败");
		}
		
		log.info("获取openid："+ result);

		String openId = null;
		String session_key = null ;
		try {
			// 解析获取openid
			JSONObject obj = new JSONObject(result);
			
			int errcode = 0;
			String errmsg = "";
			if(obj.has("errcode")) {
				errcode = obj.getInt("errcode");
			}
			
			if(obj.has("errmsg")) {
				errmsg = obj.getString("errmsg");
			}

			if (errcode != 0) {
				return  R.error(errmsg);
			}

			openId = obj.getString("openid");

			session_key = obj.getString("session_key");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(session_key)) {
			return  R.error("登录凭证校验失败");
		}

		// 解密用户信息
		/*com.alibaba.fastjson.JSONObject userInfo = WechatGetUserInfoUtil.getUserInfo(walkLoginVo.getEncryptedData(),
				session_key, walkLoginVo.getIv());
		if (StringUtils.isEmpty(userInfo.toJSONString())) {
			return R.error("获取用户信息异常");
		}
		String nickName = userInfo.getString("nickName");// 昵称
		String avatarUrl = userInfo.getString("avatarUrl");// 个人头像
		int gender = userInfo.getInteger("gender");// 性别
		String country = userInfo.getString("country");// 国家
		String province = userInfo.getString("province");// 省份
		String city = userInfo.getString("city");// 城市
		String language = userInfo.getString("language");// 语言*/

		//根据openid获取渠道用户信息表
		QueryWrapper<HnjbzChannelUser> channelUserQueryWrapper = new QueryWrapper<HnjbzChannelUser>();
		channelUserQueryWrapper.eq("OPENID", openId);
		HnjbzChannelUser channelUser = hnjbzChannelUserMapper.selectOne(channelUserQueryWrapper);
		if(channelUser==null){
			channelUser = new HnjbzChannelUser();
			channelUser.setId(io.renren.common.utils.StringUtils.get32Guid());
			channelUser.setOpenid(openId);
			channelUser.setStatus("0");
			hnjbzChannelUserMapper.insert(channelUser);
			return R.ok("请绑定手机号!").put("channelUser",channelUser);
		}
		//获取到用户基本信息
		QueryWrapper<HnjbzUserWhite> userWhiteQueryWrapper = new QueryWrapper<HnjbzUserWhite>();
		userWhiteQueryWrapper.eq("phone", channelUser.getBindingPhone());
		HnjbzUserWhite userWhite = hnjbzUserWhiteMapper.selectOne(userWhiteQueryWrapper);
		HnjbzOrg userSite = new HnjbzOrg();
		String dwOrgId = "";
		if(userWhite!=null){
			//获取用户所属分行地址
			HnjbzOrg hnjbzOrg = hnjbzOrgMapper.selectById(userWhite.getOrgId());
			String zhOrgId = hnjbzOrg.getInnerCode().split(",")[1];
            dwOrgId = hnjbzOrg.getInnerCode().split(",")[3];
			userSite = hnjbzOrgMapper.selectById(zhOrgId);
		}

		return R.ok().put("userWhite",userWhite).put("channelUser",channelUser).put("city",userSite.getName()!=null?userSite.getName().substring(0,2):"").put("dwOrgId",dwOrgId);
	}

	@Override
	public R phoneLoginIn(WalkPhoneLoginVo walkPhoneLoginVo) {
		//查询是否手机号已经绑定，不允许重复绑定
		QueryWrapper<HnjbzChannelUser> oldChannelUserQueryWrapper = new QueryWrapper<HnjbzChannelUser>();
		oldChannelUserQueryWrapper.eq("binding_phone", walkPhoneLoginVo.getPhone());
		HnjbzChannelUser oldChannelUser = hnjbzChannelUserMapper.selectOne(oldChannelUserQueryWrapper);
		if(oldChannelUser!=null){
			return R.error().put("msg","手机号已绑定用户!");
		}

		//查询是否存在对应的手机号和验证码
		QueryWrapper<HnjbzIdentifyCode> identifyCodeQueryWrapper = new QueryWrapper<HnjbzIdentifyCode>();
		identifyCodeQueryWrapper.eq("phone", walkPhoneLoginVo.getPhone());
		identifyCodeQueryWrapper.eq("code", walkPhoneLoginVo.getCode());
		HnjbzIdentifyCode hnjbzIdentifyCode = hnjbzIdentifyCodeMapper.selectOne(identifyCodeQueryWrapper);
		if(hnjbzIdentifyCode==null){
			return R.error("验证码错误!");
		}
		//对比当前时间和验证码时间，有效期五分钟
		R r = getValidateTime(hnjbzIdentifyCode.getCreateTime(),5);
//		if(Integer.parseInt(r.getCode())!=0){
//			return R.error().put("msg",r.get("msg"));
//		}
		if(hnjbzIdentifyCode!=null){
			//根据openid获取渠道用户信息表
			QueryWrapper<HnjbzChannelUser> channelUserQueryWrapper = new QueryWrapper<HnjbzChannelUser>();
			channelUserQueryWrapper.eq("OPENID", walkPhoneLoginVo.getOpenid());
			HnjbzChannelUser channelUser = hnjbzChannelUserMapper.selectOne(channelUserQueryWrapper);
			//更新用户信息到渠道用户表
			channelUser.setBindingPhone(walkPhoneLoginVo.getPhone());
			channelUser.setBindingStatus("0");
			channelUser.setUpdateTime(new Date());
			hnjbzChannelUserMapper.updateById(channelUser);
			//查询用户白名单信息
			QueryWrapper<HnjbzUserWhite> userWhiteQueryWrapper = new QueryWrapper<HnjbzUserWhite>();
			userWhiteQueryWrapper.eq("phone", walkPhoneLoginVo.getPhone());
			HnjbzUserWhite hnjbzUserWhite = hnjbzUserWhiteMapper.selectOne(userWhiteQueryWrapper);
			return R.ok().put("channelUser",channelUser).put("hnjbzUserWhite",hnjbzUserWhite).put("msg","绑定成功!");
		}else{
			return R.error("验证码错误");
		}
	}

	@Override
	public R generateIdentifyCode(WalkPhoneLoginVo walkPhoneLoginVo) {
		//查询白名单是否存在此手机号用户
		QueryWrapper<HnjbzUserWhite> userWhiteQueryWrapper = new QueryWrapper<HnjbzUserWhite>();
		userWhiteQueryWrapper.eq("phone", walkPhoneLoginVo.getPhone());
		List<HnjbzUserWhite> hnjbzUserWhiteList = hnjbzUserWhiteMapper.selectList(userWhiteQueryWrapper);
		if(hnjbzUserWhiteList.size()<=0){
			return R.error("用户未授权!");
		}
		//查询用户最新验证码，比对时间是否在一分钟内，如果是则不重复发送
		QueryWrapper<HnjbzIdentifyCode> oldIdentifyCodeQueryWrapper = new QueryWrapper<HnjbzIdentifyCode>();
		oldIdentifyCodeQueryWrapper.eq("phone", walkPhoneLoginVo.getPhone());
		oldIdentifyCodeQueryWrapper.orderByDesc("create_time");
		List<HnjbzIdentifyCode> hnjbzIdentifyCodeList = hnjbzIdentifyCodeMapper.selectList(oldIdentifyCodeQueryWrapper);
		if(hnjbzIdentifyCodeList.size()>0&&"0".equals(getValidateTime(hnjbzIdentifyCodeList.get(0).getCreateTime(),1).getCode())){
			return R.error("不允许重复发送!");
		}

		//访问短信服务器,发送验证码
		String code = String.valueOf((int)(Math.random()*1000000));

		//存储验证码
		HnjbzIdentifyCode hnjbzIdentifyCode = new HnjbzIdentifyCode();
		hnjbzIdentifyCode.setId(io.renren.common.utils.StringUtils.get32Guid());
		hnjbzIdentifyCode.setCode(code);
		hnjbzIdentifyCode.setPhone(walkPhoneLoginVo.getPhone());
		hnjbzIdentifyCode.setCreateTime(new Date());
		int count = hnjbzIdentifyCodeMapper.insert(hnjbzIdentifyCode);
		//先直接返回验证码
		return R.ok("验证码已发送").put("identifyCode",code);
	}

	public R getValidateTime(Date date,int minute){
		if(date==null){
			return R.error("时间不能为空！");
		}
		Calendar codeTime = Calendar.getInstance();
		Calendar beforeTime = Calendar.getInstance();
		Calendar afterTime = Calendar.getInstance();
		codeTime.setTime(date);
		beforeTime.setTime(new Date());
		afterTime.setTime(new Date());

		beforeTime.add(Calendar.MINUTE,Math.negateExact(minute));
		afterTime.add(Calendar.MINUTE,minute);
		if(codeTime.after(beforeTime)&&codeTime.before(afterTime)){
			return R.ok();
		}else{
			return R.error("验证码超时！");
		}

	}

}
