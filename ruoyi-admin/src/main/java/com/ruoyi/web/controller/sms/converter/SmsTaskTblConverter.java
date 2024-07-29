package com.ruoyi.web.controller.sms.converter;

import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.domain.vo.SmsTaskTblPreSummaryVO;
import com.ruoyi.common.core.domain.vo.SmsTaskTblSummaryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SmsTaskTblConverter {

    SmsTaskTblConverter INSTANCE = Mappers.getMapper(SmsTaskTblConverter.class);

    SmsTaskTblPreSummaryVO to(SmsTaskTbl smsTaskTbl);


    List<SmsTaskTblPreSummaryVO> to(List<SmsTaskTbl> smsTaskTblList);

    SmsTaskTblSummaryVO toSmsTaskTblSummaryVO(SmsTaskTbl smsTaskTbl);
    List<SmsTaskTblSummaryVO> toSmsTaskTblSummaryVOList(List<SmsTaskTbl> smsTaskTblList);
}
