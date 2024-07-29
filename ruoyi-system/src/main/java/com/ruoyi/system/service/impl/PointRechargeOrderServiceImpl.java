package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Preconditions;
import com.ruoyi.common.core.domain.entity.PointRechargeOrder;
import com.ruoyi.common.core.domain.entity.UserPoint;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.SnowFlakeUtil;
import com.ruoyi.system.mapper.PointRechargeOrderMapper;
import com.ruoyi.system.mapper.UserPointMapper;
import com.ruoyi.system.service.IPointRechargeOrderService;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

/**
 * 群发充值管理Service业务层处理
 *
 * @author dorion
 * @date 2024-07-21
 */
@Service
public class PointRechargeOrderServiceImpl implements IPointRechargeOrderService {
    @Autowired
    private PointRechargeOrderMapper pointRechargeOrderMapper;

    @Autowired
    private UserPointMapper userPointMapper;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private SnowFlakeUtil snowFlakeUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询群发充值管理
     *
     * @param idPointRechargeOrder 群发充值管理主键
     * @return 群发充值管理
     */
    @Override
    public PointRechargeOrder selectPointRechargeOrderByIdPointRechargeOrder(Long idPointRechargeOrder) {
        return pointRechargeOrderMapper.selectPointRechargeOrderByIdPointRechargeOrder(idPointRechargeOrder);
    }

    /**
     * 查询群发充值管理列表
     *
     * @param pointRechargeOrder 群发充值管理
     * @return 群发充值管理
     */
    @Override
    public List<PointRechargeOrder> selectPointRechargeOrderList(PointRechargeOrder pointRechargeOrder) {
        return pointRechargeOrderMapper.selectPointRechargeOrderList(pointRechargeOrder);
    }

    /**
     * 新增群发充值管理
     *
     * @param pointRechargeOrder 群发充值管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPointRechargeOrder(PointRechargeOrder pointRechargeOrder) {
        setPointsAndExchangeRate(pointRechargeOrder);

        setOrderNo(pointRechargeOrder);

        String loginName = ShiroUtils.getLoginName();
        pointRechargeOrder.setCreateBy(loginName);
        pointRechargeOrder.setUpdateBy(loginName);
        int i = pointRechargeOrderMapper.insertPointRechargeOrder(pointRechargeOrder);

        String status = pointRechargeOrder.getStatus();
        if (!"N".equals(status)) {
            createOrUpdateUserPoints(pointRechargeOrder);
        }


        return i;
    }

    private void setOrderNo(PointRechargeOrder pointRechargeOrder) {
        Long orderNumber = snowFlakeUtil.nextId();
        pointRechargeOrder.setOrderNumber(orderNumber.toString());
    }

    private void setPointsAndExchangeRate(PointRechargeOrder pointRechargeOrder) {
        String sysSmsExchangeRate = configService.selectConfigByKey("sys.sms.exchange.rate");

        BigDecimal amount = pointRechargeOrder.getAmount();
        BigInteger bigInteger = amount.toBigInteger();
        BigDecimal calcAmount = new BigDecimal(bigInteger);

        BigDecimal exchangeRate = new BigDecimal(sysSmsExchangeRate);
        BigDecimal points = calcAmount.multiply(exchangeRate);
        pointRechargeOrder.setPoints(points);
        pointRechargeOrder.setExchangeRate(exchangeRate);
    }

    private void createOrUpdateUserPoints(PointRechargeOrder pointRechargeOrder) {
        UserPoint userPoint = new UserPoint();
        userPoint.setUserId(pointRechargeOrder.getUserId());
        List<UserPoint> userPoints = userPointMapper.selectUserPointList(userPoint);
        if (CollectionUtil.isNotEmpty(userPoints)) {
            UserPoint userPointResult = userPoints.get(0);
            BigDecimal pointBalance = userPointResult.getPointBalance();
            BigDecimal add = pointBalance.add(pointRechargeOrder.getPoints());
            userPointResult.setPointBalance(add);
            userPointMapper.updateUserPoint(userPointResult);
        } else {
            userPoint.setPointBalance(pointRechargeOrder.getPoints());
            userPointMapper.insertUserPoint(userPoint);
        }
    }

    /**
     * 修改群发充值管理
     *
     * @param pointRechargeOrder 群发充值管理
     * @return 结果
     */
    @Override
    public int updatePointRechargeOrder(PointRechargeOrder pointRechargeOrder) {
        String loginName = ShiroUtils.getLoginName();
        pointRechargeOrder.setUpdateBy(loginName);
        String status = pointRechargeOrder.getStatus();
        if (!"N".equals(status)) {
            PointRechargeOrder pointRechargeOrderDetail = pointRechargeOrderMapper.selectPointRechargeOrderByIdPointRechargeOrder(pointRechargeOrder.getIdPointRechargeOrder());
            if (!pointRechargeOrderDetail.getStatus().equals(pointRechargeOrder.getStatus())) {
                //状态发生变化了再更新
                createOrUpdateUserPoints(pointRechargeOrderDetail);
            }
        }
        return pointRechargeOrderMapper.updatePointRechargeOrder(pointRechargeOrder);
    }

    /**
     * 批量删除群发充值管理
     *
     * @param idPointRechargeOrders 需要删除的群发充值管理主键
     * @return 结果
     */
    @Override
    public int deletePointRechargeOrderByIdPointRechargeOrders(String idPointRechargeOrders) {
        return pointRechargeOrderMapper.deletePointRechargeOrderByIdPointRechargeOrders(Convert.toStrArray(idPointRechargeOrders));
    }

    /**
     * 删除群发充值管理信息
     *
     * @param idPointRechargeOrder 群发充值管理主键
     * @return 结果
     */
    @Override
    public int deletePointRechargeOrderByIdPointRechargeOrder(Long idPointRechargeOrder) {
        return pointRechargeOrderMapper.deletePointRechargeOrderByIdPointRechargeOrder(idPointRechargeOrder);
    }

    @Override
    public Long rechargeOrder(PointRechargeOrder pointRechargeOrder) {

        Long userId = ShiroUtils.getSysUser().getUserId();
        PointRechargeOrder pointRechargeOrderParam = new PointRechargeOrder();
        pointRechargeOrderParam.setUserId(userId);
        pointRechargeOrderParam.setStatus("N");
        List<PointRechargeOrder> pointRechargeOrders = pointRechargeOrderMapper.selectPointRechargeOrderList(pointRechargeOrderParam);
        Preconditions.checkState(pointRechargeOrders.size() <=3, "每个用户1小时内最多只能创建3个充值订单");

        PointRechargeOrder pointRechargeOrderExist = pointRechargeOrders.stream().filter(pointRechargeOrderTemp -> {
            BigInteger amountOld = pointRechargeOrderTemp.getAmount().toBigInteger();
            return amountOld.equals(pointRechargeOrder.getAmount().toBigInteger());
        }).findFirst().orElse(null);

        if (pointRechargeOrderExist != null){
            return pointRechargeOrderExist.getIdPointRechargeOrder();
        }

        setPointsAndExchangeRate(pointRechargeOrder);
        Boolean hasKey = true;
        BigDecimal finalAmout;
        do {
            finalAmout = getFinalAmout(pointRechargeOrder);
            hasKey = redisTemplate.hasKey("sms_recharge_amount_" + finalAmout);
        } while (Boolean.TRUE.equals(hasKey));


        pointRechargeOrder.setAmount(finalAmout);

        setOrderNo(pointRechargeOrder);
//        Long userId = ShiroUtils.getSysUser().getUserId();
        pointRechargeOrder.setUserId(userId);
        String loginName = ShiroUtils.getLoginName();
        pointRechargeOrder.setCreateBy(loginName);
        pointRechargeOrder.setUpdateBy(loginName);
        pointRechargeOrder.setStatus("N");
        pointRechargeOrderMapper.insertPointRechargeOrder(pointRechargeOrder);
        return pointRechargeOrder.getIdPointRechargeOrder();
    }

    private static BigDecimal getFinalAmout(PointRechargeOrder pointRechargeOrder) {
        BigDecimal amount = pointRechargeOrder.getAmount();
        Random random = new Random();
        int randomInt = random.nextInt(100);
        BigDecimal finalAmout = BigDecimal.valueOf(randomInt).divide(BigDecimal.valueOf(100)).add(amount);
        return finalAmout;
    }
}
