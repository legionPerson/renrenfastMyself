package io.renren.modules.walk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.ref.RetData;
import io.renren.common.utils.DateUtils;
import io.renren.modules.walk.dao.*;
import io.renren.modules.walk.entity.*;
import io.renren.modules.walk.service.WalkDayService;
import io.renren.modules.walk.utils.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.Timestamp;
import java.util.*;

@Service
public class WalkDayServiceImpl implements WalkDayService {

    private Logger logger = LoggerFactory.getLogger(WalkDayServiceImpl.class);

    @Autowired
    private HnjbzWalkDayDao hnjbzWalkDayDao;

    @Autowired
    private HnjbzUserGoldAccountMapper hnjbzUserGoldAccountMapper;

    @Autowired
    private HnjbzGoldBudgetInfoMapper hnjbzGoldBudgetInfoMapper;

    @Autowired
    private HnjbzChannelUserMapper hnjbzChannelUserMapper;

    @Autowired
    private HnjbzChannelMapper hnjbzChannelMapper;

    @Autowired
    private HnjbzOrgMapper hnjbzOrgMapper;

    @Autowired
    private HnjbzUserWhiteMapper hnjbzUserWhiteMapper;


    @Override
    public RetData<Object> getSessionKey(HnjbzWalkDayBeanReq record) throws Exception {

        QueryWrapper<HnjbzChannel> channelQueryWrapper = new QueryWrapper<HnjbzChannel>();
        channelQueryWrapper.eq("ID", "2");
        HnjbzChannel channel = hnjbzChannelMapper.selectOne(channelQueryWrapper);

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=" + channel.getAppid();
        url += "&secret=" + channel.getAppsecret();
        url += "&js_code=" + record.getCode();
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";

        String res = HttpClientUtil.get(url, null, null, "utf-8");

        JSONObject resObj = JSON.parseObject(res);
        if (resObj == null) {
            return new RetData<>(-1, "??????????????????????????????!");
        }
        String openid = resObj.getString("openid");
        String session_key = resObj.getString("session_key");

        logger.info("getSessionKey " + res);
        if (session_key == null) {
            return new RetData<>(-1, "????????????");
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("openid", openid);
        result.put("sessionKey", session_key);

        return new RetData<>(0, "????????????", result);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public RetData<Object> getRunData(HnjbzWalkDayBeanReq record) throws Exception {

        String encryptedData = record.getEncryptedData();
        String iv = record.getIv();
        String sessionKey = record.getSessionKey();

        String result = null;
        byte[] encrypData = Base64.decodeBase64(encryptedData);
        byte[] ivData = Base64.decodeBase64(iv);
        byte[] sessionKeyB = Base64.decodeBase64(sessionKey);

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(sessionKeyB, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] doFinal = cipher.doFinal(encrypData);
        if (doFinal == null) {
            return new RetData<>(-1, "????????????");
        }
        result = new String(doFinal);

        JSONObject stepInfoListJson = JSON.parseObject(result);
        //??????json?????????stepInfoList???????????????
        JSONArray stepInfoList = JSON.parseArray(stepInfoListJson.getString("stepInfoList"));

        logger.info("stepInfoList " + stepInfoList);

        QueryWrapper<HnjbzChannelUser> wrapper = new QueryWrapper<>();
        wrapper.eq("binding_status", 0).eq("status", 0).eq("id", record.getUserId());
        HnjbzChannelUser channelUser = hnjbzChannelUserMapper.selectOne(wrapper);
        if (channelUser == null) {
            return new RetData<>(-1, "??????????????????");
        }
        String createTime = DateUtils.format(channelUser.getCreateTime());

        for (int i = 0; i < stepInfoList.size(); i++) {

            JSONObject stepInfo = stepInfoList.getJSONObject(i);
            HnjbzWalkDayBean walkDayParm = new HnjbzWalkDayBean();
            walkDayParm.setUserId(record.getUserId());
            Timestamp timeStamp = new Timestamp(Long.valueOf(stepInfo.get("timestamp") + "000"));
            String stepDate = DateUtils.format(timeStamp);
            walkDayParm.setStepDate(stepDate);
            //????????????????????????????????????????????????,?????????????????????
            if (stepDate.compareTo(createTime) < 0) {
                continue;
            }

            HnjbzWalkDayBean hnjbzWalkDayBean = hnjbzWalkDayDao.selectDto(walkDayParm);
            //????????????????????????????????????
            if (hnjbzWalkDayBean == null) {
                hnjbzWalkDayBean = new HnjbzWalkDayBean();
                hnjbzWalkDayBean.setUserId(record.getUserId());
                hnjbzWalkDayBean.setStepDate(stepDate);
                hnjbzWalkDayBean.setSteps(Long.valueOf(stepInfo.get("step") + ""));
                hnjbzWalkDayBean.setIsExchange("0");
                hnjbzWalkDayBean.setCreateTime(new Date());
                hnjbzWalkDayDao.insertDto(hnjbzWalkDayBean);
            } else {
                String stepCreateDate = DateUtils.format(hnjbzWalkDayBean.getCreateTime());
                String stepUpdateDate = DateUtils.format(hnjbzWalkDayBean.getUpdateTime() == null ? new Date() : hnjbzWalkDayBean.getUpdateTime());
                //???????????????????????????????????????????????????????????????
                if (stepDate.compareTo(stepCreateDate) < 0 || stepDate.compareTo(stepUpdateDate) < 0) {
                    continue;
                }
                hnjbzWalkDayBean.setSteps(Long.valueOf(stepInfo.get("step") + ""));
                hnjbzWalkDayBean.setUpdateTime(new Date());
                hnjbzWalkDayDao.updateDto(hnjbzWalkDayBean);
            }
        }
        return new RetData<>(0, "??????????????????", stepInfoList);
    }

    /**
     * ???????????????????????????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exchangeGoldBudget() {
    	//??????????????????
        QueryWrapper<HnjbzOrg> orgQueryWrapper = new QueryWrapper<HnjbzOrg>();
        List<HnjbzOrg> orgList = hnjbzOrgMapper.selectList(orgQueryWrapper);
        //???????????????
        Map orgMap = new HashMap();
        //????????????
        Map orgRuleMap = new HashMap();
        for(HnjbzOrg hnjbzOrg : orgList){
            orgMap.put(hnjbzOrg.getId(),hnjbzOrg.getParentId());
            orgRuleMap.put(hnjbzOrg.getId(),hnjbzOrg.getRuleId());
        }
        //????????????????????????
        QueryWrapper<HnjbzChannelUser> channelUserQueryWrapper = new QueryWrapper<HnjbzChannelUser>();
        List<HnjbzChannelUser> channelUserList = hnjbzChannelUserMapper.selectList(channelUserQueryWrapper);
        Map channelUserMap = new HashMap();
        //???????????????
        for(HnjbzChannelUser hnjbzChannelUser : channelUserList){
            channelUserMap.put(hnjbzChannelUser.getBindingPhone(),hnjbzChannelUser.getId());
        }
        //???????????????????????????
        QueryWrapper<HnjbzUserWhite> userWhiteQueryWrapper = new QueryWrapper<HnjbzUserWhite>();
        List<HnjbzUserWhite> userWhiteList = hnjbzUserWhiteMapper.selectList(userWhiteQueryWrapper);
        //????????????
        Map userWhiteMap = new HashMap();
        for(HnjbzUserWhite hnjbzUserWhite : userWhiteList){
            userWhiteMap.put(channelUserMap.get(hnjbzUserWhite.getPhone()),hnjbzUserWhite.getOrgId());
        }

        //??????????????????
        List<Map<String,String>> changRatio = hnjbzWalkDayDao.getChangRatio();
        Map ruleMap = new HashMap();
        for(Map<String,String> map :changRatio){
            ruleMap.put(map.get("id"),map.get("subscription_ratio"));
        }
        //????????????????????????
        HnjbzWalkDayBean walkDayParm = new HnjbzWalkDayBean();
        walkDayParm.setIsExchange("0");
        List<HnjbzWalkDayBean> walkDayList = hnjbzWalkDayDao.selectDataList(walkDayParm);

        for (HnjbzWalkDayBean hnjbzWalkDayBean : walkDayList) {
            String ruleId = getRuleId(hnjbzWalkDayBean.getUserId(),orgMap,orgRuleMap,userWhiteMap);
            Float ratio = StringUtils.isEmpty(ruleId)?1:Float.valueOf(String.valueOf(ruleMap.get(ruleId)));
            String stepDate = hnjbzWalkDayBean.getStepDate();
            String stepCreateDate = DateUtils.format(hnjbzWalkDayBean.getCreateTime());
            String stepUpdateDate = DateUtils.format(hnjbzWalkDayBean.getUpdateTime() == null ? hnjbzWalkDayBean.getCreateTime() : hnjbzWalkDayBean.getUpdateTime());
            //?????????????????????????????????????????????????????????????????????
            if (stepDate.compareTo(stepCreateDate) < 0 || stepDate.compareTo(stepUpdateDate) < 0) {
                //??????
                Long steps = hnjbzWalkDayBean.getSteps();
                //?????????????????????
                Integer budgetAmount = Integer.valueOf((int) (steps * ratio));
                //???????????????????????????
                QueryWrapper<HnjbzUserGoldAccount> goldAccountWrapper = new QueryWrapper<>();
                goldAccountWrapper.eq("user_id", hnjbzWalkDayBean.getUserId());
                HnjbzUserGoldAccount hnjbzUserGoldAccount = hnjbzUserGoldAccountMapper.selectOne(goldAccountWrapper);
                //?????????????????????????????????????????????????????????
                if (hnjbzUserGoldAccount == null) {
                    hnjbzUserGoldAccount = new HnjbzUserGoldAccount();
                    hnjbzUserGoldAccount.setUserId(hnjbzWalkDayBean.getUserId());
                    hnjbzUserGoldAccount.setFrontGoldAmount("0");
                    hnjbzUserGoldAccount.setLaterGoldAmount(budgetAmount + "");
                    hnjbzUserGoldAccount.setStatus("0");
                    hnjbzUserGoldAccount.setCreateTime(new Date());
                    hnjbzUserGoldAccountMapper.insert(hnjbzUserGoldAccount);
                } else {
                    Integer frontGoldAmount = Integer.valueOf(hnjbzUserGoldAccount.getLaterGoldAmount());
                    Integer laterGoldAmount = frontGoldAmount + budgetAmount;
                    hnjbzUserGoldAccount.setFrontGoldAmount(frontGoldAmount + "");
                    hnjbzUserGoldAccount.setLaterGoldAmount(laterGoldAmount + "");
                    hnjbzUserGoldAccountMapper.update(hnjbzUserGoldAccount, goldAccountWrapper);
                }
                //???????????????????????????
                HnjbzGoldBudgetInfo hnjbzGoldBudgetInfo = new HnjbzGoldBudgetInfo();
                hnjbzGoldBudgetInfo.setUuid(UUID.randomUUID().toString());
                hnjbzGoldBudgetInfo.setUserId(hnjbzWalkDayBean.getUserId());
                hnjbzGoldBudgetInfo.setBudgetAmount(budgetAmount + "");
                hnjbzGoldBudgetInfo.setBudgetFlag("1");
                hnjbzGoldBudgetInfo.setIncomeType("1");
                hnjbzGoldBudgetInfo.setStatus("0");
                hnjbzGoldBudgetInfo.setGrantDate(DateUtils.stringToDate(stepDate, "yyyy-MM-dd"));
                hnjbzGoldBudgetInfoMapper.insert(hnjbzGoldBudgetInfo);
                //????????????????????????????????????
                QueryWrapper<HnjbzWalkDayBean> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id", hnjbzWalkDayBean.getUserId())
                        .eq("step_date", hnjbzWalkDayBean.getStepDate());
                hnjbzWalkDayBean.setIsExchange("1");
                hnjbzWalkDayDao.updateDto(hnjbzWalkDayBean);
            }
        }
    }

    /**
     * ?????????????????????????????????
     * @param userId ????????????id
     * @param orgMap ???????????????
     * @param orgRuleMap ????????????
     * @param userWhiteMap ????????????
     * @return
     */
    public String getRuleId(String userId,Map orgMap,Map orgRuleMap,Map userWhiteMap){
        String userOrg = String.valueOf(userWhiteMap.get(userId));
        String ruleId = String.valueOf(orgRuleMap.get(userOrg));
        if(StringUtils.isNotEmpty(ruleId)){
            return ruleId;
        }else{
            String parentId = String.valueOf(orgMap.get(userId));
            if(StringUtils.isEmpty(parentId)){
                return "";
            }
            return getRuleId(parentId,orgMap,orgRuleMap,userWhiteMap);
        }
    }
}
