package io.renren.modules.walk.service;

import io.renren.common.utils.R;

/**
 * 微信步数解密
 * @author zln
 *
 */
public interface WeChatWalkDealService {
	
	R walkDeal(String encryptedData,String sessionkey,String iv);
	
}
