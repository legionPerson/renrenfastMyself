package io.renren.modules.walk.service;

import io.renren.common.utils.R;

public interface WeChatCouponsService {
	
	R grantCoupons(String hnAppid,String orderId,String stock_id,String openId);

	/**
	 * 立减金发放
	 * @param mchId
	 * @param mchSerialNo
	 * @param merchantPrivateKeyFile
	 * @param certificateFile
	 * @return
	 */
	String sendCoupon(String mchId,String mchSerialNo,String merchantPrivateKeyFile,String certificateFile);

	/**
	 * 立减金明细
	 * @param appId
	 * @param couponId
	 * @param openId
	 * @return
	 */
	String getCouponDetails(String appId,String couponId,String openId);

	/**
	 * 立减金明细 认证凡事
	 * @param appId
	 * @param couponId
	 * @param openId
	 * @return
	 */
	String getCouponDetailsByAuth(String appId,String couponId,String openId);


}
