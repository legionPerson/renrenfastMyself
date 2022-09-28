package io.renren.modules.walk.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.walk.service.WeChatCouponsService;
import io.renren.modules.walk.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * 立刻减金发放（后面需要独立成一个服务）
 * @author zln
 *
 */
@Service
@Slf4j
public class WeChatCouponsServiceImpl implements WeChatCouponsService{
	
	private static String yourPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1NPDZL1u99gAnF8pFDQ9aj5VCInQVmAT3D3p1kXq5C/+61TcOFnHBFffgr+asiqudfEdSGvdfaKNgOFfOkZDswTypDg7skKZlGIuWMDENn5qoTcOUI9sIicMRT28uWMX0E0ox64G04bIKGMb30nFmP5cWDYcg7z2j5HTDoa7DsqeEHcIY8JmU2NBUhS38VrFs9Smo6uFrFLawdIGTtm2ZrCsLo1z9kB6ZBrAOI+wVWfo1McPMqGRDXT4ZeNd+8MKjHryxDFG7ggZoXIHdns8PfMj65lIGoCmTgkIZbsi2ExnnyNE9bLF6DzwEKEmN5m4HVIkTq6CJBeV/0Aab5K+BAgMBAAECggEAZJ3e1lmrkVEVSJ+3Gpxj2B89+5jx6EOfDig0ETAUesek2fMmPNZNl69BEivZ5BijrjCh0X9TuFB71yOx7et6E1xA+IXr14OCQURdzak4s53Z3HZBkPJP3u/Ptr7qt0tnr10+p3VjcjKYD28um9DoUqQgmogsbicpjOV/yFjwl4yd3qen2NbblRU9Z+V8twagZqIPOu3W74FrkucqmwiIn0sd6eLi0EvHs8XPsjSHaZ9+qXwY3TZ/FA3JtjoYP6LXfGi1gSb5m1lZeCJgH79syPwSxht9XYxdkvcHMUXhzWPjc+Bx0Y2wKSzA7+FYtM1/6n/qBXhoIkBXAhUpLm+wGQKBgQDqGWzeJqWk4EH3tFGWbJw9wi/OuGP1Ef7Tm+xRkqHnGj+sWFGTjQhQDw70KCe35ZbwSZ4sZEZceNBwMc7N1p0HvRdg8Q0lkWx/8dph5vQH4H2oSFo6cOl13E/HSoeZ4J7pDWHkPGtTMYXEzejy5W8J4ZHtjrWEltOD+i1LtweUBwKBgQDGKMNjf/FM1s0cJx/ncjbq1FRyMKK2SEideYF3Miz+xIIjzXEEU9Ls6zUfztMfayFKUyrIdb1a/wKcxemA1KOnF/Jv7VOHmgt5QUiJDM1UadD+muTgU05d1pjEHBizeZSLohTUpiyXvQOwcYHmCtdJMJTAF1OT31cGHyLQB5iONwKBgAL+TDwS9m9DRtSqA5kOznijsQTNqsmJb165Ua5BV7yiw0mq33Apj3+mkaRhlxBs6R8tcc5TgerQyKmGmYRcSCm/KztcGbTvRRnlt8skrt4i6rhqJG024GVXadW8iDtZee608xl+BElOCs3XbDEe6eqQbdNBzeRiIVZArq+XKYulAoGAHiLu7D/2A7ibQw/UM0/UeExId4FcDZraqFBlVypjW2+pVyaUbJMO/gcbcjXzN9EEJBiX5hq57I/h5tPWmkg8h1oF/e27CWFtI0jBbk5rH85kAYSvEEq7HVxYqWKYgxsXc1D4GAsZtw4A9KGenszBRxlDVh92D4Qk6QJtOfQdyGsCgYEAqCMxNKfMIJYIDd6rOhMmrqJvPz1pY1tEHQLNfYjunE9f7FbrwBf/Qlzu1FOzsEoEhBI1BC7QqF/+LIHO1kYdizIjbcs1Kdgo2OHXGVkiQedvZd7ppyxY14hozvy0Vsguty643R7LTB/CAtSK/YT7RdPxVenIoOBMWecYlkBb6+s=" ;//私钥
	private static String yourMerchantId = "1574381071" ;//商户号
	private static String appid = "wx15af72b6be0bb1a6" ;//公众账号ID微信为发券方商户分配的公众账号ID
	private static String stock_creator_mchid = "1574381071" ;//公众账号ID 批次创建方商户号
	private static String yourCertificateSerialNo = "268387CBE377033FA6103AFB934F53EE41C7EF4C" ;//商户API证书序列号serial_no
	private String sendUrl = "https://api.mch.weixin.qq.com/v3/marketing/favor/users/%s/coupons";

	/**
	 * 
	 */
	@Override
	public R grantCoupons(String hnAppid, String orderId, String stock_id, String openId) {
		
		sendUrl = String.format(sendUrl, openId);
		
		System.out.println("请求地址："+sendUrl);

		String outRequestNo = yourMerchantId+ DateUtils.format(new Date(),"yyyyMMdd")+orderId;//商户号
		String coupon_id=null;
		try {
			JSONObject param = new JSONObject();
			param.put("stock_id", stock_id);//微信为每个批次分配的唯一id。校验规则：必须为代金券（全场券或单品券）批次号，不支持立减与折扣。示例值：9856000
			param.put("out_request_no", outRequestNo);//商户此次发放凭据号（格式：商户id+日期+流水号），可包含英文字母，数字，|，_，*，-等内容，不允许出现其他不合法符号，商户侧需保持唯一性。示例值： 89560002019101000121
			param.put("appid", appid);//公众账号ID微信为发券方商户分配的公众账号ID，接口传入的所有appid应该为公众号的appid或者小程序的appid（在mp.weixin.qq.com申请的）或APP的appid（在open.weixin.qq.com申请的）。校验规则：
			//1、该appid需要与接口传入中的openid有对应关系；
			//2、该appid需要与调用接口的商户号（即请求头中的商户号）有绑定关系，若未绑定，可参考该指引完成绑定（商家商户号与AppID账号关联管理）示例值：wx233544546545989
			param.put("stock_creator_mchid", stock_creator_mchid);//公众账号ID 批次创建方商户号。校验规则：接口传入的批次号需由stock_creator_mchid所创建。示例值：8956000
			
			System.out.println("请求参数："+param.toString());
			
			HttpUrl httpurl = HttpUrl.parse(sendUrl);//参与签名地址
			String schema = "WECHATPAY2-SHA256-RSA2048";
			String token = getToken("POST",httpurl,param.toString());
			String Authorization = " "+ schema + " "+ token;
			log.info("Authorization:"+Authorization);
			log.info("token:"+token.length());

			String result = HttpClientUtil.sendJsonHttpPost(sendUrl, param.toString(),Authorization);
			log.info("代金券发放："+result);
			JSONObject obj = new JSONObject(result);
			coupon_id = obj.optString("coupon_id");
			if(StringUtils.isEmpty(coupon_id)) {
				String code = obj.optString("code");
				String message = obj.optString("message");
				return R.error(message);
			}
			//发放成功后权益平台需要记录TODO
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return R.ok(coupon_id);
		
	}
	
	String getToken(String method, HttpUrl url, String body) {
	    String nonceStr = UUID.randomUUID().toString().replace("-", "");
	    long timestamp = System.currentTimeMillis() / 1000;
	    String message = buildMessage(method, url, timestamp, nonceStr, body);
	    log.info("签名验证=== "+message);
	    String signature = "";
		try {
			signature = sign(message.getBytes("utf-8"));
			log.info("signature length:"+signature.length());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("系统异常");
		}

	    return "mchid=\"" + yourMerchantId + "\","
	    + "nonce_str=\"" + nonceStr + "\","
	    + "timestamp=\"" + timestamp + "\","
	    + "serial_no=\"" + yourCertificateSerialNo + "\","
	    + "signature=\"" + signature + "\"";
	}
	
	//获取签名信息
	String sign(byte[] message) {
	    Signature sign;
		try {
			PrivateKey privateKey = getPrivateKey(yourPrivateKey);
			sign = Signature.getInstance("SHA256withRSA");
			sign.initSign(privateKey);
			sign.update(message);
			return Base64.getEncoder().encodeToString(sign.sign());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("系统异常");
		}
		return null;
	}
	
	//获取签名串
	String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
	    String canonicalUrl = url.encodedPath();
	    if (url.encodedQuery() != null) {
	      canonicalUrl += "?" + url.encodedQuery();
	    }
	    if(StringUtils.isEmpty(body)){
	    	body = "";
		}

	    return method + "\n"
	        + canonicalUrl + "\n"
	        + timestamp + "\n"
	        + nonceStr + "\n"
	        + body + "\n";
	}
	
	/**
	 * 解码PublicKey
	 * @param key
	 * @return
	 */
	public static PublicKey getPublicKey(String key) {
	    try {
	        byte[] byteKey = Base64.getDecoder().decode(key);
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePublic(x509EncodedKeySpec);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	/**
	 * 解码PrivateKey
	 * @param key
	 * @return
	 */
	public static PrivateKey  getPrivateKey(String key) {
	    try {
	        byte[] byteKey = Base64.getDecoder().decode(key);
	        PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePrivate(x509EncodedKeySpec);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	/**
	 * 获取私钥。
	 *
	 * @param  filename 私钥文件路径  (required)
	 * @return 私钥对象
	 */
/*	public static PrivateKey getPrivateKey(String filename) throws IOException {

		filename = "C:\\Users\\legion\\Desktop\\1574381071_20220906_cert\\apiclient_key.pem";
		String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
		try {
			String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "")
					.replaceAll("\\s+", "");

			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePrivate(
					new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("当前Java环境不支持RSA", e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("无效的密钥格式");
		}
	}*/
	
	public static void main(String[] args) {
		WeChatCouponsService service = new WeChatCouponsServiceImpl();
		String appid = "wx15af72b6be0bb1a6";//所属应用
		String orderId = "00003";//流水号；用于生成商户单据号
		String stockId = "16733850";//批次号
		String openId = "ocnyd5IbKCCVlwy-AbikS-DlsekE";//西乐
//		String openId = "oZfE64w19PdNRrhVJlt0sBdc6NO4";//李伟
//		String openId = "ocnyd5A6Qww7MKBkcJBoseOcUBeK";//me

//		service.grantCoupons(appid,orderId,stockId,openId);

		//证书发券
//		service.sendCoupon(appid,orderId,stockId,openId);

		//券详情查询
		String couponId = "37319726629";
//		service.getCouponDetails(appid,couponId,openId);
		service.getCouponDetailsByAuth(appid,couponId,openId);
	}

	/**
	 * 发放微信代金券
	 */
	public String sendCoupon(String appId,String orderId,String stockId,String openId) {
		//发券参数
		String mchId = "1574381071";//商户号
		String mchSerialNo = "268387CBE377033FA6103AFB934F53EE41C7EF4C";//商户证书序列号
		String outRequestNo = mchId+ DateUtils.format(new Date(),"yyyyMMdd")+orderId;//商户单据号
		//认证信息
		String merchantPrivateKeyFile = "C:\\Users\\legion\\Desktop\\1574381071_20220906_cert\\apiclient_key.pem";//私钥文件
		//商户证书文件（可以发券成功，但是报异常"应答的微信支付签名验证失败"：原因可能为传递的商户证书，不是平台证书）
		String certificateFile = "C:\\Users\\legion\\Desktop\\1574381071_20220906_cert\\wechatpay_cert.pem";
		FileInputStream privateKeyInputStream = null;//私钥文件流
		FileInputStream certificateInputStream = null;//证书文件流（应为平台证书，暂用商户证书）
		try {
			privateKeyInputStream = new FileInputStream(new File(merchantPrivateKeyFile));
			certificateInputStream = new FileInputStream(new File(certificateFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("读取私钥文件或证书文件失败, errmsg:" + e.getMessage());
			return null;
		}
		//私钥文件
		PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(privateKeyInputStream);
		//证书文件
		X509Certificate wechatpayCertificate = PemUtil.loadCertificate(certificateInputStream);
		//微信平台支付证书
		ArrayList<X509Certificate> wechatpayCertificates = new ArrayList<>();
		wechatpayCertificates.add(wechatpayCertificate);

		CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
				.withMerchant(mchId, mchSerialNo, merchantPrivateKey)
				.withWechatPay(wechatpayCertificates)
				.build();

		String sendCouponUrl = String.format("https://api.mch.weixin.qq.com/v3/marketing/favor/users/%s/coupons", openId);

		HashMap<String, String> paraMap = new HashMap<>();
		paraMap.put("stock_id",stockId);
		paraMap.put("out_request_no", outRequestNo);
		paraMap.put("appid", appId);
		paraMap.put("stock_creator_mchid", mchId);
		log.info("request url=" + sendCouponUrl);
		log.info("request params=" + new JSONObject(paraMap).toString());

		HttpPost httpPost = new HttpPost(sendCouponUrl);
		StringEntity reqEntity = new StringEntity(new JSONObject(paraMap).toString(),
				ContentType.create("application/json", "utf-8"));

		httpPost.setEntity(reqEntity);
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/json");

		CloseableHttpResponse response = null;
		try {
			log.info("微信发券接口地址："+sendCouponUrl+",入参："+new JSONObject(httpPost).toString());
			response = httpClient.execute(httpPost);
			String content = EntityUtils.toString(response.getEntity());
			int statusCode = response.getStatusLine().getStatusCode();
			log.info("微信发券接口返回结果code："+statusCode+",内容："+ content);
			if (statusCode == HttpStatus.SC_OK) {
				String result = content;
				log.info("response=" + result);
				return result;
			} else {
				String result = content;
				log.error("status_code=" + statusCode + ", response=" + result);
				return result;
			}
		} catch (Exception e) {
			log.error("excepiton:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * 查询立减金券
	 * @param appId
	 * @param couponId
	 * @param openId
	 * @return
	 */
	public String getCouponDetails(String appId,String couponId,String openId) {
		String mchId = "1574381071";//商户号
		String mchSerialNo = "268387CBE377033FA6103AFB934F53EE41C7EF4C";//商户证书序列号
		//认证信息
		String merchantPrivateKeyFile = "C:\\Users\\legion\\Desktop\\1574381071_20220906_cert\\apiclient_key.pem";//私钥文件
		//商户证书文件（可以发券成功，但是报异常"应答的微信支付签名验证失败"：原因可能为传递的商户证书，不是平台证书）
		String certificateFile = "C:\\Users\\legion\\Desktop\\1574381071_20220906_cert\\wechatpay_cert.pem";
		//私钥
		FileInputStream privateKeyInputStream = null;
		//证书
		FileInputStream certificateInputStream = null;

		try {
			privateKeyInputStream = new FileInputStream(new File(merchantPrivateKeyFile));
			certificateInputStream = new FileInputStream(new File(certificateFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("read merchantPrivateKeyFile or certificateFile faild, errmsg:" + e.getMessage());
			return null;
		}

		PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(privateKeyInputStream);

		X509Certificate wechatpayCertificate = PemUtil.loadCertificate(certificateInputStream);
		ArrayList<X509Certificate> wechatpayCertificates = new ArrayList<>();
		wechatpayCertificates.add(wechatpayCertificate);

		CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
				.withMerchant(mchId, mchSerialNo, merchantPrivateKey)
				.withWechatPay(wechatpayCertificates)
				.build();

		//get请求
		String sendCouponUrl = String.format("https://api.mch.weixin.qq.com/v3/marketing/favor/users/%s/coupons/%s?appid="+appId,openId,couponId);
		log.info("request url=" + sendCouponUrl);
		log.info("request params=" + appid+" "+openId+" "+couponId);

		HttpGet httpGet = new HttpGet(sendCouponUrl);
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("Content-Type", "application/json");

		CloseableHttpResponse response = null;
		try {
			log.info("微信查询券详情接口地址："+sendCouponUrl);
			response = httpClient.execute(httpGet);
			log.info("微信查询券详情返回结果code："+response.getStatusLine().getStatusCode()+",内容："+ EntityUtils.toString(response.getEntity()));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity());
				log.info("response=" + result);
				return result;
			} else {
				String result = EntityUtils.toString(response.getEntity());
				log.error("status_code=" + response.getStatusLine().getStatusCode() + ", response=" + result);
				return result;
			}
		} catch (Exception e) {
			log.error("查询微信代金券详情异常:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 查询立减金券(认证方式)
	 * @param appId
	 * @param couponId
	 * @param openId
	 * @return
	 */
	public String getCouponDetailsByAuth(String appId,String couponId,String openId) {
		//get请求
		String sendCouponUrl = String.format("https://api.mch.weixin.qq.com/v3/marketing/favor/users/%s/coupons/%s?appid="+appId,openId,couponId);
		log.info("request url=" + sendCouponUrl);
		log.info("request params=" + appid+" "+openId+" "+couponId);

		HttpUrl httpurl = HttpUrl.parse(sendCouponUrl);//参与签名地址
		String schema = "WECHATPAY2-SHA256-RSA2048";
		String token = getToken("GET",httpurl,"");
		String Authorization = " "+ schema + " "+ token;
		log.info("Authorization:"+Authorization);

		log.info("微信查询券详情接口地址："+sendCouponUrl);
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("Accept","application/json");
		headerMap.put("Content-Type","application/json");
		headerMap.put("Authorization",Authorization);
		String result = HttpClientUtil.get(sendCouponUrl,null,headerMap,"UTF-8");
		System.out.println(result);
		return result ;
	}

}
