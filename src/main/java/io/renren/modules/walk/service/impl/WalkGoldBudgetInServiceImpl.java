package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.HnjbzChannelUserMapper;
import io.renren.modules.walk.dao.HnjbzGoldBudgetInfoMapper;
import io.renren.modules.walk.dao.HnjbzUserGoldAccountMapper;
import io.renren.modules.walk.entity.HnjbzGoldBudgetInfo;
import io.renren.modules.walk.entity.HnjbzUserGoldAccount;
import io.renren.modules.walk.service.WalkGoldBudgetInService;
import io.renren.modules.walk.vo.ChannelUserVo;
import io.renren.modules.walk.vo.GoldBudgetInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class WalkGoldBudgetInServiceImpl extends ServiceImpl<HnjbzGoldBudgetInfoMapper, HnjbzGoldBudgetInfo> implements WalkGoldBudgetInService {
    @Autowired
    private HnjbzUserGoldAccountMapper userGoldAccountMapper;
    @Autowired
    private HnjbzChannelUserMapper channelUserMapper;

    @Override
    public R getGoldBudgetInfoList(GoldBudgetInfoVo goldBudgetInfoVo) {
        Page<HnjbzGoldBudgetInfo> page = new Page(goldBudgetInfoVo.getPage(),goldBudgetInfoVo.getPageSize());
        QueryWrapper<HnjbzGoldBudgetInfo> wrapper = new QueryWrapper<>();
        Integer count=null;
        //1收
        if ("1".equals(goldBudgetInfoVo.getBudgetFlag())){
            wrapper.eq("user_id",goldBudgetInfoVo.getUserId())
                    .eq("budget_flag","1")
                    .orderByDesc("grant_date");
            if(goldBudgetInfoVo.getIncomeType()!=null&&StringUtils.isEmpty(goldBudgetInfoVo.getIncomeType())){
                wrapper.eq("income_type",goldBudgetInfoVo.getIncomeType());
            }
             count = baseMapper.getGoldBudgetInCount(goldBudgetInfoVo.getUserId(),"1");
        }
        //支
        if ("2".equals(goldBudgetInfoVo.getBudgetFlag())){
            wrapper.eq("send_user_id",goldBudgetInfoVo.getUserId())
                    .eq("budget_flag","1")
                    .orderByDesc("grant_date");
            if(goldBudgetInfoVo.getIncomeType()!=null&&StringUtils.isEmpty(goldBudgetInfoVo.getIncomeType())){
                wrapper.eq("income_type",goldBudgetInfoVo.getIncomeType());
            }
             count = baseMapper.sendGoldBudgetInCount(goldBudgetInfoVo.getUserId(),"1");
        }
        Page<HnjbzGoldBudgetInfo> infoPage = baseMapper.selectPage(page, wrapper);
        for (HnjbzGoldBudgetInfo record : infoPage.getRecords()) {
            if (goldBudgetInfoVo.getBudgetFlag().equals("1")){
                ChannelUserVo channelUserVo = channelUserMapper.getUserName(record.getSendUserId());
                record.setName(channelUserVo==null?"兑换":channelUserVo.getName());
                record.setHeadimgurl(channelUserVo==null?"":channelUserVo.getHeadimgurl());
            }
            if (goldBudgetInfoVo.getBudgetFlag().equals("2")){
                ChannelUserVo channelUserVo = channelUserMapper.getUserName(record.getUserId());
                if(channelUserVo!=null){
                    record.setName(channelUserVo.getName());
                    record.setHeadimgurl(channelUserVo.getHeadimgurl());
                }
            }
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("records",infoPage.getRecords());
        map.put("current",infoPage.getCurrent());
        map.put("size",infoPage.getSize());
        map.put("total",infoPage.getTotal());
        map.put("count",count==null?"0":count);
        return R.ok().put("data",map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R sendGoldBudgetInfo(GoldBudgetInfoVo goldBudgetInfoVo) {
        //查询账号是否充足
        QueryWrapper<HnjbzUserGoldAccount> sendWrapper = new QueryWrapper<>();
        sendWrapper.eq("user_id",goldBudgetInfoVo.getUserId());
        HnjbzUserGoldAccount sendInfo = userGoldAccountMapper.selectOne(sendWrapper);
        if(sendInfo==null){
            sendInfo=new HnjbzUserGoldAccount();
            sendInfo.setUserId(goldBudgetInfoVo.getUserId());
            sendInfo.setFrontGoldAmount("0");
            sendInfo.setLaterGoldAmount("0");
            sendInfo.setStatus("0");
            userGoldAccountMapper.insert(sendInfo);
        }
        if (Integer.parseInt(sendInfo.getLaterGoldAmount())<Integer.parseInt(goldBudgetInfoVo.getBudgetAmount())){
            return R.error("账户余额不足");
        }
        //赠送前
        sendInfo.setFrontGoldAmount(sendInfo.getLaterGoldAmount());
        //赠送后
        sendInfo.setLaterGoldAmount(Integer.parseInt(sendInfo.getLaterGoldAmount())-Integer.parseInt(goldBudgetInfoVo.getBudgetAmount())+"");
        //更新赠送信息
        userGoldAccountMapper.update(sendInfo,sendWrapper);
        //插入工银消费金支出记录
        HnjbzGoldBudgetInfo sendBudgetInfo = new HnjbzGoldBudgetInfo();
        sendBudgetInfo.setBudgetAmount(goldBudgetInfoVo.getBudgetAmount());
        sendBudgetInfo.setBudgetFlag("2");
        sendBudgetInfo.setDisburseType("2");
        sendBudgetInfo.setStatus("0");
        sendBudgetInfo.setUuid(StringUtils.get32Guid());
        sendBudgetInfo.setUserId(goldBudgetInfoVo.getUserId());
        baseMapper.insert(sendBudgetInfo);

        //获取接收人用户信息
        QueryWrapper<HnjbzUserGoldAccount> acceptWrapper = new QueryWrapper<>();
        acceptWrapper.eq("user_id",goldBudgetInfoVo.getSendUserId());
        HnjbzUserGoldAccount acceptInfo = userGoldAccountMapper.selectOne(acceptWrapper);
        if(acceptInfo==null){
            acceptInfo=new HnjbzUserGoldAccount();
            acceptInfo.setUserId(goldBudgetInfoVo.getSendUserId());
            acceptInfo.setFrontGoldAmount("0");
            acceptInfo.setLaterGoldAmount("0");
            acceptInfo.setStatus("0");
            userGoldAccountMapper.insert(acceptInfo);
        }
        acceptInfo.setFrontGoldAmount(acceptInfo.getLaterGoldAmount());
        acceptInfo.setLaterGoldAmount(Integer.parseInt(acceptInfo.getLaterGoldAmount())+Integer.parseInt(goldBudgetInfoVo.getBudgetAmount())+"");
        userGoldAccountMapper.update(acceptInfo,acceptWrapper);
        //插入工银消费金收入记录
        HnjbzGoldBudgetInfo acceptBudgetInfo = new HnjbzGoldBudgetInfo();
        acceptBudgetInfo.setUuid(StringUtils.get32Guid());
        acceptBudgetInfo.setUserId(goldBudgetInfoVo.getSendUserId());
        acceptBudgetInfo.setBudgetAmount(goldBudgetInfoVo.getBudgetAmount());
        acceptBudgetInfo.setBudgetFlag("1");
        acceptBudgetInfo.setIncomeType("2");
        //接收用户
        acceptBudgetInfo.setSendUserId(goldBudgetInfoVo.getUserId());
        acceptBudgetInfo.setStatus("0");
        baseMapper.insert(acceptBudgetInfo);
        return R.ok();
    }
}
