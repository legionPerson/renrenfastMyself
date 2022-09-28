package io.renren.modules.walk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.common.utils.StringUtils;
import io.renren.modules.walk.dao.HnjbzGoldBudgetInfoMapper;
import io.renren.modules.walk.dao.HnjbzRightConInfoMapper;
import io.renren.modules.walk.dao.HnjbzUserGoldAccountMapper;
import io.renren.modules.walk.entity.HnjbzGoldBudgetInfo;
import io.renren.modules.walk.entity.HnjbzRightConInfo;
import io.renren.modules.walk.entity.HnjbzUserGoldAccount;
import io.renren.modules.walk.service.WalkUserGoldAccountService;
import io.renren.modules.walk.service.WeChatCouponsService;
import io.renren.modules.walk.vo.UserGoldAccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  用户消费金账户服务实现类
 * </p>
 *
 * @author wp
 * @since 2022-05-25
 */
@Slf4j
@Service
public class WalkUserGoldAccountServiceImpl extends ServiceImpl<HnjbzUserGoldAccountMapper, HnjbzUserGoldAccount> implements WalkUserGoldAccountService {
    @Autowired
    private HnjbzGoldBudgetInfoMapper hnjbzGoldBudgetInfoMapper;

    @Autowired
    private HnjbzRightConInfoMapper hnjbzRightConInfoMapper;

    @Autowired
    private WeChatCouponsService weChatCouponsService;


    @Override
    public R getUserGoldAccount(UserGoldAccountVo userGoldAccountVo) {
            QueryWrapper<HnjbzUserGoldAccount> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userGoldAccountVo.getUserId());
            HnjbzUserGoldAccount UserGoldAccount = baseMapper.selectOne(wrapper);
            //当前用户不存在工银消费金账户给默认值0
            String amount = UserGoldAccount==null?"0":UserGoldAccount.getLaterGoldAmount();
            return R.ok().put("data",amount);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R consumptionGold(UserGoldAccountVo userGoldAccountVo) {
            QueryWrapper<HnjbzUserGoldAccount> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userGoldAccountVo.getUserId());
            HnjbzUserGoldAccount userGoldAccount = baseMapper.selectOne(wrapper);
            if(userGoldAccount==null){
                userGoldAccount=new HnjbzUserGoldAccount();
                userGoldAccount.setUserId(userGoldAccountVo.getUserId());
                userGoldAccount.setFrontGoldAmount("0");
                userGoldAccount.setLaterGoldAmount("0");
                userGoldAccount.setStatus("0");
                baseMapper.insert(userGoldAccount);
            }
            String rightAmount = userGoldAccountVo.getRightAmount();
            //使用消费金数量
            int userGoldAmount = Integer.parseInt(userGoldAccountVo.getUserGoldAmount());
            //兑换优惠券数量
            int conAmount = userGoldAccountVo.getConAmount();
            rightAmount=Integer.parseInt(rightAmount)*conAmount+"";
            int totalMoney=userGoldAmount*conAmount;
            String laterGoldAmount = userGoldAccount.getLaterGoldAmount();
            int ilaterGoldAmount = Integer.parseInt(laterGoldAmount);
            if(ilaterGoldAmount>=totalMoney){
//                R r = weChatCouponsService.grantCoupons(userGoldAccountVo.getHnAppid(),
//                                                        userGoldAccountVo.getOrderId(),
//                                                        userGoldAccountVo.getStock_id(),
//                                                        userGoldAccountVo.getOpenId());
                ilaterGoldAmount-=totalMoney;
                userGoldAccount.setFrontGoldAmount(userGoldAccount.getLaterGoldAmount());
                userGoldAccount.setLaterGoldAmount(ilaterGoldAmount+"");
                //修改用户工行消费金账户
                baseMapper.update(userGoldAccount,wrapper);

                //插入工行消费金支账户
                HnjbzGoldBudgetInfo hnjbzGoldBudgetInfo = new HnjbzGoldBudgetInfo();
                hnjbzGoldBudgetInfo.setUuid(StringUtils.get32Guid());
                hnjbzGoldBudgetInfo.setUserId(userGoldAccountVo.getUserId());
                hnjbzGoldBudgetInfo.setBudgetAmount(totalMoney+"");
                hnjbzGoldBudgetInfo.setBudgetFlag("2");
                hnjbzGoldBudgetInfo.setDisburseType("1");
                hnjbzGoldBudgetInfo.setStatus("0");
                hnjbzGoldBudgetInfoMapper.insert(hnjbzGoldBudgetInfo);

                //插入工行消费金收账户
                HnjbzGoldBudgetInfo acceptBudgetInfo = new HnjbzGoldBudgetInfo();
                acceptBudgetInfo.setUuid(StringUtils.get32Guid());
                acceptBudgetInfo.setUserId("0");
                acceptBudgetInfo.setBudgetAmount(totalMoney+"");
                acceptBudgetInfo.setBudgetFlag("1");
                acceptBudgetInfo.setDisburseType("1");
                //接收用户
                acceptBudgetInfo.setSendUserId(userGoldAccountVo.getUserId());
                acceptBudgetInfo.setStatus("0");
                hnjbzGoldBudgetInfoMapper.insert(acceptBudgetInfo);

                //插入权益兑换记录表
                HnjbzRightConInfo rightConInfo = new HnjbzRightConInfo();
                rightConInfo.setUuid(StringUtils.get32Guid());
                rightConInfo.setGoldBudgetId(hnjbzGoldBudgetInfo.getUuid());
                rightConInfo.setUserId(userGoldAccountVo.getUserId());
                rightConInfo.setConRightType(userGoldAccountVo.getRightType());
                rightConInfo.setConRightId(userGoldAccountVo.getRightId());
                rightConInfo.setConAmount(conAmount+"");
                rightConInfo.setConSingleMoney(userGoldAmount+"");
                rightConInfo.setConGrossMoney(rightAmount);
                rightConInfo.setUserGoldAmount(totalMoney+"");
                rightConInfo.setStatus("0");
                hnjbzRightConInfoMapper.insert(rightConInfo);
                return R.ok("兑换成功");
            }
            return R.error("账户余额不足");
    }
}
