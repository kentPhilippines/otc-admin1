package com.ruoyi.system.api;

import com.ruoyi.common.core.domain.entity.TgPremiumOrderInfo;
import com.ruoyi.system.api.entity.fragment.*;

import java.io.IOException;
import java.math.BigDecimal;

public interface IFragmentApi {
    SearchPremiumGiftRecipientResponse searchPremiumGiftRecipient(TgPremiumOrderInfo tgPremiumOrderInfo) throws IOException;

    InitGiftPremiumResponse initGiftPremium(String recipient, Long months) throws IOException;

    GetGiftPremiumLinkResponse getGiftPremiumLink(String reqId) throws IOException;

    RawResponse rawRequest(String reqId) throws IOException;

    void sendTransactions(String address, BigDecimal amount, String comment) throws IOException;

    CheckReqRespose checkReq(String reqId) throws IOException;
}
