package com.ruoyi.system.service.impl;


import com.google.common.base.Preconditions;
import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;
import com.ruoyi.common.core.domain.vo.TgPremiumOrderInfoMultiVO;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.handler.TgPremiumTransferHandler;
import com.ruoyi.system.mapper.TgPremiumOrderInfoMapper;
import com.ruoyi.system.service.ITgPremiumOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ruoyi.common.constant.Constants.MONTHS;

/**
 * TG会员充值Service业务层处理
 *
 * @author dorion
 * @date 2024-05-04
 */
@Service
@Slf4j
public class TgPremiumOrderInfoServiceImpl implements ITgPremiumOrderInfoService {
    @Autowired
    private TgPremiumOrderInfoMapper tgPremiumOrderInfoMapper;
    @Autowired
    private TgPremiumTransferHandler tgPremiumTransferHandler;

    /**
     * 查询TG会员充值
     *
     * @param idTgPremiumOrderInfo TG会员充值主键
     * @return TG会员充值
     */
    @Override
    public TgPremiumOrderInfo selectTgPremiumOrderInfoByIdTgPremiumOrderInfo(Long idTgPremiumOrderInfo) {
        return tgPremiumOrderInfoMapper.selectTgPremiumOrderInfoByIdTgPremiumOrderInfo(idTgPremiumOrderInfo);
    }

    /**
     * 查询TG会员充值列表
     *
     * @param tgPremiumOrderInfo TG会员充值
     * @return TG会员充值
     */
    @Override
    public List<TgPremiumOrderInfo> selectTgPremiumOrderInfoList(TgPremiumOrderInfo tgPremiumOrderInfo) {
        return tgPremiumOrderInfoMapper.selectTgPremiumOrderInfoList(tgPremiumOrderInfo);
    }

    /**
     * 新增TG会员充值
     *
     * @param tgPremiumOrderInfo TG会员充值
     * @return 结果
     */
    @Override
    public int insertTgPremiumOrderInfo(TgPremiumOrderInfo tgPremiumOrderInfo) {
        return tgPremiumOrderInfoMapper.insertTgPremiumOrderInfo(tgPremiumOrderInfo);
    }

    /**
     * 修改TG会员充值
     *
     * @param tgPremiumOrderInfo TG会员充值
     * @return 结果
     */
    @Override
    public int updateTgPremiumOrderInfo(TgPremiumOrderInfo tgPremiumOrderInfo) {
        Long userId = ShiroUtils.getUserId();
        tgPremiumOrderInfo.setLcu(userId.toString());
        return tgPremiumOrderInfoMapper.updateTgPremiumOrderInfo(tgPremiumOrderInfo);
    }

    /**
     * 批量删除TG会员充值
     *
     * @param idTgPremiumOrderInfos 需要删除的TG会员充值主键
     * @return 结果
     */
    @Override
    public int deleteTgPremiumOrderInfoByIdTgPremiumOrderInfos(String idTgPremiumOrderInfos) {
        return tgPremiumOrderInfoMapper.deleteTgPremiumOrderInfoByIdTgPremiumOrderInfos(Convert.toStrArray(idTgPremiumOrderInfos));
    }

    /**
     * 删除TG会员充值信息
     *
     * @param idTgPremiumOrderInfo TG会员充值主键
     * @return 结果
     */
    @Override
    public int deleteTgPremiumOrderInfoByIdTgPremiumOrderInfo(Long idTgPremiumOrderInfo) {
        return tgPremiumOrderInfoMapper.deleteTgPremiumOrderInfoByIdTgPremiumOrderInfo(idTgPremiumOrderInfo);
    }

    @Override
    public int insertTgPremiumOrderInfoMultiVO(TgPremiumOrderInfoMultiVO tgPremiumOrderInfoMultiVO) {

        log.info("tgPremiumOrderInfoMultiVO:{}", tgPremiumOrderInfoMultiVO);

        String[] tgPremiumOrderInfoArray = tgPremiumOrderInfoMultiVO.getTgPremiumOrderInfo().split("\r\n");
        int count = 1;
        String loginName = ShiroUtils.getLoginName();
        List<TgPremiumOrderInfo> tgPremiumOrderInfoList = new ArrayList<>();


        for (String tgPremiumOrderInfoString : tgPremiumOrderInfoArray) {
            String[] split = tgPremiumOrderInfoString.split(",");
            Preconditions.checkState(split.length == 2, "第" + count + "行，请输入正确的格式[tgUserName],[months]");
            TgPremiumOrderInfo tgPremiumOrderInfo = new TgPremiumOrderInfo();
            tgPremiumOrderInfo.setRechargeTgUserName(split[0]);
            Preconditions.checkState(MONTHS.contains(split[1]), "第" + count + "行，请输入正确的月数[3,6,12]");
            tgPremiumOrderInfo.setMonths(Long.valueOf(split[1]));
            tgPremiumOrderInfo.setFcd(new Date());
            tgPremiumOrderInfo.setLcd(new Date());
            tgPremiumOrderInfo.setFcu(loginName);
            tgPremiumOrderInfo.setLcu(loginName);
            tgPremiumOrderInfo.setTgPaymentStatus("N");

            tgPremiumOrderInfoList.add(tgPremiumOrderInfo);
            count++;
        }

        if (tgPremiumOrderInfoList.size() > 0) {
            for (TgPremiumOrderInfo tgPremiumOrderInfo : tgPremiumOrderInfoList) {
                tgPremiumOrderInfoMapper.insertTgPremiumOrderInfo(tgPremiumOrderInfo);
            }


            if ("Y".equals(tgPremiumOrderInfoMultiVO.getIsJoindNow())) {
                for (TgPremiumOrderInfo tgPremiumOrderInfo : tgPremiumOrderInfoList) {
                    tgPremiumTransferHandler.doRechargeAndUpdate(tgPremiumOrderInfo);
                   /* tgPremiumOrderInfo.setTgPaymentStatus("N");
                    if (amount != null){
                        tgPremiumOrderInfo.setTgPaymentStatus("Y");
                        tgPremiumOrderInfo.setActualAmount(BigDecimal.valueOf(amount).movePointLeft(9));
                    }
                    tgPremiumOrderInfoMapper.updateTgPremiumOrderInfo(tgPremiumOrderInfo);*/
                }
            }
        }
        return 1;
    }

    @Override
    public int activeDataByIdTgPremiumOrderInfos(String ids) {
        String[] idsArray = Convert.toStrArray(ids);

        for (String id : idsArray) {
            TgPremiumOrderInfo tgPremiumOrderInfo = tgPremiumOrderInfoMapper.selectTgPremiumOrderInfoByIdTgPremiumOrderInfo(Long.valueOf(id));
            Preconditions.checkState(!"Y".equals(tgPremiumOrderInfo.getTgPaymentStatus()), tgPremiumOrderInfo.getRechargeTgUserName() + "该用户已经开通,请勿重复开通");
            tgPremiumTransferHandler.doRechargeAndUpdate(tgPremiumOrderInfo);
        }
        return 1;
    }


}
