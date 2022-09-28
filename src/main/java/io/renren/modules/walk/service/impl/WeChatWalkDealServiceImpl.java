package io.renren.modules.walk.service.impl;

import org.apache.commons.lang.StringUtils;

import io.renren.common.utils.R;
import io.renren.modules.walk.service.WeChatWalkDealService;
import io.renren.modules.walk.utils.AesCbcUtil;

/**
 * 微信步数解密模块
 * @author zln
 *
 */
public class WeChatWalkDealServiceImpl implements WeChatWalkDealService{

	@Override
	public R walkDeal(String encryptedData, String sessionkey, String iv) {
		
		if(StringUtils.isEmpty(encryptedData)||StringUtils.isEmpty(sessionkey)||StringUtils.isEmpty(iv)) {
			return R.error("获取系统参数异常");
		}
		
		String result = "";//{ “stepInfoList”: [{“timestamp”: 1445866601,“step”: 100},{“timestamp”: 1445876601,“step”: 120} ] }
		try {
			result = AesCbcUtil.decrypt(encryptedData, sessionkey,iv, "UTF-8",1);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常，请稍后再试");
		}
		return R.ok(result);
	}

}
