package com.ruoyi.web.controller.sms.converter;

import com.ruoyi.common.core.domain.entity.SmsTaskTbl;
import com.ruoyi.common.core.domain.vo.SmsTaskTblVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface SmsTaskTblVOConverter {

    SmsTaskTblVOConverter INSTANCE = Mappers.getMapper(SmsTaskTblVOConverter.class);

    @Mapping(target = "fileName",ignore = true)
    @Mapping(target = "taskName",ignore = true)
    @Mapping(target = "context",ignore = true)
    SmsTaskTbl to(SmsTaskTblVO smsTaskTblVO);
}
