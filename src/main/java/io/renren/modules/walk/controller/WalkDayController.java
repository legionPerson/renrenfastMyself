package io.renren.modules.walk.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;

import io.renren.common.ref.RetData;
import io.renren.modules.walk.entity.HnjbzWalkDayBeanReq;
import io.renren.modules.walk.service.WalkDayService;


/**
 * 步数获取
 * 
 */
@RestController
@RequestMapping("/app/walkday")
public class WalkDayController {

	private Logger logger=LoggerFactory.getLogger(WalkDayController.class);
	
    @Autowired
    private WalkDayService walkDayService;
     
    
    //获取SessionKey
    @RequestMapping("/getSessionKey")
    public RetData<Object> getSessionKey(@RequestBody HnjbzWalkDayBeanReq hnjbzWalkDayBeanReq)  {
    	
       logger.info("getSessionKey "+hnjbzWalkDayBeanReq.toString());
       
       if (StringUtils.isEmpty(hnjbzWalkDayBeanReq.getCode())) {
     	   return new RetData<>(-1, "登录凭证不能为空");
 	   }
       
       try {
    	 
		  return walkDayService.getSessionKey(hnjbzWalkDayBeanReq);
		
	   } catch (Exception e) {
		  e.printStackTrace();
		  return  new RetData<>(-1,"查询失败" );
		
	   }
               
    }
    
    
    //获取步数
    @RequestMapping("/getRunData")
    public RetData<Object> getRunData(@RequestBody HnjbzWalkDayBeanReq hnjbzWalkDayBeanReq) {
       
       if (StringUtils.isEmpty(hnjbzWalkDayBeanReq.getUserId())) {
      	   return  new RetData<>(-1,"用户Id不能为空" );
  	   }
       if (StringUtils.isEmpty(hnjbzWalkDayBeanReq.getEncryptedData())) {
     	   return new RetData<>(-1, "加密数据不能为空");
 	   }
       if (StringUtils.isEmpty(hnjbzWalkDayBeanReq.getIv())) {
     	   return  new RetData<>(-1,"初始数据不能为空" );
 	   }
       if (StringUtils.isEmpty(hnjbzWalkDayBeanReq.getSessionKey())) {
     	   return  new RetData<>(-1,"秘钥不能为空" );
 	   }
    	
        try {
        	
			return walkDayService.getRunData(hnjbzWalkDayBeanReq);
			
		} catch (Exception e) {
			 
			e.printStackTrace();
			return  new RetData<>(-1,"查询失败" );
		}
	          	        
	}
    
    
    //步数兑换工银消费金
    @RequestMapping("/exchangeGoldBudget")
    public void exchangeGoldBudget(@RequestBody HnjbzWalkDayBeanReq hnjbzWalkDayBeanReq) {
        try {
        	//todo 此处可增减用户id参数，用户登录时，更新步数后兑换之前的工银立减金
			  walkDayService.exchangeGoldBudget( );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
